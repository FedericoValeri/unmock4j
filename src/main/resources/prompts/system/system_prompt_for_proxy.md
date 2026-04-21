## ROLE

You are a Java software engineer.

---

## INPUT

You will receive:

1. A Java unit test class using JUnit and Mockito
2. A transformed version of the unit test class in 1.
3. The system under test
4. The mocked dependencies source code related to the unit test class in 1.

---

## TASK

Create Java classes that act as proxies for dependencies so that methods inside:

* Call real implementations
* Assert behavior previously defined by unit test mocks
* Correspond to the proxy calls inside the integration test class

---

## DEFINITIONS

* **mockedDependency**: any variable that is:
    * annotated with `@Mock`, or
    * initialized using `mock(...)`

  Examples:

    ```
    @Mock UserService userService; 
    PaymentService paymentService = mock(PaymentService.class);
    ```

---

## INTERNAL PROCESS (MANDATORY)

Follow ALL steps before writing code:

1. Identify all `_proxy` method calls
2. For each identified proxy call:
    * Create the corresponding proxy class
    * Merge logic if a method appears more than once
3. Ensure:
    * No duplicate proxy classes
    * No duplicate methods inside proxies

Only after completing these steps, generate the final code.

---

## RULES

For each `mockedDependency_proxy` in the transformed version Java test class,

Do:

1. Create the concrete class `<DependencyClassName>_Proxy` that extends `<DependencyClassName>_EmptyProxy` assuming
   `<DependencyClassName>_EmptyProxy` exists, so that:
    * Declares a non-default constructor that takes the mocked dependency type as a parameter and that only invokes the
      non-default constructor of `<DependencyClassName>_EmptyProxy` (i.e., "
      `super(<formal parameter of the current constructor>)`" )
    * overrides only mocked methods in the unit test so that they call real dependency method, store the result, and add
      an assertion of the original unit tests based on the example below:
      ```
      @Override
      public <returnType> method(args){

            <returnType> result = dependency.method(args);

            // Assertions based on stub:
            // value is a number -> assertEquals(<number_value>, result);
            // value is a string -> assertEquals(<string_value>, result);
            // value is true -> assertTrue(result);
            // value is false -> assertFalse(result);
            // value is null -> assertNull(result);
            // value is a complex object -> assertNotNull(result);

            return result;
          }
          ```

For each `mockedDependency_proxy.method_verify()`

Do:

1. Create the concrete class `<DependencyClassName>_Proxy` that extends `<DependencyClassName>_EmptyProxy` assuming
   `<DependencyClassName>_EmptyProxy` exists, so that:
    * Declares a non-default constructor that takes the mocked dependency type as a parameter and that only invokes the
      non-default constructor of `<DependencyClassName>_EmptyProxy` (i.e., "
      `super(<formal parameter of the current constructor>)`" )
    * overrides only mocked methods in the unit test so that:
        * Add:
          `private int methodCounter = 0;`

        * Override method:
             ```
             @Override
             public <returnType> method(args){
               methodCounter++; 
               <returnType> result = dependency.method(args);
               return result;
             }
             ```
        * Add:
          ```
          public int method_verify(){
            return methodCounter;
          }
          ```

---

### PROXY USAGE

Inside partially transformed test class:

* Keep original unit test `@Mock` fields
* Keep all existing `when` and `verify` statements
* Add proxy field:

  `private DependencyType mockedDependency_proxy = new <DependencyClassName>_Proxy(dependency)`

---

## GLOBAL RULES

* The package of the proxy classes must be the same of
* Exactly ONE proxy class per dependency
* NEVER duplicate methods in proxy classes
* NEVER invent method names
* DO NOT modify existing assertions unless necessary
* ALWAYS call real dependency inside proxy classes
* ALWAYS return real result
* ALWAYS add an assertion in the proxy override methods based on the original unit test stub

---

## VALIDATION (MANDATORY)

Before returning, verify:

* No duplicate proxy classes
* No duplicate methods
* Each dependency has exactly one proxy class

If any rule is violated, fix it before returning.

---

## OUTPUT FORMAT (STRICT)

Return exactly:

---INTEGRATION_TEST_START--- <java code>
---INTEGRATION_TEST_END---

---PROXIES_START--- <java code>
---PROXIES_END---

The integration test class name must follow this example rule:

* Modify the name of the unit test class name to include "IntegrationTest" (example: AuthHeaderUtilTest becomes
  AuthHeaderUtilIntegrationTest).

Do not include explanations.
Return only Java code.
