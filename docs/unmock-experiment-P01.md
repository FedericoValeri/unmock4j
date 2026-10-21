# UnMock experiment - P01

## Objective

Unit tests often replace the real dependencies of a class with mocks (Mockito `@Mock`, `when(...)`, `verify(...)`). Each
mock encodes an assumption about how the real dependency behaves, but the unit test never checks that assumption against
the real code. UnMock uses an LLM to turn such a unit test into an integration test: the mocks are replaced by proxies
that call the real dependencies and still check what the original test expected.
In this session you will use UnMock on real unit tests from given open-source projects, check the generated integration
test and fix it where needed. Afterward you will fill in a short questionnaire about your experience.
We are evaluating the tool, not you. There are no right or wrong outcomes: if the generated code needs many changes, or
if you cannot get it to work, that is valuable information for us.

## Target projects

- [dolphinscheduler](https://github.com/apache/dolphinscheduler/releases#release-3.4.1):
    - **SUT.txt** (TODO: replace with download links)
    - **UNIT.txt** (TODO: replace with download links)
    - **DEPENDENCIES.txt** (TODO: replace with download links)
- [nacos](https://github.com/alibaba/nacos/releases#release-3.2.1):
    - **SUT.txt** (TODO: replace with download links)
    - **UNIT.txt** (TODO: replace with download links)
    - **DEPENDENCIES.txt** (TODO: replace with download links)

Download locally the target open source projects in a directory of your choice through the provided links. Also download
the related `SUT.txt`, `UNIT.txt`, `DEPENDENCIES.txt` files. These files are the input UnMock needs to turn a
unit test into an integration test:

- **UNIT.txt**: the unit test to transform. A JUnit test class that uses Mockito mocks (`@Mock`, `when(...)`,
  `verify(...)`). It is the main input: UnMock reads which dependencies are mocked, how they are expected to behave, and
  which interactions are checked, and turns these into checks against the real dependencies.
- **SUT.txt**: the system under test, i.e. the production class that the unit test exercises (its package and class
  declaration). It tells UnMock which class the generated integration test targets.
- **DEPENDENCIES.txt**: the source code of the dependencies that are mocked in UNIT.txt (interfaces or classes, one
  after the other). UnMock uses it to see their method signatures, return types, constructors and inheritance, so that
  the generated proxies can call the real implementations instead of the mocks.

## UnMock experiment guide

Follow this guide to conduct the experiment.

### Setup

#### Prerequisites

Clone repository

`git clone https://github.com/FedericoValeri/unmock4j.git`

Install:

- **Java JDK 21**
- **Apache Maven 3.9+**

Check installed versions:

```bash
java -version
mvn -version
```

#### Environment configuration

Create a `.env` file in the project root directory with the following content:

```env
MODEL=gpt-6-luna
PROVIDER=openai
OPENAI_API_KEY=secret_key
```

Replace `secret_key` with the following openai api key:

- **OPENAI API KEY**: #### (TODO: put the real api key)

_The OpenAI API key provided to you is intended exclusively for testing the Unmock project as part of this research
activity. It is valid for one day and must not be used for any other purpose, shared with third parties, or used in
other applications. Please keep the key confidential and use it only for the UnMock evaluation._

### Procedure

First, remember to download locally the open source projects, in a directory of your choice, through the links provided in section `Target projects`.

1. See the unit test provided in `UNIT.txt` and try to understand it by identifying:

    - the class under test (also given in `SUT.txt`);
    - which dependencies are mocked (e.g. fields annotated with `@Mock`);
    - what behaviour the mocks are told to have (`when(...).thenReturn(...)`);
    - which interactions the test checks (`verify(...)`).

2. Move into the unmock project `../src/main/resources/prompts/human` directory the given files (if there are already
   files with the same names, override them):

    - SUT.txt
    - UNIT.txt
    - DEPENDENCIES.txt

   This will enable UnMock to work with the selected use case.

3. Run the application using the following commands:
    1. Build

       ```bash
       mvn clean package
       ```

    2. Execute
       ```cmd
       java -jar target/unmock-1.0-SNAPSHOT.jar
       ```

4. Run the provided script for your operating system:

    - **Windows** (PowerShell):
      ```powershell
      .\scripts\check-gen.ps1 -Repo <local_repo_path>
      ```
    - **Linux / macOS** (bash):
      ```bash
      bash scripts/check-gen.sh --repo <local_repo_path>
      ```

   Replace `<local_repo_path>` with the path of the target project repository in your machine. The script will move the
   UnMock generated files into the target open-source project and compile them.

5. Check the outcome and complete the integration test.
    1. Read the script output. It shows the module of the target project (`Module`), the folder where the generated
       files were copied (`Target`), one `copy ... -> ...` line per generated file, and finally whether the code
       compiles. The generated files are an integration test class and one or more proxy classes (`..._Proxy`).
    2. Open the target project in a IDE of your choice (e.g. IntelliJ IDEA or Eclipse) and read the UnMock generated files (the location can be found in the script output paths after `->`). **Make all your changes in
       these copies**, not in the `gen/` folder of unmock4j, which must keep UnMock's original output.
    3. If the code does not compile, fix the errors. Also look for configuration points: places where the test needs
       a real instance of a dependency that UnMock could not create on its own. Provide that instance yourself.
    4. **Do not run the script again**: it would overwrite your changes. To recompile, run this from the root of
       the target project, replacing `<module>` with the module shown by the script:

       ```bash
          mvn clean test-compile -pl <module> -am
       ```

6. Change only what is needed to make the test compile and run.

## Questionnaire

Once you've done with the experiment, you can start the questionnaire. Do it once, right after finishing the experiment,
while the experience is fresh. It takes about 5 minutes.

- Enter your participant code, which you find below. Your name is not collected.
    - **Code**: P01
- Rate each statement from 1 (strongly disagree) to 4 (strongly agree), thinking about the task you have just done.
- The open questions at the end are optional but very useful. In particular, please describe what you had to
  change in the generated code and why.

**Link to
questionnaire**: https://docs.google.com/forms/d/e/1FAIpQLSfO3-WXm8VVo0qW31DTSEtE5bbmt-5IIJwTN_oesW3JjTA32Q/viewform
