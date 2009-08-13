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
import static org.fest.swing.test.builder.JLabels.label;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.Regex.regex;

import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link JLabelMatcher}</code>.
 *
 * @author Alex Ruiz
 */
public class JLabelMatcherTest extends EDTSafeTestCase {

  @Test
  public void shouldReturnTrueIfMatchingAnyJLabel() {
    JLabelMatcher matcher = JLabelMatcher.any();
    assertThat(matcher.matches(label().createNew())).isTrue();
  }

  @Test
  public void shouldReturnFalseIfComponentIsNotJLabel() {
    JLabelMatcher matcher = JLabelMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isFalse();
  }

  @Test
  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "label";
    JLabelMatcher matcher = JLabelMatcher.withName(name);
    JLabel label = label().withName(name).createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withName("label");
    JLabel label = label().withName("button").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfNameAndTextAreEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withName("label").andText("Hello");
    JLabel label = label().withName("label").withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfNameMatchesAndTextMatchesPatternAsString() {
    JLabelMatcher matcher = JLabelMatcher.withName("label").andText("Hel.*");
    JLabel label = label().withName("label").withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfNameMatchesAndTextMatchesPattern() {
    JLabelMatcher matcher = JLabelMatcher.withName("label").andText(regex("Hel.*"));
    JLabel label = label().withName("label").withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfNameAndTextAreNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withName("someName").andText("someText");
    JLabel label = label().withName("name").withText("text").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  // TODO parameterize
  public Object[][] notMatchingNameAndText() {
    return new Object[][] {
        { "someName", "text" },
        { "name", "someText" },
        { "name", "text" }
    };
  }

  @Test
  public void shouldReturnTrueIfTextIsEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello");
    JLabel label = label().withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfTextMatchesPatternAsString() {
    JLabelMatcher matcher = JLabelMatcher.withText("He.*");
    JLabel label = label().withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfTextMatchesPattern() {
    JLabelMatcher matcher = JLabelMatcher.withText(regex("He.*"));
    JLabel label = label().withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfTextIsNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello");
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfLabelIsShowingAndTextIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JLabelMatcher matcher = JLabelMatcher.withText("Hello").andShowing();
      assertThat(matcher.matches(window.label)).isTrue();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void shouldReturnFalseIfLabelIsNotShowingAndTextIsEqualToExpected() {
    String text = "Hello";
    JLabelMatcher matcher = JLabelMatcher.withText(text).andShowing();
    JLabel label = label().withText(text).createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test
  public void shouldReturnFalseIfLabelIsShowingAndTextIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JLabelMatcher matcher = JLabelMatcher.withText("Bye").andShowing();
      assertThat(matcher.matches(window.label)).isFalse();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void shouldReturnFalseIfLabelIsNotShowingAndTextIsNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello").andShowing();
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test
  public void shouldImplementToString() {
    JLabelMatcher matcher = JLabelMatcher.withName("label").andText("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.fest.swing.core.matcher.JLabelMatcher[name='label', text='Hello', requireShowing=true]");
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

    final JLabel label = new JLabel("Hello");

    private MyWindow() {
      super(JLabelMatcherTest.class);
      addComponents(label);
    }
  }
}
