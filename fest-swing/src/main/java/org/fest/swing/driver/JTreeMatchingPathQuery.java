package org.fest.swing.driver;

import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Collections.list;

import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that finds a path in a <code>{@link JTree}</code>
 * that matches a given <code>String</code>.
 *
 * @author Alex Ruiz
 *
 * @see JTreePathFinder
 */
final class JTreeMatchingPathQuery {

  @RunsInEDT
  static TreePath findMatchingPathInVisibleAndEnabledJTree(final JTree tree, final String path,
      final JTreePathFinder pathFinder) {
    return execute(new GuiQuery<TreePath>() {
      protected TreePath executeInEDT() {
        validateIsEnabledAndShowing(tree);
        TreePath matchingPath = pathFinder.findMatchingPath(tree, path);
        return addRootIfInvisible(tree, matchingPath);
      }
    });
  }

  @RunsInEDT
  static TreePath matchingPathFor(final JTree tree, final String path, final JTreePathFinder pathFinder) {
    return execute(new GuiQuery<TreePath>() {
      protected TreePath executeInEDT() {
        TreePath matchingPath = pathFinder.findMatchingPath(tree, path);
        return addRootIfInvisible(tree, matchingPath);
      }
    });
  }

  /*
   * Adds the root node to the path, only if the JTree has an invisible root. If this is not done, a path missing the
   * root node cannot be expanded (issue 293.)
   */
  @RunsInCurrentThread
  static TreePath addRootIfInvisible(JTree tree, TreePath path) {
    if (path == null) return path;
    Object root = tree.getModel().getRoot();
    if (tree.isRootVisible() || root == null) return path;
    if (path.getPathCount() > 0 && root == path.getPathComponent(0)) return path;
    List<Object> newPath = list(path.getPath());
    newPath.add(0, root);
    return new TreePath(newPath.toArray());
  }

  private JTreeMatchingPathQuery() {}
}