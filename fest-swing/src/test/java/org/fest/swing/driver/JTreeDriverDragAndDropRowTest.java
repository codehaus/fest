/*
 * Created on Jul 16, 2009
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
import static org.fest.swing.driver.JTreeDriverTestCase.MyWindow.decorate;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.swing.TreeNodeFactory.node;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestTree;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#drag(javax.swing.JTree, int)}</code> and
 * <code>{@link JTreeDriver#drop(javax.swing.JTree, int)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverDragAndDropRowTest extends JTreeDriverTestCase {
  private TestTree dropTree;

  @RunsInEDT
  @Override void beforeShowingWindow() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        dropTree = new TestTree(new DefaultTreeModel(node("root")));
        window().add(decorate(dropTree));
      }
    });
  }

  public void shouldDragAndDropUsingGivenRows() {
    driver().drag(tree(), 2);
    driver().drop(dropTree, 0);
    DefaultMutableTreeNode sourceRoot = rootOf(tree());
    assertThat(childCountOf(sourceRoot)).isEqualTo(4);
    DefaultMutableTreeNode targetRoot = rootOf(dropTree);
    assertThat(childCountOf(targetRoot)).isEqualTo(1);
    assertThat(textOf(firstChildOf(targetRoot))).isEqualTo("branch2");
  }

  @Test(groups = GUI, dataProvider = "outOfBoundRowIndices", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowToDragIsOutOfBounds(int invalidRow) {
    driver().drag(tree(), invalidRow);
  }

  public void shouldThrowErrorWhenDraggingRowInDisabledJTree() {
    disableTree();
    try {
      driver().drag(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDraggingRowInNotShowingJTree() {
    hideWindow();
    try {
      driver().drag(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "outOfBoundRowIndices", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowToDropIsOutOfBounds(int invalidRow) {
    driver().drop(tree(), invalidRow);
  }

  public void shouldThrowErrorWhenDroppingRowInDisabledJTree() {
    disableTree();
    try {
      driver().drop(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDroppingRowInNotShowingJTree() {
    hideWindow();
    try {
      driver().drop(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
