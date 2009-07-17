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

import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#selectPaths(javax.swing.JTree, String[])}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverSelectPathsTest extends JTreeDriverSelectCellTestCase {

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPathArrayIsNull() {
    String[] paths = null;
    driver().selectPaths(tree(), paths);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfPathArrayIsEmpty() {
    String[] paths = new String[0];
    driver().selectPaths(tree(), paths);
  }

  public void shouldSelectNodesByPaths() {
    clearTreeSelection();
    updateTreeWithDefaultSelectionModel();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    driver().selectPaths(tree(), paths);
    requireSelectedPaths(paths);
  }

  public void shouldThrowErrorWhenSelectingNodesByPathsInDisabledJTree() {
    disableTree();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    try {
      driver().selectPaths(tree(), paths);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNodesByPathsInNotShowingJTree() {
    hideWindow();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    try {
      driver().selectPaths(tree(), paths);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

}
