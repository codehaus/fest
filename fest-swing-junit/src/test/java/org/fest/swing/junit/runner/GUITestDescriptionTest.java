/*
 * Created on Nov 19, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.runner;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link GUITestDescription}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestDescriptionTest {
  
  @Test public void shouldCreateDescriptionForGUITest() throws Exception {
    Class<FakeGUITest> testClass = FakeGUITest.class;
    Method testMethod = testClass.getDeclaredMethod("successfulGUITest");
    GUITestDescription description = new GUITestDescription(testClass, testMethod);
    assertThat(description.testClass()).isSameAs(testClass);
    assertThat(description.testMethod()).isSameAs(testMethod);
    assertThat(description.isGUITest()).isTrue();
  }

  @Test public void shouldCreateDescriptionForNonGUITest() throws Exception {
    Class<FakeGUITest> testClass = FakeGUITest.class;
    Method testMethod = testClass.getDeclaredMethod("successfulNonGUITest");
    GUITestDescription description = new GUITestDescription(testClass, testMethod);
    assertThat(description.testClass()).isSameAs(testClass);
    assertThat(description.testMethod()).isSameAs(testMethod);
    assertThat(description.isGUITest()).isFalse();
  }
}
