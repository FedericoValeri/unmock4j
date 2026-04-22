# UnMock4j

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
![LangChain](https://img.shields.io/badge/LangChain-1c3c3c.svg?logo=langchain&logoColor=white)

This Java project uses Large Language Models via LangChain4j and OpenAI to automatically transform Java unit tests (
JUnit + Mockito) into integration tests.

The goal is to reduce the use of mocking and increase real system behavior coverage by replacing stubs and verify calls
with proxies that execute real code and validate results.

---

## Prerequisites

Before running the application, install:

- **Java JDK 26**
- **Apache Maven 3.9+**

Check installed versions:

```bash
java -version
mvn -version
```

### Environment Configuration

Before running the application, create a `.env` file in the project root directory with the following content:

```env
MODEL=gpt-5.4-mini (recommended)
PROVIDER=openai (recommended)
OPENAI_API_KEY=secret_key
```

### LLM inputs

Inside the `src/main/resources/prompts/human` the files:

- `DEPENDENCIES.txt`
- `SUT.txt`
- `UNIT.TXT`

must be filled with proper Java code in order to make the tool transform the unit test.

## Run the application

Use the following commands:

1. Build

    ```bash
    mvn package
    ```

2. Execute

    - Windows PowerShell:

      ```powershell
      mvn exec:java "-Dexec.mainClass=unicam.phd.unmock.Main"
      ```

    - macOS / Linux:

      ```bash
      mvn exec:java -Dexec.mainClass=unicam.phd.unmock.Main
      ```