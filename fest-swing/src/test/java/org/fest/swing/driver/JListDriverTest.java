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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestList;
import org.fest.swing.test.swing.TestWindow;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.driver.JListSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.driver.JListSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.util.Range.*;
import static org.fest.util.Arrays.array;

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

  public void shouldThrowErrorIfIndexForValueNotFound() {
    try {
      driver.indexOf(dragList, "four");
      failWhenExpectingException();
    } catch (LocationUnavailableException expected) {
      assertThat(expected.getMessage()).isEqualTo("Unable to find an element matching the value 'four'");
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
    setSelectedIndex(dragList, (-1));
    robot.waitForIdle();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.clickItem(dragList, "two", RIGHT_BUTTON, 2);
    assertThat(recorder).clicked(RIGHT_BUTTON)
                        .timesClicked(2);
    Point pointClicked = recorder.pointClicked();
    assertThat(locationToIndex(dragList, pointClicked)).isEqualTo(1);
  }

  private static int locationToIndex(final JList list, final Point p) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return list.locationToIndex(p);
      }
    });
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

  public void shouldSelectItemWithGivenText() {
    driver.selectItem(dragList, "two");
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

  public void shouldPassIfSelectionIsEqualToExpectedOne() {
    setSelectedIndex(dragList, 0);
    robot.waitForIdle();
    driver.requireSelection(dragList, "one");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfExpectingSelectionButThereIsNone() {
    setSelectedIndex(dragList, (-1));
    robot.waitForIdle();
    try {
      driver.requireSelection(dragList, "one");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("No selection");
    }
  }

  public void shouldFailIfSelectionIsNotEqualToExpectedOne() {
    setSelectedIndex(dragList, 1);
    robot.waitForIdle();
    try {
      driver.requireSelection(dragList, "one");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("expected:<'one'> but was:<'two'>");
    }
  }

  public void shouldPassIfSelectedItemsIsEqualToExpectedOnes() {
    setSelectedIndices(dragList, 0, 1);
    robot.waitForIdle();
    driver.requireSelectedItems(dragList, "one", "two");
    assertCellReaderWasCalled();
  }

  private static void setSelectedIndices(final JList list, final int... indices) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        list.setSelectedIndices(indices);
      }
    });
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedSelectedItemsIsNull() {
    driver.requireSelectedItems(dragList, (String[])null);
  }

  public void shouldFailIfExpectingSelectedItemsButThereIsNone() {
    setSelectedIndex(dragList, (-1));
    robot.waitForIdle();
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("No selection");
    }
  }

  public void shouldFailIfSelectedItemCountIsNotEqualToExpectedOnes() {
    setSelectedIndex(dragList, 2);
    robot.waitForIdle();
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices#length'] expected:<2> but was:<1>");
    }
  }

  public void shouldFailIfSelectedItemsIsNotEqualToExpectedOnes() {
    setSelectedIndex(dragList, 2);
    robot.waitForIdle();
    try {
      driver.requireSelectedItems(dragList, "one");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("expected:<'one'> but was:<'three'>");
    }
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    setSelectedIndex(dragList, (-1));
    robot.waitForIdle();
    driver.requireNoSelection(dragList);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    setSelectedIndex(dragList, 0);
    robot.waitForIdle();
    try {
      driver.requireNoSelection(dragList);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("expected:<-1> but was:<0>");
    }
  }

  public void shouldShowPopupMenuAtItemWithValue() {
    JPopupMenu popupMenu = setJPopupMenuInDragList();
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.showPopupMenu(dragList, "one");
    assertThat(recorder).clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
    assertCellReaderWasCalled();
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
