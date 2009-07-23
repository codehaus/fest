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

import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#collapseRow(javax.swing.JTree, int)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverCollapseRowTest extends JTreeDriverToggleCellTestCase {

  public void shouldCollapseNodeByRow() {
    int row = 0;
    requireRowExpanded(row);
    driver().collapseRow(tree(), row);
    requireRowCollapsed(row);
  }

  public void shouldNotDoAnythingIfRowAlreadyCollapsed() {
    int row = 5;
    requireRowCollapsed(row);
    driver().collapseRow(tree(), row);
    requireRowCollapsed(row);
  }

  @Test(groups = GUI, dataProvider = "outOfBoundRowIndices", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowToCollapseIsOutOfBounds(int invalidRow) {
    driver().collapseRow(tree(), invalidRow);
  }

  public void shouldThrowErrorWhenCollapsingRowInDisabledJTree() {
    disableTree();
    try {
      driver().collapseRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenCollapsingRowInNotShowingJTree() {
    hideWindow();
    try {
      driver().collapseRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
