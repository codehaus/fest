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
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.Regex.regex;

import javax.swing.JFrame;

import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link FrameMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameMatcherTest extends EDTSafeTestCase {

  @Test
  public void shouldReturnTrueIfMatchingAnyFrame() {
    FrameMatcher matcher = FrameMatcher.any();
    assertThat(matcher.matches(frame().createNew())).isTrue();
  }

  @Test
  public void shouldReturnFalseIfComponentIsNotFrame() {
    FrameMatcher matcher = FrameMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isFalse();
  }

  @Test
  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "frame";
    FrameMatcher matcher = FrameMatcher.withName(name);
    JFrame frame = frame().withName(name).createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withName("frame");
    JFrame frame = frame().withName("label").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfNameAndTitleAreEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withName("frame").andTitle("Hello");
    JFrame frame = frame().withName("frame").withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfNameMatchesAndTitleMatchesPatternAsString() {
    FrameMatcher matcher = FrameMatcher.withName("frame").andTitle("Hel.*");
    JFrame frame = frame().withName("frame").withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfNameMatchesAndTitleMatchesPattern() {
    FrameMatcher matcher = FrameMatcher.withName("frame").andTitle(regex("Hel.*"));
    JFrame frame = frame().withName("frame").withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfNameAndTitleAreNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withName("someName").andTitle("someTitle");
    JFrame frame = frame().withName("name").withTitle("title").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  // TODO parameterize
  public Object[][] notMatchingNameAndTitle() {
    return new Object[][] {
        { "someName", "title" },
        { "name", "someTitle" },
        { "name", "title" }
    };
  }

  @Test
  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withTitle("Hello");
    JFrame frame = frame().withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfTitleMatchesPatternAsString() {
    FrameMatcher matcher = FrameMatcher.withTitle("He.*");
    JFrame frame = frame().withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnTrueIfTitleMatchesPattern() {
    FrameMatcher matcher = FrameMatcher.withTitle(regex("He.*"));
    JFrame frame = frame().withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withTitle("Hello");
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    Class<FrameMatcher> testType = FrameMatcher.class;
    TestWindow frame = TestWindow.createAndShowNewWindow(testType);
    try {
      FrameMatcher matcher = FrameMatcher.withTitle(testType.getSimpleName()).andShowing();
      assertThat(matcher.matches(frame)).isTrue();
    } finally {
      try {
        frame.destroy();
      } catch (RuntimeException e) {}
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String title = "Hello";
    FrameMatcher matcher = FrameMatcher.withTitle(title).andShowing();
    JFrame frame = frame().withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    TestWindow frame = TestWindow.createAndShowNewWindow(FrameMatcher.class);
    try {
      FrameMatcher matcher = FrameMatcher.withTitle("Hello").andShowing();
      assertThat(matcher.matches(frame)).isFalse();
    } finally {
      try {
        frame.destroy();
      } catch (RuntimeException e) {}
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withTitle("Hello").andShowing();
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test
  public void shouldImplementToString() {
    FrameMatcher matcher = FrameMatcher.withName("frame").andTitle("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.fest.swing.core.matcher.FrameMatcher[name='frame', title='Hello', requireShowing=true]");
  }
}
