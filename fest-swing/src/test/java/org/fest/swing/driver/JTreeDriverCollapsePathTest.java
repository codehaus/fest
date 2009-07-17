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
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JTree;

import org.fest.swing.exception.LocationUnavailableException;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#collapsePath(JTree, String)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverCollapsePathTest extends JTreeDriverToggleCellTestCase {

  public void shouldCollapseNodeByPath() {
    int row = 0;
    requireRowExpanded(row);
    driver().collapsePath(tree(), "root");
    requireRowCollapsed(row);
  }

  public void shouldNotDoAnythingIfPathAlreadyCollapsed() {
    int row = 2;
    requireRowCollapsed(row);
    driver().collapsePath(tree(), "root");
    requireRowCollapsed(row);
  }

  public void shouldThrowErrorIfPathToCollapseDoesNotExist() {
    try {
      driver().collapsePath(tree(), "somePath");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'somePath'");
    }
  }

  public void shouldThrowErrorWhenCollapsingPathInDisabledJTree() {
    disableTree();
    try {
      driver().collapsePath(tree(), "root");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenCollapsingPathInNotShowingJTree() {
    hideWindow();
    try {
      driver().collapsePath(tree(), "root");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
