## ROLE

You are a Java software engineer.

---

## DEFINITIONS

* **mockedDependency**:
    * any private field that is annotated with `@Mock`, or
    * any variable of the form: `<ClassName> className = mock(<ClassName>.class);`

  Examples:

  ```
  @Mock UserService userService; 
  
  PaymentService paymentService = mock(PaymentService.class);
  ```

---

## INPUT

You will receive:

1. A Java unit test class using JUnit and Mockito
2. A partially transformed version of the unit test class in 1.
3. The system under test package and class name
4. The mocked dependencies source code related to the unit test class in 1.

---

## TASK

1. Create Java classes that act as proxies for dependencies so that methods inside:
    * Call real implementations
    * Assert behavior previously defined by unit test mocks
    * Correspond to the proxy calls inside the integration test class
2. Create the final integration test class from the partially transformed version of the unit test class.

---

## RULES

For each `<mockedDependency>_proxy` method call in the partially transformed version of the unit test,

Do:

1. Create the concrete class `<DependencyClassName>_Proxy` that extends `<DependencyClassName>_EmptyProxy` assuming
   `<DependencyClassName>_EmptyProxy` exists, so that:
    * Declares a non-default constructor that takes the mocked dependency type as a parameter and that only invokes the
      non-default constructor of `<DependencyClassName>_EmptyProxy` (i.e., "
      `super(<formal parameter of the current constructor>)`" )
    * overrides only mocked methods in the unit test so that they call real dependency method, store the result, and add
      an assertion based on the original unit test original stub, like so:
      ```
      @Override
      public <returnType> method(args){
            <returnType> result = dependency.method(args);            
            // Assertion must be placed here
            return result;
          }
      ```
      The assertion must be written based on the value returned in the original unit test stub, following these rules:
        * value is a number: `assertEquals(<value>, result);`
        * value is a string: `assertEquals(<value>, result);`
        * value is a true: `assertTrue(result)`
        * value is a false: `assertFalse(result)`
        * value is a null: `assertNull(result)`
        * value is a complex object: `assertNotNull(result)`

For each `<mockedDependency>_proxy.method_verify()` in the partially transformed version of the unit test,

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

### PROXY USAGE

Inside the partially transformed version of the unit test class:

* Keep original unit test `@Mock` fields or `mock(<DependencyClassName>.class)` definitions
* Keep all existing `when` statements
* Add proxy initialization near the mockedDependency definitions.

  Examples:
  ```
  @Mock
  private ProductRepository productRepository;  
  private ProductRepository productRepository_proxy = new ProductRepository_Proxy(productRepository);
  ```
  ```
  Alert alertMock = mock(Alert.class);
  Alert alertMock_proxy = new Alert_Proxy(alertMock);
  ```

---

## GLOBAL RULES

* The package of the proxy classes must be the same of the unit test class.
* The imports in the generated proxy classes must also include all the imports from the unit test class, with no
  duplicates.
* Use the same Junit version of the original unit test in the proxy classes.
* Exactly ONE proxy class per dependency
* NEVER duplicate methods in proxy classes
* If there are both stubs and verify related to the same method, merge rules for stubs and verify into the generated
  @override method.
* NEVER invent method names
* DO NOT modify existing assertions unless necessary, but remove original `verify` statements of the unit test.
* ALWAYS call real dependency inside proxy classes
* ALWAYS return real result
* ALWAYS add an assertion in the proxy override methods based on the original unit test stub following the rules

---

## OUTPUT FORMAT

Return exactly:

---INTEGRATION_TEST_START--- <java code>
---INTEGRATION_TEST_END---

---PROXIES_START--- <java code>
---PROXIES_END---

The integration test class name must follow this example rule:

* Modify the name of the unit test class name to include "IntegrationTest" (example: AuthHeaderUtilTest becomes
  AuthHeaderUtilIntegrationTest).

Return only the generated Java code.

* Do not include explanations.
* Do not include comments.
