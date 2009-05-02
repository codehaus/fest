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
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JFrame;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link FrameMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class FrameMatcherTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfMatchingAnyFrame() {
    FrameMatcher matcher = FrameMatcher.any();
    assertThat(matcher.matches(frame().createNew())).isTrue();
  }

  public void shouldReturnFalseIfComponentIsNotFrame() {
    FrameMatcher matcher = FrameMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isFalse();
  }

  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "frame";
    FrameMatcher matcher = FrameMatcher.withName(name);
    JFrame frame = frame().withName(name).createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withName("frame");
    JFrame frame = frame().withName("label").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  public void shouldReturnTrueIfNameAndTitleAreEqualToExpected() {
    String name = "frame";
    String title = "Hello";
    FrameMatcher matcher = FrameMatcher.withName(name).andTitle(title);
    JFrame frame = frame().withName(name).withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test(dataProvider = "notMatchingNameAndTitle")
  public void shouldReturnFalseIfNameAndTitleAreNotEqualToExpected(String name, String title) {
    FrameMatcher matcher = FrameMatcher.withName("someName").andTitle("someTitle");
    JFrame frame = frame().withName(name).withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @DataProvider(name = "notMatchingNameAndTitle")
  public Object[][] notMatchingNameAndTitle() {
    return new Object[][] {
        { "someName", "title" },
        { "name", "someTitle" },
        { "name", "title" }
    };
  }

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String title = "Hello";
    FrameMatcher matcher = FrameMatcher.withTitle(title);
    JFrame frame = frame().withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withTitle("Hello");
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test(groups = GUI)
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

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String title = "Hello";
    FrameMatcher matcher = FrameMatcher.withTitle(title).andShowing();
    JFrame frame = frame().withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test(groups = GUI)
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

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    FrameMatcher matcher = FrameMatcher.withTitle("Hello").andShowing();
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }
}
