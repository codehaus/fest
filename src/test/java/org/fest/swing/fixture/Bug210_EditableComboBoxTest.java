/*
 * Created on Oct 11, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.test.task.JComboBoxSetSelectedItemTask.setSelectedItem;
import static org.fest.util.Arrays.array;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=210" target="_blank">Bug 210</a>.
 * <p>
 * Demonstrate bug when testing <code>JComboBox</code>es. If value programmatically added to <code>JComboBox</code>
 * using <code>setSelectedItem</code> then FEST assertion <code>requireSelection</code> fails while JUnit
 * <code>assertEqual</code> passes. (FEST 1.0b1, Java 1.5)
 * </p>
 * 
 * @author Ewan McDougall
 * @author Alex Ruiz
 */
@Test(groups = { BUG, GUI })
public class Bug210_EditableComboBoxTest {

  private final static String ADDED_STRING = "rocket";
  private final static int INITIAL_INDEX = 0;

  private String[] values;
  private DialogFixture dialog;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    values = array("hat", "son");
    dialog = new DialogFixture(MyDialog.createNew(values));
    dialog.show();
  }
  
  @AfterMethod public void tearDown() {
    dialog.cleanUp();
  }

  public void shouldHaveAddedStringSelected() {
    JComboBoxFixture comboBox = dialog.comboBox("cb");
    comboBox.requireSelection(values[INITIAL_INDEX]);
    comboBox.enterText(ADDED_STRING);

    // You would then maybe save added value to database etc.
    // The user goes back to screen with combo box so you reconstructed component and set selected item as saved value
    setSelectedItem(comboBox.component(), ADDED_STRING);
    assertThat(selectedItemOf(comboBox.component())).isEqualTo(ADDED_STRING);
    comboBox.requireSelection(ADDED_STRING);
  }

  @RunsInEDT
  private static Object selectedItemOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        return comboBox.getSelectedItem();
      }
    });
  }

  private static class MyDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyDialog createNew(final String[] items) {
      return execute(new GuiQuery<MyDialog>() {
        protected MyDialog executeInEDT() {
          return new MyDialog(items);
        }
      });
    }
    
    private MyDialog(String[] items) {
      JComboBox comboBox = new JComboBox(items);
      comboBox.setEditable(true);
      comboBox.setSelectedIndex(INITIAL_INDEX);
      comboBox.setName("cb");
      add(comboBox);
      setTitle(Bug210_EditableComboBoxTest.class.getSimpleName());
    }
  }

}
