/*
 * Created on May 20, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.timing.Timeout.timeout;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.Dialog;

import javax.swing.JDialog;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.finder.WindowFinderTest;
import org.fest.swing.test.swing.DialogLauncherWindow;
import org.fest.swing.test.swing.DialogLauncherWindow.DialogToLaunch;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link Dialog}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureDialogLookupTest {

  private FrameFixture launcher;
  private Robot robot;
  private DialogLauncherWindow launcherWindow;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    launcherWindow = DialogLauncherWindow.createNew(WindowFinderTest.class);
    launcher = new FrameFixture(robot, launcherWindow);
    launcher.show();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindDialogByType() {
    launchDialog(0);
    DialogFixture dialog = launcher.dialog();
    assertCorrectDialogWasFound(dialog);
  }

  public void shouldFindDialogByTypeUsingTimeout() {
    launchDialog(200);
    DialogFixture dialog = launcher.dialog(timeout(300));
    assertCorrectDialogWasFound(dialog);
  }

  public void shouldFailIfDialogNotFoundAfterTimeout() {
    try {
      launcher.dialog(timeout(100));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  public void shouldFindDialogWithMatcher() {
    launchDialog(0);
    GenericTypeMatcher<JDialog> matcher = new DialogByTitleMatcher();
    DialogFixture dialog = launcher.dialog(matcher);
    assertCorrectDialogWasFound(dialog);
  }

  public void shouldFindDialogWithMatcherUsingTimeout() {
    launchDialog(200);
    DialogFixture dialog = launcher.dialog(new DialogByTitleMatcher(), timeout(300));
    assertCorrectDialogWasFound(dialog);
  }

  public void shouldFailIfDialogNotFoundWithMatcherAfterTimeout() {
    try {
      launcher.dialog(new DialogByTitleMatcher(), timeout(300));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  public void shouldFindDialogByName() {
    launchDialog(0);
    DialogFixture dialog = launcher.dialog("dialog");
    assertCorrectDialogWasFound(dialog);
  }

  public void shouldFindDialogByNameUsingTimeout() {
    launchDialog(200);
    DialogFixture dialog = launcher.dialog("dialog", timeout(300));
    assertCorrectDialogWasFound(dialog);
  }

  private void assertCorrectDialogWasFound(DialogFixture dialog) {
    assertThat(dialog.component()).isInstanceOf(DialogToLaunch.class);
  }

  public void shouldFailIfDialogNotFoundByNameAfterTimeout() {
    try {
      launcher.dialog("dialog", timeout(300));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  private void assertErrorMessageIsCorrect(WaitTimedOutError e) {
    assertThat(e.getMessage()).contains("Timed out waiting for dialog to be found");
  }

  private void launchDialog(int delay) {
    launcherWindow.dialogLaunchDelay(delay);
    launcher.button("launchDialog").click();
  }

  private static class DialogByTitleMatcher extends GenericTypeMatcher<JDialog> {
    private static final String TITLE = "Launched Dialog";

    private DialogByTitleMatcher() {
      super(JDialog.class);
    }

    protected boolean isMatching(JDialog dialog) {
      return TITLE.equals(dialog.getTitle());
    }

    @Override public String toString() {
      return concat("dialog with title ", quote(TITLE));
    }
  }
}
