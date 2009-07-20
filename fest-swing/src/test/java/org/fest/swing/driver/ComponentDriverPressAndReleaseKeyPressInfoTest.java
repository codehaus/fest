/*
 * Created on Jul 19, 2009
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
package org.fest.swing.driver;

import static java.awt.Event.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentDriver#pressAndReleaseKey(java.awt.Component, org.fest.swing.core.KeyPressInfo)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverPressAndReleaseKeyPressInfoTest extends ComponentDriverTestCase {

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfKeyPressInfoIsNull() {
    driver().pressAndReleaseKey(button(), null);
  }

  public void shouldPressAndReleaseKeyInSpecifiedKeyPressInfo() {
    assertThatTextFieldIsEmpty();
    driver().pressAndReleaseKey(textField(), keyCode(VK_A).modifiers(SHIFT_MASK));
    assertThatTextInTextFieldIs("A");
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeyInDisabledComponent() {
    assertThatTextFieldIsEmpty();
    disableTextField();
    try {
      driver().pressAndReleaseKey(textField(), keyCode(VK_A).modifiers(SHIFT_MASK));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeyInNotShowingComponent() {
    assertThatTextFieldIsEmpty();
    hideWindow();
    try {
      driver().pressAndReleaseKey(textField(), keyCode(VK_A).modifiers(SHIFT_MASK));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }
}
