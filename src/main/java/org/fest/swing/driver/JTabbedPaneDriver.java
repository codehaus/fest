/*
 * Created on Jan 27, 2008
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
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.data.Index;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.driver.JTabbedPaneSelectTabTask.setSelectedTab;
import static org.fest.swing.driver.JTabbedPaneTabTitlesQuery.tabTitlesOf;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands simulation of user input on a <code>{@link JTabbedPane}</code>. Unlike <code>JTabbedPaneFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JTabbedPane}</code>s. This class is intended for
 * internal use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneDriver extends JComponentDriver {

  private final JTabbedPaneLocation location;

  /**
   * Creates a new </code>{@link JTabbedPaneDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTabbedPaneDriver(Robot robot) {
    this(robot, new JTabbedPaneLocation());
  }

  /**
   * Creates a new </code>{@link JTabbedPaneDriver}</code>.
   * @param robot the robot to use to simulate user input.
   * @param location knows how to find the location of a tab.
   */
  JTabbedPaneDriver(Robot robot, JTabbedPaneLocation location) {
    super(robot);
    this.location = location;
  }

  /**
   * Returns the titles of all the tabs.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @return the titles of all the tabs.
   */
  @RunsInEDT
  public String[] tabTitles(JTabbedPane tabbedPane) {
    return tabTitlesOf(tabbedPane);
  }

  /**
   * Simulates a user selecting the tab containing the given title.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param title the given text to match.
   * @throws IllegalStateException if the <code>JTabbedPane</code> is disabled.
   * @throws IllegalStateException if the <code>JTabbedPane</code> is not showing on the screen.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  @RunsInEDT
  public void selectTab(JTabbedPane tabbedPane, String title) {
    Pair<Integer, Point> tabToSelectInfo = tabToSelectInfo(location, tabbedPane, title);
    Point target = tabToSelectInfo.ii;
    if (target != null) {
      click(tabbedPane, target);
      return;
    }
    setTabDirectly(tabbedPane, tabToSelectInfo.i);
  }

  @RunsInEDT
  private static Pair<Integer, Point> tabToSelectInfo(final JTabbedPaneLocation location, final JTabbedPane tabbedPane, final String title) {
    return execute(new GuiQuery<Pair<Integer, Point>>() {
      protected Pair<Integer, Point> executeInEDT() {
        validateIsEnabledAndShowing(tabbedPane);
        int index = location.indexOf(tabbedPane, title);
        Point point = null;
        try {
          point = location.pointAt(tabbedPane, index);
        } catch (LocationUnavailableException e) {}
        return new Pair<Integer, Point>(index, point);
      }
    });
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param index the index of the tab to select.
   * @throws IllegalStateException if the <code>JTabbedPane</code> is disabled.
   * @throws IllegalStateException if the <code>JTabbedPane</code> is not showing on the screen.
   * @throws IllegalArgumentException if the given index is not within the <code>JTabbedPane</code> bounds.
   */
  public void selectTab(JTabbedPane tabbedPane, int index) {
    try {
      Point p = pointAt(location, tabbedPane, index);
      click(tabbedPane, p);
    } catch (LocationUnavailableException e) {
      setTabDirectly(tabbedPane, index);
    }
  }

  @RunsInEDT
  private static Point pointAt(final JTabbedPaneLocation location, final JTabbedPane tabbedPane, final int index) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(tabbedPane);
        return location.pointAt(tabbedPane, index);
      }
    });
  }

  @RunsInEDT
  void setTabDirectly(JTabbedPane tabbedPane, int index) {
    setSelectedTab(tabbedPane, index);
    robot.waitForIdle();
  }

  /**
   * Returns the currently selected component for the given <code>{@link JTabbedPane}</code>.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @return the currently selected component for the given <code>JTabbedPane</code>.
   */
  @RunsInEDT
  public Component selectedComponentOf(JTabbedPane tabbedPane) {
    return selectedComponent(tabbedPane);
  }

  @RunsInEDT
  private static Component selectedComponent(final JTabbedPane tabbedPane) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return tabbedPane.getSelectedComponent();
      }
    });
  }

  /**
   * Asserts that the title of the tab at the given index is equal to the given title.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param title the expected title.
   * @param index the index of the tab.
   * @throws AssertionError if the title of the tab at the given index is not equal to the given one.
   */
  @RunsInEDT
  public void requireTabTitle(JTabbedPane tabbedPane, String title, Index index) {
    String actualTitle = titleAt(tabbedPane, index);
    assertThat(actualTitle).as(propertyName(tabbedPane, "titleAt")).isEqualTo(title);
  }

  @RunsInEDT
  private static String titleAt(final JTabbedPane tabbedPane, final Index index) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return tabbedPane.getTitleAt(index.value);
      }
    });
  }

  /**
   * Asserts that the tabs of the given <code>{@link JTabbedPane}</code> have the given titles. The tab titles are
   * evaluated by index order, for example, the first tab is expected to have the first title in the given array, and so 
   * on.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param titles the expected titles.
   * @throws AssertionError if the title of any of the tabs is not equal to the expected titles.
   */
  @RunsInEDT
  public void requireTabTitles(JTabbedPane tabbedPane, String[] titles) {
    String[] actualTitles = allTabTitlesIn(tabbedPane);
    assertThat(actualTitles).as(propertyName(tabbedPane, "tabTitles")).isEqualTo(titles);
  }

  @RunsInEDT
  private static String[] allTabTitlesIn(final JTabbedPane tabbedPane) {
    return execute(new GuiQuery<String[]>() {
      protected String[] executeInEDT() {
        List<String> allTitles = new ArrayList<String>();
        int tabCount = tabbedPane.getTabCount();
        for (int i = 0; i < tabCount; i++)
          allTitles.add(tabbedPane.getTitleAt(i));
        return allTitles.toArray(new String[allTitles.size()]);
      }
    });
  }
}
