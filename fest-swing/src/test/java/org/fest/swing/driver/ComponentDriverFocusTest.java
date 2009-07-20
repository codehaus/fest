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

import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.timing.Pause.pause;

import org.fest.swing.timing.Condition;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentDriver#focus(java.awt.Component)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverFocusTest extends ComponentDriverTestCase {

  public void shouldGiveFocusToComponent() {
    driver().focus(button());
    pause(new Condition("Component has focus") {
      public boolean test() {
        return hasFocus(button());
      }
    });
  }

  public void shouldThrowErrorWhenGivingFocusToDisabledComponent() {
    disableButton();
    try {
      driver().focus(button());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatButtonDoesNotHaveFocus();
  }

  public void shouldThrowErrorWhenGivingFocusToNotShowingComponent() {
    hideWindow();
    try {
      driver().focus(button());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatButtonDoesNotHaveFocus();
  }
}
