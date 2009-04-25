/*
 * Created on Mar 16, 2009
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
package org.fest.swing.junit.v4_3_1.runner;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TestInfo}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TestInfoTest {

  private Object test;
  private Class<?> type;

  @BeforeClass public void setUpOnce() {
    test = new Object();
    type = SomeGuiTest.class;
  }

  public void shouldCreateTestInfoForGuiTestMethod() throws Exception {
    Method method = type.getDeclaredMethod("successfulGUITest");
    TestInfo testInfo = new TestInfo(test, type, method);
    assertTestInfoCreatedCorrectly(testInfo, method);
    assertThat(testInfo.isGUITest()).isTrue();
    assertThat(testInfo.screenshotFileName()).isEqualTo("org.fest.swing.junit.v4_3_1.runner.SomeGuiTest.successfulGUITest");
  }

  public void shouldCreateTestInfoForNonGuiTestMethod() throws Exception {
    Method method = method("successfulNonGUITest");
    TestInfo testInfo = new TestInfo(test, type, method);
    assertTestInfoCreatedCorrectly(testInfo, method);
    assertThat(testInfo.isGUITest()).isFalse();
  }

  private Method method(String name) throws Exception {
    return type.getDeclaredMethod(name);
  }

  private void assertTestInfoCreatedCorrectly(TestInfo testInfo, Method testMethod) {
    assertThat(testInfo.test()).isSameAs(test);
    assertThat(testInfo.type()).isSameAs(type);
    assertThat(testInfo.method()).isSameAs(testMethod);
    assertThat(testInfo.description().getDisplayName()).contains(type.getName())
                                                       .contains(testMethod.getName());
  }
}
