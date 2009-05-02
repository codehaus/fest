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
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JDialog;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link DialogMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class DialogMatcherTest {

  @BeforeMethod public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfMatchingAnyDialog() {
    DialogMatcher matcher = DialogMatcher.any();
    assertThat(matcher.matches(dialog().createNew())).isTrue();
  }

  public void shouldReturnFalseIfComponentIsNotDialog() {
    DialogMatcher matcher = DialogMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isFalse();
  }

  public void shouldReturnTrueIfNameIsEqualToExpected() {
    String name = "dialog";
    DialogMatcher matcher = DialogMatcher.withName(name);
    JDialog dialog = dialog().withName(name).createNew();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  public void shouldReturnFalseIfNameIsNotEqualToExpected() {
    DialogMatcher matcher = DialogMatcher.withName("dialog");
    JDialog dialog = dialog().withName("label").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  public void shouldReturnTrueIfNameAndTitleAreEqualToExpected() {
    String name = "dialog";
    String title = "Hello";
    DialogMatcher matcher = DialogMatcher.withName(name).andTitle(title);
    JDialog dialog = dialog().withName(name).withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  @Test(dataProvider = "notMatchingNameAndTitle")
  public void shouldReturnFalseIfNameAndTitleAreNotEqualToExpected(String name, String title) {
    DialogMatcher matcher = DialogMatcher.withName("someName").andTitle("someTitle");
    JDialog dialog = dialog().withName(name).withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isFalse();
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
    DialogMatcher matcher = DialogMatcher.withTitle(title);
    JDialog dialog = dialog().withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    DialogMatcher matcher = DialogMatcher.withTitle("Hello");
    JDialog dialog = dialog().withTitle("Bye").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfDialogIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    String title = "Hello";
    JDialog dialog = dialog().withTitle(title).createAndShow();
    try {
      DialogMatcher matcher = DialogMatcher.withTitle(title).andShowing();
      assertThat(matcher.matches(dialog)).isTrue();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfDialogIsNotShowingAndTitleIsEqualToExpected() {
    String title = "Hello";
    DialogMatcher matcher = DialogMatcher.withTitle(title).andShowing();
    JDialog dialog = dialog().withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfDialogIsShowingAndTitleIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    TestWindow window = TestWindow.createAndShowNewWindow(DialogMatcher.class);
    TestDialog dialog = TestDialog.createAndShowNewDialog(window);
    try {
      DialogMatcher matcher = DialogMatcher.withTitle("Hello").andShowing();
      assertThat(matcher.matches(dialog)).isFalse();
    } finally {
      dialog.destroy();
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfDialogIsNotShowingAndTitleIsNotEqualToExpected() {
    DialogMatcher matcher = DialogMatcher.withTitle("Hello").andShowing();
    JDialog dialog = dialog().withTitle("Bye").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  public void shouldImplementToString() {
    DialogMatcher matcher = DialogMatcher.withName("dialog").andTitle("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.fest.swing.core.matcher.DialogMatcher[name='dialog', title='Hello', requireShowing=true]");
  }
}
