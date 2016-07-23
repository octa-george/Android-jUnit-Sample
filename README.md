# Android-jUnit-Sample

## 1. The purpose of software tests
### 1.1. What are software tests?
A software test is a piece of software, which executes another pierce of software. It validates if that code results in the expected state (state testing) or executes the expected sequence of events (behavior testing).

### 1.2. Why are software tests helpful?
Software unit tests help the developer to verify that the logic of a piece of the program is correct.

Running tests automatically helps to identify software regressions introduced by changes in the source code. Having a high test coverage of your code allows you to continue developing features without having to perform lots of manual tests.

### 1.3. Testing frameworks for Java
There are several testing frameworks available for Java. The most popular ones are JUnit and TestNG

This description focuses on JUnit.

## 2. Testing terminology
### 2.1. Code (or application) under test
The code which is tested is typically called the code under test. If you are testing an application, this is called the application under test.

### 2.2. Test fixture
A test fixture is a fixed state in code which is tested used as input for a test. Another way to describe this is a test precondition.

For example, a test fixture might be a a fixed string, which is used as input for a method. The test would validate if the method behaves correctly with this input.

### 2.3. Unit tests and unit testing
A unit test is a piece of code written by a developer that executes a specific functionality in the code to be tested and asserts a certain behavior or state.

The percentage of code which is tested by unit tests is typically called test coverage.

A unit test targets a small unit of code, e.g., a method or a class. External dependencies should be removed from unit tests, e.g., by replacing the dependency with a test implementation or a (mock) object created by a test framework.

Unit tests are not suitable for testing complex user interface or component interaction. For this, you should develop integration tests.

### 2.4. Integration tests
An integration test aims to test the behavior of a component or the integration between a set of components. The term functional test is sometimes used as synonym for integration test. Integration tests check that the whole system works as intended, therefore they are reducing the need for intensive manual tests.

These kinds of tests allow you to translate your user stories into a test suite. The test would resemble an expected user interaction with the application.

### 2.5. Performance tests
Performance tests are used to benchmark software components repeatedly. Their purpose is to ensure that the code under test runs fast enough even if it’s under high load.

### 2.6. Behavior vs. state testing
A test is a behavior test (also called interaction test) if it checks if certain methods were called with the correct input parameters. A behavior test does not validate the result of a method call.

State testing is about validating the result. Behavior testing is about testing the behavior of the application under test.

If you are testing algorithms or system functionality, in most cases you may want to test state and not interactions. A typical test setup uses mocks or stubs of related classes to abstract the interactions with these other classes away Afterwards you test the state or the behavior depending on your need.

## 3. Test organization
### 3.1. Where should the test be located?
Typical, unit tests are created in a separate project or separate source folder to keep the test code separate from the real code.

### 3.2. Which part of the software should be tested?
What should be tested is a highly controversial topic. Some developers believe every statement in your code should be tested.

In any case you should write software tests for the critical and complex parts of your application. If you introduce new features a solid test suite also protects you against regression in existing code.

In general it it safe to ignore trivial code . For example, it is typical useless to write tests for getter and setter methods which simply assign values to fields. Writing tests for these statements is time consuming and pointless, as you would be testing the Java virtual machine. The JVM itself already has test cases for this. If you are developing end user applications you are safe to assume that a field assignment works in Java.

If you start developing tests for an existing code base without any tests, it is good practice to start writing tests for the parts of the application in which most of the errors happened in the past. This way you can focus on the critical parts of your application.

