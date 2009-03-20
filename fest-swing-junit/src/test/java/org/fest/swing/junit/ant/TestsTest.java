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
import static org.junit.runner.Description.createSuiteDescription;
import junit.framework.*;

import org.junit.runner.Description;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Tests}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TestsTest {

  public void shouldReturnUnknownIfTestIsNull() {
    assertThat(Tests.methodNameFrom(null)).isEqualTo("unknown");
  }

  public void shouldReturnToStringIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = constructor().withParameterTypes(Description.class)
                                             .in(JUnit4TestCaseFacade.class)
                                             .newInstance(createSuiteDescription("hello"));
    assertThat(Tests.methodNameFrom(test)).isEqualTo("hello");
  }

  public void shouldReturnToStringWithoutClassNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = constructor().withParameterTypes(Description.class)
                                             .in(JUnit4TestCaseFacade.class)
                                             .newInstance(createSuiteDescription("hello(world)"));
    assertThat(Tests.methodNameFrom(test)).isEqualTo("hello");
  }

  public void shouldReturnNameIfTestIsInstanceOfTestCase() {
    TestCase test = new TestCase("Leia") {};
    assertThat(Tests.methodNameFrom(test)).isEqualTo("Leia");
  }

  public void shouldReturnNameByCallingNameMethodOfTest() {
    class MyTest implements junit.framework.Test {
      public String name() { return "name"; }
      public int countTestCases() { return 0; }
      public void run(TestResult result) {}
    }
    MyTest test = new MyTest();
    assertThat(Tests.methodNameFrom(test)).isEqualTo("name");
  }

  public void shouldReturnNameByCallingGetNameMethodOfTest() {
    class MyTest implements junit.framework.Test {
      public String getName() { return "name"; }
      public int countTestCases() { return 0; }
      public void run(TestResult result) {}
    }
    MyTest test = new MyTest();
    assertThat(Tests.methodNameFrom(test)).isEqualTo("name");
  }
}
