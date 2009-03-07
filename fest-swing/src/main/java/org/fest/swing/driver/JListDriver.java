/*
 * Created on Jan 19, 2008
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

import java.awt.Point;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;
import org.fest.util.Arrays;

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.awt.AWT.visibleCenterOf;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.CommonValidations.validateCellReader;
import static org.fest.swing.driver.JListContentQuery.contents;
import static org.fest.swing.driver.JListItemValueQuery.itemValue;
import static org.fest.swing.driver.JListMatchingItemQuery.*;
import static org.fest.swing.driver.JListScrollToItemTask.*;
import static org.fest.swing.driver.JListSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.driver.JListSelectionValueQuery.*;
import static org.fest.swing.driver.JListSelectionValuesQuery.selectionValues;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JList}</code>. Unlike <code>JListFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JList}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver extends JComponentDriver {

  private static final String SELECTED_INDICES_PROPERTY = "selectedIndices";
  private static final String SELECTED_INDICES_LENGTH_PROPERTY = concat(SELECTED_INDICES_PROPERTY, "#length");
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  private JListCellReader cellReader;

  /**
   * Creates a new </code>{@link JListDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JListDriver(Robot robot) {
    super(robot);
    cellReader(new BasicJListCellReader());
  }

  /**
   * Returns an array of <code>String</code>s that represents the contents of the given <code>{@link JList}</code>,
   * using this driver's <code>{@link JListCellReader}</code>.
   * @param list the target <code>JList</code>.
   * @return an array of <code>String</code>s that represents the contents of the given <code>JList</code>.
   * @see #cellReader(JListCellReader)
   */
  @RunsInEDT
  public String[] contentsOf(JList list) {
    return contents(list, cellReader);
  }

  /**
   * Selects the items matching the given values.
   * @param list the target <code>JList</code>.
   * @param values the values to match.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the any of the given values cannot be found.
   */
  public void selectItems(final JList list, final String[] values) {
    if (values == null) throw new NullPointerException("Array of values should not be null");
    if (Arrays.isEmpty(values)) throw new IllegalArgumentException("Array of values should not be empty");
    new MultipleSelectionTemplate(robot) {
      int elementCount() { 
        return values.length; 
      }
      void selectElement(int index) { 
        selectItem(list, values[index]); 
      }
    }.multiSelect();
  }

  /**
   * Clicks the item matching the given value using left mouse button once.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public void selectItem(JList list, String value) {
    Pair<Integer, Point> scrollInfo = scrollToItemIfNotSelectedYet(list, value, cellReader);
    robot.waitForIdle();
    verify(scrollInfo, value);
    if (scrollInfo.ii == null) return; // already selected cell.
    robot.click(list, cellCenterIn(scrollInfo));
  }

  /**
   * Clicks the first item matching the given value, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void clickItem(JList list, String value, MouseButton button, int times) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, value, cellReader);
    robot.waitForIdle();
    verify(scrollInfo, value);
    robot.click(list, cellCenterIn(scrollInfo), button, times);
  }

  /**
   * Selects the items under the given indices.
   * @param list the target <code>JList</code>.
   * @param indices the indices of the items to select.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices is negative or greater than the index of the last item in
   * the <code>JList</code>.
   */
  public void selectItems(final JList list, final int[] indices) {
    if (indices == null) throw new NullPointerException("The array of indices should not be null");
    if (isEmptyArray(indices)) throw new IllegalArgumentException("The array of indices should not be empty");
    new MultipleSelectionTemplate(robot) {
      int elementCount() { return indices.length; }
      void selectElement(int index) { selectItem(list, indices[index]); }
    }.multiSelect();
  }

  private boolean isEmptyArray(int[] array) { return array == null || array.length == 0; }

  /**
   * Selects the items in the specified range.
   * @param list the target <code>JList</code>.
   * @param from the starting point of the selection.
   * @param to the last item to select.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  @RunsInEDT
  public void selectItems(JList list, From from, To to) {
    selectItems(list, from.value, to.value);
  }

  /**
   * Selects the items in the specified range.
   * @param list the target <code>JList</code>.
   * @param start the starting point of the selection.
   * @param end the last item to select (inclusive.)
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  @RunsInEDT
  public void selectItems(JList list, int start, int end) {
    selectItem(list, start);
    robot.pressKey(VK_SHIFT);
    clickItem(list, end, LEFT_BUTTON, 1);
    robot.releaseKey(VK_SHIFT);
  }

  /**
   * Selects the item under the given index using left mouse button once.
   * @param list the target <code>JList</code>.
   * @param index the index of the item to click.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  @RunsInEDT
  public void selectItem(JList list, int index) {
    Point cellCenter = scrollToItemIfNotSelectedYet(list, index);
    robot.waitForIdle();
    if (cellCenter == null) return; // cell already selected
    robot.click(list, cellCenter);
  }

  /**
   * Clicks the item under the given index, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>.
   * @param index the index of the item to click.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  @RunsInEDT
  public void clickItem(JList list, int index, MouseButton button, int times) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    robot.click(list, cellCenter, button, times);
  }

  /**
   * Verifies that the selected item in the <code>{@link JList}</code> matches the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws AssertionError if the selected item does not match the value.
   */
  @RunsInEDT
  public void requireSelection(final JList list, String value) {
    Object selection = singleSelectionValue(list, cellReader);
    if (NO_SELECTION_VALUE == selection) failNoSelection(list);
    assertThat(selection).as(selectedIndexProperty(list)).isEqualTo(value);
  }

  /**
   * Verifies that the selected items in the <code>{@link JList}</code> match the given values.
   * @param list the target <code>JList</code>.
   * @param items the values to match.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the selected items do not match the given values.
   */
  @RunsInEDT
  public void requireSelectedItems(JList list, String... items) {
    if (items == null) throw new NullPointerException("The array of items should not be null");
    requireEqualSelection(list, items, selectionOf(list));
  }
  
  /**
   * Returns an array of <code>String</code>s that represents the selection in the given <code>{@link JList}</code>,
   * using this driver's <code>{@link JListCellReader}</code>.
   * @param list the target <code>JList</code>.
   * @return an array of <code>String</code>s that represents the selection in the given <code>JList</code>.
   * @see #cellReader(JListCellReader)
   */
  @RunsInEDT
  public String[] selectionOf(JList list) {
    return selectionValues(list, cellReader);
  }

  private void requireEqualSelection(JList list, String[] expected, String[] actual) {
    int selectionCount = actual.length;
    if (selectionCount == 0) failNoSelection(list);
    assertThat(selectionCount).as(propertyName(list, SELECTED_INDICES_LENGTH_PROPERTY)).isEqualTo(expected.length);
    for (int i = 0; i < selectionCount; i++) {
      Description description = propertyName(list, concat(SELECTED_INDICES_PROPERTY, "[", valueOf(i), "]"));
      assertThat(actual[i]).as(description).isEqualTo(expected[i]);
    }
  }

  /**
   * Verifies that the <code>{@link JList}</code> does not have a selection.
   * @param list the target <code>JList</code>.
   * @throws AssertionError if the <code>JList</code> has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(JList list) {
    assertThat(selectedIndexOf(list)).as(selectedIndexProperty(list)).isEqualTo(-1);
  }

  @RunsInEDT
  private void failNoSelection(JList list) {
    fail(concat("[", selectedIndexProperty(list).value(), "] No selection"));
  }

  @RunsInEDT
  private Description selectedIndexProperty(JList list) {
    return propertyName(list, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Starts a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public void drag(JList list, String value) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, value, cellReader);
    robot.waitForIdle();
    verify(scrollInfo, value);
    super.drag(list, cellCenterIn(scrollInfo));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(JList list, String value) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, value, cellReader);
    robot.waitForIdle();
    verify(scrollInfo, value);
    super.drop(list, cellCenterIn(scrollInfo));
  }

  /**
   * Starts a drag operation at the location of the given index.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the 
   * <code>JList</code>.
   */
  @RunsInEDT
  public void drag(JList list, int index) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    super.drag(list, cellCenter);
  }
  
  /**
   * Ends a drag operation at the location of the given index.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the 
   * <code>JList</code>.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(JList list, int index) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    super.drop(list, cellCenter);
  }

  /**
   * Ends a drag operation at the center of the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(JList list) {
    assertIsEnabledAndShowing(list);
    super.drop(list, visibleCenterOf(list));
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public JPopupMenu showPopupMenu(JList list, String value) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, value, cellReader);
    robot.waitForIdle();
    verify(scrollInfo, value);
    return robot.showPopupMenu(list, cellCenterIn(scrollInfo));
  }

  private void verify(Pair<Integer, Point> scrollInfo, String value) {
    if (ITEM_NOT_FOUND.equals(scrollInfo)) throw indexNotFoundFor(value);
  }

  private Point cellCenterIn(Pair<Integer, Point> scrollInfo) {
    return scrollInfo.ii;
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>.
   * @param index the index of the item.
   * @return a driver that manages the displayed pop-up menu.
   * @throws IllegalStateException if the <code>JList</code> is disabled.
   * @throws IllegalStateException if the <code>JList</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  @RunsInEDT
  public JPopupMenu showPopupMenu(JList list, int index) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    return robot.showPopupMenu(list, cellCenter);
  }

  /**
   * Returns the coordinates of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @return the coordinates of the item at the given item.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public Point pointAt(JList list, String value) {
    return centerOfMatchingItemCell(list, value, cellReader);
  }

  /**
   * Returns the index of the first item matching the given value.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public int indexOf(JList list, String value) {
    int index = itemIndex(list, value, cellReader);
    if (index >= 0) return index;
    throw indexNotFoundFor(value);
  }

  @RunsInEDT
  private static int itemIndex(final JList list, final String value, final JListCellReader cellReader) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return matchingItemIndex(list, value, cellReader);
      }
    });
  }

  /**
   * Returns the <code>String</code> representation of the element under the given index, using this driver's
   * <code>{@link JListCellReader}</code>.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   * @see #cellReader(JListCellReader)
   */
  @RunsInEDT
  public String value(JList list, int index) {
    return itemValue(list, index, cellReader);
  }

  private static LocationUnavailableException indexNotFoundFor(String value) {
    throw new LocationUnavailableException(concat("Unable to find an element matching the value ", quote(value)));
  }

  /**
   * Updates the implementation of <code>{@link JListCellReader}</code> to use when comparing internal values of a
   * <code>{@link JList}</code> and the values expected in a test.
   * @param newCellReader the new <code>JListCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JListCellReader newCellReader) {
    validateCellReader(newCellReader);
    cellReader = newCellReader;
  }
}
