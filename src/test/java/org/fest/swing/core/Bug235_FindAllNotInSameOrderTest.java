/*
 * Created on Nov 30, 2008
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

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=235">Bug 235</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { BUG, GUI })
public class Bug235_FindAllNotInSameOrderTest {

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
  
  public void shouldAlwaysReturnAllFoundComponentsInSameOrder() {
    MyWindow window = MyWindow.createNew();
    TypeMatcher matcher = new TypeMatcher(JTextField.class);
    List<Component> firstFound = findAll(window, matcher);
    assertThat(firstFound).hasSize(3);
    window.destroy();
    for (int i = 0; i < 20; i++) {
      window = MyWindow.createNew();
      List<Component> found = findAll(window, matcher);
      assertThat(found.get(0).getName()).isSameAs(firstFound.get(0).getName());
      assertThat(found.get(1).getName()).isSameAs(firstFound.get(1).getName());
      assertThat(found.get(2).getName()).isSameAs(firstFound.get(2).getName());
      window.destroy();
    }
  }

  private List<Component> findAll(MyWindow window, TypeMatcher matcher) {
    return new ArrayList<Component>(robot.finder().findAll(window, matcher));
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
    
    private MyWindow() {
      super(Bug235_FindAllNotInSameOrderTest.class);
      addComponents(textFieldWithName("one"), textFieldWithName("two"), textFieldWithName("three"));
    }
    
    @RunsInCurrentThread
    private static JTextField textFieldWithName(String name) {
      JTextField textField = new JTextField(20);
      textField.setName(name);
      return textField;
    }
  }
}
