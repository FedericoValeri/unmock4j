# UnMock4j

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
![LangChain](https://img.shields.io/badge/LangChain-1c3c3c.svg?logo=langchain&logoColor=white)

This tool leverage Large Language Models to automatically transform Java unit tests that make use of mocks, into
integration tests. The goal is to leverage mock assumptions to increase real system behavior coverage by replacing stubs
and verify calls
with proxies that execute real code and validate results.

---

## Prerequisites

Install:

- **Java JDK 26**
- **Apache Maven 3.9+**

Check installed versions:

```bash
java -version
mvn -version
```

### Environment configuration

Create a `.env` file in the project root directory with the following content:

```env
MODEL=gpt-5.4-mini (recommended)
PROVIDER=openai (recommended)
OPENAI_API_KEY=secret_key
```

Also ollama provider is supported. In that case you don't need to set any openai api key.

### LLM inputs

Inside the `src/main/resources/prompts/human` the files:

- `DEPENDENCIES.txt`
- `SUT.txt`
- `UNIT.txt`

must be filled with proper Java code in order to make the tool transform the unit test.

**IMPORTANT NOTE**: the files belonging to the java code inserted in the previous files must be available to the
application, so in the `pom.xml` there must be references to the projects of those classes.

## Run the application

Use the following commands:

1. Build

    ```bash
    mvn clean package
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