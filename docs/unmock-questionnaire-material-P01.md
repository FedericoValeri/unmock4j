# UnMock questionnaire material

## Participant

- **Code**: P01
- **OPENAI API KEY**: #### (TODO: put the real api key)

_The OpenAI API key provided to you is intended exclusively for testing the Unmock project as part of this research
activity. It is valid for one day and must not be used for any other purpose, shared with third parties, or used in
other applications. Please keep the key confidential and use it only for the UnMock evaluation._

## Input files

For each target project you receive three text files containing Java code. They are the input UnMock needs to turn a
unit test into an integration test:

- **UNIT.txt**: the unit test to transform. A JUnit test class that uses Mockito mocks (`@Mock`, `when(...)`,
  `verify(...)`). It is the main input: UnMock reads which dependencies are mocked, how they are expected to behave, and
  which interactions are checked, and turns these into checks against the real dependencies.
- **SUT.txt**: the system under test, i.e. the production class that the unit test exercises (its package and class
  declaration). It tells UnMock which class the generated integration test targets.
- **DEPENDENCIES.txt**: the source code of the dependencies that are mocked in UNIT.txt (interfaces or classes, one
  after the other). UnMock uses it to see their method signatures, return types, constructors and inheritance, so that
  the generated proxies can call the real implementations instead of the mocks.

## Target projects

Download locally the target open source projects in a directory of your choice through the provided links. Also download
the related `SUT.txt`, `UNIT.txt`, `DEPENDENCIES.txt` files.

- [dolphinscheduler](https://github.com/apache/dolphinscheduler/releases#release-3.4.1):
    - **SUT.txt** (TODO: replace with download links)
    - **UNIT.txt** (TODO: replace with download links)
    - **DEPENDENCIES.txt** (TODO: replace with download links)
- [nacos](https://github.com/alibaba/nacos/releases#release-3.2.1):
    - **SUT.txt** (TODO: replace with download links)
    - **UNIT.txt** (TODO: replace with download links)
    - **DEPENDENCIES.txt** (TODO: replace with download links)

## UnMock repository

Download the UnMock project directly from the following link:

https://github.com/FedericoValeri/unmock4j

## UnMock usage guide

Follow this guide to conduct the experiment.

https://github.com/FedericoValeri/unmock4j/blob/main/docs/unmock-guide.md

## Questionnaire link

https://docs.google.com/forms/d/e/1FAIpQLSfO3-WXm8VVo0qW31DTSEtE5bbmt-5IIJwTN_oesW3JjTA32Q/viewform
