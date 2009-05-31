/*
 * Created on Jan 12, 2008
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

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.Pair;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.CommonValidations.validateCellReader;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.driver.JTreeChildrenShowUpCondition.untilChildrenShowUp;
import static org.fest.swing.driver.JTreeEditableQuery.isEditable;
import static org.fest.swing.driver.JTreeExpandPathTask.expandPath;
import static org.fest.swing.driver.JTreeMatchingPathQuery.*;
import static org.fest.swing.driver.JTreeToggleExpandStateTask.toggleExpandState;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Arrays.*;
import static org.fest.util.Collections.list;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JTree}</code>. Unlike <code>JTreeFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JTree}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 */
public class JTreeDriver extends JComponentDriver {

  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTION_PROPERTY = "selection";

  private final JTreeLocation location;
  private final JTreePathFinder pathFinder;

  /**
   * Creates a new </code>{@link JTreeDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTreeDriver(Robot robot) {
    super(robot);
    location = new JTreeLocation();
    pathFinder = new JTreePathFinder();
  }

  /**
   * Change the open/closed state of the given row, if possible.
   * <p>
   * NOTE: a reasonable assumption is that the toggle control is just to the left of the row bounds and is roughly a
   * square the dimensions of the row height. Clicking in the center of that square should work.
   * </p>
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if is not possible to toggle row for the <code>JTree</code>'s <code>TreeUI</code>.
   */
  @RunsInEDT
  public void toggleRow(JTree tree, int row) {
    Pair<Point, Integer> toggleRowInfo = toggleRowInfo(tree, row, location);
    // Alternatively, we can reflect into the UI and do a single click on the appropriate expand location, but this is
    // safer.
    Point p = toggleRowInfo.i;
    int toggleClickCount = toggleRowInfo.ii;
    if (toggleClickCount == 0) {
      toggleRowThroughTreeUI(tree, p);
      robot.waitForIdle();
      return;
    }
    robot.click(tree, p, LEFT_BUTTON, toggleClickCount);
  }

  @RunsInEDT
  private static Pair<Point, Integer> toggleRowInfo(final JTree tree, final int row, final JTreeLocation location) {
    return execute(new GuiQuery<Pair<Point, Integer>>() {
      protected Pair<Point, Integer> executeInEDT() {
        validateIsEnabledAndShowing(tree);
        Point p = location.pointAt(tree, row);
        return new Pair<Point, Integer>(p, tree.getToggleClickCount());
      }
    });
  }
  
