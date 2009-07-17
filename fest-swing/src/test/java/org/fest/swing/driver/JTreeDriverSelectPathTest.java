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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.assertions.StringAssert;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.LocationUnavailableException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#selectPath(javax.swing.JTree, String)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverSelectPathTest extends JTreeDriverSelectCellTestCase {

  public void shouldThrowErrorIfPathToSelectNotFound() {
    try {
      driver().selectPath(tree(), "another");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test(groups = GUI, dataProvider = "selectionPath")
  public void shouldSelectNodeByPath(String treePath) {
    clearTreeSelection();
    driver().selectPath(tree(), treePath);
    requireSelectedPath(treePath);
  }

  @RunsInEDT
  private StringAssert requireSelectedPath(String treePath) {
    return assertThat(textOf(selectionPathOf(tree()))).isEqualTo(treePath);
  }

  @RunsInEDT
  private static TreePath selectionPathOf(final JTree tree) {
    return execute(new GuiQuery<TreePath>() {
      protected TreePath executeInEDT() {
        return tree.getSelectionPath();
      }
    });
  }

  @DataProvider(name = "selectionPath")
  public Object[][] selectionPath() {
    return new Object[][] {
        { "root/branch1" }, { "root/branch1/branch1.2" }, { "root" }
    };
  }

  public void shouldThrowErrorWhenSelectingNodeByPathInDisabledJTree() {
    disableTree();
    try {
      driver().selectPath(tree(), "root/branch1");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNodeByPathInNotShowingJTree() {
    hideWindow();
    try {
      driver().selectPath(tree(), "root/branch1");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

}
