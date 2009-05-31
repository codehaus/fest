/*
 * Created on Feb 4, 2009
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
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=293">Bug 293</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug293_ErrorWhenSelectingPathIfJTreeRootIsInvisible {

  private Robot robot;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldSelectPathIfRootIsInvisible() {
    MyWindow window = MyWindow.createNew(false);
    JTreeFixture treeFixture = new JTreeFixture(robot, window.tree);
    robot.showWindow(window);
    treeFixture.selectPath("node1/node11/node111");
    Object selection = treeFixture.component().getSelectionPath().getLastPathComponent();
    assertThat(selection).isSameAs(window.nodeToSelect);
  }

  public void shouldSelectPathIfRootIsVisible() {
    MyWindow window = MyWindow.createNew(true);
    JTreeFixture treeFixture = new JTreeFixture(robot, window.tree);
    robot.showWindow(window);
    treeFixture.selectPath("root/node1/node11/node111");
    Object selection = treeFixture.component().getSelectionPath().getLastPathComponent();
    assertThat(selection).isSameAs(window.nodeToSelect);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew(final boolean treeRootVisible) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(treeRootVisible);
        }
      });
    }

    final JTree tree = new JTree();
    final MutableTreeNode nodeToSelect = node("node111");

    private MyWindow(boolean treeRootVisible) {
      super(Bug293_ErrorWhenSelectingPathIfJTreeRootIsInvisible.class);
      tree.setModel(new DefaultTreeModel(root()));
      tree.setPreferredSize(new Dimension(200, 200));
      tree.setRootVisible(treeRootVisible);
      add(tree);
    }

    private MutableTreeNode root() {
      return node("root",
          node("node1",
              node("node11",
                  nodeToSelect,
                  node("node112")
                  )
              )
          );
    }

    private static DefaultMutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }
  }

}
