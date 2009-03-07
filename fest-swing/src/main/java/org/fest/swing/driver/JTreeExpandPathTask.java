package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.util.Arrays;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands a task that ensures that the node identified by the specified path is expanded and viewable. This action
 * is executed in the event dispatch thread.
 * @see JTree#isExpanded(TreePath)
 * @see JTree#expandPath(TreePath)
 *
 * @author Yvonne Wang
 */
final class JTreeExpandPathTask {

  @RunsInEDT
  static void expandPath(final JTree tree, final TreePath path) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        TreePath realPath = path;
        if (path.getPathCount() == 1 && !tree.isRootVisible()) {
          Object root = tree.getModel().getRoot();
          Object target = path.getLastPathComponent();
          if (target != root) {
            realPath = new TreePath(Arrays.array(root, target));
          }
        }
        if (!tree.isExpanded(path)) tree.expandPath(realPath);
      }
    });
  }

  private JTreeExpandPathTask() {}
}