/*
 * Created on Oct 11, 2008
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

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.MethodInvocations.Args;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.MethodInvocations.Args.args;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;

/**
 * Tests for <code>{@link JSpinnerSetValueTask}</code>
 *
 * @author Alex Ruiz 
 */
@Test(groups = { GUI, ACTION })
public class JSpinnerSetValueTaskTest {

  private Robot robot;
  private MyWindow window;
  private MySpinner spinner;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetValue() {
    String value = "Three";
    spinner.startRecording();
    JSpinnerSetValueTask.setValue(spinner, value);
    robot.waitForIdle();
    assertThat(JSpinnerValueQuery.valueOf(spinner)).isEqualTo(value);
    spinner.requireInvoked("setValue", args(value));
  }

  public void shouldThrowErrorIfJSpinnerIsDisabled() {
    disable(spinner);
    robot.waitForIdle();
    try {
      JSpinnerSetValueTask.setValue(spinner, "Two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }
  
  public void shouldThrowErrorIfJSpinnerIsNotShowing() {
    hide(window);
    robot.waitForIdle();
    try {
      JSpinnerSetValueTask.setValue(spinner, "Two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
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

    final MySpinner spinner = new MySpinner("One", "Two", "Three");

    private MyWindow() {
      super(JSpinnerSetValueTask.class);
      spinner.setValue("Two");
      addComponents(spinner);
    }
  }
  
  private static class MySpinner extends JSpinner {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();
    
    MySpinner(Object...values) {
      super(new SpinnerListModel(values));
    }

    @Override public void setValue(Object value) {
      if (recording) methodInvocations.invoked("setValue", args(value));
      super.setValue(value);
    }
    
    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
