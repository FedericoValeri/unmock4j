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
MODEL=gpt-6-luna (recommended)
PROVIDER=openai
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
   ```cmd
   java -jar target/unmock-1.0-SNAPSHOT.jar
   ```

## Insights

We focus only on unit test classes that have as mocked dependencies the following types:

* Interface
* Concrete class with default constructor

Selecting mocked dependencies that are concrete classes with a non default constructor will give problems in the proxy
classes creation phase.

___

Assertions derived from `verify` statements transformation must call the corresponding `_verify` proxy method, so
we must replace all the mock references with the proxy otherwise the count won't work.
If we had only been interested in `when` statements this would not have been necessary.

In the following example the `methodsCache` reference have been replaced with `mockedDependency_proxy`:

```java
class McpConfigurationIntegrationTest {

    @Mock
    ControllerMethodsCache methodsCache;

    private ControllerMethodsCache mockedDependency_proxy = new ControllerMethodsCache_Proxy(methodsCache);

    @Test
    void testInit() {
        McpConfiguration mcpConfiguration = new McpConfiguration(mockedDependency_proxy);
        mcpConfiguration.init();
        assertEquals(1, ((ControllerMethodsCache_Proxy) mockedDependency_proxy).initClassMethod_verify());
    }
}
```

---

In order to check the generated `.java` files, the following command is run into the target project:

`mvn clean test-compile -pl <module_name> -am`

or automate via the available script:

e.g.

`.\scripts\check-gen.ps1 -Repo C:\repos\shenyu`

## Tested repositories

| Project           | Tag                | Repository                                        |
|-------------------|--------------------|---------------------------------------------------|
| dolphinscheduler  | `3.4.1`            | https://github.com/apache/dolphinscheduler        |
| nacos             | `3.2.1`            | https://github.com/alibaba/nacos                  |
| arthas            | `arthas-all-4.3.5` | https://github.com/alibaba/arthas                 |
| incubator-seata   | `v2.0.0`           | https://github.com/apache/incubator-seata         |
| sentinel          | `1.8.8`            | https://github.com/alibaba/Sentinel               |
| openapi-generator | `v7.24.0`          | https://github.com/OpenAPITools/openapi-generator |
| shenyu            | `v2.7.1`           | https://github.com/apache/shenyu                  |

Clone them with git rather than downloading the release zips: on Windows, extracting the zips fails because some
paths are longer than 260 characters. `core.longpaths=true` lets Git for Windows write those paths (it has no effect
on Linux/macOS), and `--depth 1` downloads only the tagged release, not the full history.

```bash
git clone -c core.longpaths=true --depth 1 --branch 3.4.1            https://github.com/apache/dolphinscheduler.git
git clone -c core.longpaths=true --depth 1 --branch 3.2.1            https://github.com/alibaba/nacos.git
git clone -c core.longpaths=true --depth 1 --branch arthas-all-4.3.5 https://github.com/alibaba/arthas.git
git clone -c core.longpaths=true --depth 1 --branch v2.0.0           https://github.com/apache/incubator-seata.git
git clone -c core.longpaths=true --depth 1 --branch 1.8.8            https://github.com/alibaba/Sentinel.git
git clone -c core.longpaths=true --depth 1 --branch v7.24.0          https://github.com/OpenAPITools/openapi-generator.git
git clone -c core.longpaths=true --depth 1 --branch v2.7.1           https://github.com/apache/shenyu.git
```

The same commands work in PowerShell, cmd and bash. On Windows, also clone into a short folder (e.g. `C:\repos`),
because Maven and some plugins can still fail on very long paths.

