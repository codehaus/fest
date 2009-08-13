/*
 * Created on Nov 12, 2007
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

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.hierarchy.JFrameContentPaneQuery.contentPaneOf;
import static org.fest.util.Arrays.array;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.test.awt.ToolkitStub;
import org.fest.swing.test.core.SequentialTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link NewHierarchy}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class NewHierarchyTest extends SequentialTestCase {

  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private ToolkitStub toolkit;
  private WindowFilter filter;
  private MyWindow window;

  @Override protected final void onSetUp() {
    toolkit = ToolkitStub.createNew();
    window = MyWindow.createNew();
    filter = new WindowFilter();
  }

  @Override protected final void onTearDown() {
    window.destroy();
  }

  @Test
  public void shouldIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit() {
    new NewHierarchy(toolkit, filter, true);
    assertThat(filter.isIgnored(window)).isTrue();
    assertThatTransientWindowListenerWasAddedToToolkit();
  }

  @Test
  public void shouldNotIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit() {
    new NewHierarchy(toolkit, filter, false);
    assertThat(filter.isIgnored(window)).isFalse();
    assertThatTransientWindowListenerWasAddedToToolkit();
  }

  private void assertThatTransientWindowListenerWasAddedToToolkit() {
    List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
    assertThat(eventListeners).hasSize(1);
    WeakEventListener weakEventListener = eventListeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isInstanceOf(TransientWindowListener.class);
  }

  @Test
  public void shouldReturnNoChildrenIfComponentIsFiltered() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.childrenOf(window)).isEmpty();
  }

  @Test
  public void shouldReturnUnfilteredChildrenOfUnfilteredComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    filter.ignore(window.textField);
    assertThat(hierarchy.childrenOf(contentPaneOf(window))).containsOnly(window.comboBox);
  }

  @Test
  public void shouldNotContainFilteredComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.contains(window)).isFalse();
  }

  @Test
  public void shouldContainUnfilteredComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    assertThat(hierarchy.contains(window)).isTrue();
  }

  @Test
  public void shouldNotContainFilteredWindowsInRootWindows() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.roots()).excludes(window);
  }

  @Test
  public void shouldContainUnfilteredWindowsInRootWindows() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    assertThat(hierarchy.roots()).contains(window);
  }

  @Test
  public void shouldRecognizeGivenComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.roots()).excludes(window);
    hierarchy.recognize(window);
    assertThat(hierarchy.roots()).contains(window);
  }

  // TODO Test method dispose(Window)

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

    final JComboBox comboBox = new JComboBox(array("One", "Two"));
    final JTextField textField = new JTextField(20);

    private MyWindow() {
      super(NewHierarchyTest.class);
      addComponents(comboBox, textField);
    }
  }
}
