/*
 * Created on Nov 14, 2008
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
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.ACTION;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.util.regex.Pattern;

import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JComboBoxMatchingItemQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JComboBoxMatchingItemQueryTest {

  private static final String[] ITEMS = { "first", "second", "third" };

  private Robot robot;
  private JComboBox comboBox;
  private JComboBoxCellReader cellReader;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    cellReader = new BasicJComboBoxCellReader();
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "indices", groups = { GUI, ACTION })
  public void shouldReturnMatchingIndex(int index) {
    assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, ITEMS[index], cellReader)).isEqualTo(index);
  }

  public void shouldReturnMatchingIndexWhenUsingPatternAsString() {
    assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, "f.*", cellReader)).isEqualTo(0);
  }

  public void shouldReturnNegativeOneIfNoMatchingIndexFound() {
    assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, "Hello", cellReader)).isEqualTo(-1);
  }

  @DataProvider(name = "indices") public Object[][] indices() {
    return new Object[][] { { 0 }, { 1 }, { 2 } };
  }

  public void shouldReturnMatchingIndexWhenUsingPattern() {
    Pattern p = regex("f.*");
    assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, p, cellReader)).isEqualTo(0);
  }

  public void shouldReturnNegativeOneIfNoMatchingIndexFoundWhenUsingPattern() {
    Pattern p = regex("Hello");
    assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, p, cellReader)).isEqualTo(-1);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(ITEMS);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxMatchingItemQuery.class);
      addComponents(comboBox);
    }
  }

}
