/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.test.builder.JTrees;

import static javax.swing.tree.TreeSelectionModel.CONTIGUOUS_TREE_SELECTION;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.task.JTreeSelectRowTask.selectRow;
import static org.fest.swing.test.task.JTreeSetSelectionModelTask.setSelectionModel;

/**
 * Tests for <code>{@link JTreeFormatter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JTreeFormatterTest {

  private JTree tree;
  private JTreeFormatter formatter;
  
  @BeforeMethod public void setUp() {
    tree = JTrees.tree().withName("tree")
                        .withValues("One", "Two", "Three")
                        .createNew();
    formatter = new JTreeFormatter();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJTree() {
    formatter.format(textField().createNew());
  }
  
  public void shouldFormatJTree() {
    setContiguousSelectionModeTo(tree);
    selectSecondRowIn(tree);
    String formatted = formatter.format(tree);
    assertThat(formatted).contains(tree.getClass().getName())
                         .contains("name='tree'")
                         .contains("selectionCount=1")
                         .contains("selectionPaths=['[root, Two]']")
                         .contains("selectionMode=CONTIGUOUS_TREE_SELECTION")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  private static void setContiguousSelectionModeTo(final JTree tree) {
    TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
    selectionModel.setSelectionMode(CONTIGUOUS_TREE_SELECTION);
    setSelectionModel(tree, selectionModel);
  }

  private static void selectSecondRowIn(final JTree tree) {
    selectRow(tree, 1);
  }
  
  public void shouldFormatJTreeWithoutSelectionModel() {
    setDiscontiguousSelectionModeTo(tree);
    String formatted = formatter.format(tree);
    assertThat(formatted).contains(tree.getClass().getName())
                         .contains("name='tree'")
                         .contains("selectionCount=0")
                         .contains("selectionPaths=[]")
                         .contains("selectionMode=DISCONTIGUOUS_TREE_SELECTION")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  private static void setDiscontiguousSelectionModeTo(final JTree tree) {
    setSelectionModel(tree, null);
  }
}
