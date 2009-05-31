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

import javax.swing.JTextField;

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
import static org.fest.swing.test.core.MethodInvocations.Args.args;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTextComponentSetTextTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JTextComponentSetTextTaskTest {

  static final String TEXTBOX_TEXT = "Hello World";

  private Robot robot;
  private MyTextField textField;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    textField = window.textField;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldEnterText() {
    textField.startRecording();
    JTextComponentSetTextTask.setTextIn(textField, TEXTBOX_TEXT);
    robot.waitForIdle();
    assertThat(textOf(textField)).isEqualTo(TEXTBOX_TEXT);
    textField.requireInvoked("setText", args(TEXTBOX_TEXT));
  }
  
  private static String textOf(final MyTextField textField) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return textField.getText();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyTextField textField = new MyTextField();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTextComponentSetTextTaskTest.class);
      add(textField);
    }
  }

  private static class MyTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTextField() {
      super(20);
    }

    @Override public void setText(String text) {
      if (recording) methodInvocations.invoked("setText", args(text));
      super.setText(text);
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
