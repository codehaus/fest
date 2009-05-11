/*
 * Created on Jul 12, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static java.awt.Color.BLUE;
import static java.awt.Font.PLAIN;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseClickInfo.leftButton;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.test.builder.JPopupMenus.popupMenu;
import static org.fest.swing.test.builder.JTableHeaders.tableHeader;
import static org.fest.swing.test.builder.JTables.table;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellByColumnId;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTableDriver;
import org.fest.swing.exception.ActionFailedException;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test
public class JTableFixtureTest extends CommonComponentFixtureTestCase<JTable> {

  private JTableDriver driver;
  private JTable target;
  private JTableFixture fixture;
  private TableCell cell;

  void onSetUp() {
    driver = createMock(JTableDriver.class);
    target = table().createNew();
    fixture = new JTableFixture(robot(), target);
    fixture.driver(driver);
    cell = row(6).column(8);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "table";
    expectLookupByName(name, JTable.class);
    verifyLookup(new JTableFixture(robot(), name));
  }

  public void shouldReturnColumnIndexGivenName() {
    final String columnName = "column0";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.columnIndex(target, columnName)).andReturn(6);
      }

      protected void codeToTest() {
        int result = fixture.columnIndexFor(columnName);
        assertThat(result).isEqualTo(6);
      }
    }.run();
  }

  public void shouldThrowErrorIfColumnWithGivenNameNotFound() {
    final ActionFailedException expected = actionFailure("Thrown on purpose");
    final String columnName = "column0";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.columnIndex(target, columnName)).andThrow(expected);
      }

      protected void codeToTest() {
        try {
          fixture.columnIndexFor(columnName);
          failWhenExpectingException();
        } catch (ActionFailedException e) {
          assertThat(e).isSameAs(expected);
        }
      }
    }.run();
  }

  public void shouldReturnCellHavingGivenColumnName() {
    final int row = 6;
    final int column = 8;
    final TableCellByColumnId cellByColumnName = TableCellByColumnId.row(row).columnId("column0");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.cell(target, cellByColumnName)).andReturn(row(row).column(column));
      }

      protected void codeToTest() {
        JTableCellFixture cellFixture = fixture.cell(cellByColumnName);
        assertThat(cellFixture.row()).isEqualTo(row);
        assertThat(cellFixture.column()).isEqualTo(column);
      }
    }.run();
  }

  public void shouldReturnRowCount() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.rowCountOf(target)).andReturn(3);
      }

      protected void codeToTest() {
        int result = fixture.rowCount();
        assertThat(result).isEqualTo(3);
      }
    }.run();
  }

  public void shouldSelectCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectCell(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectCell(cell));
      }
    }.run();
  }

  public void shouldRequireNoSelection() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNoSelection(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNoSelection());
      }
    }.run();
  }

  public void shouldSelectCells() {
    final TableCell[] cells = { cell };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectCells(target, cells);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectCells(cells));
      }
    }.run();
  }

  public void shouldReturnSelectionContents() {
    final String content = "A Cell";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.selectionValue(target)).andReturn(content);
      }

      protected void codeToTest() {
        Object result = fixture.selectionValue();
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  public void shouldDragAtCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, cell);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(cell));
      }
    }.run();
  }

  public void shouldDropAtCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, cell);
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(cell));
      }
    }.run();
  }

  public void shouldReturnPointAtCell() {
    final Point p = new Point(6, 8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.pointAt(target(), cell)).andReturn(p);
      }

      protected void codeToTest() {
        Point result = fixture.pointAt(cell);
        assertThat(result).isSameAs(p);
      }
    }.run();
  }

  public void shouldReturnCellContent() {
    final String content = "A Cell";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.value(target(), cell)).andReturn(content);
      }

      protected void codeToTest() {
        Object result = fixture.valueAt(cell);
        assertThat(result).isSameAs(content);
      }
    }.run();
  }

  public void shouldClickCellWithGivenMouseButton() {
    final MouseButton button = LEFT_BUTTON;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.click(target, cell, button, 1);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click(cell, button));
      }
    }.run();
  }

  public void shouldClickCellWithGivenMouseClickInfo() {
    final MouseClickInfo mouseClickInfo = leftButton().times(2);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.click(target, cell, mouseClickInfo.button(), mouseClickInfo.times());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click(cell, mouseClickInfo));
      }
    }.run();
  }

  public void shouldShowJPopupMenuAtCell() {
    final JPopupMenu popup = popupMenu().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenuAt(target, cell)).andReturn(popup);
      }

      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(cell);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  public void shouldReturnJTableHeaderFixture() {
    final JTableHeader header = tableHeader().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.tableHeaderOf(target)).andReturn(header);
      }

      protected void codeToTest() {
        JTableHeaderFixture tableHeader = fixture.tableHeader();
        assertThat(tableHeader.target).isSameAs(header);
      }
    }.run();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldThrowErrorIfJTableHeaderIsNull() {
    fixture.tableHeader();
  }

  public void shouldSetCellReaderInDriver() {
    final JTableCellReader reader = createMock(JTableCellReader.class);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.cellReader(reader);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.cellReader(reader));
      }
    }.run();
  }

  public void shouldSetCellWriterInDriver() {
    final JTableCellWriter writer = createMock(JTableCellWriter.class);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.cellWriter(writer);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.cellWriter(writer));
      }
    }.run();
  }

  public void shouldReturnCellFont() {
    final Font font = new Font("SansSerif", PLAIN, 8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.font(target, cell)).andReturn(font);
      }

      protected void codeToTest() {
        FontFixture fontFixture = fixture.fontAt(cell);
        assertThat(fontFixture.target()).isSameAs(font);
        assertThat(fontFixture.description()).contains(target.getClass().getName())
                                             .contains("property:'font [row=6, column=8]'");
      }
    }.run();
  }

  public void shouldReturnCellBackgroundColor() {
    final Color background = BLUE;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.background(target, cell)).andReturn(background);
      }

      protected void codeToTest() {
        ColorFixture colorFixture = fixture.backgroundAt(cell);
        assertThat(colorFixture.target()).isSameAs(background);
        assertThat(colorFixture.description()).contains(target.getClass().getName())
                                              .contains("property:'background [row=6, column=8]'");
      }
    }.run();
  }

  public void shouldReturnCellForegroundColor() {
    final Color foreground = BLUE;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.foreground(target, cell)).andReturn(foreground);
      }

      protected void codeToTest() {
        ColorFixture colorFixture = fixture.foregroundAt(cell);
        assertThat(colorFixture.target()).isSameAs(foreground);
        assertThat(colorFixture.description()).contains(target.getClass().getName())
                                              .contains("property:'foreground [row=6, column=8]'");
      }
    }.run();
  }

  public void shouldRequireCellValue() {
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireCellValue(target, cell, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireCellValue(cell, value));
      }
    }.run();
  }

  public void shouldRequireEditableCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEditable(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEditable(cell));
      }
    }.run();
  }

  public void shouldRequireNotEditableCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotEditable(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotEditable(cell));
      }
    }.run();
  }

  public void shouldEnterValueInCell() {
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.enterValueInCell(target, cell, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.enterValue(cell, value));
      }
    }.run();
  }

  public void shouldReturnCell() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.validate(target, cell);
        expectLastCall().once();
      }

      protected void codeToTest() {
        JTableCellFixture cellFixture = fixture.cell(cell);
        assertThat(cellFixture.table()).isSameAs(fixture);
        assertThat(cellFixture.cell()).isSameAs(cell);
      }
    }.run();
  }

  public void shouldFindCellByValue() {
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.cell(target, value)).andReturn(cell);
      }

      protected void codeToTest() {
        TableCell result = fixture.cell(value);
        assertThat(result).isEqualTo(cell);
      }
    }.run();
  }

  public void shouldThrowErrorIfCellCannotBeFoundWithGivenValue() {
    final ActionFailedException expected = actionFailure("Thrown on purpose");
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.cell(target, value)).andThrow(expected);
      }

      protected void codeToTest() {
        try {
          fixture.cell(value);
          failWhenExpectingException();
        } catch (ActionFailedException e) {
          assertThat(e).isSameAs(expected);
        }
      }
    }.run();
  }

  public void shouldRequireRowCount() {
    final int rowCount = 6;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireRowCount(target, rowCount);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireRowCount(rowCount));
      }
    }.run();
  }

  public void shouldRequireColumnCount() {
    final int columnCount = 6;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireColumnCount(target, columnCount);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireColumnCount(columnCount));
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JTable target() { return target; }
  JTableFixture fixture() { return fixture; }
}
