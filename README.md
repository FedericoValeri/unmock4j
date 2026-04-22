# UnMock

![LangChain](https://img.shields.io/badge/LangChain-Framework-green)
![OpenAI](https://img.shields.io/badge/OpenAI-API-black)
![JUnit](https://img.shields.io/badge/JUnit-Testing-red)
![Mockito](https://img.shields.io/badge/Mockito-Mocking-orange)

This Java project uses Large Language Models via LangChain4j and OpenAI to automatically transform Java unit tests (
JUnit + Mockito) into integration tests.

The goal is to reduce the use of mocking and increase real system behavior coverage by replacing stubs and verify calls
with proxies that execute real code and validate results.

---

## Features

- Automatic transformation from unit tests to integration tests
- Use of LLMs for semantic code analysis
- Automatic identification of:
    - mocked dependencies
    - stubs (`when(...).thenReturn(...)`)
    - verify statements (`verify(...)`)
- Generation of proxy classes for each dependency
- Conversion of:
    - stubs into assertions on real results
    - verify statements into verifiable counters
- Structured, ready-to-use output

---

## How it works

1. **Input:**
    - Java test class (JUnit + Mockito)
    - Related production code

2. **The system:**
    - Analyzes mocks, stubs, and verify statements
    - Builds proxies for each dependency
    - Replaces:
        - stubs with real calls + assertions
        - verify statements with counters

3. **Output:**
    - Transformed integration test
    - Generated proxy classes