/*
 * Created on Mar 19, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.constructor;
import static org.fest.reflect.core.Reflection.staticInnerClass;
import static org.junit.runner.Description.createSuiteDescription;
import junit.framework.*;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTaskMirrorImpl;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.junit.runner.Description;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Tests}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TestsTest {

  public void shouldReturnUnknownIfTestIsNull() {
    assertThat(Tests.testMethodNameFrom(null)).isEqualTo("unknown");
  }

  public void shouldReturnToStringAsMethodNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello");
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("hello");
  }

  public void shouldReturnToStringWithoutClassNameAsMethodNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello(world)");
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("hello");
  }

  public void shouldReturnNameAsMethodNameIfTestIsInstanceOfTestCase() {
    TestCase test = new TestCase("Leia") {};
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("Leia");
  }

  public void shouldReturnNameByCallingNameMethodOfTest() {
    MyTestWithName test = new MyTestWithName();
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("name");
  }

  private static class MyTestWithName implements junit.framework.Test {
    public String name() { return "name"; }
    public int countTestCases() { return 0; }
    public void run(TestResult result) {}
  }

  public void shouldReturnNameByCallingGetNameMethodOfTest() {
    MyTestWithGetName test = new MyTestWithGetName();
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("name");
  }

  private static class MyTestWithGetName implements junit.framework.Test {
    public String getName() { return "name"; }
    public int countTestCases() { return 0; }
    public void run(TestResult result) {}
  }

  public void shouldReturnUnknownIfTestDoesNotHaveNameOrGetNameMethods() {
    junit.framework.Test test = new junit.framework.Test() {
      public int countTestCases() { return 0; }
      public void run(TestResult result) {}
    };
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("unknown");
  }

  public void shouldReturnTestClassNameFromVmExitErrorTest() {
    Class<?> vmExitErrorTestClass = staticInnerClass("VmExitErrorTest").in(JUnitTaskMirrorImpl.class).get();
    Object test = constructor().withParameterTypes(String.class, JUnitTest.class, String.class)
                               .in(vmExitErrorTestClass)
                               .newInstance("someMessage", new JUnitTest("testClassName"), "testName");
    assertThat(test).isInstanceOf(junit.framework.Test.class);
    assertThat(Tests.testClassNameFrom((junit.framework.Test)test)).isEqualTo("testClassName");
  }

  public void shouldReturnToStringAsClassNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello");
    assertThat(Tests.testClassNameFrom(test)).isEqualTo(JUnit4TestCaseFacade.class.getName());
  }

  public void shouldReturnToStringWithoutClassNameAsClasNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello(world)");
    assertThat(Tests.testClassNameFrom(test)).isEqualTo("world");
  }

  private JUnit4TestCaseFacade createJUnit4TestCaseFacade(String description) {
    return constructor().withParameterTypes(Description.class)
                        .in(JUnit4TestCaseFacade.class)
                        .newInstance(createSuiteDescription(description));
  }

  public void shouldReturnToStringAsClassNameIfTestIsInstanceOfTestCase() {
    TestCase test = new TestCase("Leia") {};
    assertThat(Tests.testClassNameFrom(test)).isEqualTo(test.getClass().getName());
  }
}
