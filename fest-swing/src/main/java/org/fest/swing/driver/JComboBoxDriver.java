/*
 * Created on Jan 21, 2008
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
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

import static javax.swing.text.DefaultEditorKit.selectAllAction;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.driver.CommonValidations.validateCellReader;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.driver.JComboBoxAccessibleEditorValidator.validateEditorIsAccessible;
import static org.fest.swing.driver.JComboBoxContentQuery.contents;
import static org.fest.swing.driver.JComboBoxEditableQuery.isEditable;
import static org.fest.swing.driver.JComboBoxItemIndexValidator.validateIndex;
import static org.fest.swing.driver.JComboBoxMatchingItemQuery.matchingItemIndex;
import static org.fest.swing.driver.JComboBoxSelectionValueQuery.*;
import static org.fest.swing.driver.JComboBoxSetPopupVisibleTask.setPopupVisible;
import static org.fest.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JComboBox}</code>. Unlike <code>JComboBoxFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JComboBox}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver extends JComponentDriver {

  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  private final JListDriver listDriver;
  private final JComboBoxDropDownListFinder dropDownListFinder;

  private JComboBoxCellReader cellReader;

  /**
   * Creates a new </code>{@link JComboBoxDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JComboBoxDriver(Robot robot) {
    super(robot);
    listDriver = new JListDriver(robot);
    dropDownListFinder = new JComboBoxDropDownListFinder(robot);
    cellReader(new BasicJComboBoxCellReader());
  }

  /**
   * Returns an array of <code>String</code>s that represents the contents of the given <code>{@link JComboBox}</code>
   * list. The <code>String</code> representation of each element is performed using this driver's
   * <code>{@link JComboBoxCellReader}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @return an array of <code>String</code>s that represent the contents of the given <code>JComboBox</code> list.
   * @see #value(JComboBox, int)
   * @see #cellReader(JComboBoxCellReader)
   */
  @RunsInEDT
  public String[] contentsOf(JComboBox comboBox) {
    return contents(comboBox, cellReader);
  }

  /**
   * Selects the first item matching the given text in the <code>{@link JComboBox}</code>. Value matching is performed
   * using this fixture's <code>{@link JComboBoxCellReader}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param value the value to match
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws IllegalStateException if the <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>JComboBox</code> is not showing on the screen.
   * @see #cellReader(JComboBoxCellReader)
   */
  @RunsInEDT
  public void selectItem(JComboBox comboBox, String value) {
    int index = matchingItemIndex(comboBox, value, cellReader);
    if (index < 0)
      throw new LocationUnavailableException(concat(
          "Unable to find item ", quote(value), " among the JComboBox contents (", format(contentsOf(comboBox)), ")"));
    selectItem(comboBox, index);
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in the <code>{@link JComboBox}</code>
   * matches the given text.
   * @param comboBox the target <code>JComboBox</code>.
   * @param value the text to match.
   * @throws AssertionError if the selected item does not match the given value.
   */
  @RunsInEDT
  public void requireSelection(JComboBox comboBox, String value) {
    Object selection = selection(comboBox, cellReader);
    if (NO_SELECTION_VALUE == selection)
      fail(concat("[", selectedIndexProperty(comboBox).value(), "] No selection"));
    assertThat(selection).as(selectedIndexProperty(comboBox)).isEqualTo(value);
  }

  /**
   * Verifies that the <code>{@link JComboBox}</code> does not have any selection.
   * @param comboBox the target <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(JComboBox comboBox) {
    Object selection = selection(comboBox, cellReader);
    if (NO_SELECTION_VALUE == selection) return;
    fail(concat(
        "[", selectedIndexProperty(comboBox).value(), "] Expecting no selection, but found:<", quote(selection), ">"));
  }

  /**
   * Returns the <code>String</code> representation of the element under the given index, using this driver's
   * <code>{@link JComboBoxCellReader}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JComboBox</code>.
   * @see #cellReader(JComboBoxCellReader)
   */
  public String value(JComboBox comboBox, int index) {
    return valueAsText(comboBox, index, cellReader);
  }

  @RunsInEDT
  private static String valueAsText(final JComboBox comboBox, final int index, final JComboBoxCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        validateIndex(comboBox, index);
        return cellReader.valueAt(comboBox, index);
      }
    });
  }

  private Description selectedIndexProperty(JComboBox comboBox) {
    return propertyName(comboBox, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Selects the item under the given index in the <code>{@link JComboBox}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param index the given index.
   * @throws IllegalStateException if the <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>JComboBox</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JComboBox</code>.
   */
  @RunsInEDT
  public void selectItem(final JComboBox comboBox, int index) {
    validateCanSelectItem(comboBox, index);
    showDropDownList(comboBox);
    selectItemAtIndex(comboBox, index);
    hideDropDownListIfVisible(comboBox);
  }
  
  @RunsInEDT
  private static void validateCanSelectItem(final JComboBox comboBox, final int index) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        validateIsEnabledAndShowing(comboBox);
        validateIndex(comboBox, index);
      }
    });
  }

  @RunsInEDT
  void showDropDownList(JComboBox comboBox) {
    // Location of pop-up button activator is LAF-dependent
    dropDownVisibleThroughUIDelegate(comboBox, true);
  }

  @RunsInEDT
  private void selectItemAtIndex(final JComboBox comboBox, final int index) {
    JList dropDownList = dropDownListFinder.findDropDownList();
    if (dropDownList != null) {
      listDriver.selectItem(dropDownList, index);
      return;
    }
    setSelectedIndex(comboBox, index);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideDropDownListIfVisible(JComboBox comboBox) {
    dropDownVisibleThroughUIDelegate(comboBox, false);
  }

  @RunsInEDT
  private void dropDownVisibleThroughUIDelegate(JComboBox comboBox, final boolean visible) {
    setPopupVisible(comboBox, visible);
    robot.waitForIdle();
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code>, replacing any text. This action
   * is executed only if the <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to enter.
   * @throws IllegalStateException if the <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>JComboBox</code> is not showing on the screen.
   * @throws IllegalStateException if the <code>JComboBox</code> is not editable.
   */
  @RunsInEDT
  public void replaceText(JComboBox comboBox, String text) {
    selectAllText(comboBox);
    enterText(comboBox, text);
  }

  /**
   * Simulates a user selecting the text in the <code>{@link JComboBox}</code>. This action is executed only if the
   * <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @throws IllegalStateException if the <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>JComboBox</code> is not showing on the screen.
   * @throws IllegalStateException if the <code>JComboBox</code> is not editable.
   */
  @RunsInEDT
  public void selectAllText(JComboBox comboBox) {
    Component editor = accessibleEditorOf(comboBox);
    if (!(editor instanceof JComponent)) return;
    focus(editor);
    invokeAction((JComponent) editor, selectAllAction);
  }

  @RunsInEDT
  private static Component accessibleEditorOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        validateEditorIsAccessible(comboBox);
        return comboBox.getEditor().getEditorComponent();
      }
    });
  }
  
  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code>. This action is executed only
   * if the <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to enter.
   * @throws IllegalStateException if the <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>JComboBox</code> is not showing on the screen.
   * @throws IllegalStateException if the <code>JComboBox</code> is not editable.
   */
  @RunsInEDT
  public void enterText(JComboBox comboBox, String text) {
    inEdtValidateEditorIsAccessible(comboBox);
    focus(comboBox);
    robot.enterText(text);
  }

  @RunsInEDT
  private static void inEdtValidateEditorIsAccessible(final JComboBox comboBox) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        validateEditorIsAccessible(comboBox);
      }
    });
  }

  /**
   * Find the <code>{@link JList}</code> in the pop-up raised by the <code>{@link JComboBox}</code>, if the LAF actually
   * uses one.
   * @return the found <code>JList</code>.
   * @throws ComponentLookupException if the <code>JList</code> in the pop-up could not be found.
   */
  @RunsInEDT
  public JList dropDownList() {
    JList list = dropDownListFinder.findDropDownList();
    if (list == null) throw listNotFound();
    return list;
  }

  private ComponentLookupException listNotFound() {
    throw new ComponentLookupException("Unable to find the pop-up list for the JComboBox");
  }

  /**
   * Asserts that the given <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> is not editable.
   */
  @RunsInEDT
  public void requireEditable(final JComboBox comboBox) {
    assertEditable(comboBox, true);
  }

  /**
   * Asserts that the given <code>{@link JComboBox}</code> is not editable.
   * @param comboBox the given <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> is editable.
   */
  @RunsInEDT
  public void requireNotEditable(JComboBox comboBox) {
    assertEditable(comboBox, false);
  }

  @RunsInEDT
  private void assertEditable(JComboBox comboBox, boolean expected) {
    assertThat(isEditable(comboBox)).as(editableProperty(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private static Description editableProperty(JComboBox comboBox) {
    return propertyName(comboBox, EDITABLE_PROPERTY);
  }

  /**
   * Updates the implementation of <code>{@link JComboBoxCellReader}</code> to use when comparing internal values
   * of a <code>{@link JComboBox}</code> and the values expected in a test.
   * @param newCellReader the new <code>JComboBoxCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JComboBoxCellReader newCellReader) {
    validateCellReader(newCellReader);
    cellReader = newCellReader;
  }
}
