/*
 * Created on Nov 1, 2008
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
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;

/**
 * Tests for <code>{@link JListSelectionValuesQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListSelectionValuesQueryTest {

  private Robot robot;
  private JList list;
  private JListCellReader cellReader;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    robot.showWindow(window);
    cellReader = new BasicJListCellReader();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnEmptyArrayIfJListHasNoSelection() {
    String[] selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
    assertThat(selection).isEmpty();
  }

  @Test(dataProvider = "selection", groups = GUI)
  public void shouldReturnSelectionOfJListAsText(List<Integer> indices, List<String> values) {
    setSelectedIndices(list, indices);
    robot.waitForIdle();
    String[] selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
    assertThat(selection).containsOnly(values.toArray());
  }

  @RunsInEDT
  private void setSelectedIndices(final JList list, final List<Integer> indices) {
    int count = indices.size();
    final int[] toSelect = new int[count];
    for (int i = 0; i < count; i++) toSelect[i] = indices.get(i);
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setSelectedIndices(toSelect);
      }
    });
  }

  @DataProvider(name = "selection") public Object[][] selection() {
    return new Object[][] {
        { list(0),       list("One") },
        { list(0, 1),    list("One", "Two") },
        { list(2, 3),    list("Three", "Four") },
        { list(0, 1, 2), list("One", "Two", "Three") }
    };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array("One", "Two", "Three", "Four"));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListSelectionValuesQueryTest.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
