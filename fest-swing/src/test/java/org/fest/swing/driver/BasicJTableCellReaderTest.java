/*
 * Created on Apr 12, 2008
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
import static java.awt.Color.WHITE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentBackgroundQuery.backgroundOf;
import static org.fest.swing.query.ComponentFontQuery.fontOf;
import static org.fest.swing.query.ComponentForegroundQuery.foregroundOf;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.data.BooleanProvider;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BasicJTableCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJTableCellReaderTest {

  private Robot robot;
  private JTable table;
  private BasicJTableCellReader reader;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    table = window.table;
    reader = new BasicJTableCellReader();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnModelValueToStringIfRendererNotRecognized() {
    setModelData(table, new Object[][] { array(new Jedi("Yoda")) }, array("Names"));
    setJToolBarAsCellRenderer(table);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  @RunsInEDT
  private static void setModelData(final JTable table, final Object[][] data, final Object[] columnNames) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
      }
    });
  }

  @RunsInEDT
  private static void setJToolBarAsCellRenderer(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        setCellRendererComponent(table, new JToolBar());
      }
    });
  }

  public void shouldReturnFontFromRenderer() {
    JLabel label = setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Font font = fontAt(reader, table, 0, 0);
    assertThat(font).isEqualTo(fontOf(label));
  }
  
  @RunsInEDT
  private static Font fontAt(final BasicJTableCellReader reader, final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Font>() {
      protected Font executeInEDT() {
        return reader.fontAt(table, row, column);
      }
    });
  }

  public void shouldReturnBackgroundColorFromRenderer() {
    JLabel label = setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Color background = backgroundAt(reader, table, 0, 0);
    assertThat(background).isEqualTo(backgroundOf(label));
  }

  @RunsInEDT
  private static Color backgroundAt(final BasicJTableCellReader reader, final JTable table, final int row, 
      final int column) {
    return execute(new GuiQuery<Color>() {
      protected Color executeInEDT() {
        return reader.backgroundAt(table, row, column);
      }
    });
  }

  public void shouldReturnForegroundColorFromRenderer() {
    JLabel label = setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Color foreground = foregroundAt(reader, table, 0, 0);
    assertThat(foreground).isEqualTo(foregroundOf(label));
  }

  @RunsInEDT
  private static Color foregroundAt(final BasicJTableCellReader reader, final JTable table, final int row, 
      final int column) {
    return execute(new GuiQuery<Color>() {
      protected Color executeInEDT() {
        return reader.foregroundAt(table, row, column);
      }
    });
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Hello");
  }

  @RunsInEDT
  private static JLabel setJLabelAsCellRenderer(final JTable table) {
    return execute(new GuiQuery<JLabel>() {
      protected JLabel executeInEDT() {
        JLabel label = new JLabel("Hello");
        label.setBackground(BLUE);
        label.setForeground(WHITE);
        setCellRendererComponent(table, label);
        return label;
      }
    });
  }

  public void shouldReturnSelectionFromCellRendererIfRendererIsJComboBox() {
    setJComboBoxAsCellRenderer(table, 1);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }

  public void shouldReturnNullIfRendererIsJComboBoxWithoutSelection() {
    setJComboBoxAsCellRenderer(table, -1);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isNull();
  }

  @RunsInEDT
  private static void setJComboBoxAsCellRenderer(final JTable table, final int comboBoxSelectedIndex) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        JComboBox comboBox = new JComboBox(array("One", "Two"));
        comboBox.setSelectedIndex(comboBoxSelectedIndex);
        setCellRendererComponent(table, comboBox);
      }
    });
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsSelectedIfRendererIsJCheckBox(boolean selected) {
    setJCheckBoxAsCellRenderer(table, "Hello", selected);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo(String.valueOf(selected));
  }

  @RunsInEDT
  private static void setJCheckBoxAsCellRenderer(final JTable table, final String text, final boolean selected) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        JCheckBox checkBox = new JCheckBox(text, selected);
        setCellRendererComponent(table, checkBox);
      }
    });
  }

  @RunsInCurrentThread
  private static void setCellRendererComponent(JTable table, Component renderer) {
    CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
  }
  
  @RunsInEDT
  private static String valueAt(final BasicJTableCellReader reader, final JTable table, final int row, final 
      int column) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return reader.valueAt(table, row, column);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTable table = new JTable(1, 1);

    private MyWindow() {
      super(BasicJTableCellReaderTest.class);
      addComponents(table);
    }
  }
}
