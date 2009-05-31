/*
 * Created on Jan 18, 2008
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
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.swing.TestTree;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTreeSetEditableTask.setEditable;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.test.task.JTreeSelectRowTask.selectRow;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeDriver}</code>.
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTreeDriverTest {

  private Robot robot;
  private MyWindow window;
  private JTree dragTree;
  private JTree dropTree;

  private JTreeDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTreeDriver(robot);
    window = MyWindow.createNew();
    dragTree = window.dragTree;
    dropTree = window.dropTree;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSelectNodeByRow() {
    clearSelectionOf(dragTree);
    robot.waitForIdle();
    assertThat(selectionRowsOf(dragTree)).isNull();
    driver.selectRow(dragTree, 0);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(new int[] { 0 });
    driver.selectRow(dragTree, 1);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(new int[] { 1 });
    driver.selectRow(dragTree, 0);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(new int[] { 0 });
  }

  public void shouldThrowErrorWhenSelectingNodeByRowInDisabledJTree() {
    disableDragTree();
    try {
      driver.selectRow(dragTree, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }    
  }

  public void shouldThrowErrorWhenSelectingNodeByRowInNotShowingJTree() {
    hideWindow();
    try {
      driver.selectRow(dragTree, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }    
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRowArrayIsNull() {
    int[] rows = null;
    driver.selectRows(dragTree, rows);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfRowArrayIsEmpty() {
    int[] rows = new int[0];
    driver.selectRows(dragTree, rows);
  }

  public void shouldSelectNodesByRow() {
    clearSelectionOf(dragTree);
    setDefaultSelectionModelTo(dragTree);
    robot.waitForIdle();
    assertThat(selectionRowsOf(dragTree)).isNull();
    int[] rows = { 0, 1, 2 };
    driver.selectRows(dragTree, rows);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(rows);
  }

  @RunsInEDT
  private static int[] selectionRowsOf(final JTree tree) {
    return execute(new GuiQuery<int[]>() {
      protected int[] executeInEDT() {
        return tree.getSelectionRows();
      }
    });
  }

  public void shouldThrowErrorWhenSelectingNodesByRowInDisabledJTree() {
    disableDragTree();
    int[] rows = { 0, 1, 2 };
    try {
      driver.selectRows(dragTree, rows);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNodesByRowInNotShowingJTree() {
    hideWindow();
    int[] rows = { 0, 1, 2 };
    try {
      driver.selectRows(dragTree, rows);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldToggleNodeByRow() {
    assertThat(isRowExpanded(dragTree, 1)).isFalse();
    driver.toggleRow(dragTree, 1);
    assertThat(isRowExpanded(dragTree, 1)).isTrue();
    driver.toggleRow(dragTree, 1);
    assertThat(isRowExpanded(dragTree, 1)).isFalse();
  }

  @RunsInEDT
  private static boolean isRowExpanded(final JTree tree, final int row) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return tree.isExpanded(row);
      }
    });
  }

  public void shouldThrowErrorIfPathNotFound() {
    try {
      driver.selectPath(dragTree, "another");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test(groups = GUI, dataProvider = "selectionPath")
  public void shouldSelectNodeByPath(String treePath) {
    clearSelectionOf(dragTree);
    robot.waitForIdle();
    driver.selectPath(dragTree, treePath);
    assertThat(textOf(selectionPathOf(dragTree))).isEqualTo(treePath);
  }

  private static TreePath selectionPathOf(final JTree tree) {
    return execute(new GuiQuery<TreePath>() {
      protected TreePath executeInEDT() {
        return tree.getSelectionPath();
      }
    });
  }

  @DataProvider(name = "selectionPath")
  public Object[][] selectionPath() {
    return new Object[][] {
        { "root/branch1" },
        { "root/branch1/branch1.2" },
        { "root" }
    };
  }

  public void shouldThrowErrorWhenSelectingNodeByPathInDisabledJTree() {
    disableDragTree();
    try {
      driver.selectPath(dragTree, "root/branch1");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }    
  }

  public void shouldThrowErrorWhenSelectingNodeByPathInNotShowingJTree() {
    hideWindow();
    try {
      driver.selectPath(dragTree, "root/branch1");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }    
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPathArrayIsNull() {
    String[] paths = null;
    driver.selectPaths(dragTree, paths);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfPathArrayIsEmpty() {
    String[] paths = new String[0];
    driver.selectPaths(dragTree, paths);
  }

  public void shouldSelectNodesByPaths() {
    clearSelectionOf(dragTree);
    setDefaultSelectionModelTo(dragTree);
    robot.waitForIdle();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    driver.selectPaths(dragTree, paths);
    TreePath[] selectionPaths = selectionPathsOf(dragTree);
    assertThat(selectionPaths).hasSize(2);
    assertThat(textOf(selectionPaths[0])).isEqualTo(paths[0]);
    assertThat(textOf(selectionPaths[1])).isEqualTo(paths[1]);
  }

  @RunsInEDT
  private static void setDefaultSelectionModelTo(final JTree tree) {
    final DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionModel(selectionModel);
      }
    });
  }

  @RunsInEDT
  private static TreePath[] selectionPathsOf(final JTree tree) {
    return execute(new GuiQuery<TreePath[]>() {
      protected TreePath[] executeInEDT()  {
        return tree.getSelectionPaths();
      }
    });
  }

  public void shouldThrowErrorWhenSelectingNodesByPathsInDisabledJTree() {
    disableDragTree();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    try {
      driver.selectPaths(dragTree, paths);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNodesByPathsInNotShowingJTree() {
    hideWindow();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    try {
      driver.selectPaths(dragTree, paths);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @RunsInEDT
  private void disableDragTree() {
    disable(dragTree);
    robot.waitForIdle();
  }
  
  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  public void shouldDragAndDropUsingGivenTreePaths() {
    driver.drag(dragTree, "root/branch1/branch1.1");
    driver.drop(dropTree, "root");
    DefaultMutableTreeNode branch1 = firstChildOfRootOf(dragTree);
    assertThat(childCountOf(branch1)).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    DefaultMutableTreeNode root = rootOf(dropTree);
    assertThat(childCountOf(root)).isEqualTo(1);
    assertThat(textOf(firstChildOf(root))).isEqualTo("branch1.1");
  }

  public void shouldDragAndDropUsingGivenRows() {
    driver.drag(dragTree, 2);
    driver.drop(dropTree, 0);
    DefaultMutableTreeNode sourceRoot = rootOf(dragTree);
    assertThat(childCountOf(sourceRoot)).isEqualTo(1);
    DefaultMutableTreeNode destinationRoot = rootOf(dropTree);
    assertThat(childCountOf(destinationRoot)).isEqualTo(1);
    assertThat(textOf(firstChildOf(destinationRoot))).isEqualTo("branch2");
  }

  @RunsInEDT
  private String textOf(TreePath path) {
    return textOf(path, driver.separator());
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

  @RunsInEDT
  private static String textOf(final DefaultMutableTreeNode node) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return (String)node.getUserObject();
      }
    });
  }


  @RunsInEDT
  private static DefaultMutableTreeNode firstChildOfRootOf(final JTree tree) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
        return (DefaultMutableTreeNode)root.getChildAt(0);
      }
    });
  }

  @RunsInEDT
  private static int childCountOf(final TreeNode node) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return node.getChildCount();
      }
    });
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRowIndexArrayIsNull() {
    driver.requireSelection(dragTree, (int[])null);
  }

  public void shouldPassIfRowIsSelected() {
    DefaultMutableTreeNode root = rootOf(dragTree);
    TreePath path = new TreePath(array(root, firstChildOf(root)));
    setSelectionPath(dragTree, path);
    robot.waitForIdle();
    driver.requireSelection(dragTree, intArray(1));
  }

  public void shouldPassIfPathIsSelected() {
    DefaultMutableTreeNode root = rootOf(dragTree);
    TreePath path = new TreePath(array(root, firstChildOf(root)));
    setSelectionPath(dragTree, path);
    robot.waitForIdle();
    driver.requireSelection(dragTree, array("root/branch1"));
  }

  public void shouldPassIfPathsAreSelected() {
    DefaultMutableTreeNode root = rootOf(dragTree);
    TreeNode branch1 = firstChildOf(root);
    TreePath rootBranch1 = new TreePath(array(root, branch1));
    TreePath rootBranch1Branch11 = new TreePath(array(root, branch1, firstChildOf(branch1)));
    setSelectionPaths(dragTree, array(rootBranch1, rootBranch1Branch11));
    robot.waitForIdle();
    driver.requireSelection(dragTree, array("root/branch1", "root/branch1/branch1.1"));
  }

  private static DefaultMutableTreeNode firstChildOf(final TreeNode node) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return (DefaultMutableTreeNode)node.getChildAt(0);
      }
    });
  }


  private static void setSelectionPaths(final JTree tree, final TreePath[] paths) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionPaths(paths);
      }
    });
  }

  private static DefaultMutableTreeNode rootOf(final JTree tree) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return (DefaultMutableTreeNode)tree.getModel().getRoot();
      }
    });
  }

  public void shouldFailIfExpectingSelectedRowAndTreeHasNoSelection() {
    setSelectionPath(dragTree, null);
    robot.waitForIdle();
    try {
      driver.requireSelection(dragTree, intArray(1));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("No selection");
    }
  }

  public void shouldFailIfSelectedRowIsNotEqualToExpectedSelection() {
    DefaultMutableTreeNode root = rootOf(dragTree);
    setSelectionPath(dragTree, new TreePath(array(root)));
    robot.waitForIdle();
    try {
      driver.requireSelection(dragTree, intArray(1));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("array:<[0]> does not contain element(s):<[1]>");
    }
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedPathArrayIsNull() {
    driver.requireSelection(dragTree, (String[])null);
  }

  public void shouldFailIfExpectingSelectedPathAndTreeHasNoSelection() {
    setSelectionPath(dragTree, null);
    robot.waitForIdle();
    try {
      driver.requireSelection(dragTree, array("root/branch1"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("No selection");
    }
  }

  public void shouldFailIfSelectedPathIsNotEqualToExpectedSelection() {
    DefaultMutableTreeNode root = rootOf(dragTree);
    setSelectionPath(dragTree, new TreePath(array(root)));
    robot.waitForIdle();
    try {
      driver.requireSelection(dragTree, array("root/branch1"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("array:<[[root]]> does not contain element(s):<[[root, branch1]]>");
    }
  }

  private static void setSelectionPath(final JTree tree, final TreePath path) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionPath(path);
      }
    });
  }

  private int[] intArray(int...values) {
    return values;
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    clearSelectionOf(dragTree);
    robot.waitForIdle();
    driver.requireNoSelection(dragTree);
  }

  private static void clearSelectionOf(final JTree tree) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.clearSelection();
      }
    });
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstRowOf(dragTree);
    robot.waitForIdle();
    try {
      driver.requireNoSelection(dragTree);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'")
                                .contains("expected no selection but was:<[[root]]>");
    }
  }

  private static void selectFirstRowOf(final JTree tree) {
    selectRow(tree, 0);
  }

  public void shouldPassIfTreeIsEditable() {
    setEditable(dragTree, true);
    robot.waitForIdle();
    driver.requireEditable(dragTree);
  }

  public void shouldFailIfTreeIsNotEditableAndExpectingEditable() {
    setEditable(dragTree, false);
    robot.waitForIdle();
    try {
      driver.requireEditable(dragTree);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfTreeIsNotEditable() {
    setEditable(dragTree, false);
    robot.waitForIdle();
    driver.requireNotEditable(dragTree);
  }

  public void shouldFailIfTreeIsEditableAndExpectingNotEditable() {
    setEditable(dragTree, true);
    robot.waitForIdle();
    try {
      driver.requireNotEditable(dragTree);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  public void shouldShowPopupMenuAtRow() {
    JPopupMenu popupMenu = driver.showPopupMenu(dragTree, 0);
    assertThat(popupMenu).isSameAs(window.popupMenu);
  }

  public void shouldShowPopupMenuAtPath() {
    JPopupMenu popupMenu = driver.showPopupMenu(dragTree, "root");
    assertThat(popupMenu).isSameAs(window.popupMenu);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfSeparatorIsNull() {
    driver.separator(null);
  }

  public void shouldSetPathSeparator() {
    driver.separator("|");
    assertThat(driver.separator()).isEqualTo("|");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final Dimension TREE_SIZE = new Dimension(200, 100);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final TestTree dragTree = new TestTree(nodes());
    final TestTree dropTree = new TestTree(rootOnly());
    final JPopupMenu popupMenu = new JPopupMenu();

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
            node("branch2")
        );
      return new DefaultTreeModel(root);
    }

    private static TreeModel rootOnly() {
      return new DefaultTreeModel(node("root"));
    }

    private static MutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }

    private MyWindow() {
      super(JTreeDriverTest.class);
      add(decorate(dragTree));
      dragTree.addMouseListener(new Listener(popupMenu));
      add(decorate(dropTree));
      popupMenu.add(new JMenuItem("Hello"));
      setPreferredSize(new Dimension(600, 400));
    }

    private Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }

    private static class Listener extends MouseAdapter {
      private final JPopupMenu popupMenu;

      Listener(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
      }

      @Override public void mouseReleased(MouseEvent e) {
        if (!e.isPopupTrigger()) return;
        Component c = e.getComponent();
        if (!(c instanceof JTree)) return;
        JTree tree = (JTree)c;
        int x = e.getX();
        int y = e.getY();
        int row = tree.getRowForLocation(x, y);
        if (row == 0) popupMenu.show(tree, x, y);
      }
    }

  }
}
