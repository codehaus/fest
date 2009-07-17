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
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JPopupMenu;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#showPopupMenu(javax.swing.JTree, int)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverShowPopupMenuAtRowTest extends JTreeDriverShowPopupMenuAtCellTestCase {

  public void shouldShowPopupMenuAtRow() {
    JPopupMenu popupMenu = driver().showPopupMenu(tree(), 0);
    assertThat(popupMenu).isSameAs(popupMenu());
  }

  // TODO test with invalid state and invalid input
}
