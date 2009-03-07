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

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JComboBoxSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.driver.JComboBoxSetEditableTask.setEditable;
import static org.fest.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JComboBoxDriverTest {

  private Robot robot;
  private JComboBoxCellReaderStub cellReader;
  private JComboBox comboBox;
  private JComboBoxDriver driver;
  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    cellReader = new JComboBoxCellReaderStub();
    driver = new JComboBoxDriver(robot);
    driver.cellReader(cellReader);
    window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnComboBoxContents() {
    Object[] contents = driver.contentsOf(comboBox);
    assertThat(contents).isEqualTo(array("first", "second", "third"));
    assertCellReaderWasCalled();
  }

  public void shouldSelectItemAtGivenIndex() {
    clearSelectionInComboBox();
    driver.selectItem(comboBox, 2);
    assertThatSelectedItemIsEqualTo("third");
  }

  public void shouldThrowErrorWhenSelectingItemWithGivenIndexInDisabledJComboBox() {
    clearSelectionAndDisableComboBox();
    try {
      driver.selectItem(comboBox, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatComboBoxHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemWithGivenIndexInNotShowingJComboBox() {
    hideWindow();
    try {
      driver.selectItem(comboBox, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldSelectItemWithGivenText() {
    clearSelectionInComboBox();
    driver.selectItem(comboBox, "second");
    assertThatSelectedItemIsEqualTo("second");
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorWhenSelectingItemWithGivenTextInDisabledJComboBox() {
    clearSelectionAndDisableComboBox();
    try {
      driver.selectItem(comboBox, "first");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatComboBoxHasNoSelection();
  }

  public void shouldThrowErrorWhenSelectingItemWithGivenTextInNotShowingJComboBox() {
    hideWindow();
    try {
      driver.selectItem(comboBox, "first");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  private void assertThatComboBoxHasNoSelection() {
    assertThatSelectedIndexIsEqualTo(-1);
  }

  public void shouldNotSelectItemWithGivenTextIfAlreadySelected() {
    setSelectedIndex(comboBox, 1);
    robot.waitForIdle();
    driver.selectItem(comboBox, "second");
    assertThatSelectedItemIsEqualTo("second");
  }

  private void assertThatSelectedItemIsEqualTo(String expected) {
    assertThat(selectedItemOf(comboBox)).isEqualTo(expected);
  }

  private static Object selectedItemOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        return comboBox.getSelectedItem();
      }
    });
  }

  @Test(groups = GUI, expectedExceptions = LocationUnavailableException.class)
  public void shouldThrowErrorIfMatchingItemToSelectDoesNotExist() {
    driver.selectItem(comboBox, "hundred");
  }

  public void shouldReturnTextAtGivenIndex() {
    String value = driver.value(comboBox, 2);
    assertThat(value).isEqualTo("third");
    assertCellReaderWasCalled();
  }

  public void shouldReturnDropDownList() {
    driver.click(comboBox);
    JList dropDownList = driver.dropDownList();
    assertThat(contentsOf(dropDownList)).isEqualTo(array("first", "second", "third"));
  }

  @RunsInEDT
  private Object[] contentsOf(final JList list) {
    return execute(new GuiQuery<Object[]>() {
      protected Object[] executeInEDT() {
        ListModel model = list.getModel();
        int elementCount = model.getSize();
        Object[] elements = new Object[elementCount];
        for (int i = 0; i < elementCount; i++) 
          elements[i] = model.getElementAt(i);
        return elements;
      }
    });
  }

  public void shouldPassIfItHasExpectedSelection() {
    selectFirstItemInComboBox();
    driver.requireSelection(comboBox, "first");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfItDoesNotHaveExpectedSelection() {
    selectFirstItemInComboBox();
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("expected:<'second'> but was:<'first'>");
    }
  }

  public void shouldFailIfItDoesNotHaveAnySelectionAndExpectingSelection() {
    clearSelectionInComboBox();
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("No selection");
    }
  }

  public void shouldPassIfItIsEditableAndHasExpectedSelection() {
    makeComboBoxEditableAndSelectItem("Hello World");
    driver.requireSelection(comboBox, "Hello World");
  }

  public void shouldFailIfItIsEditableAndDoesNotHaveExpectedSelection() {
    makeComboBoxEditableAndSelectItem("Hello World");
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("expected:<'second'> but was:<'Hello World'>");
    }
  }
  
  @RunsInEDT
  private void makeComboBoxEditableAndSelectItem(Object itemToSelect) {
    setEditableAndSetSelectedItem(comboBox, itemToSelect);
    robot.waitForIdle();
  }
  
  @RunsInEDT
  private static void setEditableAndSetSelectedItem(final JComboBox comboBox, final Object itemToSelect) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setEditable(true);
        comboBox.setSelectedItem(itemToSelect);
      }
    });
  }

  public void shouldFailIfItEditableAndDoesNotHaveAnySelectionAndExpectingSelection() {
    setEditableAndClearSelection(comboBox);
    robot.waitForIdle();
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("No selection");
    }
  }
  
  @RunsInEDT
  private static void setEditableAndClearSelection(final JComboBox comboBox) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setSelectedIndex(-1);
        comboBox.setEditable(true);
      }
    });
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    clearSelectionInComboBox();
    driver.requireNoSelection(comboBox);
  }

  @RunsInEDT
  private void clearSelectionInComboBox() {
    setSelectedIndex(comboBox, (-1));
    robot.waitForIdle();
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstItemInComboBox();
    try {
      driver.requireNoSelection(comboBox);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'")
                                .contains("Expecting no selection, but found:<'first'>");
    }
  }

  public void shouldPassIfComboBoxIsEditable() {
    makeComboBoxEditable();
    driver.requireEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsNotEditableAndExpectingEditable() {
    makeComboBoxNotEditable();
    try {
      driver.requireEditable(comboBox);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldThrowErrorWhenSelectingAllTextInDisabledJComboBox() {
    disableComboBox();
    try {
      driver.selectAllText(comboBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingAllTextInNotShowingJComboBox() {
    hideWindow();
    try {
      driver.selectAllText(comboBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingAllTextInNotEditableJComboBox() {
    makeComboBoxNotEditable();
    try {
      driver.selectAllText(comboBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotEditableComboBox(e);
    }
  }
  public void shouldSelectAllText() {
    setEditableAndSelectFirstElement(comboBox, true);
    robot.waitForIdle();
    driver.selectAllText(comboBox);
    assertSelectedTextIsEqualTo("first");
  }

  @RunsInEDT
  private void assertSelectedTextIsEqualTo(String expected) {
    Component editor = editorOf(comboBox);
    assertThat(editor).isInstanceOf(JTextComponent.class);
    JTextComponent textBox = (JTextComponent)editor;
    assertThat(selectedTextOf(textBox)).isEqualTo(expected);
  }
  
  @RunsInEDT
  private static String selectedTextOf(final JTextComponent textBox) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return textBox.getSelectedText();
      }
    });
  }

  public void shouldThrowErrorWhenEnteringTextInDisabledJComboBox() {
    disableComboBox();
    try {
      driver.enterText(comboBox, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  private void disableComboBox() {
    disable(comboBox);
    robot.waitForIdle();
  }

  public void shouldThrowErrorWhenEnteringTextInNotShowingJComboBox() {
    hideWindow();
    try {
      driver.enterText(comboBox, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenEnteringTextInNotEditableJComboBox() {
   makeComboBoxNotEditable();
    try {
      driver.enterText(comboBox, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotEditableComboBox(e);
    }
  }

  public void shouldEnterText() {
    makeComboBoxEditable();
    driver.enterText(comboBox, "Hello");
    assertThat(textIn(comboBox)).contains("Hello");
  }

  private void assertActionFailureDueToNotEditableComboBox(IllegalStateException e) {
    assertThat(e.getMessage()).contains("Expecting component").contains("to be editable");
  }

  public void shouldReplaceText() {
    setEditableAndSelectFirstElement(comboBox, true);
    driver.replaceText(comboBox, "Hello");
    assertThat(textIn(comboBox)).isEqualTo("Hello");
  }

  @RunsInEDT
  private static void setEditableAndSelectFirstElement(final JComboBox comboBox, final boolean editable) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setSelectedIndex(0);
        comboBox.setEditable(editable);
      }
    });
  }

  public void shouldThrowErrorWhenReplacingTextInDisabledJComboBox() {
    disableComboBox();
    try {
      driver.replaceText(comboBox, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenReplacingTextInNotEditableJComboBox() {
    makeComboBoxNotEditable();
    try {
      driver.replaceText(comboBox, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotEditableComboBox(e);
    }
  }

  @RunsInEDT
  private void selectFirstItemInComboBox() {
    setSelectedIndex(comboBox, 0);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void assertThatSelectedIndexIsEqualTo(int expected) {
    int selectedIndex = selectedIndexOf(comboBox);
    assertThat(selectedIndex).isEqualTo(expected);
  }

  @RunsInEDT
  private static String textIn(final JComboBox comboBox) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT()  {
        Component editor = comboBox.getEditor().getEditorComponent();
        if (editor instanceof JLabel) return ((JLabel)editor).getText();
        if (editor instanceof JTextComponent) return ((JTextComponent)editor).getText();
        return null;
      }
    });
  }

  private static Component editorOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return comboBox.getEditor().getEditorComponent();
      }
    });
  }

  public void shouldPassIfComboBoxIsNotEditable() {
    makeComboBoxNotEditable();
    driver.requireNotEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsEditableAndExpectingNotEditable() {
    makeComboBoxEditable();
    try {
      driver.requireNotEditable(comboBox);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  public void shouldShowDropDownListWhenComboBoxIsNotEditable() {
    makeComboBoxNotEditable();
    driver.showDropDownList(comboBox);
    assertThatDropDownIsVisible();
  }

  public void shouldShowDropDownListWhenComboBoxIsEditable() {
    makeComboBoxEditable();
    driver.showDropDownList(comboBox);
    assertThatDropDownIsVisible();
  }

  @RunsInEDT
  private void makeComboBoxEditable() {
    setEditable(comboBox, true);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void makeComboBoxNotEditable() {
    setEditable(comboBox, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void assertThatDropDownIsVisible() {
    assertThat(isDropDownVisible(comboBox)).isTrue();
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void clearSelectionAndDisableComboBox() {
    clearSelectionAndDisable(comboBox);
    robot.waitForIdle();
  }
  
  @RunsInEDT
  private static void clearSelectionAndDisable(final JComboBox comboBox) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setSelectedIndex(-1);
        comboBox.setEnabled(false);
      }
    });
  }

  @RunsInEDT
  private static boolean isDropDownVisible(final JComboBox comboBox) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return comboBox.getUI().isPopupVisible(comboBox);
      }
    });
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

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

    @RunsInEDT
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxDriverTest.class);
      add(comboBox);
    }
  }

  private static class JComboBoxCellReaderStub extends BasicJComboBoxCellReader {
    private final MethodInvocations methodInvocations = new MethodInvocations();
    
    JComboBoxCellReaderStub() {}

    @Override public String valueAt(JComboBox comboBox, int index) {
      methodInvocations.invoked("valueAt");
      return super.valueAt(comboBox, index);
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