## 4. Using JUnit
### 4.1. The JUnit framework
JUnit in version 4.x is a test framework which uses annotations to identify methods that specify a test. The main websites for JUnit are the JUnit homepage (http://junit.org/) and the Github project page (https://github.com/junit-team/junit).

### 4.2. How to define a test in JUnit?
A JUnit test is a method contained in a class which is only used for testing. This is called a Test class. To write a JUnit 4 test with the you annotate a method with the @org.junit.Test annotation.

This method executes the code under test. You use an assert method, provided by JUnit or another assert framework, to check an expected result versus the actual result. These method calls are typically called asserts or assert statements.

You should provide meaningful messages in assert statements. That makes it easier for the user to identify and fix the problem. This is especially true if someone looks at the problem, who did not write the code under test or the test code.

### 4.3. Example JUnit test
The following code shows a JUnit test. This test assumes that the MyClass class exists and has a multiply(int, init) method.

```java
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MyTests {

        @Test
        public void multiplicationOfZeroIntegersShouldReturnZero() {
                MyClass tester = new MyClass(); // MyClass is tested

                // assert statements
                assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
                assertEquals("0 x 10 must be 0", 0, tester.multiply(0, 10));
                assertEquals("0 x 0 must be 0", 0, tester.multiply(0, 0));
        }
}
```

### 4.4. JUnit naming conventions
There are several potential naming conventions for JUnit tests. A widely-used solution is to add the suffix "-test" to the names of the test classes and create them in a new package "test".

As a general rule, a test name should explain what the test does. If that is done correctly, reading the actual implementation can be avoided.

One possible convention is to use the word "should" in the test method name. For example, "ordersShouldBeCreated" or "menuShouldGetActive". This gives a hint what should happen if the test method is executed.

### 4.5. JUnit naming conventions for Maven
If you are using the Maven build system, you should prefer the "Test" suffix over "Tests". The Maven build system (via its surfire plug-in) automatically includes such classes in its test scope.

### 4.6. JUnit test suites
If you have several test classes, you can combine them into a test suite . Running a test suite executes all test classes in that suite in the specified order. A test suite can also contain other test suites.

The following example code demonstrates the usage of a test suite. It contains two test classes (MyClassTest and MySecondClassTest). If you want to add another test class, you can add it to the @Suite.SuiteClasses statement.

```java
package com.vogella.junit.first;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
                MyClassTest.class,
                MySecondClassTest.class })

public class AllTests {

}
```

### 4.7. Run your test from the command line
You can also run your JUnit tests outside our IDE via standard Java code. Build systems like Apache Maven or Gradle in combination with a Continuous Integration Server (like Jenkins) can be used to execute tests automatically on a regular basis.

The org.junit.runner.JUnitCore class provides the runClasses() method. This method allows you to run one or several tests classes. As a return parameter you receive an object of the type org.junit.runner.Result. This object can be used to retrieve information about the tests.

The following class demonstrates how to run the MyClassTest. This class executes your test class and write potential failures to the console.

```java
package de.vogella.junit.first;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyTestRunner {
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(MyClassTest.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
  }
}
```

This class can be executed like any other Java program on the command line. You only need to add the JUnit library JAR file to the classpath.

## 5. Basic JUnit code constructs
### 5.1. Available JUnit annotations
JUnit 4.x uses annotations to mark methods as test methods and to configure them. The following table gives an overview of the most important annotations in JUnit.

#### Table 1. Annotations
| Annotation    |	Description   |
| ------------- |:-------------:| 
| @Test public void method() | The @Test annotation identifies a method as a test method. |
| @Test (expected = Exception.class) | Fails if the method does not throw the named exception. |
| @Test(timeout=100) | Fails if the method takes longer than 100 milliseconds. |
| @Before public void method() | This method is executed before each test. It is used to prepare the test environment (e.g., read input data, initialize the class). |
| @After public void method() | This method is executed after each test. It is used to cleanup the test environment (e.g., delete temporary data, restore defaults). It can also save memory by cleaning up expensive memory structures. |
| @BeforeClass public static void method() | This method is executed once, before the start of all tests. It is used to perform time intensive activities, for example, to connect to a database. Methods marked with this annotation need to be defined as static to work with JUnit. |
| @AfterClass public static void method() | This method is executed once, after all tests have been finished. It is used to perform clean-up activities, for example, to disconnect from a database. Methods annotated with this annotation need to be defined as static to work with JUnit. |
| @Ignore or @Ignore("Why disabled") | Ignores the test method. This is useful when the underlying code has been changed and the test case has not yet been adapted. Or if the execution time of this test is too long to be included. It is best practice to provide the optional description, why the test is disabled. |

### 5.2. Assert statements
JUnit provides static methods to test for certain conditions via the Assert`class. These assert statements typically start with `assert . They allow you to specify the error message, the expected and the actual result. An assertion method compares the actual value returned by a test to the expected value. It throws an AssertionException if the comparison fails.

The following table gives an overview of these methods. Parameters in [] brackets are optional and of type String.

#### Table 2. Methods to assert test results
| Statement	    | Description   |
| ------------- |:-------------:| 
| fail(message) | Let the method fail. Might be used to check that a certain part of the code is not reached or to have a failing test before the test code is implemented. The message parameter is optional. |
| assertTrue([message,] boolean condition) | Checks that the boolean condition is true. |
| assertFalse([message,] boolean condition) | Checks that the boolean condition is false. |
| assertEquals([message,] expected, actual) | Tests that two values are the same. Note: for arrays the reference is checked not the content of the arrays. |
| assertEquals([message,] expected, actual, tolerance) | Test that float or double values match. The tolerance is the number of decimals which must be the same. |
| assertNull([message,] object) | Checks that the object is null. |
| assertNotNull([message,] object) | Checks that the object is not null. |
| assertSame([message,] expected, actual) | Checks that both variables refer to the same object. |
| assertNotSame([message,] expected, actual) | Checks that both variables refer to different objects. |

### 5.3. Test execution order
JUnit assumes that all test methods can be executed in an arbitrary order. Well-written test code should not assume any order, i.e., tests should not depend on other tests.

As of JUnit 4.11 the default is to use a deterministic, but not predictable, order for the execution of the tests.

You can use an annotation to define that the test methods are sorted by method name, in lexicographic order. To activate this feature, annotate your test class with the @FixMethodOrder(MethodSorters.NAME_ASCENDING) annotation. You can also explicitely set the default by using the MethodSorters.DEFAULT parameter in this annotation. You can also use MethodSorters.JVM which uses the JVM defaults, which may vary from run to run.

### 5.4. Disabling tests
The @Ignore annotation allow to statically ignore a test. Alternatively you can use Assume.assumeFalse or Assume.assumeTrue to define a condition for the test. Assume.assumeFalse marks the test as invalid, if its condition evaluates to true. Assume.assumeTrue evaluates the test as invalid if its condition evaluates to false. For example, the following disables a test on Linux:

`Assume.assumeFalse(System.getProperty("os.name").contains("Linux"));`

## 6. Support for jUnit
### 6.1. JUnit static imports

Static import is a feature that allows fields and methods defined in a class as public static to be used without specifying the class in which the field is defined.

JUnit assert statements are typically defined as public static to allow the developer to write short test statements. The following snippet demonstrates an assert statement with and without static imports.

```java
// without static imports you have to write the following statement
Assert.assertEquals("10 x 5 must be 50", 50, tester.multiply(10, 5));


// alternatively define assertEquals as static import
import static org.junit.Assert.assertEquals;

// more code

// use assertEquals directly because of the static import
assertEquals("10 x 5 must be 50", 50, tester.multiply(10, 5));
```

### 6.2. Testing exception
The @Test (expected = Exception.class) annotation is limited as it can only test for one exception. To testexceptions, you can use the following testpattern.

```java
try {
   mustThrowException();
   fail();
} catch (Exception e) {
   // expected
   // could also check for message of exception, etc.
}
``` 

### 6.3. Using JUnit with Gradle
To use JUnit in your Gradle build, add a testCompile dependency to your build file.

```groovy
apply plugin: 'java'

dependencies {
  testCompile 'junit:junit:4.12'
}
```

## 7. Advanced JUnit options
### 7.1. Parameterized test
JUnit allows you to use parameters in a tests class. This class can contain one test method and this method is executed with the different parameters provided.

You mark a test class as a parameterized test with the @RunWith(Parameterized.class) annotation.

Such a test class must contain a static method annotated with the @Parameters annotation. That method generates and returns a collection of arrays. Each item in this collection is used as parameter for the test method.

You can use the @Parameter annotation on public fields to get the test values injected in the test.

The following code shows an example for a parameterized test. It tests the multiply() method of the MyClass class which is included as inner class for the purpose of this example.

```java
package testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class ParameterizedTestFields {

    // fields used together with @Parameter must be public
    @Parameter
    public int m1;
    @Parameter (value = 1)
    public int m2;


    // creates the test data
    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { 1 , 2 }, { 5, 3 }, { 121, 4 } };
        return Arrays.asList(data);
    }


    @Test
    public void testMultiplyException() {
        MyClass tester = new MyClass();
        assertEquals("Result", m1 * m2, tester.multiply(m1, m2));
    }


    // class to be tested
    class MyClass {
        public int multiply(int i, int j) {
            return i *j;
        }
    }
}
```

Alternatively to using the @Parameter annotation you can use a constructor in which you store the values for each test. The number of elements in each array provided by the method annotated with @Parameters must correspond to the number of parameters in the constructor of the class. The class is created for each parameter and the test values are passed via the constructor to the class.

```java
package de.vogella.junit.first;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParameterizedTestUsingConstructor {

    private int m1;
    private int m2;

    public ParameterizedTestUsingConstructor(int p1, int p2) {
        m1 = p1;
        m2 = p2;
    }

    // creates the test data
    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { 1 , 2 }, { 5, 3 }, { 121, 4 } };
        return Arrays.asList(data);
    }


    @Test
    public void testMultiplyException() {
        MyClass tester = new MyClass();
        assertEquals("Result", m1 * m2, tester.multiply(m1, m2));
    }


    // class to be tested
    class MyClass {
        public int multiply(int i, int j) {
            return i *j;
        }
    }
}
```

If you run this test class, the test method is executed with each defined parameter. In the above example the test method is executed three times.

A more flexible and easier to write approach is provided by the JUnitParams from https://github.com/Pragmatists/JUnitParams.

### 7.2. JUnit Rules
Via JUnit rules you can add behavior to each tests in a test class. You can annotate fields of type TestRule with the @Rule annotation. You can create objects which can be used and configured in your test methods. This adds more flexibility to your tests. You could, for example, specify which exception message you expect during the execution of your test code.

```java
package de.vogella.junit.first;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RuleExceptionTesterExample {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void throwsIllegalArgumentExceptionIfIconIsNull() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("Negative value not allowed");
    ClassToBeTested t = new ClassToBeTested();
    t.methodToBeTest(-1);
  }
}
```

JUnit already provides several useful rule implementations. For example, the TemporaryFolder class allows to setup files and folders which are automatically removed after each test run.

The following code shows an example for the usage of the TemporaryFolder implementation.

```java
package de.vogella.junit.first;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RuleTester {

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void testUsingTempFolder() throws IOException {
    File createdFolder = folder.newFolder("newfolder");
    File createdFile = folder.newFile("myfilefile.txt");
    assertTrue(createdFile.exists());
  }
}
```

For more examples of existing rules see https://github.com/junit-team/junit4/wiki/Rules . === Writing custom JUnit rules

To write your custom rule, you need to implement the TestRule interface. This interface defines the apply(Statement, Description) method which must return an instance of Statement. Statement represent the tests within the JUnit runtime and Statement#evaluate() run these. Description describes the individual test. It allows to read information about the test via reflection.

The following is a simple example for adding a log statement to an Android application before and after test execution.

```java
package testing.android.vogella.com.asynctask;

import android.util.Log;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MyCustomRule implements TestRule {
    private Statement base;
    private Description description;

    @Override
    public Statement apply(Statement base, Description description) {
        this.base = base;
        this.description = description;
        return new MyStatement(base);
    }

    public class MyStatement extends Statement {
        private final Statement base;

        public MyStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
                System.
            Log.w("MyCustomRule",description.getMethodName() + "Started" );
            try {
                base.evaluate();
            } finally {
                Log.w("MyCustomRule",description.getMethodName() + "Finished");
            }
        }
    }
}
```

To use this rule, simple add a field annotated with @Rule to your test class.

```java
@Rule
public MyCustomRule myRule = new MyCustomRule();
```

### 7.3. Categories
It is possible to define categories of tests and include or exclude them based on annotations. The following example is based on the JUnit 4.8 release notes.

```java
public interface FastTests { /* category marker */
}

public interface SlowTests { /* category marker */
}

public class A {
        @Test
        public void a() {
                fail();
        }

        @Category(SlowTests.class)
        @Test
        public void b() {
        }
}

@Category({ SlowTests.class, FastTests.class })
public class B {
        @Test
        public void c() {
        }
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@SuiteClasses({ A.class, B.class })
// Note that Categories is a kind of Suite
public class SlowTestSuite {
        // Will run A.b and B.c, but not A.a
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses({ A.class, B.class })
// Note that Categories is a kind of Suite
public class SlowTestSuite {
        // Will run A.b, but not A.a or B.c
}
```

## 8. Mocking - Testing with mock objects
Unit testing also makes use of object mocking. In this case the real object is exchanged by a replacement which has a predefined behavior for the test.

### 8.1. Target and challenge of unit testing
A unit test should test a class in isolation. Side effects from other classes or the system should be eliminated if possible. To eliminate these side effects you have to replace dependencies to other classes. This can be done via using replacements for the real dependencies.

### 8.2. Classifications of different test classes
A dummy object is passed around but never used, i.e., its methods are never called. Such an object can for example be used to fill the parameter list of a method.

Fake objects have working implementations, but are usually simplified. For example, they use an in memory database and not a real database.

A stub class is an partial implementation for an interface or class with the purpose of using an instance of this stub class during testing. Stubs usually do responding at all to anything outside what’s programmed in for the test. Stubs may also record information about calls

A mock object is a dummy implementation for an interface or a class in which you define the output of certain method calls.

Test doubles can be passed to other objects which are tested. Your tests can validate that the class reacts correctly during tests. For example, you can validate if certain methods on the mock object were called. This helps to ensure that you only test the class while running tests and that your tests are not affected by any side effects.

Mock objects are typically configured. Mock objects typically require less code to configure and should therefore be preferred.

### 8.3. Mock object generation
You can create mock objects manually (via code) or use a mock framework to simulate these classes. Mock frameworks allow you to create mock objects at runtime and define their behavior.

The classical example for a mock object is a data provider. In production an implementation to connect to the real data source is used. But for testing a mock object simulates the data source and ensures that the test conditions are always the same.

These mock objects can be provided to the class which is tested. Therefore, the class to be tested should avoid any hard dependency on external data.

Mocking or mock frameworks allows testing the expected interaction with the mock object. You can, for example, validate that only certain methods have been called on the mock object.

### 8.4. Using Mockito for mocking objects
Mockito is a popular mock framework which can be used in conjunction with JUnit. Mockito allows you to create and configure mock objects. Using Mockito simplifies the development of tests for classes with external dependencies significantly.

If you use Mockito in tests you typically:

* Mock away external dependencies and insert the mocks into the code under test
* Execute the code under test
* Validate that the code executed correctly

![alt text](http://www.vogella.com/tutorials/Mockito/img/xmockitousagevisualization.png.pagespeed.ic.O5G0-DReR4.png "Mockito usage visualization")

## 9. Adding Mockito as dependencies to a project
### 9.1. Using Grade
If you use Gradle, add the following dependency to the Gradle build file.

```groovy
repositories { jcenter() }
dependencies { testCompile "org.mockito:mockito-core:2.0.57-beta" }
```

### 9.2. Using Maven
Maven users can declare a dependency. Search for g:"org.mockito", a:"mockito-core" via the http://search.maven.org website to	find the correct pom entry.

## 10. Using the Mockito API
### 10.1. Static imports
If you add a static import for org.mockito.Mockito.*;, you can access Mockitos methods like mock() directly. Static imports allows you to call static members, i.e., methods and fields of a class directly without specifying the class.

### 10.2. Creating and configuring mock objects with Mockito
Mockito supports the creation of mock objects. For this you can use the static mock() method.

Mockito also supports the creation of mock objects based on the @Mock annotation.

If you use this annotation, you must initialize the mock objects. The MockitoRule allows this. It invokes the static method MockitoAnnotations.initMocks(this) to populate the annotated fields. Alternatively you can use @RunWith(MockitoJUnitRunner.class).

The usage of the @Mock annotation and the MockitoRule rule is demonstrated by the following example.

```java
import static org.mockito.Mockito.*;

public class MockitoTest  {

        @Mock
        MyDatabase databaseMock; // (1)

        @Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); // (2)

        @Test
        public void testQuery()  {
                ClassToTest t  = new ClassToTest(databaseMock); // (3)
                boolean check = t.query("* from t"); // (4)
                assertTrue(check); // (5)
                verify(databaseMock).query("* from t"); // (6)
        }
}
```

* (1) Tells Mockito to most the databaseMock instance
* (2) Tells Mockito to create the mocks based on the @Mock annotation
* (3) Instantiates the class under test using the created mock
* (4) Executes some code of the class under test
* (5) Asserts that the method call returned true
* (6) Verify that the query method was called on the MyDatabase mock

### 10.3. Configuring mocks
To configure which values are returned at a method call the Mockito framework defines a fluent API.

The when(…​.).thenReturn(…​.) method chain is be used to specify a condition and a return value for this condition. If you specify more than one value,	they are returned in the order of specification until the last one is used. Afterwards the last	specified value	is returned. Mocks can also return different values depending on arguments passed into a method. You also use methods like anyString or anyInt to define that independent of the input value a certain return value should be returned.

```java
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Test
public void test1()  {
        //  create mock
        MyClass test = Mockito.mock(MyClass.class);

        // define return value for method getUniqueId()
        when(test.getUniqueId()).thenReturn(43);

        // use mock in test....
        assertEquals(test.getUniqueId(), 43);
}


// Demonstrates the return of multiple values
@Test
public void testMoreThanOneReturnValue()  {
        Iterator i= mock(Iterator.class);
        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
        String result=i.next()+" "+i.next();
        //assert
        assertEquals("Mockito rocks", result);
}

// this test demonstrates how to return values based on the input
@Test
public void testReturnValueDependentOnMethodParameter()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(1,c.compareTo("Mockito"));
}

// this test demonstrates how to return values independent of the input value

@Test
public void testReturnValueInDependentOnMethodParameter()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        //assert
        assertEquals(-1 ,c.compareTo(9));
}

// return a value based on the type of the provide parameter

@Test
public void testReturnValueInDependentOnMethodParameter()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo(isA(Todo.class))).thenReturn(0);
        //assert
        Todo todo = new Todo(5);
        assertEquals(todo ,c.compareTo(new Todo(1)));
}
```

The doReturn(…​).when(…​).methodCall call chain works similar but is useful for void methods. The doThrow variant can be used for methods which return void to throw an exception. This usage is demonstrated by the following code snippet.

```java
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

// this test demonstrates how use doThrow

@Test(expected=IOException.class)
public void testForIOException() {
        // create an configure mock
        OutputStream mockStream = mock(OutputStream.class);
        doThrow(new IOException()).when(mockStream).close();

        // use mock
        OutputStreamWriter streamWriter= new OutputStreamWriter(mockStream);
        streamWriter.close();
}
```

### 10.4. Verify the calls on the mock objects
Mockito keeps track of all the method calls and their parameters to the mock object. You can use the verify() method on the mock object to verify that the specified conditions are met For example, you can verify that a method has been called with certain parameters. This kind of testing is sometimes called behavior testing. Behavior testing does not check the result of a method call, but it checks that a method is called with the right parameters.

```java
import static org.mockito.Mockito.*;

@Test
public void testVerify()  {
        // create and configure mock
        MyClass test = Mockito.mock(MyClass.class);
        when(test.getUniqueId()).thenReturn(43);


        // call method testing on the mock with parameter 12
        test.testing(12);
        test.getUniqueId();
        test.getUniqueId();


        // now check if method testing was called with the parameter 12
        verify(test).testing(Matchers.eq(12));

        // was the method called twice?
        verify(test, times(2)).getUniqueId();

        // other alternatives for verifiying the number of method calls for a method
        verify(mock, never()).someMethod("never called");
        verify(mock, atLeastOnce()).someMethod("called at least once");
        verify(mock, atLeast(2)).someMethod("called at least twice");
        verify(mock, times(5)).someMethod("called five times");
        verify(mock, atMost(3)).someMethod("called at most 3 times");
}
```

### 10.5. Wrapping Java objects with Spy
@Spy or the spy() method can be used to wrap a real object. Every call, unless specified otherwise, is delegated to the object.

```java
import static org.mockito.Mockito.*;

// Lets mock a LinkedList
List list = new LinkedList();
List spy = spy(list);

//You have to use doReturn() for stubbing
doReturn("foo").when(spy).get(0);

// this would not work
// real method is called so spy.get(0)
// throws IndexOutOfBoundsException (list is still empty)
when(spy.get(0)).thenReturn("foo");
```

The verifyNoMoreInteractions() allows you to check that no other method was called.

### 10.6. Using @InjectMocks for dependency injection via Mockito
You also have the @InjectMocks annotation which tries to do constructor, method or field dependency injection based on the type. For example, assume that you have the following class.

```java
public class ArticleManager {
    private User user;
    private ArticleDatabase database;

    ArticleManager(User user) {
     this.user = user;
    }

    void setDatabase(ArticleDatabase database) { }
}
```

This class can be constructed via Mockito and its dependencies can be fulfullied with mock objects as demonstrated by the following code snippet.

```java
@RunWith(MockitoJUnitRunner.class)
public class ArticleManagerTest  {

       @Mock ArticleCalculator calculator;
       @Mock ArticleDatabase database;
       @Most User user;

       @Spy private UserProvider userProvider = new ConsumerUserProvider();

       @InjectMocks private ArticleManager manager; (1)

       @Test public void shouldDoSomething() {
               // assume that ArticleManager has a method called initialize which calls a method
               // addListener with an instance of ArticleListener
               manager.initialize();

           // validate that addListener was called
               verify(database).addListener(any(ArticleListener.class));
       }
}
```

* (1) creates an instance of ArticleManager and injects the mocks into it

For more information see http://docs.mockito.googlecode.com/hg/1.9.5/org/mockito/InjectMocks.html.

### 10.7. Capturing the arguments
The ArgumentCaptor class allows to access the arguments of method calls during the verification. This allows to capture these arguments of method calls and to use them for tests.

```java
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


public class MockitoTests {

        @Rule public MockitoRule rule = MockitoJUnit.rule();

        @Captor
    private ArgumentCaptor<List<String>> captor;


        @Test
    public final void shouldContainCertainListItem() {
                List<String> asList = Arrays.asList("someElement_test", "someElement");
        final List<String> mockedList = mock(List.class);
        mockedList.addAll(asList);

        verify(mockedList).addAll(captor.capture());
        final List<String> capturedArgument = captor.<List<String>>getValue();
        assertThat(capturedArgument, hasItem("someElement"));
    }
}
```

### 10.8. Limitations
Mockito has certain limitations. It can not test the following constructs:

* final classes
* anonymous classes
* primitive types

## 11. Using Mockito on Android
Mockito can also be directly used in Android unit tests by adding the dependency to it to the Gradle build file. To use it in instrumented Android tests (since the release 1.9.5). Which requires that dexmaker and dexmaker-mockito are also added as dependency in the Gradle build file.

```groovy
dependencies {
    testCompile 'junit:junit:4.12'
    // required if you want to use Mockito for unit tests
    testCompile 'org.mockito:mockito-core:1.+'
    // required if you want to use Mockito for Android instrumentation tests
    androidTestCompile 'org.mockito:mockito-core:1.+'
    androidTestCompile "com.google.dexmaker:dexmaker:1.2"
    androidTestCompile "com.google.dexmaker:dexmaker-mockito:1.2"
}
```

## 12. Exercise: Write an instrumented unit test using Mockito
### 12.1. Create Application under tests on Android
Create an Android application with the package name com.vogella.android.testing.mockito.contextmock. Add a static method	which allows to create an intent with certain parameters as in the following example.

```java
public static Intent createQuery(Context context, String query, String value) {
        // Reuse MainActivity for simplification
    Intent i = new Intent(context, MainActivity.class);
    i.putExtra("QUERY", query);
    i.putExtra("VALUE", value);
    return i;
}
```

### 12.2. Add the Mockito dependency to the app/build.gradle file

```groovy
dependencies {
    // the following is required to use Mockito and JUnit for your
    // instrumentation unit tests on the JVM
        androidTestCompile 'junit:junit:4.12'
    androidTestCompile 'org.mockito:mockito-core:2.0.57-beta'
    androidTestCompile 'com.android.support.test:runner:0.3'
    androidTestCompile "com.google.dexmaker:dexmaker:1.2"
    androidTestCompile "com.google.dexmaker:dexmaker-mockito:1.2"

    // the following is required to use Mockito and JUnit for your unit
    // tests on the JVM
    testCompile 'junit:junit:4.12'
    testCompile 'org.mockito:mockito-core:1.+'
}
```

### 12.3. Create test
Create a new unit test using Mockito to check that the intent is triggered with the correct extra data.

For this you mock the Context	object with Mockito as in the following example.

```java
package com.vogella.android.testing.mockitocontextmock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TextIntentCreation {

    @Test
    public void testIntentShouldBeCreated() {
        Context context = Mockito.mock(Context.class);
        Intent intent = MainActivity.createQuery(context, "query", "value");
        assertNotNull(intent);
        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertEquals("query", extras.getString("QUERY"));
        assertEquals("value", extras.getString("VALUE"));
    }
}
```

## 13. Exercise: Creating mock objects using Mockito
### 13.1. Target
Create an API, which can be mocked and use Mockito to do the job.

### 13.2. Create a sample Twitter API
Implement a TwitterClient, which works with ITweet instances. But imagine these ITweet instances are pretty cumbersome to get, e.g., by using a complex service, which would have to be started.

```java
public interface ITweet {

        String getMessage();
}
public class TwitterClient {

        public void sendTweet(ITweet tweet) {
                String message = tweet.getMessage();

                // send the message to Twitter
        }
}
```

### 13.3. Mocking ITweet instances
In order to avoid starting up a complex service to get ITweet instances, they can also be mocked by Mockito.

```java
@Test
public void testSendingTweet() {
        TwitterClient twitterClient = new TwitterClient();

        ITweet iTweet = mock(ITweet.class);

        when(iTweet.getMessage()).thenReturn("Using mockito is great");

        twitterClient.sendTweet(iTweet);
}
```

Now the TwitterClient can make use of a mocked ITweet instance and will get "Using Mockito is great" as message when calling getMessage() on the mocked ITweet.

### 13.4. Verify method invocation
Ensure that getMessage() is at least called once.

```java
@Test
public void testSendingTweet() {
        TwitterClient twitterClient = new TwitterClient();

        ITweet iTweet = mock(ITweet.class);

        when(iTweet.getMessage()).thenReturn("Using mockito is great");

        twitterClient.sendTweet(iTweet);

        verify(iTweet, atLeastOnce()).getMessage();
}
```

### 13.5. Validate
Run the test and validate that it is successful.

## 14. Mocking static methods
### 14.1. Powermock for mocking static methods
Mockito cannot mock static methods. For this you can use Powermock.

```java
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetworkReader {
    public static String getLocalHostname() {
        String hostname = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            // Get hostname
            hostname = addr.getHostName();
        } catch ( UnknownHostException e ) {
        }
        return hostname;
    }
}
```

To write a test which mocks away the NetworkReader as dependency you can use the following snippet.

```java
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith( PowerMockRunner.class )
@PrepareForTest( NetworkReader.class )
public class MyTest {

// Find the tests here

 @Test
public void testSomething() {
    mockStatic( NetworkUtil.class );
    when( NetworkReader.getLocalHostname() ).andReturn( "localhost" );

    // now test the class which uses NetworkReader
}

```



#### Using a wrapper instead of Powermock

Sometimes you can also use a wrapper around a static method, which can be mocked with Powermock.



#### Credits go to [Lars Vogel](http://www.vogella.com/) for his awesome tutorials!
* http://www.vogella.com/tutorials/JUnit/article.html
* http://www.vogella.com/tutorials/Mockito/article.html

