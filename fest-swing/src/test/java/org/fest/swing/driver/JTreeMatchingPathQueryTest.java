/*
 * Created on Jun 6, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.swing.TreeNodeFactory.node;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.core.TestGroups;
import org.fest.swing.test.swing.TestTree;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JTreeMatchingPathQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = TestGroups.GUI)
public class JTreeMatchingPathQueryTest {

  private Robot robot;
  private JTree tree;
  private JTreePathFinder pathFinder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    pathFinder = new JTreePathFinder();
    robot = BasicRobot.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindMatchingPathWhenRootIsInvisible() {
    TreePath treePath = JTreeMatchingPathQuery.matchingPathFor(tree, "branch1/branch1.1", pathFinder);
    Object[] path = treePath.getPath();
    assertThat(path).hasSize(3);
    assertThatIsTreeNodeWithGivenText(path[0], "root");
    assertThatIsTreeNodeWithGivenText(path[1], "branch1");
    assertThatIsTreeNodeWithGivenText(path[2], "branch1.1");
  }

  public void shouldFindMatchingPathWhenRootIsVisible() {
    makeTreeRootVisible();
    TreePath treePath = JTreeMatchingPathQuery.matchingPathFor(tree, "root/branch1/branch1.1", pathFinder);
    Object[] path = treePath.getPath();
    assertThat(path).hasSize(3);
    assertThatIsTreeNodeWithGivenText(path[0], "root");
    assertThatIsTreeNodeWithGivenText(path[1], "branch1");
    assertThatIsTreeNodeWithGivenText(path[2], "branch1.1");
  }

  private void makeTreeRootVisible() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setRootVisible(true);
      }
    });
    robot.waitForIdle();
  }

  private static void assertThatIsTreeNodeWithGivenText(Object o, String text) {
    assertThat(o).isInstanceOf(DefaultMutableTreeNode.class);
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)o;
    assertThat(node.getUserObject()).isEqualTo(text);
  }

  public void shouldThrowErrorIfPathNotFound() {
    try {
      JTreeMatchingPathQuery.matchingPathFor(tree, "hello", pathFinder);
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'hello'");
    }
  }

  public void shouldReturnNullIfPathToAddRootToIsNull() {
    TreePath treePath = JTreeMatchingPathQuery.addRootIfInvisible(tree, null);
    assertThat(treePath).isNull();
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
            node("branch2")
        );
      return new DefaultTreeModel(root);
    }

    private MyWindow() {
      super(JTreeMatchingPathQueryTest.class);
      tree.setRootVisible(false);
      add(decorate(tree));
      setPreferredSize(new Dimension(600, 400));
    }

    private static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }
  }
}
