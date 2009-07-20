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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.fest.swing.test.recorder.ClickRecorder;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentDriver#doubleClick(java.awt.Component)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverDoubleClickTest extends ComponentDriverTestCase {

  public void shouldDoubleClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    driver().doubleClick(button());
    assertThat(clickRecorder).wasDoubleClicked()
                             .clickedAt(centerOf(button()));
  }

  public void shouldThrowErrorWhenDoubleClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    disableButton();
    try {
      driver().doubleClick(button());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenDoubleClickingNotShowingComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    hideWindow();
    try {
      driver().doubleClick(button());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

}