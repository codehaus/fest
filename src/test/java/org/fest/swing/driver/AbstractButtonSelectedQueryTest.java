/*
 * Created on Aug 9, 2008
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

import javax.swing.JCheckBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractButtonSelectedQuery;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.data.BooleanProvider;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.test.task.AbstractButtonSetSelectedTask.setSelected;

/**
 * Tests for <code>{@link AbstractButtonSelectedQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class AbstractButtonSelectedQueryTest {

  private Robot robot;
  private MyCheckBox checkBox;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    checkBox = window.checkBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class, groups = { GUI, ACTION })
  public void shouldIndicateIfAbstractButtonIsSelected(final boolean selected) {
    setSelected(checkBox, selected);
    robot.waitForIdle();
    checkBox.startRecording();
    boolean isSelected = AbstractButtonSelectedQuery.isSelected(checkBox);
    assertThat(isSelected).isEqualTo(selected);
    checkBox.requireInvoked("isSelected");
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

    final MyCheckBox checkBox = new MyCheckBox("A Button");

    private MyWindow() {
      super(AbstractButtonSelectedQueryTest.class);
      addComponents(checkBox);
    }
  }

  private static class MyCheckBox extends JCheckBox {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyCheckBox(String text) { super(text); }

    void startRecording() { recording = true; }

    @Override public boolean isSelected() {
      if (recording) methodInvocations.invoked("isSelected");
      return super.isSelected();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
