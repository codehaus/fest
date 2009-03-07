/*
 * Created on Aug 11, 2008
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

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTextComponentSelectTextTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTextComponentSelectTextTaskTest {

  static final String TEXTBOX_TEXT = "Hello World";

  private Robot robot;
  private JTextComponent textBox;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    textBox = window.textBox;
    robot.showWindow(window);
  }

  @Test(dataProvider = "selectionIndices", groups = GUI)
  public void shouldSelectText(int start, int end) {
    selectTextInRange(textBox, start, end);
    robot.waitForIdle();
    String selection = selectedTextOf(textBox);
    assertThat(selection).isEqualTo(TEXTBOX_TEXT.substring(start, end));
  }

  @DataProvider(name = "selectionIndices") public Object[][] selectionIndices() {
    return new Object[][] {
        { 0, 5 },
        { 1, 9 },
        { 6, 8 }
    };
  }

  @RunsInEDT
  private static void selectTextInRange(final JTextComponent textBox, final int start, final int end) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        JTextComponentSelectTextTask.selectTextInRange(textBox, start, end);
      }
    });
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textBox = new JTextField(20);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTextComponentSelectTextTaskTest.class);
      setPreferredSize(new Dimension(80, 60));
      add(textBox);
      textBox.setText(TEXTBOX_TEXT);
    }
  }
}
