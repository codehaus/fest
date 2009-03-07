/*
 * Created on Apr 12, 2008
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

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JLabels.label;
import static org.fest.swing.test.builder.JToolBars.toolBar;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJTreeCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJTreeCellReaderTest {

  private Robot robot;
  private JTree tree;
  private BasicJTreeCellReader reader;
  private DefaultMutableTreeNode root;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    root = window.root;
    tree = window.tree;
    reader = new BasicJTreeCellReader();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = label().withText("First").createNew();
    setCellRendererComponent(tree, label);
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo("First");
  }

  public void shouldReturnTextFromTreeIfRendererIsNotJLabel() {
    setCellRendererComponent(tree, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }

  public void shouldReturnNullIfTextOfModelValueIsDefaultToString() {
    class Person {}
    root = new DefaultMutableTreeNode(new Person());
    setRootInTree(tree, root);
    setCellRendererComponent(tree, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isNull();
  }

  @RunsInEDT
  private static void setRootInTree(final JTree tree, final DefaultMutableTreeNode root) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        ((DefaultTreeModel)tree.getModel()).setRoot(root);
      }
    });
  }

  @RunsInEDT
  private static void setCellRendererComponent(final JTree tree, final Component renderer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setCellRenderer(new CustomCellRenderer(renderer));
      }
    });
  }

  @RunsInEDT
  private static Component unrecognizedRenderer() {
    return toolBar().createNew();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTree tree;
    final DefaultMutableTreeNode root;

    private MyWindow() {
      super(BasicJTreeCellReaderTest.class);
      root = newRoot();
      tree = new JTree(root);
      tree.setPreferredSize(new Dimension(100, 100));
      addComponents(tree);
    }

    private static DefaultMutableTreeNode newRoot() {
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
      rootNode.add(new DefaultMutableTreeNode("Node1"));
      return rootNode;
    }
  }
}
