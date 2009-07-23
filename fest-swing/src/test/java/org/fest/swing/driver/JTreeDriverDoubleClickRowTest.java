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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Point;

import javax.swing.JTree;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.recorder.ClickRecorder;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#doubleClickRow(JTree, int)}</code>
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverDoubleClickRowTest extends JTreeDriverTestCase {

  public void shouldDoubleClickRow() {
    ClickRecorder recorder = ClickRecorder.attachTo(tree());
    int row = 5;
    driver().doubleClickRow(tree(), row);
    assertThat(recorder).clicked(MouseButton.LEFT_BUTTON)
                        .timesClicked(2);
    assertThat(rowAt(recorder.pointClicked())).isEqualTo(row);
  }

  @RunsInEDT
  private int rowAt(Point p) {
    return rowAtPoint(tree(), p);
  }
  
  @RunsInEDT
  private static int rowAtPoint(final JTree tree, final Point p) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tree.getRowForLocation(p.x, p.y);
      }
    });
  }

  public void shouldThrowErrorWhenDoubleClickingRowInDisabledJTree() {
    disableTree();
    try {
      driver().doubleClickRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDoubleClickingRowInNotShowingJTree() {
    hideWindow();
    try {
      driver().doubleClickRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }}
