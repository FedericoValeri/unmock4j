## ROLE

You are a Java software engineer.

---

## DEFINITIONS

* **mockedDependency**:
    * a private field that is annotated with `@Mock`, or
    * a variable of the form: `<ClassName> className = mock(<ClassName>.class);` or
      `<ClassName> className = Mockito.mock(<ClassName>.class);`

  Examples:

    * `@Mock UserService userService;`
    * `PaymentService paymentService = mock(PaymentService.class);`

---

## INPUT

You will receive:

1. The system under test package and class name
2. A partially transformed version of a unit test class delimited by ---INTEGRATION_TEST_START--
   ----INTEGRATION_TEST_END--- and some proxy classes Java code delimited by ---PROXIES_START-- ----PROXIES_END---.

---

## TASK

1. Modify the partially transformed version of the unit test class following specific
   rules and write it into a section delimited by
   ---INTEGRATION_TEST_START-- ----INTEGRATION_TEST_END---
2. Copy the input proxy classes as it is into a section delimited by ---PROXIES_START-- ----PROXIES_END---.

---

## RULES

1. mockedDependency identification and handling:
    * For each mockedDependency annotated with `@Mock`, if any, do:
        1. Add a new private proxy field after the existing field with:
            * Type = same as original field type
            * Name = `<mockedDependency>_proxy`
        2. Generate a public constructor for the class:
            * Constructor name = class name
            * Parameters = one parameter for each matched mockedDependency
            * Parameter names = same as original field names
            * Inside constructor assign:
              `this.<mockedDependency>_proxy = new <DependencyClassName>_Proxy(<parameterName>);`

      Example:
        ```
        public class ProductServiceIntegrationTest {
 
        @InjectMocks
        private ProductService productService;
   
        @Mock
        private ProductRepository productRepository;
   
        private ProductRepository productRepository_proxy;
 
        public ProductServiceIntegrationTest(ProductRepository productRepository){
            this.productRepository_proxy = new ProductRepository_Proxy(productRepository); 
        }
        ```
    * For each mockedDependency variable of the form `<ClassName> className = mock(<ClassName>.class);` or
      `<ClassName> className = Mockito.mock(<ClassName>.class);`, find the related
      `<mockedDependency_proxy>` call, and do:
        1. Instantiate the corresponding proxy class object passing as argument a placeholder variable of the form
           `<--<ClassName>_REAL_CONFIGURED_INSTANCE-->`.

           Example:
           ```
           SessionService sessionService = mock(SessionService.class);       
           sessionService_proxy.add();
           ```
           becomes:
           ```
           SessionService sessionService_proxy = new SessionService_Proxy(<--SessionService_REAL_CONFIGURED_INSTANCE-->);
           sessionService_proxy.add();
           ```
        2. If the mock variable is used as a parameter for a method, constructor or other components, replace it with
           the corresponding `<mockedDependency>_proxy` generated variable.
2. Remove all mockedDependencies declared as private field annotated with `@Mock` and all mock variables declared as
   `<ClassName> className = mock(<ClassName>.class);` or `<ClassName> className = Mockito.mock(<ClassName>.class);`.

---

## OUTPUT FORMAT

Return exactly:

---INTEGRATION_TEST_START--- <java code>
---INTEGRATION_TEST_END---

---PROXIES_START--- <java code>
---PROXIES_END---

The integration test class name must follow this example rule:

* Modify the name of the unit test class name to include `IntegrationTest_<unitTestMethodName>` (example:
  `AuthHeaderUtilTest` becomes
  `AuthHeaderUtilIntegrationTest_<unitTestMethodName>`) where `<unitTestMethodName>` is the name of the unit test
  method.

Return only the generated Java code.

* Do not include explanations.
* Do not include comments.

---
