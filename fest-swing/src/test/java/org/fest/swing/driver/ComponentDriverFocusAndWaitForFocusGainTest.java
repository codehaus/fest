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

import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;

import java.util.concurrent.CountDownLatch;

import org.fest.swing.test.util.StopWatch;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentDriver#focusAndWaitForFocusGain(java.awt.Component)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverFocusAndWaitForFocusGainTest extends ComponentDriverTestCase {

  public void shouldGiveFocusToComponentAndWaitTillComponentIsFocused() {
    assertThatButtonDoesNotHaveFocus();
    button().waitToRequestFocus();
    final CountDownLatch done = new CountDownLatch(1);
    StopWatch stopWatch = startNewStopWatch();
    new Thread() {
      @Override public void run() {
        driver().focusAndWaitForFocusGain(button());
        done.countDown();
      }
    }.start();
    try {
      done.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    stopWatch.stop();
    assertThatButtonHasFocus();
    assertThatWaited(stopWatch, TIME_TO_WAIT_FOR_FOCUS_GAIN);
  }

  public void shouldThrowErrorWhenGivingFocusToDisabledComponenAndWaiting() {
    disableButton();
    try {
      driver().focusAndWaitForFocusGain(button());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenGivingFocusToNotShowingComponenAndWaiting() {
    hideWindow();
    try {
      driver().focusAndWaitForFocusGain(button());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
