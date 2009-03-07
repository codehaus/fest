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

import javax.swing.JLabel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JLabels.label;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link JLabelMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JLabelMatcherTest {

  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "label";
    JLabelMatcher matcher = JLabelMatcher.withName(name);
    JLabel label = label().withName(name).createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withName("label");
    JLabel label = label().withName("button").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  public void shouldReturnTrueIfNameAndTextAreEqualToExpected() {
    String name = "label";
    String text = "Hello";
    JLabelMatcher matcher = JLabelMatcher.withName(name).andText(text);
    JLabel label = label().withName(name).withText(text).createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test(dataProvider = "notMatchingNameAndText")
  public void shouldReturnFalseIfNameAndTextAreNotEqualToExpected(String name, String text) {
    JLabelMatcher matcher = JLabelMatcher.withName("someName").andText("someText");
    JLabel label = label().withName(name).withText(text).createNew();
    assertThat(matcher.matches(label)).isFalse();
  }
  
  @DataProvider(name = "notMatchingNameAndText")
  public Object[][] notMatchingNameAndText() {
    return new Object[][] {
        { "someName", "text" },
        { "name", "someText" },
        { "name", "text" }
    };
  }

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfTextIsEqualToExpected() {
    String text = "Hello";
    JLabelMatcher matcher = JLabelMatcher.withText(text);
    JLabel label = label().withText(text).createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  public void shouldReturnFalseIfTextIsNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello");
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test(groups = GUI)
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

  public void shouldReturnFalseIfLabelIsNotShowingAndTextIsEqualToExpected() {
    String text = "Hello";
    JLabelMatcher matcher = JLabelMatcher.withText(text).andShowing();
    JLabel label = label().withText(text).createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test(groups = GUI)
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

  public void shouldReturnFalseIfLabelIsNotShowingAndTextIsNotEqualToExpected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello").andShowing();
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
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
