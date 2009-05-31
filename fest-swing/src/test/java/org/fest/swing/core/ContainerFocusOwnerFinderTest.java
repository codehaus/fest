/*
 * Created on May 31, 2009
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
package org.fest.swing.core;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import java.awt.*;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ContainerFocusOwnerFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ContainerFocusOwnerFinderTest {

  private ContainerFocusOwnerFinder finder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
    finder = new ContainerFocusOwnerFinder();
  }

  public void shouldReturnNullIfContainerIsNotWindow() {
    Container c = createMock(Container.class);
    assertThat(finder.focusOwnerOf(c)).isNull();
  }

  public void shouldReturnNullIfWindowIsNotShowing() {
    final Window w = mockWindow();
    new EasyMockTemplate(w) {
      protected void expectations() {
        expect(w.isShowing()).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwnerOf(w)).isNull();
      }
    }.run();
  }

  public void shouldReturnFocusOwnerInWindow() {
    final Window w = mockWindow();
    final Component focusOwner = mockComponent();
    new EasyMockTemplate(w) {
      protected void expectations() {
        expect(w.isShowing()).andReturn(true);
        expect(w.getFocusOwner()).andReturn(focusOwner);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwnerOf(w)).isSameAs(focusOwner);
      }
    }.run();
  }

  public void shouldReturnFocusOwnerInOwnedWindowWhenTopWindowDoesNotHaveFocusOwner() {
    final Window w = mockWindow();
    final Window owned = mockWindow();
    final Component focusOwner = mockComponent();
    new EasyMockTemplate(w, owned) {
      protected void expectations() {
        expect(w.isShowing()).andReturn(true);
        expect(w.getFocusOwner()).andReturn(null);
        expect(w.getOwnedWindows()).andReturn(array(owned));
        expect(owned.getFocusOwner()).andReturn(focusOwner);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwnerOf(w)).isSameAs(focusOwner);
      }
    }.run();
  }

  public void shouldReturnNullIfTopWindowOrOwnedWindowsDoNotHaveFocusOwner() {
    final Window w = mockWindow();
    final Window owned = mockWindow();
    new EasyMockTemplate(w, owned) {
      protected void expectations() {
        expect(w.isShowing()).andReturn(true);
        expect(w.getFocusOwner()).andReturn(null);
        expect(w.getOwnedWindows()).andReturn(array(owned));
        expect(owned.getFocusOwner()).andReturn(null);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwnerOf(w)).isNull();
      }
    }.run();
  }

  private static Window mockWindow() {
    return createMock(Window.class);
  }

  private static Component mockComponent() {
    return createMock(Component.class);
  }
}
