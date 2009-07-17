/*
 * Created on Jul 16, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.swing.TreeNodeFactory.node;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestTree;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Base class for test cases for <code>{@link JTreeDriver}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class JTreeDriverTestCase {

  private Robot robot;
  private JTreeDriver driver;
  private MyWindow window;

  @BeforeClass public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public final void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    driver = new JTreeDriver(robot);
    window = MyWindow.createNew(getClass());
    beforeShowingWindow();
    robot.showWindow(window);
  }

  void beforeShowingWindow() {}

  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  @RunsInEDT
  final void disableTree() {
    disable(tree());
    robot.waitForIdle();
  }

  @RunsInEDT
  final void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  final String textOf(TreePath path) {
    return textOf(path, driver().separator());
  }

  @RunsInEDT
  private static String textOf(final TreePath path, final String separator) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        if (path == null) return null;
        Object[] values = path.getPath();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
          if (i != 0) b.append(separator);
          Object value = values[i];
          if (value instanceof DefaultMutableTreeNode)
            b.append(((DefaultMutableTreeNode)value).getUserObject());
        }
        return b.toString();
      }
    });
  }

  @DataProvider(name = "outOfBoundRowIndices")
  public final Object[][] outOfBoundRowIndices() {
    return new Object[][] {
        { -1 }, { 6 }, { 100 }
    };
  }

  @RunsInEDT
  final DefaultMutableTreeNode firstChildInRoot() {
    return firstChildInRootOf(tree());
  }

  @RunsInEDT
  private static DefaultMutableTreeNode firstChildInRootOf(final JTree tree) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
        return (DefaultMutableTreeNode)root.getChildAt(0);
      }
    });
  }

  @RunsInEDT
  static int childCountOf(final TreeNode node) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return node.getChildCount();
      }
    });
  }

  @RunsInEDT
  static DefaultMutableTreeNode firstChildOf(final TreeNode node) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return (DefaultMutableTreeNode)node.getChildAt(0);
      }
    });
  }

  @RunsInEDT
  static String textOf(final DefaultMutableTreeNode node) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return (String)node.getUserObject();
      }
    });
  }

  @RunsInEDT
  static DefaultMutableTreeNode rootOf(final JTree tree) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return (DefaultMutableTreeNode)tree.getModel().getRoot();
      }
    });
  }

  final Robot robot() { return robot; }
  final JTreeDriver driver() { return driver; }
  final MyWindow window() { return window; }
  final JTree tree() { return window.tree; }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static final Dimension TREE_SIZE = new Dimension(200, 100);

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final TestTree tree = new TestTree(nodes());

    private static TreeModel nodes() {
      MutableTreeNode root =
        node("root",
            node("branch1",
                node("branch1.1",
                    node("branch1.1.1"),
                    node("branch1.1.2")
                ),
                node("branch1.2")
            ),
            node("branch2"),
            node("branch3"),
            node("branch4"),
            node("branch5",
                node("branch5.1")
            )
        );
      return new DefaultTreeModel(root);
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      add(decorate(tree));
    }

    @RunsInCurrentThread
    static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }
  }
}
