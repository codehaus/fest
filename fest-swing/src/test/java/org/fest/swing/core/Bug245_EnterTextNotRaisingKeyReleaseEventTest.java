/*
 * Created on Dec 4, 2008
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
package org.fest.swing.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=245">Bug 245</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug245_EnterTextNotRaisingKeyReleaseEventTest {

  private Robot robot;
  private JTextField textField;

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

  public void shouldRaiseKeyRelease() {
    KeyReleaseListener keyReleaseListener = new KeyReleaseListener();
    textField.addKeyListener(keyReleaseListener);
    robot.focusAndWaitForFocusGain(textField);
    robot.enterText("Hello");
    assertThat(textField.getText()).isEqualTo("Hello");
    assertThat(keyReleaseListener.keyCodes).containsOnly(VK_H, VK_SHIFT, VK_E, VK_L, VK_L, VK_O);
 }
  
  private static class KeyReleaseListener extends KeyAdapter {
    private final List<Integer> keyCodes = new ArrayList<Integer>();
    
    @Override public void keyReleased(KeyEvent e) {
      keyCodes.add(e.getKeyCode());
    }
    
    Integer[] released() {
      return keyCodes.toArray(new Integer[keyCodes.size()]);
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
    
    final JTextField textField = new JTextField(20);

    private MyWindow() {
      super(Bug245_EnterTextNotRaisingKeyReleaseEventTest.class);
      addComponents(textField);
    }
  }
  
}
