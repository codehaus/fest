/*
 * Created on Oct 25, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestMdiWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.hierarchy.JInternalFrameIconifyTask.iconify;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.swing.TestMdiWindow.createAndShowNewWindow;

/**
 * Tests for <code>{@link JDesktopPaneChildrenFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JDesktopPaneChildrenFinderTest {

  private JDesktopPaneChildrenFinder finder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    finder = new JDesktopPaneChildrenFinder();
  }

  public void shouldReturnEmptyCollectionIfComponentIsNotJDesktopPane() {
    Container container = textField().createNew();
    assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
  }

  public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }

  @Test(groups = GUI)
  public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() {
    ScreenLock.instance().acquire(this);
    final TestMdiWindow window = createAndShowNewWindow(getClass());
    iconify(window.internalFrame());
    Collection<Component> children = execute(new GuiQuery<Collection<Component>>() {
      protected Collection<Component> executeInEDT() {
        return finder.nonExplicitChildrenOf(window.desktop());
      }
    });
    try {
      assertThat(children).containsOnly(window.internalFrame());
    } finally {
      try {
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }
}
