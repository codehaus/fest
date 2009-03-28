/*
 * Created on Mar 27, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Collections.list;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-102" target="_blank">FEST-102</a>
 *
 * @author Alex Ruiz
 */
@Test public class FEST102_EnteringTextInJComboBoxIgnoresFirstCharacter {

  private Robot robot;

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
  }

  @AfterMethod public void tearDown() {
    if (robot != null) robot.cleanUp();
  }

  public void shouldEnterTextInComboBoxWithIntegerValues() {
    MyWindow window = MyWindow.createNew(new Vector<Integer>(list(1999, 2000, 2001, 2002)));
    robot.showWindow(window);
    JComboBoxFixture comboBox = new JComboBoxFixture(robot, window.comboBox);
    comboBox.enterText("20");
    assertEditorHasValue(comboBox, "20");
  }

  public void shouldEnterTextInComboBoxWithStringValues() {
    MyWindow window = MyWindow.createNew(new Vector<String>(list("1999", "2000", "2001", "2002")));
    robot.showWindow(window);
    JComboBoxFixture comboBox = new JComboBoxFixture(robot, window.comboBox);
    comboBox.enterText("78");
    assertEditorHasValue(comboBox, "78");
  }

  private void assertEditorHasValue(JComboBoxFixture comboBox, String expected) {
    JTextField editor = editorIn(comboBox);
    assertThat(textOf(editor)).isEqualTo(expected);
  }

  private JTextField editorIn(JComboBoxFixture comboBox) {
    return (JTextField)comboBox.component().getEditor().getEditorComponent();
  }

  @RunsInEDT
  static String textOf(final JTextComponent textComponent) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return textComponent.getText();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox;

    @RunsInEDT
    static MyWindow createNew(final Vector<?> comboBoxItems) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(comboBoxItems);
        }
      });
    }

    private MyWindow(Vector<?> comboBoxItems) {
      super(FEST102_EnteringTextInJComboBoxIgnoresFirstCharacter.class);
      comboBox = new JComboBox(comboBoxItems);
      comboBox.setEditable(true);
      comboBox.setSelectedIndex(-1);
      addComponents(comboBox);
    }
  }
}
