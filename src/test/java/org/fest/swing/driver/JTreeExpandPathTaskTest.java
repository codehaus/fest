/*
 * Created on Aug 19, 2008
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
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTreeExpandedPathQuery.isExpanded;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.ACTION;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JTreeExpandPathTask}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JTreeExpandPathTaskTest {

  private Robot robot;
  private MyWindow window;
  private JTree tree;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    tree = window.tree;
    robot.showWindow(window);
  }

  @AfterMethod public void destroy() {
    robot.cleanUp();
  }

  public void shouldExpandPath() {
    TreePath rootPath = new TreePath(window.root);
    assertThat(isExpanded(tree, rootPath)).isFalse();
    JTreeExpandPathTask.expandPath(tree, rootPath);
    robot.waitForIdle();
    assertThat(isExpanded(tree, rootPath)).isTrue();
  }

  public void shouldExpandPathIfRootIsInvisible() {
    hideRoot();
    TreePath nodePath = new TreePath(array(window.root, window.node));
    assertThat(isExpanded(tree, nodePath)).isFalse();
    JTreeExpandPathTask.expandPath(tree, new TreePath(window.node));
    robot.waitForIdle();
    assertThat(isExpanded(tree, nodePath)).isTrue();
  }

  @RunsInEDT
  private void hideRoot() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setRootVisible(false);
      }
    });
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTree tree;
    final DefaultMutableTreeNode root;
    final DefaultMutableTreeNode node;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTreeExpandPathTaskTest.class);
      root = new DefaultMutableTreeNode("root");
      node = new DefaultMutableTreeNode("node");
      node.add(new DefaultMutableTreeNode("node1"));
      tree = new JTree(root);
      root.add(node);
      tree.setPreferredSize(new Dimension(300, 200));
      addComponents(tree);
      tree.collapsePath(new TreePath(root));
    }
  }
}
