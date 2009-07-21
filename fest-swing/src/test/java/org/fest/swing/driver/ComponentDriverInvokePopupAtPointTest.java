/*
 * Created on Jul 20, 2009
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
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Point;

import javax.swing.JPopupMenu;

import org.fest.swing.test.recorder.ClickRecorder;
import org.testng.annotations.Test;

/**
 * Test for <code>{@link ComponentDriver#invokePopupMenu(java.awt.Component, java.awt.Point)}</code>
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ComponentDriverInvokePopupAtPointTest extends ComponentDriverInvokePopupTestCase {

  public void shouldShowJPopupMenu() {
    Point p = new Point(8, 6);
    ClickRecorder recorder = ClickRecorder.attachTo(textField());
    JPopupMenu popupMenu = driver().invokePopupMenu(textField(), p);
    assertThat(popupMenu).isSameAs(popupMenu());
    assertThat(recorder).wasRightClicked().timesClicked(1).clickedAt(p);
  }

  public void shouldThrowErrorWhenShowingPopupMenuInDisabledComponent() {
    assertThatTextFieldIsEmpty();
    disableTextField();
    try {
      driver().invokePopupMenu(textField(), new Point(8, 6));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenShowingPopupMenuInNotShowingComponent() {
    assertThatTextFieldIsEmpty();
    hideWindow();
    try {
      driver().invokePopupMenu(textField(), new Point(8, 6));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }}
