/*
 * Created on Nov 3, 2008
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
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JListMatchingItemQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListMatchingItemQueryTest {

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

  @Test(dataProvider = "matchingItems", groups = GUI)
  public void shouldReturnIndexOfMatchingItem(final String value, int expectedIndex) {
    assertThat(findMatchingItem(value)).isEqualTo(expectedIndex);
  }

  @DataProvider(name = "matchingItems") public Object[][] matchingItems() {
    return new Object[][] {
        { "Yoda", 0 },
        { "Luke", 1 }
    };
  }

  @Test(dataProvider = "matchingPatternsAsString", groups = GUI)
  public void shouldReturnIndexOfItemMatchingPatternAsString(final String pattern, int expectedIndex) {
    assertThat(findMatchingItem(pattern)).isEqualTo(expectedIndex);
  }

  @DataProvider(name = "matchingPatternsAsString") public Object[][] matchingPatternsAsString() {
    return new Object[][] {
        { "Yod.*", 0 },
        { "Luk.*", 1 }
    };
  }

  @Test(dataProvider = "matchingPatterns", groups = GUI)
  public void shouldReturnIndexOfItemMatchingPattern(final Pattern pattern, int expectedIndex) {
    assertThat(findMatchingItem(pattern)).isEqualTo(expectedIndex);
  }

  @RunsInEDT
  private int findMatchingItem(final Pattern pattern) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return JListMatchingItemQuery.matchingItemIndex(list, pattern, cellReader);
      }
    });
  }

  @DataProvider(name = "matchingPatternsAsString") public Object[][] matchingPatterns() {
    return new Object[][] {
        { regex("Yod.*"), 0 },
        { regex("Luk.*"), 1 }
    };
  }

  public void shouldReturnNegativeOneIfMatchingItemNotFound() {
    assertThat(findMatchingItem("Leia")).isEqualTo(-1);
  }

  @RunsInEDT
  private int findMatchingItem(final String value) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return JListMatchingItemQuery.matchingItemIndex(list, value, cellReader);
      }
    });
  }

  public void shouldReturnIndicesOfItemsMatchingPatternAsString() {
    assertThat(findMatchingItems(".*")).hasSize(2).contains(0, 1);
  }

  public void shouldReturnIndicesOfItemsMatchingPatternsAsString() {
    assertThat(findMatchingItems("Y.*", "L.*")).hasSize(2).contains(0, 1);
  }

  @RunsInEDT
  private Collection<Integer> findMatchingItems(final String...values) {
    return JListMatchingItemQuery.matchingItemIndices(list, values, cellReader);
  }

  public void shouldReturnIndicesOfItemsMatchingPattern() {
    assertThat(findMatchingItems(regex(".*"))).hasSize(2).contains(0, 1);
  }

  public void shouldReturnIndicesOfItemsMatchingPatterns() {
    assertThat(findMatchingItems(regex("Y.*"), regex("L.*"))).hasSize(2).contains(0, 1);
  }

  @RunsInEDT
  private Collection<Integer> findMatchingItems(final Pattern...patterns) {
    return JListMatchingItemQuery.matchingItemIndices(list, patterns, cellReader);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array(new Jedi("Yoda"), new Jedi("Luke")));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListMatchingItemQueryTest.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
