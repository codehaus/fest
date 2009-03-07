/*
 * Created on Jun 3, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.finder.WindowFinder.findDialog;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests lookup of a modal dialog. This test tries to reproduce the problem reported at
 * <a href="http://groups.google.com/group/easytesting/browse_thread/thread/c42bd103c28d6a1a" target="_blank">this mailing list message</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ModalDialogLookupTest {

  private Robot robot;
  private MyWindow frame;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = MyWindow.createNew();
    robot.showWindow(frame);
  }
  
  public void shouldShowModalDialogAndNotBlock() {
    FrameFixture frameFixture = new FrameFixture(robot, frame);
    frameFixture.button("launch").click();
    DialogFixture dialogFixture = findDialog(TestDialog.class).using(robot);
    assertThat(dialogFixture.target).isSameAs(frame.dialog);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
     
    final JButton button = new JButton("Launch");
    final TestDialog dialog = TestDialog.createNewDialog(this);
    
    private MyWindow() {
      super(ModalDialogLookupTest.class);
      button.setName("launch");
      add(button);
      dialog.setModal(true);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          dialog.setVisible(true);
        }
      });
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
