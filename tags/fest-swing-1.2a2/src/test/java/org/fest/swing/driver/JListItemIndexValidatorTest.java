/*
 * Created on Nov 3, 2008
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

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.testng.annotations.*;

import org.fest.assertions.Assertions;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JListItemIndexValidator}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListItemIndexValidatorTest {

  private Robot robot;
  private JList list;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test(dataProvider = "validIndices", groups = GUI)
  public void shouldPassIfIndexIsValid(int index) {
    JListItemIndexValidator.validateIndex(list, index);
  }
  
  @DataProvider(name = "validIndices") public Object[][] validIndices() {
    return new Object[][] {
      { 0 }, { 1 }, { 2 }
    };
  }
  
  public void shouldThrowErrorIfIndexIsNegative() {
    try {
      JListItemIndexValidator.validateIndex(list, -1);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("Item index (-1) should be between [0] and [2] (inclusive)");
    }
  }

  public void shouldThrowErrorIfIndexIsGreaterThanLastItemIndex() {
    try {
      JListItemIndexValidator.validateIndex(list, 3);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("Item index (3) should be between [0] and [2] (inclusive)");
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array("One", "Two", "Three"));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListItemIndexValidator.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
