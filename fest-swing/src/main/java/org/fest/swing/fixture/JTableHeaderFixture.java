/*
 * Created on Mar 16, 2008
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
package org.fest.swing.fixture;

import javax.swing.JPopupMenu;
import javax.swing.table.JTableHeader;

import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableHeaderDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Understands simulation of user events on a <code>{@link JTableHeader}</code>.
 *
 * @author Yvonne Wang
 */
public class JTableHeaderFixture extends ComponentFixture<JTableHeader> {

  private JTableHeaderDriver driver;

  /**
   * Creates a new </code>{@link JTableHeaderFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTableHeader</code>.
   * @param target the <code>JTableHeader</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JTableHeaderFixture(Robot robot, JTableHeader target) {
    super(robot, target);
    updateDriver(new JTableHeaderDriver(robot));
  }

  final void updateDriver(JTableHeaderDriver newDriver) {
    driver = newDriver;
  }

  /**
   * Simulates a user clicking the column under the given index, in this fixture's <code>{@link JTableHeader}</code>.
   * @param index the index of the column to click.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public JTableHeaderFixture clickColumn(int index) {
    driver.clickColumn(target, index);
    return this;
  }

  /**
   * Shows a pop-up menu using this fixture's <code>{@link JTableHeader}</code> as the invoker of the pop-up menu.
   * @param columnIndex the index of the column where the pop-up menu will be displayed.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(int columnIndex) {
    JPopupMenu popupMenu = driver.showPopupMenu(target, columnIndex);
    return new JPopupMenuFixture(robot, popupMenu);
  }

  /**
   * Shows a pop-up menu using this fixture's <code>{@link JTableHeader}</code> as the invoker of the pop-up menu.
   * @param columnName the name of the column where the pop-up menu will be displayed.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(String columnName) {
    JPopupMenu popupMenu = driver.showPopupMenu(target, columnName);
    return new JPopupMenuFixture(robot, popupMenu);
  }
  
  /**
   * Simulates a user clicking the column under the given index, in this fixture's <code>{@link JTableHeader}</code>,
   * using the given mouse button, the given number of times.
   * @param index the index of the column to click.
   * @param mouseClickInfo specifies the mouse button to use and the number of times to click.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public JTableHeaderFixture clickColumn(int index, MouseClickInfo mouseClickInfo) {
    driver.clickColumn(target, index, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given one, in this fixture's
   * <code>{@link JTableHeader}</code>.
   * @param columnName the column name to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is not showing on the screen.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public JTableHeaderFixture clickColumn(String columnName) {
    driver.clickColumn(target, columnName);
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given one, in this fixture's
   * <code>{@link JTableHeader}</code>, using the given mouse button, the given number of times.
   * @param columnName the column name to match
   * @param mouseClickInfo specifies the mouse button to use and the number of times to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTableHeader</code> is not showing on the screen.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public JTableHeaderFixture clickColumn(String columnName, MouseClickInfo mouseClickInfo) {
    if (mouseClickInfo == null) throw new NullPointerException("The given MouseClickInfo should not be null");
    driver.clickColumn(target, columnName, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }
}
