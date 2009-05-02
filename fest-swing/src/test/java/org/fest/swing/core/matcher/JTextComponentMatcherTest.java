/*
 * Created on Jul 16, 2008
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
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JTextComponentMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JTextComponentMatcherTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfMatchingAnyJTextComponent() {
    JTextComponentMatcher matcher = JTextComponentMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isTrue();
  }

  public void shouldReturnFalseIfComponentIsNotJTextComponent() {
    JTextComponentMatcher matcher = JTextComponentMatcher.any();
    assertThat(matcher.matches(dialog().createNew())).isFalse();
  }

  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "textField";
    JTextComponentMatcher matcher = JTextComponentMatcher.withName(name);
    JTextField textField = textField().withName(name).createNew();
    assertThat(matcher.matches(textField)).isTrue();
  }

  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName("textField");
    JTextField textField = textField().withName("label").createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  public void shouldReturnTrueIfNameAndTextAreEqualToExpected() {
    String name = "textField";
    String text = "Hello";
    JTextComponentMatcher matcher = JTextComponentMatcher.withName(name).andText(text);
    JTextField textField = textField().withName(name).withText(text).createNew();
    assertThat(matcher.matches(textField)).isTrue();
  }

  @Test(dataProvider = "notMatchingNameAndText")
  public void shouldReturnFalseIfNameAndTextAreNotEqualToExpected(String name, String text) {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName("someName").andText("someText");
    JTextField textField = textField().withName(name).withText(text).createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  @DataProvider(name = "notMatchingNameAndText")
  public Object[][] notMatchingNameAndText() {
    return new Object[][] {
        { "someName", "text" },
        { "name", "someText" },
        { "name", "text" }
    };
  }

  public void shouldReturnTrueIfTextIsEqualToExpected() {
    String text = "Hello";
    JTextComponentMatcher matcher = JTextComponentMatcher.withText(text);
    JTextField textField = textField().withText(text).createNew();
    assertThat(matcher.matches(textField)).isTrue();
  }

  public void shouldReturnFalseIfTextIsNotEqualToExpected() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withText("Hello");
    JTextField textField = textField().withText("Bye").createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfTextComponentIsShowingAndTextIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JTextComponentMatcher matcher = JTextComponentMatcher.withText("Hello").andShowing();
      assertThat(matcher.matches(window.textField)).isTrue();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfTextComponentIsNotShowingAndTextIsEqualToExpected() {
    String text = "Hello";
    JTextComponentMatcher matcher = JTextComponentMatcher.withText(text).andShowing();
    JTextField textField = textField().withText(text).createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfTextComponentIsShowingAndTextIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JTextComponentMatcher matcher = JTextComponentMatcher.withText("Bye").andShowing();
      assertThat(matcher.matches(window.textField)).isFalse();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfTextComponentIsNotShowingAndTextIsNotEqualToExpected() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withText("Hello").andShowing();
    JTextField textField = textField().withText("Bye").createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  public void shouldImplementToString() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName("textField").andText("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.fest.swing.core.matcher.JTextComponentMatcher[name='textField', text='Hello', requireShowing=true]");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return display(new MyWindow());
        }
      });
    }

    final JTextField textField = new JTextField("Hello");

    private MyWindow() {
      super(JLabelMatcherTest.class);
      addComponents(textField);
    }
  }
}
