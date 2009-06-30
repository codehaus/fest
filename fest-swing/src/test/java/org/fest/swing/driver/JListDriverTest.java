/*
 * Created on Feb 24, 2008
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

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.driver.JListSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.driver.JListSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.util.Range.from;
import static org.fest.swing.util.Range.to;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;
import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestList;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JListDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JListDriverTest {

  private Robot robot;
  private JListCellReaderStub cellReader;
  private MyWindow window;
  private TestList dragList;
  private TestList dropList;
  private JListDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    cellReader = new JListCellReaderStub();
    driver = new JListDriver(robot);
    driver.cellReader(cellReader);
    window = MyWindow.createNew();
    dragList = window.dragList;
    dropList = window.dropList;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnLocationOfValue() {
    Point p = driver.pointAt(dragList, "two");
    int index = locationToIndex(dragList, p);
    assertThat(index).isEqualTo(1);
    assertCellReaderWasCalled();
  }

  public void shouldReturnIndexForValue() {
    int index = driver.indexOf(dragList, "three");
    assertThat(index).isEqualTo(2);
    assertCellReaderWasCalled();
  }

  public void shouldReturnIndexForItemMatchingPatternAsString() {
    int index = driver.indexOf(dragList, "thr.*");
    assertThat(index).isEqualTo(2);
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfIndexForValueNotFound() {
    try {
      driver.indexOf(dragList, "four");
      failWhenExpectingException();
    } catch (LocationUnavailableException expected) {
      assertThat(expected.getMessage()).isEqualTo(
          "Unable to find item matching the value 'four' among the JList contents ['one', 'two', 'three']");
    }
  }

  public void shouldReturnIndexForItemMatchingPattern() {
    int index = driver.indexOf(dragList, regex("thr.*"));
    assertThat(index).isEqualTo(2);
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfIndexForItemMatchingPatternNotFound() {
    try {
      driver.indexOf(dragList, regex("fou.*"));
      failWhenExpectingException();
    } catch (LocationUnavailableException expected) {
      assertThat(expected.getMessage()).isEqualTo(
          "Unable to find item matching the pattern 'fou.*' among the JList contents ['one', 'two', 'three']");
    }
  }

  public void shouldReturnTextOfElement() {
    Object text = driver.value(dragList, 0);
    assertThat(text).isEqualTo("one");
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfIndexOutOfBoundsWhenLookingForText() {
    try {
      driver.value(dragList, 6);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException expected) {
      assertThat(expected.getMessage()).isEqualTo("Item index (6) should be between [0] and [2] (inclusive)");
    }
  }

  public void shouldReturnSelection() {
    setSelectedIndices(dragList, 0, 2);
    robot.waitForIdle();
    String[] selection = driver.selectionOf(dragList);
    assertThat(selection).containsOnly("one", "three");
    assertCellReaderWasCalled();
  }

  public void shouldReturnListContents() {
    Object[] contents = driver.contentsOf(dragList);
    assertThat(contents).isEqualTo(array("one", "two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldClickItemWithGivenText() {
    clearDragListSelection();
    robot.waitForIdle();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.clickItem(dragList, "two", RIGHT_BUTTON, 2);
    assertThat(recorder).clicked(RIGHT_BUTTON)
                        .timesClicked(2);
    Point pointClicked = recorder.pointClicked();
    assertThat(locationToIndex(dragList, pointClicked)).isEqualTo(1);
  }

  public void shouldClickItemMatchingGivenPatternAsString() {
    clearDragListSelection();
    robot.waitForIdle();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.clickItem(dragList, "tw.*", RIGHT_BUTTON, 2);
    assertThat(recorder).clicked(RIGHT_BUTTON)
                        .timesClicked(2);
    Point pointClicked = recorder.pointClicked();
    assertThat(locationToIndex(dragList, pointClicked)).isEqualTo(1);
  }

  public void shouldClickItemMatchingGivenPattern() {
    clearDragListSelection();
    robot.waitForIdle();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.clickItem(dragList, regex("two"), RIGHT_BUTTON, 2);
    assertThat(recorder).clicked(RIGHT_BUTTON)
                        .timesClicked(2);
    Point pointClicked = recorder.pointClicked();
    assertThat(locationToIndex(dragList, pointClicked)).isEqualTo(1);
  }

  public void shouldClearSelection() {
    setSelectedIndex(dragList, 1);
    robot.waitForIdle();
    driver.clearSelection(dragList);
    assertThat(selectedIndexOf(dragList)).isEqualTo(-1);
  }

  public void shouldSelectItemAtGivenIndex() {
    driver.selectItem(dragList, 2);
    assertThat(selectedValue(dragList)).isEqualTo("three");
  }

  public void shouldNotSelectItemIfAlreadySelected() {
    setSelectedIndex(dragList, 1);
    robot.waitForIdle();
    driver.selectItem(dragList, 1);
    assertThat(selectedIndexOf(dragList)).isEqualTo(1);
  }

  public void shouldThrowErrorWhenSelectingItemAtGivenIndexInDisabledJList() {
    disableDragList();
    try {
      driver.selectItem(dragList, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemAtGivenIndexInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItem(dragList, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorIfMatchingItemToSelectNotFound() {
    try {
      driver.selectItem(dragList, "ten");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo(
          "Unable to find item matching the value 'ten' among the JList contents ['one', 'two', 'three']");
    }
  }

  public void shouldSelectItemWithGivenText() {
    driver.selectItem(dragList, "two");
    assertThat(selectedValue(dragList)).isEqualTo("two");
    assertCellReaderWasCalled();
  }

  public void shouldSelectItemMatchingPatternAsString() {
    driver.selectItem(dragList, "tw.*");
    assertThat(selectedValue(dragList)).isEqualTo("two");
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorWhenSelectingItemWithGivenTextInDisabledJList() {
    disableDragList();
    try {
      driver.selectItem(dragList, "two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemWithGivenTextInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItem(dragList, "two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemMatchingPattern() {
    driver.selectItem(dragList, regex("tw.*"));
    assertThat(selectedValue(dragList)).isEqualTo("two");
    assertCellReaderWasCalled();
  }

  private static Object selectedValue(final JList list) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        return list.getSelectedValue();
      }
    });
  }

  public void shouldThrowErrorWhenSelectingItemMatchingPatternInDisabledJList() {
    disableDragList();
    try {
      driver.selectItem(dragList, regex("tw.*"));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemMatchingPatternInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItem(dragList, regex("tw.*"));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorIfItemToSelectMatchingPatternNotFound() {
    try {
      driver.selectItem(dragList, regex("ten"));
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo(
          "Unable to find item matching the pattern 'ten' among the JList contents ['one', 'two', 'three']");
    }
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfValuesToSelectIsNull() {
    String[] values = null;
    driver.selectItems(dragList, values);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfValuesToSelectIsEmpty() {
    String[] values = new String[0];
    driver.selectItems(dragList, values);
  }

  public void shouldSelectItemsWithGivenText() {
    driver.selectItems(dragList, array("two", "three"));
    assertThat(selectedValues(dragList)).isEqualTo(array("two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldSelectItemsMatchingGivenPatternsAsString() {
    driver.selectItems(dragList, array("tw.*", "thr.*"));
    assertThat(selectedValues(dragList)).isEqualTo(array("two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldSelectItemsMatchingGivenPatternAsString() {
    driver.selectItems(dragList, array("t.*"));
    assertThat(selectedValues(dragList)).isEqualTo(array("two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorWhenSelectingItemsWithGivenTextInDisabledJList() {
    disableDragList();
    try {
      driver.selectItems(dragList, array("two", "three"));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemsWithGivenTextInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItems(dragList, array("two", "three"));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorIfNoItemsMatchingTextWereSelected() {
    try {
      driver.selectItems(dragList, array("abc"));
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).contains(
          "Unable to find item matching the value 'abc' among the JList contents ['one', 'two', 'three']");
    }
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfPatternsToSelectIsNull() {
    Pattern[] patterns = null;
    driver.selectItems(dragList, patterns);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfPatternsToSelectIsEmpty() {
    Pattern[] patterns = new Pattern[0];
    driver.selectItems(dragList, patterns);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAnyPatternInArraySelectIsNull() {
    Pattern[] patterns = { regex("hello"), null };
    driver.selectItems(dragList, patterns);
  }

  public void shouldSelectItemsMatchingGivenPatterns() {
    driver.selectItems(dragList, array(regex("t.*")));
    assertThat(selectedValues(dragList)).isEqualTo(array("two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorWhenSelectingItemsMatchingGivenPatternsInDisabledJList() {
    disableDragList();
    try {
      driver.selectItems(dragList, array(regex("two"), regex("three")));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemsMatchingGivenPatternsInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItems(dragList, array(regex("two"), regex("three")));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorIfNoItemsMatchingPatternWereSelected() {
    try {
      driver.selectItems(dragList, array(regex("abc")));
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).contains(
          "Unable to find item matching the pattern 'abc' among the JList contents ['one', 'two', 'three']");
    }
  }

  public void shouldSelectItemsWithGivenIndices() {
    driver.selectItems(dragList, new int[] { 1, 2 });
    assertThat(selectedValues(dragList)).isEqualTo(array("two", "three"));
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfIndicesToSelectIsNull() {
    int[] indices = null;
    driver.selectItems(dragList, indices);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfIndicesToSelectIsEmpty() {
    int[] indices = new int[0];
    driver.selectItems(dragList, indices);
  }

  public void shouldSelectItemsWithGivenIndicesEvenIfIndexArrayHasOneElement() {
    driver.selectItems(dragList, new int[] { 1 });
    assertThat(selectedValues(dragList)).isEqualTo(array("two"));
  }

  public void shouldThrowErrorWhenSelectingItemsWithGivenIndicesInDisabledJList() {
    disableDragList();
    try {
      driver.selectItems(dragList, new int[] { 1, 2 });
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemsWithGivenIndicesInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItems(dragList, new int[] { 1, 2 });
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemsInFluentRange() {
    driver.selectItems(dragList, from(0), to(1));
    assertThat(selectedValues(dragList)).isEqualTo(array("one", "two"));
  }

  public void shouldThrowErrorWhenSelectingItemsInFluentRangeInDisabledJList() {
    disableDragList();
    try {
      driver.selectItems(dragList, from(0), to(1));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemsInFluentRangeInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItems(dragList, from(0), to(1));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemsInGivenRange() {
    driver.selectItems(dragList, 0, 1);
    assertThat(selectedValues(dragList)).isEqualTo(array("one", "two"));
  }

  @RunsInEDT
  private static Object[] selectedValues(final JList list) {
    return execute(new GuiQuery<Object[]>() {
      protected Object[] executeInEDT() {
        return list.getSelectedValues();
      }
    });
  }

  public void shouldThrowErrorWhenSelectingItemsInGivenRangeInDisabledJList() {
    disableDragList();
    try {
      driver.selectItems(dragList, 0, 1);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemsInGivenRangeInNotShowingJList() {
    hideWindow();
    try {
      driver.selectItems(dragList, 0, 1);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertDragListHasNoSelection();
  }

  public void shouldReturnValueAtGivenIndex() {
    Object text = driver.value(dragList, 2);
    assertThat(text).isEqualTo("three");
  }

  public void shouldReturnIndexOfValue() {
    int index = driver.indexOf(dragList, "three");
    assertThat(index).isEqualTo(2);
    assertCellReaderWasCalled();
  }

  public void shouldPassIfSelectionValueIsEqualToExpectedOne() {
    selectFirstItemInDragList();
    driver.requireSelection(dragList, "one");
    assertCellReaderWasCalled();
  }

  public void shouldPassIfSelectionValueMatchesPatternAsString() {
    selectFirstItemInDragList();
    driver.requireSelection(dragList, "on.*");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfExpectingSelectionValueButThereIsNone() {
    clearDragListSelection();
    try {
      driver.requireSelection(dragList, "one");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("No selection");
    }
  }

  public void shouldFailIfSelectionValueIsNotEqualToExpectedOne() {
    setSelectedIndex(dragList, 1);
    robot.waitForIdle();
    try {
      driver.requireSelection(dragList, "one");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("actual value:<'two'> is not equal to or does not match pattern:<'one'>");
    }
  }

  public void shouldPassIfSelectionValueMatchesPattern() {
    selectFirstItemInDragList();
    driver.requireSelection(dragList, regex("on.*"));
    assertCellReaderWasCalled();
  }

  public void shouldFailIfExpectingSelectionMatchingPatternButThereIsNone() {
    clearDragListSelection();
    try {
      driver.requireSelection(dragList, regex("one"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("No selection");
    }
  }

  public void shouldFailIfSelectionValueDoesNotMatchPattern() {
    setSelectedIndex(dragList, 1);
    robot.waitForIdle();
    try {
      driver.requireSelection(dragList, regex("one"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("actual value:<'two'> does not match pattern:<'one'>");
    }
  }


  public void shouldPassIfSelectionIndexIsEqualToExpectedOne() {
    selectFirstItemInDragList();
    driver.requireSelection(dragList, 0);
  }

  public void shouldFailIfExpectingSelectionIndexButThereIsNone() {
    clearDragListSelection();
    try {
      driver.requireSelection(dragList, 0);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("No selection");
    }
  }

  public void shouldFailIfSelectionIndexIsNotEqualToExpectedOne() {
    setSelectedIndex(dragList, 1);
    robot.waitForIdle();
    try {
      driver.requireSelection(dragList, 0);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("expected:<0> but was:<1>");
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedSelectedItemsIsNull() {
    driver.requireSelectedItems(dragList, (String[])null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfExpectedSelectedItemsIsEmpty() {
    driver.requireSelectedItems(dragList, new String[0]);
  }

  public void shouldFailIfExpectingSelectedItemsButThereIsNone() {
    clearDragListSelection();
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices'")
                                .contains("expected:<['one', 'two']> but was:<[]>");
    }
  }

  public void shouldFailIfSelectedItemsIsNotEqualToExpectedOnes() {
    setSelectedIndex(dragList, 2);
    robot.waitForIdle();
    try {
      driver.requireSelectedItems(dragList, "one");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices'")
                                .contains("expected:<['one']> but was:<['three']>");
    }
  }

  public void shouldPassIfSelectedItemsIsEqualToExpectedOnes() {
    setSelectedIndices(dragList, 0, 1);
    robot.waitForIdle();
    driver.requireSelectedItems(dragList, "two", "one");
    assertCellReaderWasCalled();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPatternForExpectedSelectedItemsIsNull() {
    driver.requireSelectedItems(dragList, (Pattern[])null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfPatternForExpectedSelectedItemsIsEmpty() {
    driver.requireSelectedItems(dragList, new Pattern[0]);
  }

  public void shouldFailIfExpectingSelectedItemsToMatchPatternButThereIsNone() {
    clearDragListSelection();
    try {
      driver.requireSelectedItems(dragList, regex("one"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices'")
                                .contains("expected:<['one']> but was:<[]>");
    }
  }

  public void shouldFailIfSelectedItemsMatchingPatternAreNotEqualToExpectedOnes() {
    selectFirstItemInDragList();
    try {
      driver.requireSelectedItems(dragList, regex("t.*"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      System.out.println(e.getMessage());
      assertThat(e.getMessage()).contains("expected:<['two', 'three']> but was:<['one']>");
    }
  }

  public void shouldPassIfSelectedItemsMatchesPattern() {
    setSelectedIndices(dragList, 1, 2);
    robot.waitForIdle();
    driver.requireSelectedItems(dragList, regex("t.*"));
    assertCellReaderWasCalled();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedSelectedIndicesIsNull() {
    driver.requireSelectedItems(dragList, (int[])null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfExpectedSelectedIndicesIsEmpty() {
    driver.requireSelectedItems(dragList, new int[0]);
  }

  public void shouldFailIfExpectingSelectedIndicesButThereIsNone() {
    clearDragListSelection();
    try {
      driver.requireSelectedItems(dragList, 0, 1);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices'")
                                .contains("expected:<[0, 1]> but was:<[]>");
    }
  }

  public void shouldFailIfSelectedIndicesIsNotEqualToExpectedOnes() {
    setSelectedIndex(dragList, 2);
    robot.waitForIdle();
    try {
      driver.requireSelectedItems(dragList, 0, 1);
      failWhenExpectingException();
    } catch (AssertionError e) {
      System.out.println(e.getMessage());
      assertThat(e.getMessage()).contains("property:'selectedIndices'")
                                .contains("expected:<[0, 1]> but was:<[2]>");
    }
  }

  public void shouldPassIfSelectedIndicesIsEqualToExpectedOnes() {
    setSelectedIndices(dragList, 0, 1);
    robot.waitForIdle();
    driver.requireSelectedItems(dragList, 1, 0);
  }

  @RunsInEDT
  private static void setSelectedIndices(final JList list, final int... indices) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        list.setSelectedIndices(indices);
      }
    });
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    clearDragListSelection();
    driver.requireNoSelection(dragList);
  }

  private void clearDragListSelection() {
    setSelectedIndex(dragList, (-1));
    robot.waitForIdle();
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstItemInDragList();
    try {
      driver.requireNoSelection(dragList);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("expected:<-1> but was:<0>");
    }
  }

  private void selectFirstItemInDragList() {
    setSelectedIndex(dragList, 0);
    robot.waitForIdle();
  }

  public void shouldShowPopupMenuAtItemWithValue() {
    JPopupMenu popupMenu = setJPopupMenuInDragList();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.showPopupMenu(dragList, "one");
    assertThat(recorder).clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
    assertThat(locationToIndex(dragList, recorder.pointClicked())).isEqualTo(0);
    assertCellReaderWasCalled();
  }

  public void shouldShowPopupMenuAtItemMatchingPatternAsString() {
    JPopupMenu popupMenu = setJPopupMenuInDragList();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.showPopupMenu(dragList, "o.*");
    assertThat(recorder).clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
    assertThat(locationToIndex(dragList, recorder.pointClicked())).isEqualTo(0);
    assertCellReaderWasCalled();
  }

  public void shouldShowPopupMenuAtItemMatchingPattern() {
    JPopupMenu popupMenu = setJPopupMenuInDragList();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.showPopupMenu(dragList, regex("o.*"));
    assertThat(recorder).clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
    assertThat(locationToIndex(dragList, recorder.pointClicked())).isEqualTo(0);
    assertCellReaderWasCalled();
  }

  @RunsInEDT
  private static int locationToIndex(final JList list, final Point p) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return list.locationToIndex(p);
      }
    });
  }

  public void shouldThrowErrorWhenShowingPopupMenuAtItemWithValueInDisabledJList() {
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    disableDragList();
    try {
      driver.showPopupMenu(dragList, "one");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(recorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenShowingPopupMenuAtItemWithValueInNotShowingJList() {
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    hideWindow();
    try {
      driver.showPopupMenu(dragList, "one");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(recorder).wasNotClicked();
  }

  public void shouldShowPopupMenuAtItemWithIndex() {
    JPopupMenu popupMenu = setJPopupMenuInDragList();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.showPopupMenu(dragList, 0);
    recorder.clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
  }

  @RunsInEDT
  private JPopupMenu setJPopupMenuInDragList() {
    JPopupMenu popupMenu = setComponentPopupMenuInEDT(dragList);
    robot.waitForIdle();
    return popupMenu;
  }

  @RunsInEDT
  private static JPopupMenu setComponentPopupMenuInEDT(final JList list) {
    return execute(new GuiQuery<JPopupMenu>() {
      protected JPopupMenu executeInEDT() {
        JMenuItem menuItem = new JMenuItem("Frodo");
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(menuItem);
        list.setComponentPopupMenu(popupMenu);
        return popupMenu;
      }
    });
  }

  public void shouldThrowErrorWhenShowingPopupMenuAtItemWithIndexInDisabledJList() {
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    disableDragList();
    try {
      driver.showPopupMenu(dragList, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(recorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenShowingPopupMenuAtItemWithIndexInNotShowingJList() {
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    hideWindow();
    try {
      driver.showPopupMenu(dragList, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(recorder).wasNotClicked();
  }

  public void shouldDragAndDropValueUsingGivenValues() {
    driver.drag(dragList, "two");
    driver.drop(dropList, "six");
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "two"));
    assertCellReaderWasCalled();
  }

  public void shouldDragAndDropValuesMatchingPatternAsString() {
    driver.drag(dragList, "tw.*");
    driver.drop(dropList, "s.*");
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "two"));
    assertCellReaderWasCalled();
  }

  public void shouldDragAndDropValuesMatchingPattern() {
    driver.drag(dragList, regex("tw.*"));
    driver.drop(dropList, regex("s.*"));
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "two"));
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorWhenDraggingItemWithValueInDisabledJList() {
    disableDragList();
    try {
      driver.drag(dragList, "two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDraggingItemWithValueInNotShowingJList() {
    hideWindow();
    try {
      driver.drag(dragList, "two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenDroppingItemWithValueInDisabledJList() {
    disableDropList();
    try {
      driver.drop(dropList, "six");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDroppingItemWithValueInNotShowingJList() {
    hideWindow();
    try {
      driver.drop(dropList, "six");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldDrop() {
    driver.drag(dragList, "two");
    driver.drop(dropList);
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).hasSize(4);
  }

  public void shouldThrowErrorWhenDroppingInDisabledJList() {
    disableDropList();
    try {
      driver.drop(dropList);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDroppingInNotShowingJList() {
    hideWindow();
    try {
      driver.drop(dropList);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldDragAndDropValueUsingGivenIndices() {
    driver.drag(dragList, 2);
    driver.drop(dropList, 2);
    assertThat(dragList.elements()).isEqualTo(array("one", "two"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "three"));
  }

  public void shouldThrowErrorWhenDraggingItemWithIndexInDisabledJList() {
    disableDragList();
    try {
      driver.drag(dragList, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDraggingItemWithIndexInNotShowingJList() {
    hideWindow();
    try {
      driver.drag(dragList, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenDroppingItemWithIndexInDisabledJList() {
    disableDropList();
    try {
      driver.drop(dropList, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDroppingItemWithIndexInNotShowingJList() {
    hideWindow();
    try {
      driver.drop(dropList, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void disableDragList() {
    disable(dragList);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void disableDropList() {
    disable(dropList);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void assertDragListHasNoSelection() {
    assertThat(selectedIndexOf(dragList)).isEqualTo(-1);
  }

  private void assertCellReaderWasCalled() {
    cellReader.requireInvoked("valueAt");
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final TestList dragList = new TestList("one", "two", "three");
    final TestList dropList = new TestList("four", "five", "six");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListDriverTest.class);
      dragList.setName("dragList");
      dropList.setName("dropList");
      add(decorate(dragList));
      add(decorate(dropList));
    }

    private JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }

  private static class JListCellReaderStub extends BasicJListCellReader {
    private final MethodInvocations methodInvocations = new MethodInvocations();

    JListCellReaderStub() {}

    @Override public String valueAt(JList list, int index) {
      methodInvocations.invoked("valueAt");
      return super.valueAt(list, index);
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
