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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import javax.swing.tree.*;

import org.fest.swing.annotation.RunsInEDT;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#requireSelection(javax.swing.JTree, String[])}</code>
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverRequireSelectedPathsTest extends JTreeDriverSelectCellTestCase {

  public void shouldPassIfPathIsSelected() {
    selectFirstChildOfRoot();
    driver().requireSelection(tree(), array("root/branch1"));
  }

  public void shouldPassIfPathsAreSelected() {
    selectBranch1AndBranch1_1();
    driver().requireSelection(tree(), array("root/branch1", "root/branch1/branch1.1"));
  }

  @RunsInEDT
  private void selectBranch1AndBranch1_1() {
    DefaultMutableTreeNode root = rootOf(tree());
    TreeNode branch1 = firstChildOf(root);
    TreePath rootBranch1 = new TreePath(array(root, branch1));
    TreePath rootBranch1Branch1_1 = new TreePath(array(root, branch1, firstChildOf(branch1)));
    select(array(rootBranch1, rootBranch1Branch1_1));
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedPathArrayIsNull() {
    driver().requireSelection(tree(), (String[])null);
  }

  public void shouldFailIfExpectingSelectedPathAndTreeHasNoSelection() {
    clearTreeSelection();
    try {
      driver().requireSelection(tree(), array("root/branch1"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("No selection");
    }
  }

  public void shouldFailIfSelectedPathIsNotEqualToExpectedSelection() {
    selectFirstChildOfRoot();
    try {
      driver().requireSelection(tree(), array("root/branch2"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("expecting selection:<['root/branch2']> but was:<[[root, branch1]]>");
    }
  }

}
