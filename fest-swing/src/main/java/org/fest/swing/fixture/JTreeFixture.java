/*
 * Created on May 21, 2007
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

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.BasicJTreeCellReader;
import org.fest.swing.driver.JTreeDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JTree}</code> and verification of the state of such
 * <code>{@link JTree}</code>.
 * <p>
 * <code>{@link TreePath}</code>s can be specified using <code>String</code>s. For example, for the following
 * tree:
 * 
 * <pre><code>
 * root
 *   |
 *   -- node1
 *      |
 *      -- node1.1
 * </code></pre>
 * 
 * we can identify the node "node1.1" as follows:
 * 
 * <pre><code>
 *   root/node1/node1.1
 * </code></pre>
 * 
 * </p>
 * <p>
 * The default path separator is "/". It can be changed by calling <code>{@link #separator(String)}</code>.
 * </p>
 * <p>
 * The conversion between the values given in tests and the values being displayed by a <code>{@link JTree}</code>
 * renderer is performed by a <code>{@link JTreeCellReader}</code>. This fixture uses a
 * <code>{@link BasicJTreeCellReader}</code> by default.
 * </p>
 * 
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JTreeFixture extends JPopupMenuInvokerFixture<JTree> implements CommonComponentFixture {

  private JTreeDriver driver;

  /**
   * Creates a new <code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTree</code>.
   * @param target the <code>JTree</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JTreeFixture(Robot robot, JTree target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTree</code>.
   * @param treeName the name of the <code>JTree</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JTree</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JTree</code> is found.
   */
  public JTreeFixture(Robot robot, String treeName) {
    super(robot, treeName, JTree.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JTreeDriver(robot));
  }

  void updateDriver(JTreeDriver newDriver) {
    driver = newDriver;
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   */
  public JTreeFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   */
  public JTreeFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   */
  public JTreeFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   */
  public JTreeFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   */
  public JTreeFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Simulates a user dragging a row from this fixture's <code>{@link JTree}</code>.
   * @param row the index of the row to drag.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JTreeFixture drag(int row) {
    driver.drag(target, row);
    return this;
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JTree}</code>.
   * @param path the path corresponding to the item to drag.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the given path cannot be found.
   */
  public JTreeFixture drag(String path) {
    driver.drag(target, path);
    return this;
  }

  /**
   * Simulates a user dropping an item into this fixture's <code>{@link JTree}</code>.
   * @param row the row to drop the item to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public JTreeFixture drop(int row) {
    driver.drop(target, row);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JTree}</code>.
   * @param path the path corresponding to the item to drop.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public JTreeFixture drop(String path) {
    driver.drop(target, path);
    return this;
  }

  /**
   * Select the given path, expanding parent nodes if necessary.
   * @param path the path to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the given path cannot be found.
   */
  public JTreeFixture selectPath(String path) {
    driver.selectPath(target, path);
    return this;
  }
  
  /**
   * Select the given paths, expanding parent nodes if necessary.
   * @param paths the paths to select.
   * @return this fixture.
   * @throws NullPointerException if the array of rows is <code>null</code>.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the any of the given paths cannot be found.
   */
  public JTreeFixture selectPaths(String... paths) {
    driver.selectPaths(target, paths);
    return this;
  }
 
  /**
   * Simulates a user selecting the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JTreeFixture selectRow(int row) {
    driver.selectRow(target, row);
    return this;
  }

  /**
   * Simulates a user selecting the tree nodes at the given rows.
   * @param rows the indices of the rows to select.
   * @return this fixture.
   * @throws NullPointerException if the array of rows is <code>null</code>.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for any of the given rows cannot be found.
   */
  public JTreeFixture selectRows(int... rows) {
    driver.selectRows(target, rows);
    return this;
  }

  /**
   * Simulates a user toggling the open/closed state of the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if is not possible to toggle row for the <code>JTree</code>'s <code>TreeUI</code>.
   */
  public JTreeFixture toggleRow(int row) {
    driver.toggleRow(target, row);
    return this;
  }

  /**
   * Shows a pop-up menu at the position of the node in the given row.
   * @param row the index of the row invoking the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(int row) {
    JPopupMenu popupMenu = driver.showPopupMenu(target, row);
    return new JPopupMenuFixture(robot, popupMenu);
  }
  
  /**
   * Shows a pop-up menu at the position of the last node in the given path. The last node in the given path will be
   * made visible (by expanding the parent node(s)) if it is not visible.
   * @param path the path of the node invoking the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if the given path cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(String path) {
    JPopupMenu popupMenu = driver.showPopupMenu(target, path);
    return new JPopupMenuFixture(robot, popupMenu);
  }

  /**
   * Gives input focus to this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   */
  public JTreeFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JTree}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JTreeFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys in this fixture's <code>{@link JTree}</code>.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JTreeFixture pressAndReleaseKeys(int...keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JTree}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JTreeFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JTree}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   * @throws IllegalStateException if this fixture's <code>JTree</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JTree</code> is not showing on the screen.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   */
  public JTreeFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> does not have input focus.
   */
  public JTreeFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is disabled.
   */
  public JTreeFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JTree</code> is never enabled.
   */
  public JTreeFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is enabled.
   */
  public JTreeFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is not visible.
   */
  public JTreeFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is visible.
   */
  public JTreeFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is editable.
   * @throws AssertionError if this fixture's <code>JTree</code> is not editable.
   * @return this fixture. 
   * @throws AssertionError if this fixture's <code>JTree</code> is not editable.
   */
  public JTreeFixture requireEditable() {
    driver.requireEditable(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is not editable.
   * @throws AssertionError if this fixture's <code>JTree</code> is editable.
   * @return this fixture. 
   * @throws AssertionError if this fixture's <code>JTree</code> is editable.
   */
  public JTreeFixture requireNotEditable() {
    driver.requireNotEditable(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> selection is equal to the given paths.
   * @param paths the given paths, expected to be selected.
   * @return this fixture.
   * @throws NullPointerException if the array of paths is <code>null</code>.
   * @throws LocationUnavailableException if any of the given path cannot be found.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given paths.
   */
  public JTreeFixture requireSelection(String... paths) {
    driver.requireSelection(target, paths);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTree}</code>'s selected rows are equal to the given one.
   * @param rows the indices of the rows, expected to be selected.
   * @throws NullPointerException if the array of row indices is <code>null</code>.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given rows.
   * @return this fixture.
   */
  public JTreeFixture requireSelection(int... rows) {
    driver.requireSelection(target, rows);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTree}</code>'s does not have any selection.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> has a selection.
   */
  public JTreeFixture requireNoSelection() {
    driver.requireNoSelection(target);
    return this;
  }

  /**
   * Returns the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @return the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   */
  public String separator() {
    return driver.separator();
  }

  /**
   * Updates the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s. The default
   * value is "/".
   * @param separator the new separator.
   * @return this fixture.
   * @throws NullPointerException if the given separator is <code>null</code>.
   */
  public JTreeFixture separator(String separator) {
    driver.separator(separator);
    return this;
  }
  
  /**
   * Updates the implementation of <code>{@link JTreeCellReader}</code> to use when comparing internal values of a
   * <code>{@link JTree}</code> and the values expected in a test. The default implementation to use
   * is <code>{@link BasicJTreeCellReader}</code>.
   * @param cellReader the new <code>JTreeCellValueReader</code> to use.
   * @throws NullPointerException if <code>cellReader</code> is <code>null</code>.
   * @return this fixture.
   */
  public JTreeFixture cellReader(JTreeCellReader cellReader) {
    driver.cellReader(cellReader);
    return this;
  }
}