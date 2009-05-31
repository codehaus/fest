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

import java.awt.Component;
import java.awt.Container;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link HierarchyBasedFocusOwnerFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class HierarchyBasedFocusOwnerFinderTest {

  private ContainerFocusOwnerFinder delegate;
  private HierarchyRootsSource rootsSource;
  private HierarchyBasedFocusOwnerFinder finder;

  @BeforeMethod public void setUp() {
    delegate = createMock(ContainerFocusOwnerFinder.class);
    rootsSource = createMock(HierarchyRootsSource.class);
    finder = new HierarchyBasedFocusOwnerFinder(delegate, rootsSource);
  }

  public void shouldReturnFocusOwnerFromDelegate() {
    final Container c = createMock(Container.class);
    final Container[] roots = array(c);
    final Component focusOwner = createMock(Component.class);
    new EasyMockTemplate(delegate, rootsSource) {
      protected void expectations() {
        expect(rootsSource.existingHierarchyRoots()).andReturn(roots);
        expect(delegate.focusOwnerOf(c)).andReturn(focusOwner);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwner()).isSameAs(focusOwner);
      }
    }.run();
  }

  public void shouldReturnNullIfDelegateDidNotFindFocusOwner() {
    final Container c = createMock(Container.class);
    final Container[] roots = array(c);
    new EasyMockTemplate(delegate, rootsSource) {
      protected void expectations() {
        expect(rootsSource.existingHierarchyRoots()).andReturn(roots);
        expect(delegate.focusOwnerOf(c)).andReturn(null);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwner()).isNull();
      }
    }.run();
  }

  public void shouldReturnNullIfThereAreNoRootContainers() {
    final Container[] roots = new Container[0];
    new EasyMockTemplate(delegate, rootsSource) {
      protected void expectations() {
        expect(rootsSource.existingHierarchyRoots()).andReturn(roots);
      }

      protected void codeToTest() {
        assertThat(finder.focusOwner()).isNull();
      }
    }.run();
  }
}
