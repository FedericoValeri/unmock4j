<#
.SYNOPSIS
    Copies the UnMock4j generated sources into the cloned target project and checks that they compile.

.DESCRIPTION
    Reads the unit test package and class name from UNIT.txt, finds the module of the cloned
    repository that contains the original test, copies <GenDir>/<package path>/*.java into that
    module's src/test/java/<package path>/ and runs "mvn clean test-compile -pl <module> -am".
    Copied paths are recorded in <Repo>/.unmock-copied.txt so that -Clean can remove them.
    The build runs on the lowest installed JDK that satisfies the Java version declared in the
    repository's root pom.xml. Installed JDKs are discovered by maven-toolchains-plugin 3.2.0
    plus ~\.m2\toolchains.xml; -JavaHome overrides the choice.

.EXAMPLE
    .\scripts\check-gen.ps1 -Repo C:\repos\shenyu

.EXAMPLE
    .\scripts\check-gen.ps1 -Repo C:\repos\shenyu -GenDir r3 `
        -UnitTest src\main\resources\prompts\readyToUse\human\shenyu-LdapConfigurationTest\UNIT.txt -DryRun

.EXAMPLE
    .\scripts\check-gen.ps1 -Repo C:\repos\shenyu -Clean
#>
param(
    [Parameter(Mandatory = $true)][string]$Repo,
    [string]$UnitTest,
    [string]$GenDir,
    [string]$Module,
    [string]$JavaHome,
    [switch]$DryRun,
    [switch]$Clean
)

$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $PSScriptRoot
if (-not $UnitTest) { $UnitTest = Join-Path $root 'src\main\resources\prompts\human\UNIT.txt' }
if (-not $GenDir) { $GenDir = Join-Path $root 'gen' }
if (-not [IO.Path]::IsPathRooted($GenDir)) { $GenDir = Join-Path (Get-Location) $GenDir }

$Repo = (Resolve-Path $Repo).Path.TrimEnd('\')   # tab completion adds '\', which breaks the Substring calls below
$manifest = Join-Path $Repo '.unmock-copied.txt'

# --- Clean mode: remove files copied by a previous run -------------------------------------
if ($Clean) {
    if (-not (Test-Path $manifest)) { Write-Host "Nothing to clean: $manifest not found."; exit 0 }
    foreach ($f in Get-Content $manifest) {
        if ($f -and (Test-Path $f)) { Remove-Item $f; Write-Host "removed $f" }
    }
    Remove-Item $manifest
    exit 0
}

# --- 1. Package and test class (same regex as FullClassNamesExtractor) ---------------------
$source = Get-Content -Raw $UnitTest
$m = [regex]::Match($source, 'package\s+([\w.]+)\s*;.*?\b(?:interface|class)\s+(\w+)',
        [Text.RegularExpressions.RegexOptions]::Singleline)
if (-not $m.Success) { throw "Cannot find package/class declaration in $UnitTest" }
$package = $m.Groups[1].Value
$testClass = $m.Groups[2].Value
$packagePath = $package.Replace('.', '\')
Write-Host "Unit test : $package.$testClass"

# --- 2. Generated sources for that package -------------------------------------------------
$genPackageDir = Join-Path $GenDir $packagePath
$sources = @(Get-ChildItem -Path $genPackageDir -Filter *.java -File -ErrorAction SilentlyContinue)
if ($sources.Count -eq 0) { throw "No generated .java files in $genPackageDir" }

# --- 3. Module that contains the original test ---------------------------------------------
function Get-ModuleDir([string]$path) {
    $idx = $path.IndexOf('\src\test\java\')
    return $path.Substring(0, $idx)
}

$excluded = '\\(target|node_modules|\.git)\\'
if ($Module) {
    $moduleDir = (Resolve-Path (Join-Path $Repo $Module)).Path
} else {
    $candidates = @(Get-ChildItem -Path $Repo -Recurse -File -Filter "$testClass.java" -ErrorAction SilentlyContinue |
            Where-Object { $_.FullName -notmatch $excluded -and $_.FullName.EndsWith("\src\test\java\$packagePath\$testClass.java") } |
            ForEach-Object { Get-ModuleDir $_.FullName })

    if ($candidates.Count -eq 0) {
        # Fallback: any module whose test sources already contain the package
        $candidates = @(Get-ChildItem -Path $Repo -Recurse -Directory -ErrorAction SilentlyContinue |
                Where-Object { $_.FullName -notmatch $excluded -and $_.FullName.EndsWith("\src\test\java\$packagePath") } |
                ForEach-Object { Get-ModuleDir $_.FullName } |
                Sort-Object { $_.Length } -Descending)
    }
    $candidates = @($candidates | Select-Object -Unique)

    if ($candidates.Count -eq 0) { throw "No module in $Repo contains src\test\java\$packagePath. Use -Module <relative path>." }
    if ($candidates.Count -gt 1) {
        Write-Host "Several modules match:"
        $candidates | ForEach-Object { Write-Host "  $($_.Substring($Repo.Length).TrimStart('\'))" }
        throw "Ambiguous module. Rerun with -Module <relative path>."
    }
    $moduleDir = $candidates[0]
}

$moduleRel = $moduleDir.Substring($Repo.Length).TrimStart('\').Replace('\', '/')
$targetDir = Join-Path $moduleDir "src\test\java\$packagePath"
Write-Host "Module    : $(if ($moduleRel) { $moduleRel } else { '(root)' })"
Write-Host "Target    : $targetDir"

# --- 4. Copy -------------------------------------------------------------------------------
$copied = @()
foreach ($s in $sources) {
    $dest = Join-Path $targetDir $s.Name
    Write-Host "copy $($s.FullName) -> $dest"
    if (-not $DryRun) {
        New-Item -ItemType Directory -Force -Path $targetDir | Out-Null
        Copy-Item -Force $s.FullName $dest
    }
    $copied += $dest
}
if (-not $DryRun) {
    $previous = @()
    if (Test-Path $manifest) { $previous = @(Get-Content $manifest) }
    ($previous + $copied) | Select-Object -Unique | Set-Content $manifest
}

# --- 5. JDK required by the target project -------------------------------------------------
function Get-JavaMajor([string]$v) {
    if ($v -match '^1\.(\d+)') { return [int]$Matches[1] }   # 1.8 -> 8
    if ($v -match '^(\d+)') { return [int]$Matches[1] }
    return $null
}

if ($JavaHome) {
    $JavaHome = (Resolve-Path $JavaHome).Path
    Write-Host "Java      : $JavaHome (-JavaHome)"
} else {
    # Highest version wins: sentinel compiles module-info with release 17 and the rest with 8
    $pomFile = Join-Path $Repo 'pom.xml'
    $pom = if (Test-Path $pomFile) { Get-Content -Raw $pomFile } else { '' }
    $tags = 'java\.version|jdk\.version|maven\.compiler\.(?:release|source|target)|release|source|target'
    $required = ([regex]::Matches($pom, "<(?:$tags)>\s*([\d.]+)\s*</") |
            ForEach-Object { Get-JavaMajor $_.Groups[1].Value } | Measure-Object -Maximum).Maximum

    if (-not $required) {
        Write-Host "Java      : not declared in pom.xml, using JAVA_HOME=$env:JAVA_HOME"
    } else {
        # Maven finds the JDKs installed on this machine (JAVA_HOME, JAVAxx_HOME, ~/.jdks, SDKMAN,
        # /usr/lib/jvm, Program Files, ...); the user's ~/.m2/toolchains.xml adds declared ones
        $discovered = [IO.Path]::GetTempFileName()
        Push-Location ([IO.Path]::GetTempPath())   # away from the repo pom, which Maven would load
        $ErrorActionPreference = 'Continue'
        & mvn -B -q 'org.apache.maven.plugins:maven-toolchains-plugin:3.2.0:generate-jdk-toolchains-xml' "-Dtoolchain.file=$discovered" 2>&1 | Out-Null
        $discoveryExit = $LASTEXITCODE
        $ErrorActionPreference = 'Stop'
        Pop-Location
        if ($discoveryExit -ne 0) { Write-Warning "JDK discovery failed (mvn exit $discoveryExit)." }

        $xml = (@($discovered, "$HOME\.m2\toolchains.xml") | Where-Object { Test-Path $_ } |
                ForEach-Object { Get-Content -Raw $_ }) -join "`n"
        Remove-Item $discovered -ErrorAction SilentlyContinue
        $jdks = @([regex]::Matches($xml, '(?s)<toolchain>.*?</toolchain>') | ForEach-Object {
            $v = [regex]::Match($_.Value, '<version>\s*([^<]+?)\s*</version>').Groups[1].Value
            $h = [regex]::Match($_.Value, '<jdkHome>\s*([^<]+?)\s*</jdkHome>').Groups[1].Value
            # Version ranges and ${...} in hand-written files give no major and are skipped
            $major = Get-JavaMajor $v
            if ($major -and $h -and (Test-Path $h)) { [pscustomobject]@{ Home = $h; Major = $major } }
        })
        # Lowest installed JDK that is >= the required one: old Lombok breaks on newer JDKs
        $pick = $jdks | Where-Object { $_.Major -ge $required } | Sort-Object Major | Select-Object -First 1

        if (-not $pick) {
            $found = ($jdks | ForEach-Object { $_.Major } | Sort-Object -Unique) -join ', '
            throw "pom.xml requires Java $required but no installed JDK >= $required was found (found: $(if ($found) { $found } else { 'none' })). Install JDK $required or pass -JavaHome <dir>."
        }
        $JavaHome = $pick.Home
        Write-Host "Java      : $required required by pom.xml -> $JavaHome"
    }
}

# --- 6. Build ------------------------------------------------------------------------------
$mvnArgs = @('-B', 'clean', 'test-compile')
if ($moduleRel) {
    # Modules that compile C/C++ (org.codehaus.mojo:native-maven-plugin, e.g. arthas-vmtool) need g++;
    # take their released jar from the Maven repository instead of building them. Not GraalVM's
    # native-maven-plugin (nacos console): Maven rejects '!x' when x is outside the -am set
    $native = @(Get-ChildItem -Path $Repo -Recurse -File -Filter pom.xml -ErrorAction SilentlyContinue |
            Where-Object { $_.FullName -notmatch $excluded -and $_.DirectoryName -ne $Repo -and $_.DirectoryName -ne $moduleDir } |
            Where-Object { (Get-Content -Raw $_.FullName) -match 'org\.codehaus\.mojo</groupId>\s*<artifactId>native-maven-plugin' } |
            ForEach-Object { '!' + $_.DirectoryName.Substring($Repo.Length).TrimStart('\').Replace('\', '/') })
    $mvnArgs += @('-pl', ((@($moduleRel) + $native) -join ','), '-am')
}
$mvnArgs += @(
    '-DskipTests', '-Dcheckstyle.skip=true', '-Drat.skip=true',
    '-Dspotless.check.skip=true', '-Dspotless.apply.skip=true',
    '-Dmaven.javadoc.skip=true', '-Djacoco.skip=true'
)
Write-Host ""
$quotedArgs = ($mvnArgs | ForEach-Object { '"' + $_ + '"' }) -join ' '
$envPrefix = if ($JavaHome) { "`$env:JAVA_HOME=`"$JavaHome`"; " } else { '' }
Write-Host "${envPrefix}cd `"$Repo`"; mvn $quotedArgs"
if ($DryRun) { exit 0 }

Push-Location $Repo
$oldJavaHome = $env:JAVA_HOME
if ($JavaHome) { $env:JAVA_HOME = $JavaHome }   # mvn runs on JAVA_HOME, and javac comes from that JVM
# Windows PowerShell 5.1 turns native stderr lines into errors, which 'Stop' would make fatal
$ErrorActionPreference = 'Continue'
try {
    & mvn @mvnArgs 2>&1 | Tee-Object -Variable mvnOutput | Out-Host
    $exitCode = $LASTEXITCODE
} finally {
    $env:JAVA_HOME = $oldJavaHome
    Pop-Location
}

# --- 7. Report -----------------------------------------------------------------------------
Write-Host ""
Write-Host "mvn exit code: $exitCode"
$names = $sources | ForEach-Object { [regex]::Escape($_.Name) }
$errors = @($mvnOutput | ForEach-Object { "$_" } |
        Where-Object { $_ -match '\[ERROR\]' -and $_ -match ($names -join '|') })
if ($errors.Count -gt 0) {
    Write-Host "Compile errors in generated files:"
    $errors | ForEach-Object { Write-Host "  $_" }
} elseif ($exitCode -eq 0) {
    Write-Host "Generated files compile."
}
exit $exitCode
