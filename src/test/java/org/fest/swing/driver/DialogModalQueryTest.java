/*
 * Created on Aug 8, 2008
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

import java.awt.Dimension;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.data.BooleanProvider;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link DialogModalQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class DialogModalQueryTest {

  private Robot robot;
  private MyDialog dialog;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    dialog = MyDialog.createNew();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class, groups = { GUI, ACTION })
  public void shouldIndicateWhetherDialogIsModal(final boolean modal) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        dialog.setModal(modal);
      }
    });
    robot.waitForIdle();
    robot.showWindow(dialog);
    dialog.startRecording();
    assertThat(DialogModalQuery.isModal(dialog)).isEqualTo(modal);
    dialog.requireInvoked("isModal");
  }

  private static class MyDialog extends TestDialog {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyDialog createNew() {
      return execute(new GuiQuery<MyDialog>() {
        protected MyDialog executeInEDT() {
          return new MyDialog();
        }
      });
    }

    private MyDialog() {
      super(TestWindow.createNewWindow(DialogModalQueryTest.class));
      setPreferredSize(new Dimension(200, 100));
    }

    void startRecording() { recording = true; }

    @Override public boolean isModal() {
      if (recording) methodInvocations.invoked("isModal");
      return super.isModal();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
