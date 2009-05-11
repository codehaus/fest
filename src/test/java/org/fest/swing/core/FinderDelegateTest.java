/*
 * Created on Jun 5, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.core;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.util.Collections.list;

import java.awt.Component;
import java.awt.Container;
import java.util.*;

import javax.swing.JButton;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FinderDelegate}</code>.
 *
 * @author Alex Ruiz
 */
public class FinderDelegateTest {

  private ComponentHierarchy hierarchy;
  private Container dialog;
  private Component textField;
  private Component button;
  private FinderDelegate finder;

  @BeforeMethod public void setUp() {
    hierarchy = createMock(ComponentHierarchy.class);
    dialog = dialog().createNew();
    textField = textField().createNew();
    button = button().createNew();
    finder = new FinderDelegate();
  }

  @Test public void shouldReturnComponentsMatchingComponentMatcher() {
    final ComponentMatcher matcher = new ComponentMatcher() {
      public boolean matches(Component c) {
        return c instanceof JButton;
      }
    };
    final List<Component> empty = new ArrayList<Component>();
    new EasyMockTemplate(hierarchy) {
      protected void expectations() {
        hierarchy.roots();
        expectLastCall().andReturn(list(dialog));
        hierarchy.childrenOf(dialog);
        expectLastCall().andReturn(list(textField, button));
        hierarchy.childrenOf(textField);
        expectLastCall().andReturn(empty);
        hierarchy.childrenOf(button);
        expectLastCall().andReturn(empty);
      }

      protected void codeToTest() {
        Collection<Component> found = finder.find(hierarchy, matcher);
        assertThat(found).containsOnly(button);
      }
    }.run();
  }

  @Test public void shouldReturnComponentsMatchingGenericTypeMatcher() {
    final GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      protected boolean isMatching(JButton b) {
        return true;
      }
    };
    final List<Component> empty = new ArrayList<Component>();
    new EasyMockTemplate(hierarchy) {
      protected void expectations() {
        hierarchy.roots();
        expectLastCall().andReturn(list(dialog));
        hierarchy.childrenOf(dialog);
        expectLastCall().andReturn(list(textField, button));
        hierarchy.childrenOf(textField);
        expectLastCall().andReturn(empty);
        hierarchy.childrenOf(button);
        expectLastCall().andReturn(empty);
      }

      protected void codeToTest() {
        Collection<JButton> found = finder.find(hierarchy, matcher);
        assertThat(found).containsOnly(button);
      }
    }.run();
  }
}
