/*
 * Created on Feb 25, 2008
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

import static java.awt.Color.BLUE;
import static java.awt.Font.PLAIN;
import static java.lang.Integer.parseInt;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.driver.JTableCellEditableQuery.isCellEditable;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.driver.JTableClearSelectionTask.clearSelectionOf;
import static org.fest.swing.driver.JTableRowCountQuery.rowCountOf;
import static org.fest.swing.driver.JTableSelectedRowCountQuery.selectedRowCountOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.swing.test.swing.TestTable.columnNames;
import static org.fest.swing.test.swing.TestTable.createCellTextUsing;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.util.Range.from;
import static org.fest.swing.util.Range.to;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellByColumnId;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.data.ZeroAndNegativeProvider;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JTableDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTableDriverTest {

  private static final int COLUMN_COUNT = 6;
  private static final int ROW_COUNT = 10;

  private Robot robot;
  private JTableCellReaderStub cellReader;
  private TestTable dragTable;
  private TestTable dropTable;
  private JTableDriver driver;
  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    cellReader = new JTableCellReaderStub();
    driver = new JTableDriver(robot);
    driver.cellReader(cellReader);
    window = MyWindow.createNew();
    dragTable = window.dragTable;
    dropTable = window.dropTable;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellToValidateIsNull() {
    driver.validate(dragTable, null);
  }

  @Test(groups = GUI, dataProvider = "cellsAndEventModes")
  public void shouldSelectCell(int row, int column) {
    driver.selectCell(dragTable, row(row).column(column));
    assertThat(isCellSelected(dragTable, row, column)).isTrue();
  }

  @DataProvider(name = "cellsAndEventModes")
  public Object[][] cellsAndEventModes() {
    return new Object[][] { { 6, 5 }, { 0, 0 }, { 8, 3 }, { 5, 2 } };
  }

  public void shouldReturnRowCount() {
    assertThat(driver.rowCountOf(dragTable)).isEqualTo(ROW_COUNT);
  }

  public void shouldThrowErrorWhenSelectingCellInDisabledJTable() {
    disableDragTable();
    try {
      driver.selectCell(dragTable, row(0).column(0));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingCellInNotShowingJTable() {
    hideWindow();
    try {
      driver.selectCell(dragTable, row(0).column(0));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfCellsToSelectIsNull() {
    TableCell[] cells = null;
    driver.selectCells(dragTable, cells);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfCellsToSelectIsEmpty() {
    TableCell[] cells = new TableCell[0];
    driver.selectCells(dragTable, cells);
  }

  public void shouldSelectCells() {
    setMultipleIntervalSelectionTo(dragTable);
    robot.waitForIdle();
    driver.selectCells(dragTable, new TableCell[] { row(0).column(0), row(2).column(0) });
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
    assertThat(isCellSelected(dragTable, 2, 0)).isTrue();
  }

  public void shouldSelectCellsEvenIfArrayHasOneElement() {
    setMultipleIntervalSelectionTo(dragTable);
    robot.waitForIdle();
    driver.selectCells(dragTable, new TableCell[] { row(0).column(0) });
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
  }

  public void shouldThrowErrorWhenSelectingCellsInDisabledJTable() {
    disableDragTable();
    try {
      driver.selectCells(dragTable, new TableCell[] { row(0).column(0), row(2).column(0) });
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldNotSelectCellIfAlreadySelected() {
    driver.selectCell(dragTable, row(0).column(0));
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
    driver.selectCell(dragTable, row(0).column(0));
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
  }

  private static boolean isCellSelected(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return table.isCellSelected(row, column);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldReturnValueOfGivenRowAndColumn(int row, int column) {
    String value = driver.value(dragTable, row, column);
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldReturnValueOfGivenCell(int row, int column) {
    String value = driver.value(dragTable, row(row).column(column));
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldReturnValueOfSelectedCell(int row, int column) {
    driver.selectCell(dragTable, row(row).column(column));
    String value = driver.selectionValue(dragTable);
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldFindCellByValue(int row, int column) {
    String value = createCellTextUsing(row, column);
    TableCell cell = driver.cell(dragTable, value);
    assertThat(cell.row).isEqualTo(row);
    assertThat(cell.column).isEqualTo(column);
    assertCellReaderWasCalled();
  }

  @DataProvider(name = "cells") public Object[][] cells() {
    return new Object[][] { { 6, 5 }, { 0, 0 }, { 8, 3 }, { 5, 2 } };
  }

  public void shouldFindCellMatchingPatternAsString() {
    TableCell cell = driver.cell(dragTable, "1.*");
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(0);
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfCellCannotBeFoundWithGivenValue() {
    try {
      driver.cell(dragTable, "Hello World");
      failWhenExpectingException();
    } catch (ActionFailedException expected) {
      assertThat(expected.getMessage()).contains("Unable to find cell matching value 'Hello World'");
    }
  }

  public void shouldFindCellMatchingPattern() {
    TableCell cell = driver.cell(dragTable, regex("1.*"));
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(0);
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "columnNames")
  public void shouldReturnCellHavingGivenColumnName(String columnName) {
    TableCell cell = driver.cell(dragTable, TableCellByColumnId.row(0).columnId(columnName));
    assertThat(cell.row).isEqualTo(0);
    assertThat(cell.column).isEqualTo(parseInt(columnName));
  }

  public void shouldThrowErrorWhenCreatingCellWithColumnNameIfGivenCellIsNull() {
    try {
      driver.cell(dragTable, (TableCellByColumnId)null);
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).contains("The instance of TableCellByColumnId should not be null");
    }
  }

  @Test(groups = GUI, expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorWhenCreatingCellWithColumnNameIfRowIndexIsOutOfBounds() {
    driver.cell(dragTable, TableCellByColumnId.row(-1).columnId("Hello"));
  }

  @Test(groups = GUI, expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorWhenCreatingCellWithColumnNameIfColumnWithMatchingNameNotFound() {
    driver.cell(dragTable, TableCellByColumnId.row(0).columnId("Hello"));
  }

  @Test(groups = GUI, dataProvider = "columnNames")
  public void shouldReturnColumnIndexGivenName(String columnName) {
    assertThat(driver.columnIndex(dragTable, columnName)).isEqualTo(parseInt(columnName));
  }

  @DataProvider(name = "columnNames") public Object[][] columnNameArray() {
    return new Object[][] { { "0" }, { "1" }, { "2" }, { "3" } };
  }

  public void shouldThrowErrorIfColumnWithGivenNameNotFound() {
    try {
      driver.columnIndex(dragTable, "Hello World");
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertThat(e.getMessage()).contains("Unable to find a column with id 'Hello World");
    }
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    clearSelectionOf(dragTable);
    robot.waitForIdle();
    driver.requireNoSelection(dragTable);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstCellOf(dragTable);
    robot.waitForIdle();
    try {
      driver.requireNoSelection(dragTable);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("expected no selection but was:<rows=[0], columns=[0]>");
    }
  }

  private static void selectFirstCellOf(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.changeSelection(0, 0, false, false);
      }
    });
  }

  public void shouldReturnNullAsSelectionContentIfNoSelectedCell() {
    assertThat(selectedRowCountOf(dragTable)).isZero();
    assertThat(driver.selectionValue(dragTable)).isNull();
  }

  public void shouldDragAndDrop() {
    int dragRowCount = rowCountOf(dragTable);
    int dropRowCount = rowCountOf(dropTable);
    driver.drag(dragTable, row(3).column(0));
    driver.drop(dropTable, row(1).column(0));
    assertThat(rowCountOf(dragTable)).isEqualTo(dragRowCount - 1);
    assertThat(cellValueOf(dragTable, 3, 0)).isEqualTo(createCellTextUsing(4, 0));
    assertThat(rowCountOf(dropTable)).isEqualTo(dropRowCount + 1);
    assertThat(cellValueOf(dropTable, 2, 0)).isEqualTo(createCellTextUsing(3, 0));
  }

  public void shouldPassIfCellValueIsEqualToExpected() {
    driver.requireCellValue(dragTable, row(0).column(0), "0-0");
  }

  public void shouldPassIfCellValueMatchesPatternAsString() {
    driver.requireCellValue(dragTable, row(0).column(0), "0.*");
  }

  public void shouldFailIfCellValueIsNotEqualToExpected() {
    try {
      driver.requireCellValue(dragTable, row(0).column(0), "0-1");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value [row=0, column=0]'")
                                .contains("actual value:<'0-0'> is not equal to or does not match pattern:<'0-1'>");
    }
  }

  public void shouldPassIfCellValueMatchesPattern() {
    driver.requireCellValue(dragTable, row(0).column(0), regex("0.*"));
  }

  public void shouldFailIfCellValueDoesNotMatchPattern() {
    try {
      driver.requireCellValue(dragTable, row(0).column(0), regex("0-1"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value [row=0, column=0]'")
                                .contains("actual value:<'0-0'> does not match pattern:<'0-1'>");
    }
  }

  public void shouldClickCellGivenNumberOfTimes() {
    ClickRecorder recorder = attachTo(dragTable);
    TableCell cell = row(0).column(1);
    driver.click(dragTable, cell, LEFT_BUTTON, 3);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(3);
    assertThatCellWasClicked(cell, recorder.pointClicked());
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class,
      dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfNumberOfTimesToClickCellIsZeroOrNegative(int index) {
    driver.click(dragTable, row(0).column(1), LEFT_BUTTON, index);
  }

  public void shouldShowPopupMenuAtCell() {
    setJPopupMenuToJTable(dragTable);
    robot.waitForIdle();
    ClickRecorder recorder = attachTo(dragTable);
    TableCell cell = row(0).column(1);
    driver.showPopupMenuAt(dragTable, cell);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(1);
    assertThatCellWasClicked(cell, recorder.pointClicked());
  }

  private void assertThatCellWasClicked(TableCell cell, Point pointClicked) {
    Point pointAtCell = new JTableLocation().pointAt(dragTable, cell.row, cell.column);
    assertThat(pointClicked).isEqualTo(pointAtCell);
  }

  private static void setJPopupMenuToJTable(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("Leia"));
        table.setComponentPopupMenu(popupMenu);
      }
    });
  }

  public void shouldReturnCellFont() {
    final JTableCellReader mockCellReader = mockCellReader();
    final Font font = new Font("SansSerif", PLAIN, 8);
    driver.cellReader(mockCellReader);
    new EasyMockTemplate(mockCellReader) {
      protected void expectations() {
        expect(mockCellReader.fontAt(dragTable, 0, 0)).andReturn(font);
      }

      protected void codeToTest() {
        Font result = driver.font(dragTable, row(0).column(0));
        assertThat(result).isSameAs(font);
      }
    }.run();
  }

  public void shouldReturnCellBackgroundColor() {
    final JTableCellReader mockCellReader = mockCellReader();
    final Color background = BLUE;
    driver.cellReader(mockCellReader);
    new EasyMockTemplate(mockCellReader) {
      protected void expectations() {
        expect(mockCellReader.backgroundAt(dragTable, 0, 0)).andReturn(background);
      }

      protected void codeToTest() {
        Color result = driver.background(dragTable, row(0).column(0));
        assertThat(result).isSameAs(background);
      }
    }.run();
  }

  public void shouldReturnCellForegroundColor() {
    final JTableCellReader mockCellReader = mockCellReader();
    final Color foreground = BLUE;
    driver.cellReader(mockCellReader);
    new EasyMockTemplate(mockCellReader) {
      protected void expectations() {
        expect(mockCellReader.foregroundAt(dragTable, 0, 0)).andReturn(foreground);
      }

      protected void codeToTest() {
        Color result = driver.foreground(dragTable, row(0).column(0));
        assertThat(result).isSameAs(foreground);
      }
    }.run();
  }

  private JTableCellReader mockCellReader() {
    return createMock(JTableCellReader.class);
  }

  @Test public void shouldEnterValueInCell() {
    dragTable.cellEditable(0, 0, true);
    robot.waitForIdle();
    final JTableCellWriter cellWriter = mockCellWriter();
    final String value = "Hello";
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.enterValue(dragTable, 0, 0, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.enterValueInCell(dragTable, row(0).column(0), value);
      }
    }.run();
  }

  public void shouldThrowErrorWhenEditingCellInDisabledJTable() {
    disableDragTable();
    try {
      driver.enterValueInCell(dragTable, row(0).column(0), "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenEditingCellInNotShowingJTable() {
    hideWindow();
    try {
      driver.enterValueInCell(dragTable, row(0).column(0), "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, expectedExceptions = IllegalStateException.class)
  public void shouldThrowErrorIfCellToEditIsNotEditable() {
    TableCell cell = row(0).column(0);
    assertThat(isCellEditable(dragTable, cell)).isFalse();
    driver.enterValueInCell(dragTable, cell, "Hello");
  }

  @Test public void shouldReturnEditorComponentInCell() {
    final JTableCellWriter cellWriter = mockCellWriter();
    final Component editor = textField().withText("Hello").createNew();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        expect(cellWriter.editorForCell(dragTable, 0, 0)).andReturn(editor);
      }

      protected void codeToTest() {
        Component result = driver.cellEditor(dragTable, row(0).column(0));
        assertThat(result).isSameAs(editor);
      }
    }.run();
  }

  @Test public void shouldStartCellEditing() {
    final JTableCellWriter cellWriter = mockCellWriter();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.startCellEditing(dragTable, 0, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.startCellEditing(dragTable, row(0).column(0));
      }
    }.run();
  }

  @Test public void shouldStopCellEditing() {
    final JTableCellWriter cellWriter = mockCellWriter();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.stopCellEditing(dragTable, 0, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.stopCellEditing(dragTable, row(0).column(0));
      }
    }.run();
  }

  @Test public void shouldCancelCellEditing() {
    final JTableCellWriter cellWriter = mockCellWriter();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.cancelCellEditing(dragTable, 0, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.cancelCellEditing(dragTable, row(0).column(0));
      }
    }.run();
  }

  private JTableCellWriter mockCellWriter() {
    return createMock(JTableCellWriter.class);
  }

  public void shouldPassIfCellIsEditableAsAnticipated() {
    dragTable.cellEditable(0, 0, true);
    robot.waitForIdle();
    driver.requireEditable(dragTable, row(0).column(0));
  }

  public void shouldFailIfCellIsNotEditableAndExpectingEditable() {
    dragTable.cellEditable(0, 0, false);
    robot.waitForIdle();
    try {
      driver.requireEditable(dragTable, row(0).column(0));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable [row=0, column=0]'")
                                .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfCellIsNotEditableAsAnticipated() {
    dragTable.cellEditable(0, 0, false);
    robot.waitForIdle();
    driver.requireNotEditable(dragTable, row(0).column(0));
  }

  public void shouldFailIfCellIsEditableAndExpectingNotEditable() {
    dragTable.cellEditable(0, 0, true);
    robot.waitForIdle();
    try {
      driver.requireNotEditable(dragTable, row(0).column(0));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable [row=0, column=0]'")
                                .contains("expected:<false> but was:<true>");
    }
  }

  private void assertCellReaderWasCalled() {
    cellReader.requireInvoked("valueAt");
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellWriterIsNull() {
    driver.cellWriter(null);
  }

  public void shouldReturnJTableHeader() {
    assertThat(driver.tableHeaderOf(dragTable)).isSameAs(window.dragTableHeader);
  }

  public void shouldFailIfRowCountNotEqualToExpected() {
    try {
      driver.requireRowCount(dragTable, 12);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'rowCount'")
                                .contains("expected:<12> but was:<10>");
    }
  }

  public void shouldPassIfRowCountIsEqualToExpected() {
    driver.requireRowCount(dragTable, ROW_COUNT);
  }

  public void shouldFailIfColumnCountNotEqualToExpected() {
    try {
      driver.requireColumnCount(dragTable, 12);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'columnCount'")
                                .contains("expected:<12> but was:<6>");
    }
  }

  public void shouldPassIfColumnCountIsEqualToExpected() {
    driver.requireColumnCount(dragTable, COLUMN_COUNT);
  }

  public void shouldReturnSelectedRowIndex() {
    selectRow(dragTable, 1);
    robot.waitForIdle();
    assertThat(driver.selectedRow(dragTable)).isEqualTo(1);
  }

  public void shouldThrowErrorIfTableDoesNotHaveSelectedRowAndExpectingOneSelectedRow() {
    clearSelectionOf(dragTable);
    robot.waitForIdle();
    try {
      driver.selectedRow(dragTable);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("The JTable does not have any selected row(s)");
    }
  }

  public void shouldThrowErrorIfTableHasMoreThanOneSelectedRowAndExpectingOneSelectedRow() {
    selectRows(dragTable, from(0), to(2));
    robot.waitForIdle();
    try {
      driver.selectedRow(dragTable);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Expecting JTable to have only one selected row, but had:<[0, 1, 2]>");
    }
  }

  @RunsInEDT
  private static void selectRow(JTable table, int row) {
    selectRows(table, row, row);
  }

  @RunsInEDT
  private static void selectRows(JTable table, From from, To to) {
    selectRows(table, from.value, to.value);
  }

  @RunsInEDT
  private static void selectRows(final JTable table, final int from, final int to) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        if (from != to) table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionInterval(from, to);
      }
    });
  }

  private static void setMultipleIntervalSelectionTo(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
      }
    });
  }

  @RunsInEDT
  private void disableDragTable() {
    disable(dragTable);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 100);

    final TestTable dragTable = new TestTable(ROW_COUNT, COLUMN_COUNT);
    final TestTable dropTable = new TestTable(dropTableData(2, COLUMN_COUNT), columnNames(COLUMN_COUNT));

    final JTableHeader dragTableHeader;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private static Object[][] dropTableData(int rowCount, int columnCount) {
      Object[][] data = new Object[rowCount][columnCount];
      for (int i = 0; i < rowCount; i++)
        for (int j = 0; j < columnCount; j++)
          data[i][j] = createCellTextUsing(ROW_COUNT + i, j);
      return data;
    }

    private MyWindow() {
      super(JTableDriverTest.class);
      add(decorate(dragTable));
      add(decorate(dropTable));
      dragTableHeader = dragTable.getTableHeader();
      setPreferredSize(new Dimension(600, 400));
    }

    private Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }

  private static class JTableCellReaderStub extends BasicJTableCellReader {
    private final MethodInvocations methodInvocations = new MethodInvocations();

    JTableCellReaderStub() {}

    @Override public String valueAt(JTable table, int row, int column) {
      methodInvocations.invoked("valueAt");
      return super.valueAt(table, row, column);
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
