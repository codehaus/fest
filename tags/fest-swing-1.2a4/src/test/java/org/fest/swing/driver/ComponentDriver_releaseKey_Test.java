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
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.driver;

import static java.awt.event.KeyEvent.VK_A;
import static org.fest.swing.test.core.CommonAssertions.*;

import org.junit.Test;

/**
 * Tests for <code>{@link ComponentDriver#pressKey(java.awt.Component, int)}</code> and
 * <code>{@link ComponentDriver#releaseKey(java.awt.Component, int)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_releaseKey_Test extends ComponentDriver_TestCase {

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    disableTextField();
    try {
      driver.pressKey(window.textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    assertThatTextFieldIsEmpty();
    try {
      driver.pressKey(window.textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenReleasingKeyInDisabledComponent() {
    disableTextField();
    try {
      driver.releaseKey(window.textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenReleasingKeyInNotShowingComponent() {
    try {
      driver.releaseKey(window.textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

}
