<#
.SYNOPSIS
    Zips the UnMock4j generated sources and the participant's versions of them.

.DESCRIPTION
    Run it after check-gen.ps1 (and after fixing the copied files, if needed). It asks
    "check-gen.ps1 -DryRun" which files were copied, then writes unmock-<UnitTest>.zip in the
    unmock4j root with two folders:
      original\  the files UnMock generated (<gen-dir>\<package path>\*.java)
      modified\  the same files as they are now in the target project
    The zip is built with the JDK's "jar" tool, which every participant already has.

.EXAMPLE
    .\scripts\pack-gen.ps1 -Repo C:\repos\dolphinscheduler
#>
param(
    [Parameter(Mandatory = $true)][string]$Repo,
    [string]$UnitTest,
    [string]$GenDir,
    [string]$Module,
    [string]$JavaHome
)

$ErrorActionPreference = 'Stop'

# --- 1. Copied files, as check-gen.ps1 resolves them --------------------------------------
$checkArgs = @{ Repo = $Repo; DryRun = $true }
foreach ($k in 'UnitTest', 'GenDir', 'Module', 'JavaHome') {
    if ($PSBoundParameters.ContainsKey($k)) { $checkArgs[$k] = $PSBoundParameters[$k] }
}
$info = & (Join-Path $PSScriptRoot 'check-gen.ps1') @checkArgs 6>&1 | ForEach-Object { "$_" }

$testClass = ($info | Select-String '^Unit test : .*\.(\w+)$').Matches.Groups[1].Value
if (-not $testClass) { $info | Write-Host; throw 'check-gen.ps1 -DryRun did not report the test class.' }
$copies = $info | Select-String '^copy (.+) -> (.+)$'
if (-not $copies) { $info | Write-Host; throw 'check-gen.ps1 -DryRun listed no generated files.' }

# jar from the JDK check-gen picked: Windows JDK installers often leave its bin\ off PATH
$jdk = ($info | Select-String '^Java\s+: (?:.* -> |.*JAVA_HOME=)?(.+?)(?: \(-JavaHome\))?$').Matches.Groups[1].Value
$jar = if ($jdk -and (Test-Path "$jdk\bin\jar.exe")) { "$jdk\bin\jar.exe" } else { 'jar' }
if (-not (Get-Command $jar -ErrorAction SilentlyContinue)) { throw "jar not found in the JDK ($jdk) nor on PATH. Pass -JavaHome <jdk dir>." }

$zip = Join-Path (Split-Path $PSScriptRoot -Parent) "unmock-$testClass.zip"
$stage = Join-Path ([IO.Path]::GetTempPath()) ([IO.Path]::GetRandomFileName())
New-Item -ItemType Directory -Path "$stage\original", "$stage\modified" | Out-Null

try {
    # --- 2. Stage original\ and modified\ --------------------------------------------------
    foreach ($c in $copies) {
        $src = $c.Matches.Groups[1].Value
        $dest = $c.Matches.Groups[2].Value
        Copy-Item $src "$stage\original\"
        if (Test-Path $dest) {
            Copy-Item $dest "$stage\modified\"
            Write-Host "added $(Split-Path $src -Leaf)"
        } else {
            Write-Warning "$dest not found (not copied by check-gen, or deleted); only the original is packed."
        }
    }

    # --- 3. Zip ----------------------------------------------------------------------------
    # jar, not Compress-Archive: Windows PowerShell 5 writes "\" in entry names
    if (Test-Path $zip) { Remove-Item $zip }
    & $jar -cMf $zip -C $stage original -C $stage modified
    if ($LASTEXITCODE -ne 0) { throw "jar failed with exit code $LASTEXITCODE" }
    Write-Host ""
    Write-Host "Zip       : $zip"
} finally {
    Remove-Item $stage -Recurse -Force
}
