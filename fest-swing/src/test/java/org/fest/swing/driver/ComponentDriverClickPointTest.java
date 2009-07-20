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

import java.awt.Point;

import org.fest.swing.test.recorder.ClickRecorder;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentDriver#click(java.awt.Component, java.awt.Point)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverClickPointTest extends ComponentDriverTestCase {

  public void shouldClickComponentOnGivenPoint() {
    Point center = centerOf(button());
    Point where = new Point(center.x + 1, center.y + 1);
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    driver().click(button(), where);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(where)
                             .timesClicked(1);

  }

  public void shouldThrowErrorWhenClickingDisabledComponentAtGivenPoint() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    disableButton();
    try {
      driver().click(button(), new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenClickingNotShowingComponentAtGivenPoint() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button());
    hideWindow();
    try {
      driver().click(button(), new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

}