  @RunsInEDT
  private static void toggleRowThroughTreeUI(final JTree tree, final Point p) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        TreeUI treeUI = tree.getUI();
        if (!(treeUI instanceof BasicTreeUI)) throw actionFailure(concat("Can't toggle row for ", treeUI));
        toggleExpandState(tree, p);
      }
    });
  }

  /**
   * Selects the given rows.
   * @param tree the target <code>JTree</code>.
   * @param rows the rows to select.
   * @throws NullPointerException if the array of rows is <code>null</code>.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the given rows is less than zero or equal than or greater than the 
   * number of visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for any of the given rows cannot be found.
   */
  @RunsInEDT
  public void selectRows(final JTree tree, final int[] rows) {
    if (rows == null) throw new NullPointerException("The array of rows should not be null");
    if (isEmptyArray(rows)) throw new IllegalArgumentException("The array of rows should not be empty");
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return rows.length;
      }
      void selectElement(int index) {
        selectRow(tree, rows[index]);
      }
    }.multiSelect();
  }

  /**
   * Selects the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the row to select.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   */
  @RunsInEDT
  public void selectRow(JTree tree, int row) {
    scrollAndSelectRow(tree, row);
  }

  /**
   * Selects the given paths, expanding parent nodes if necessary.
   * @param tree the target <code>JTree</code>.
   * @param paths the paths to select.
   * @throws NullPointerException if the array of rows is <code>null</code>.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if any the given path cannot be found.
   */
  @RunsInEDT
  public void selectPaths(final JTree tree, final String[] paths) {
    if (paths == null) throw new NullPointerException("The array of paths should not be null");
    if (isEmpty(paths)) throw new IllegalArgumentException("The array of paths should not be empty");
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return paths.length;
      }
      void selectElement(int index) {
        selectPath(tree, paths[index]);
      }
    }.multiSelect();
  }

  /**
   * Selects the given path, expanding parent nodes if necessary.
   * @param tree the target <code>JTree</code>.
   * @param path the path to select.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the given path cannot be found.
   */
  @RunsInEDT
  public void selectPath(JTree tree, String path) {
    selectMatchingPath(tree, path);
  }

  /**
   * Shows a pop-up menu at the position of the node in the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @return a driver that manages the displayed pop-up menu.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  @RunsInEDT
  public JPopupMenu showPopupMenu(JTree tree, int row) {
    return robot.showPopupMenu(tree, validateAndFindPointAtRow(tree, row, location));
  }

  @RunsInEDT
  private static Point validateAndFindPointAtRow(final JTree tree, final int row, final JTreeLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(tree);
        return location.pointAt(tree, row);
      }
    });
  }

  /**
   * Shows a pop-up menu at the position of the last node in the given path. The last node in the given path will be
   * made visible (by expanding the parent node(s)) if it is not visible.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @return a driver that manages the displayed pop-up menu.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @see #separator(String)
   */
  @RunsInEDT
  public JPopupMenu showPopupMenu(JTree tree, String path) {
    TreePath matchingPath = findVisibleMatchingPath(tree, path, pathFinder);
    matchingPath = addRootIfInvisible(tree, matchingPath);
    makeVisible(tree, matchingPath, false);
    return robot.showPopupMenu(tree, pointAtPath(tree, matchingPath, location));
  }

  @RunsInEDT
  private static Point pointAtPath(final JTree tree, final TreePath path, final JTreeLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        return location.pointAt(tree, path);
      }
    });
  }

  /**
   * Starts a drag operation at the location of the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  @RunsInEDT
  public void drag(JTree tree, int row) {
    Point p = scrollAndSelectRow(tree, row);
    drag(tree, p);
  }

  @RunsInEDT
  private Point scrollAndSelectRow(JTree tree, int row) {
    Point p = scrollToRowToSelect(tree, row, location); 
    robot.click(tree, p);
    return p;
  }
  
  @RunsInEDT
  private static Point scrollToRowToSelect(final JTree tree, final int row, final JTreeLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(tree);
        Rectangle rowBounds = tree.getRowBounds(location.validIndex(tree, row));
        tree.scrollRectToVisible(rowBounds);
        return new Point(rowBounds.x + 1, rowBounds.y + rowBounds.height / 2);
      }
    });
  }

  /**
   * Ends a drag operation at the location of the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(JTree tree, int row) {
    drop(tree, scrollToRow(tree, row, location));
  }
  
  @RunsInEDT
  private static Point scrollToRow(final JTree tree, final int row, final JTreeLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(tree);
        tree.scrollRectToVisible(tree.getRowBounds(row));
        return location.pointAt(tree, row);
      }
    });
  }

  /**
   * Starts a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @see #separator(String)
   */
  @RunsInEDT
  public void drag(JTree tree, String path) {
    Point p = selectMatchingPath(tree, path);
    drag(tree, p);
  }

  @RunsInEDT
  private Point selectMatchingPath(JTree tree, String path) {
    TreePath matchingPath = findVisibleMatchingPath(tree, path, pathFinder);
    matchingPath = addRootIfInvisible(tree, matchingPath);
    makeVisible(tree, matchingPath, false);
    Point p = scrollToPathToSelect(tree, matchingPath, location);
    robot.click(tree, p);
    return p;
  }
  
  @RunsInEDT
  private static TreePath addRootIfInvisible(final JTree tree, final TreePath path) {
    return execute(new GuiQuery<TreePath>() {
      protected TreePath executeInEDT() {
        if (tree.isRootVisible()) return path;
        Object root = tree.getModel().getRoot();
        if (path.getPathCount() > 0) {
          Object first = path.getPathComponent(0);
          if (root == first) return path;
        }
        List<Object> newPath = list(path.getPath());
        newPath.add(0, root);
        return new TreePath(newPath.toArray());
      }
    });
  }
  
  @RunsInEDT
  private static Point scrollToPathToSelect(final JTree tree, final TreePath path, final JTreeLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        Rectangle pathBounds = tree.getPathBounds(path);
        tree.scrollRectToVisible(pathBounds);
        return new Point(pathBounds.x + 1, pathBounds.y + pathBounds.height / 2);
      }
    });
  }

  /**
   * Ends a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @throws IllegalStateException if the <code>JTree</code> is disabled.
   * @throws IllegalStateException if the <code>JTree</code> is not showing on the screen.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   * @see #separator(String)
   */
  @RunsInEDT
  public void drop(JTree tree, String path) {
    Point p = scrollToMatchingPath(tree, path, pathFinder, location);
    drop(tree, p);
  }

  @RunsInEDT
  private static Point scrollToMatchingPath(final JTree tree, final String path, 
      final JTreePathFinder pathFinder, final JTreeLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(tree);
        TreePath matchingPath = pathFinder.findMatchingPath(tree, path);
        tree.scrollRectToVisible(tree.getPathBounds(matchingPath));
        return location.pointAt(tree, matchingPath);
      }
    });
  }

  /**
   * Matches, makes visible, and expands the path one component at a time, from uppermost ancestor on down, since
   * children may be lazily loaded/created.
   * @param tree the target <code>JTree</code>.
   * @param path the tree path to make visible.
   * @param expandWhenFound indicates if nodes should be expanded or not when found.
   * @return if it was necessary to make visible and/or expand a node in the path.
   */
  @RunsInEDT
  private boolean makeVisible(JTree tree, TreePath path, boolean expandWhenFound) {
    boolean changed = false;
    if (path.getPathCount() > 1) changed = makeParentVisible(tree, path);
    if (!expandWhenFound) return changed;
    expandPath(tree, path);
    waitForChildrenToShowUp(tree, path);
    return true;
  }
  
  @RunsInEDT
  private boolean makeParentVisible(JTree tree, TreePath path) {
    boolean changed = makeVisible(tree, path.getParentPath(), true);
    if (changed) robot.waitForIdle();
    return changed;
  }

  @RunsInEDT
  private void waitForChildrenToShowUp(JTree tree, TreePath path) {
    int timeout = robot.settings().timeoutToBeVisible();
    try {
      pause(untilChildrenShowUp(tree, path), timeout);
    } catch (WaitTimedOutError e) {
      throw new LocationUnavailableException(e.getMessage());
    }
  }
  
  /**
   * Asserts that the given <code>{@link JTree}</code>'s selected rows are equal to the given one.
   * @param tree the target <code>JTree</code>.
   * @param rows the indices of the rows, expected to be selected.
   * @throws NullPointerException if the array of row indices is <code>null</code>.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given rows.
   */
  @RunsInEDT
  public void requireSelection(JTree tree, int[] rows) {
    if (rows == null) throw new NullPointerException("The array of row indices should not be null");
    requireSelection(tree, rows, pathFinder, selectionProperty(tree));
  }

  @RunsInEDT
  private static void requireSelection(final JTree tree, final int[] rows, final JTreePathFinder pathFinder, 
      final Description errorMessage) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        assertHasSelection(tree, rows, pathFinder, errorMessage);
      }
    });
  }

  @RunsInCurrentThread
  private static void assertHasSelection(final JTree tree, final int[] rows, final JTreePathFinder pathFinder,
      final Description errorMessage) {
    int[] selectionRows = tree.getSelectionRows();
    if (isEmptyArray(selectionRows)) failNoSelection(errorMessage);
    int rowCount = rows.length;
    for (int i = 0; i < rowCount; i++)
      assertHasSelection(rows[i], selectionRows, errorMessage);
  }

  private static boolean isEmptyArray(int[] array) { return array == null || array.length == 0; }

  @RunsInEDT
  private static void assertHasSelection(int row, int[] selectionRows, final Description errorMessage) {
    assertThat(selectionRows).as(errorMessage).contains(row);
  }

  /**
   * Asserts that the given <code>{@link JTree}</code>'s selected paths are equal to the given one.
   * @param tree the target <code>JTree</code>.
   * @param paths the given paths, expected to be selected.
   * @throws NullPointerException if the array of paths is <code>null</code>.
   * @throws LocationUnavailableException if any of the given paths cannot be found.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given paths.
   * @see #separator(String)
   */
  @RunsInEDT
  public void requireSelection(JTree tree, String[] paths) {
    if (paths == null) throw new NullPointerException("The array of paths should not be null");
    requireSelection(tree, paths, pathFinder, selectionProperty(tree));
  }

  @RunsInEDT
  private static void requireSelection(final JTree tree, final String[] paths, final JTreePathFinder pathFinder, 
      final Description errorMessage) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        assertHasSelection(tree, paths, pathFinder, errorMessage);
      }
    });
  }
  
  @RunsInCurrentThread
  private static void assertHasSelection(final JTree tree, final String[] paths, final JTreePathFinder pathFinder,
      final Description errorMessage) {
    TreePath[] selectionPaths = tree.getSelectionPaths();
    if (isEmpty(selectionPaths)) failNoSelection(errorMessage);
    int pathCount = paths.length;
    for (int i = 0; i < pathCount; i++)
      assertHasSelection(matchingPathFor(tree, paths[i], pathFinder), selectionPaths, errorMessage);
  }

  private static void failNoSelection(final Description errorMessage) {
    fail(concat("[", errorMessage.value(), "] No selection"));
  }

  @RunsInEDT
  private static void assertHasSelection(TreePath path, TreePath[] selectionPaths, final Description errorMessage) {
    assertThat(selectionPaths).as(errorMessage).contains(path);
  }
  
  /**
   * Asserts that the given <code>{@link JTree}</code> does not have any selection.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(JTree tree) {
    assertNoSelection(tree, selectionProperty(tree));
  }

  @RunsInEDT
  private static void assertNoSelection(final JTree tree, final Description errorMessage) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        if (tree.getSelectionCount() == 0) return;
        String message = concat(
            "[", errorMessage.value(), "] expected no selection but was:<", format(tree.getSelectionPaths()), ">");
        fail(message);
      }
    });
  }
  
  @RunsInEDT
  private Description selectionProperty(JTree tree) {
    return propertyName(tree, SELECTION_PROPERTY);
  }

  /**
   * Asserts that the given <code>{@link JTree}</code> is editable.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> is not editable.
   */
  @RunsInEDT
  public void requireEditable(JTree tree) {
    assertEditable(tree, true);
  }

  /**
   * Asserts that the given <code>{@link JTree}</code> is not editable.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> is editable.
   */
  @RunsInEDT
  public void requireNotEditable(JTree tree) {
    assertEditable(tree, false);
  }

  @RunsInEDT
  private void assertEditable(JTree tree, boolean editable) {
    assertThat(isEditable(tree)).as(editableProperty(tree)).isEqualTo(editable);
  }

  @RunsInEDT
  private static Description editableProperty(JTree tree) {
    return propertyName(tree, EDITABLE_PROPERTY);
  }

  /**
   * Returns the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @return the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   */
  public String separator() {
    return pathFinder.separator();
  }

  /**
   * Updates the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @param newSeparator the new separator.
   * @throws NullPointerException if the given separator is <code>null</code>.
   */
  public void separator(String newSeparator) {
    if (newSeparator == null) throw new NullPointerException("The path separator should not be null");
    pathFinder.separator(newSeparator);
  }

  /**
   * Updates the implementation of <code>{@link JTreeCellReader}</code> to use when comparing internal values of a
   * <code>{@link JTree}</code> and the values expected in a test.
   * @param newCellReader the new <code>JTreeCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JTreeCellReader newCellReader) {
    validateCellReader(newCellReader);
    pathFinder.cellReader(newCellReader);
  }
}
