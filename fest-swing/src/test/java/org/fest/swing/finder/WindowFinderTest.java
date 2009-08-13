/*
 * Created on Jul 30, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.finder;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.WindowLauncher;
import org.fest.swing.test.swing.WindowLauncher.DialogToLaunch;
import org.fest.swing.test.swing.WindowLauncher.WindowToLaunch;
import org.junit.Test;

/**
 * Tests for <code>{@link WindowFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WindowFinderTest extends RobotBasedTestCase {

  private FrameFixture launcher;
  private WindowLauncher launcherWindow;

  @Override protected void onSetUp() {
    launcherWindow = WindowLauncher.createNew(WindowFinderTest.class);
    launcher = new FrameFixture(robot, launcherWindow);
    launcher.show();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfFrameNameIsNull() {
    WindowFinder.findFrame((String)null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameNameIsEmpty() {
    WindowFinder.findFrame("");
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfFrameTypeIsNull() {
    WindowFinder.findFrame((Class<Frame>)null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfFrameMatcherIsNull() {
    GenericTypeMatcher<JFrame> matcher = null;
    WindowFinder.findFrame(matcher);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameSearchTimeoutIsNegative() {
    WindowFinder.findFrame("frame").withTimeout(-20);
  }

  @Test(expected = WaitTimedOutError.class)
  public void shouldTimeOutIfFrameNotFound() {
    WindowFinder.findFrame("myFrame").withTimeout(10).using(launcher.robot);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfTimeUnitToFindFrameIsNull() {
    WindowFinder.findFrame("frame").withTimeout(10, null).using(launcher.robot);
  }

  @Test(expected = WaitTimedOutError.class)
  public void shouldTimeOutWhenUsingTimeUnitsIfFrameNotFound() {
    WindowFinder.findDialog("myFrame").withTimeout(10, TimeUnit.MILLISECONDS).using(launcher.robot);
  }

  @Test
  public void shouldFindFrameByNameAfterLogin() {
    launchFrame();
    FrameFixture frame = WindowFinder.findFrame("frame").using(launcher.robot);
    assertThat(frame.target).isInstanceOf(WindowToLaunch.class);
  }

  @Test
  public void shouldFindFrameByNameAfterLoginUsingTimeUnit() {
    launchFrame();
    FrameFixture frame = WindowFinder.findFrame("frame").withTimeout(2, SECONDS).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(WindowToLaunch.class);
  }

  @Test
  public void shouldFindFrameUsingMatcherAfterLogin() {
    launchFrame();
    GenericTypeMatcher<JFrame> matcher = new GenericTypeMatcher<JFrame>(JFrame.class) {
      protected boolean isMatching(JFrame frame) {
        return "frame".equals(frame.getName()) && isShowing(frame);
      }
    };
    FrameFixture frame = WindowFinder.findFrame(matcher).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(WindowToLaunch.class);
  }

  @Test
  public void shouldFindFrameByTypeAfterLogin() {
    launchFrame();
    FrameFixture frame = WindowFinder.findFrame(WindowToLaunch.class).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(WindowToLaunch.class);
  }

  private void launchFrame() {
    launchFrame(500);
  }

  private void launchFrame(int delay) {
    launcherWindow.windowLaunchDelay(delay);
    launcher.button("launchFrame").click();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfDialogNameIsNull() {
    WindowFinder.findDialog((String)null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogNameIsEmpty() {
    WindowFinder.findDialog("");
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfDialogTypeIsNull() {
    WindowFinder.findDialog((Class<Dialog>)null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfDialogMatcherIsNull() {
    GenericTypeMatcher<JDialog> matcher = null;
    WindowFinder.findDialog(matcher);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogSearchTimeoutIsNegative() {
    WindowFinder.findDialog("dialog").withTimeout(-20);
  }

  @Test(expected = WaitTimedOutError.class)
  public void shouldTimeOutIfDialogNotFound() {
    WindowFinder.findDialog("myDialog").withTimeout(5).using(launcher.robot);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfTimeUnitToFindDialogIsNull() {
    WindowFinder.findDialog("dialog").withTimeout(10, null).using(launcher.robot);
  }

  @Test(expected = WaitTimedOutError.class)
  public void shouldTimeOutWhenUsingTimeUnitsIfDialogNotFound() {
    WindowFinder.findDialog("myDialog").withTimeout(10, TimeUnit.MILLISECONDS).using(launcher.robot);
  }

  @Test
  public void shouldFindDialogByNameAfterLoadingSettings() {
    launchDialog();
    DialogFixture dialog = WindowFinder.findDialog("dialog").using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void shouldFindDialogByTypeAfterLoadingSettings() {
    launchDialog();
    DialogFixture dialog = WindowFinder.findDialog(DialogToLaunch.class).using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void shouldFindDialogUsingMatcherAfterLoadingSettings() {
    launchDialog();
    GenericTypeMatcher<JDialog> matcher = new GenericTypeMatcher<JDialog>(JDialog.class) {
      protected boolean isMatching(JDialog dialog) {
        return "dialog".equals(dialog.getName()) && isShowing(dialog);
      }
    };
    DialogFixture dialog = WindowFinder.findDialog(matcher).using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }

  private void launchDialog() {
    launchDialog(500);
  }

  private void launchDialog(int delay) {
    launcherWindow.dialogLaunchDelay(delay);
    launcher.button("launchDialog").click();
  }
}
