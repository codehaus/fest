/*
 * Created on Aug 11, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.util.PatternTextMatcher;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableHeaderLocation}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTableHeaderLocationTest {

  private Robot robot;
  private JTableHeaderLocation location;
  private JTableHeader tableHeader;

  @BeforeClass public void setUpClass() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    location = new JTableHeaderLocation();
    MyWindow window = MyWindow.createNew();
    tableHeader = window.tableHeader;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "columnIndices")
  public void shouldReturnPointAtHeaderByIndex(int index) {
    Point point = pointAt(location, tableHeader, index);
    assertThat(point).isEqualTo(expectedPoint(index));
  }

  @DataProvider(name = "columnIndices") public Object[][] columnIndices() {
    return new Object[][] { { 0 }, { 1 } };
  }

  @Test(groups = GUI, dataProvider = "columnNames")
  public void shouldClickColumnWithName(String columnName, int columnIndex) {
    Point point = pointAt(location, tableHeader, columnName);
    assertThat(point).isEqualTo(expectedPoint(columnIndex));
  }

  @DataProvider(name = "columnNames") public Object[][] columnNames() {
    return new Object[][] { { "0", 0 }, { "1", 1 } };
  }

  @RunsInEDT
  private Point expectedPoint(int index) {
    final JTableHeader header = tableHeader;
    Rectangle r = rectOf(header, index);
    Point expected = new Point(r.x + r.width / 2, r.y + r.height / 2);
    return expected;
  }

  @RunsInEDT
  private static Rectangle rectOf(final JTableHeader tableHeader, final int index) {
    return execute(new GuiQuery<Rectangle>() {
      protected Rectangle executeInEDT() {
        return tableHeader.getHeaderRect(index);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "indicesOutOfBound", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfColumnIndexOutOfBounds(int columnIndex) {
    pointAt(location, tableHeader, columnIndex);
  }

  @DataProvider(name = "indicesOutOfBound") public Object[][] indicesOutOfBound() {
    return new Object[][] { { -1 }, { 2 } };
  }

  public void shouldThrowErrorIfColumnNameNotMatching() {
    try {
      pointAt(location, tableHeader, "Hello");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find column with name matching value 'Hello'");
    }
  }
  
  public void shouldThrowErrorIfColumnNameNotMatchingPattern() {
    try {
      pointAt(location, tableHeader, regex("Hello"));
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find column with name matching pattern 'Hello'");
    }
  }

  @RunsInEDT
  private static Point pointAt(final JTableHeaderLocation location, final JTableHeader tableHeader, final int index) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() throws Throwable {
        return location.pointAt(tableHeader, index);
      }
    });
  }

  @RunsInEDT
  private static Point pointAt(final JTableHeaderLocation location, final JTableHeader tableHeader, 
      final String columnName) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() throws Throwable {
        return location.pointAt(tableHeader, columnName);
      }
    });
  }

  @RunsInEDT
  private static Point pointAt(final JTableHeaderLocation location, final JTableHeader tableHeader, 
      final Pattern columnNamePattern) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() throws Throwable {
        return location.pointAt(tableHeader, new PatternTextMatcher(columnNamePattern));
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 200);

    final TestTable table;
    final JTableHeader tableHeader;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTableHeaderLocationTest.class);
      table = new TestTable(6, 2);
      tableHeader = table.getTableHeader();
      add(decorate(table));
      setPreferredSize(new Dimension(600, 400));
    }

    private static Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }

}
