/*
 * Created on Oct 13, 2008
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

import javax.swing.table.TableColumn;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.MethodInvocations.Args;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;

import static java.lang.Integer.parseInt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.MethodInvocations.Args.args;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTableColumnByIdentifierQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JTableColumnByIdentifierQueryTest {
  
  private Robot robot;
  private MyTable table;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    table = window.table;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test(dataProvider = "columnNames", groups = { GUI, ACTION })
  public void shouldReturnColumnIndexGivenIdentifier(String identifier) {
    table.startRecording();
    int index = parseInt(identifier);
    assertThat(columnIndexByIdentifier(table, identifier)).isEqualTo(index);
    table.requireInvoked("getColumn", args(identifier));
  }

  @DataProvider(name = "columnNames") public Object[][] columnNames() {
    return new Object[][] { { "0" }, { "1" }, { "2" }, { "3" } };
  }
  
  public void shouldReturnNegativeOneIfColumnIndexNotFound() {
    String identifier = "Hello World";
    table.startRecording();
    assertThat(columnIndexByIdentifier(table, identifier)).isEqualTo(-1);
    table.requireInvoked("getColumn", args(identifier));    
  }

  @RunsInEDT
  private static int columnIndexByIdentifier(final MyTable table, final String identifier) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return JTableColumnByIdentifierQuery.columnIndexByIdentifier(table, identifier);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyTable table = new MyTable();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTableColumnByIdentifierQueryTest.class);
      addComponents(table);
    }
  }

  private static class MyTable extends TestTable {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(2, 6);
    }

    @Override public TableColumn getColumn(Object identifier) {
      if (recording) methodInvocations.invoked("getColumn", args(identifier));
      return super.getColumn(identifier);
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }

}
