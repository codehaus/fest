/*
 * Created on Nov 1, 2007
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.hierarchy.JFrameContentPaneQuery.contentPaneOf;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialTestCase;
import org.fest.swing.test.swing.TestMdiWindow;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link ParentFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ParentFinderTest extends SequentialTestCase {

  private ParentFinder finder;

  @Override protected final void onSetUp() {
    finder = new ParentFinder();
  }

  @Test
  public void shouldReturnParentOfComponent() {
    final MyWindow window = MyWindow.createNew();
    try {
      Container parent = findParent(finder, window.textField);
      assertThat(parent).isSameAs(contentPaneOf(window));
    } finally {
      window.destroy();
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTextField textField = new JTextField();

    private MyWindow() {
      super(ParentFinderTest.class);
      addComponents(textField);
    }
  }

  @Test
  public void shouldReturnParentOfInternalFrame() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    JInternalFrame internalFrame = window.internalFrame();
    try {
      assertThat(findParent(finder, internalFrame)).isNotNull()
                                                   .isSameAs(desktopPaneOf(internalFrame));
    } finally {
      window.destroy();
    }
  }

  @RunsInEDT
  private static Container findParent(final ParentFinder finder, final Component c) {
    return execute(new GuiQuery<Container>() {
      protected Container executeInEDT() {
        return finder.parentOf(c);
      }
    });
  }

  @RunsInEDT
  private static JDesktopPane desktopPaneOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopPane>() {
      protected JDesktopPane executeInEDT() {
        return internalFrame.getDesktopIcon().getDesktopPane();
      }
    });
  }
}
