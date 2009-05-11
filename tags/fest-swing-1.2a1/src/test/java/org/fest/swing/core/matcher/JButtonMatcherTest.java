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
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JButtonMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JButtonMatcherTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfMatchingAnyJButton() {
    JButtonMatcher matcher = JButtonMatcher.any();
    assertThat(matcher.matches(button().createNew())).isTrue();
  }

  public void shouldReturnFalseIfComponentIsNotJButton() {
    JButtonMatcher matcher = JButtonMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isFalse();
  }

  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "button";
    JButtonMatcher matcher = JButtonMatcher.withName(name);
    JButton button = button().withName(name).createNew();
    assertThat(matcher.matches(button)).isTrue();
  }

  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    JButtonMatcher matcher = JButtonMatcher.withName("button");
    JButton button = button().withName("label").createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  public void shouldReturnTrueIfNameAndTextAreEqualToExpected() {
    String name = "button";
    String text = "Hello";
    JButtonMatcher matcher = JButtonMatcher.withName(name).andText(text);
    JButton button = button().withName(name).withText(text).createNew();
    assertThat(matcher.matches(button)).isTrue();
  }

  @Test(dataProvider = "notMatchingNameAndText")
  public void shouldReturnFalseIfNameAndTextAreNotEqualToExpected(String name, String text) {
    JButtonMatcher matcher = JButtonMatcher.withName("someName").andText("someText");
    JButton button = button().withName(name).withText(text).createNew();
    assertThat(matcher.matches(button)).isFalse();
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
    JButtonMatcher matcher = JButtonMatcher.withText(text);
    JButton button = button().withText(text).createNew();
    assertThat(matcher.matches(button)).isTrue();
  }

  public void shouldReturnFalseIfTextIsNotEqualToExpected() {
    JButtonMatcher matcher = JButtonMatcher.withText("Hello");
    JButton button = button().withText("Bye").createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfButtonIsShowingAndTextIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JButtonMatcher matcher = JButtonMatcher.withText("Hello").andShowing();
      assertThat(matcher.matches(window.button)).isTrue();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfButtonIsNotShowingAndTextIsEqualToExpected() {
    String text = "Hello";
    JButtonMatcher matcher = JButtonMatcher.withText(text).andShowing();
    JButton button = button().withText(text).createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfButtonIsShowingAndTextIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JButtonMatcher matcher = JButtonMatcher.withText("Bye").andShowing();
      assertThat(matcher.matches(window.button)).isFalse();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfButtonIsNotShowingAndTextIsNotEqualToExpected() {
    JButtonMatcher matcher = JButtonMatcher.withText("Hello").andShowing();
    JButton button = button().withText("Bye").createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  public void shouldImplementToString() {
    JButtonMatcher matcher = JButtonMatcher.withName("button").andText("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.fest.swing.core.matcher.JButtonMatcher[name='button', text='Hello', requireShowing=true]");
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

    final JButton button = new JButton("Hello");

    private MyWindow() {
      super(JButtonMatcherTest.class);
      addComponents(button);
    }
  }
}
