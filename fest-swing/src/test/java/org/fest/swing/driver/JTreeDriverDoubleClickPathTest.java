/*
 * Created on Jul 23, 2009
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

import javax.swing.JTree;

import org.fest.swing.core.MouseButton;
import org.fest.swing.test.recorder.ClickRecorder;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#doubleClickPath(JTree, String)}</code>
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverDoubleClickPathTest extends JTreeDriverDoubleClickCellTestCase {

  public void shouldDoubleClickPath() {
    ClickRecorder recorder = ClickRecorder.attachTo(tree());
    driver().doubleClickPath(tree(), "root");
    assertThat(recorder).clicked(MouseButton.LEFT_BUTTON)
                        .timesClicked(2);
    assertThat(rowAt(recorder.pointClicked())).isEqualTo(0);
  }

  public void shouldThrowErrorWhenDoubleClickingPathInDisabledJTree() {
    disableTree();
    try {
      driver().doubleClickPath(tree(), "root");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDoubleClickingPathInNotShowingJTree() {
    hideWindow();
    try {
      driver().doubleClickPath(tree(), "root");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }}
