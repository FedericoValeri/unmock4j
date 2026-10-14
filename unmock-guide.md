# UnMock guide

## Setup

### Prerequisites

Install:

- **Java JDK 21**
- **Apache Maven 3.9+**

Check installed versions:

```bash
java -version
mvn -version
```

### Environment configuration

Create a `.env` file in the project root directory with the following content:

```env
MODEL=gpt-6-luna
PROVIDER=openai
OPENAI_API_KEY=secret_key
```

Replace `secret_key` with the given openai api key.

## Usage

1. Move into the `src/main/resources/prompts/human` directory the given files (if there are already files with the same
   names, override them):
    - SUT.txt
    - UNIT.txt
    - DEPENDENCIES.txt

   This will enable UnMock to work with the selected use case.

2. Run the application using the following commands:
    1. Build

       ```bash
       mvn clean package
       ```

    2. Execute
       ```cmd
       java -jar target/unmock-1.0-SNAPSHOT.jar
       ```

3. Check the results via the provided script:
   `.\scripts\check-gen.ps1 -Repo <local_repo_path>`

   Replace `<local_repo_path>` with the path of the target project repository in your machine.
