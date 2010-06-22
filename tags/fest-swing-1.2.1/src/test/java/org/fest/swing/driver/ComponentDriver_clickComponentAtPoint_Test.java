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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.test.core.CommonAssertions.*;

import java.awt.Point;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for <code>{@link ComponentDriver#click(java.awt.Component, java.awt.Point)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_clickComponentAtPoint_Test extends ComponentDriver_TestCase {

  @Test
  public void should_click_Component_at_given_point() {
    showWindow();
    Point center = centerOf(window.button);
    Point where = new Point(center.x + 1, center.y + 1);
    ClickRecorder clickRecorder = ClickRecorder.attachTo(window.button);
    driver.click(window.button, where);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(where)
                             .timesClicked(1);

  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(window.button);
    disableButton();
    try {
      driver.click(window.button, new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(window.button);
    try {
      driver.click(window.button, new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }
}
