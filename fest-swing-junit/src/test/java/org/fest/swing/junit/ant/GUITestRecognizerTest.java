/*
 * Created on Apr 24, 2009
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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link GUITestRecognizer}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GUITestRecognizerTest {

  private static final String TEST_CLASS_NAME = SomeTest.class.getName();

  private GUITestRecognizer recognizer;

  @BeforeClass public void setUpOnce() {
    recognizer = new GUITestRecognizer();
  }

  public void shouldReturnIsGuiTestIfTestMethodHasAnnotation() {
    boolean isGuiTest = recognizer.isGUITest(TEST_CLASS_NAME, "guiTest");
    assertThat(isGuiTest).isTrue();
  }

  public void shouldReturnIsNotGuiTestIfTestMethodDoesNotHaveAnnotation() {
    boolean isGuiTest = recognizer.isGUITest(TEST_CLASS_NAME, "nonGuiTest");
    assertThat(isGuiTest).isFalse();
  }

  public void shouldReturnIsNotGuiTestIfTestMethodInCaseOfError() {
    boolean isGuiTest = recognizer.isGUITest(TEST_CLASS_NAME, "someMethod");
    assertThat(isGuiTest).isFalse();
  }
}
