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
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.fest.swing.core.MouseButton;
import org.fest.swing.test.recorder.ClickRecorder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentDriver#click(java.awt.Component, org.fest.swing.core.MouseButton)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverClickMouseButtonTest extends ComponentDriverTestCase {

  @Test(dataProvider = "mouseButtons", groups = GUI)
  public void shouldClickComponentWithGivenMouseButtonOnce(MouseButton mouseButton) {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    driver().click(button(), mouseButton);
    assertThat(clickRecorder).wasClickedWith(mouseButton)
                             .clickedAt(centerOf(button()))
                             .timesClicked(1);
  }

  @DataProvider(name = "mouseButtons") public Object[][] mouseButtons() {
    return new Object[][] { { LEFT_BUTTON }, { MIDDLE_BUTTON }, { RIGHT_BUTTON } };
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMouseButtonIsNull() {
    driver().click(button(), (MouseButton)null);
  }

  public void shouldThrowErrorWhenClickingDisabledComponentWithGivenMouseButton() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    disableButton();
    try {
      driver().click(button(), RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenClickingNotShowingComponentWithGivenMouseButton() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    hideWindow();
    try {
      driver().click(button(), RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }
}
