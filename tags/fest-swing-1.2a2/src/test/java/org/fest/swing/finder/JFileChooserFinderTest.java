/*
 * Created on Oct 29, 2007
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
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JFileChooser;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.test.swing.JFileChooserLauncherWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JFileChooserFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JFileChooserFinderTest {

  private Robot robot;
  private FrameFixture frameFixture;
  private JFileChooserLauncherWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = JFileChooserLauncherWindow.createNew(JFileChooserFinderTest.class);
    frameFixture = new FrameFixture(robot, window);
    frameFixture.show();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindFileChooser() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().using(robot);
    assertThat(found.target).isSameAs(window.fileChooser());
  }

  public void shouldFindFileChooserBeforeGivenTimeoutExpires() {
    window.launchDelay(2000);
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().withTimeout(5, SECONDS).using(robot);
    assertThat(found.target).isSameAs(window.fileChooser());
  }

  @Test(groups = GUI, expectedExceptions = WaitTimedOutError.class)
  public void shouldFailIfFileChooserNotFound() {
    JFileChooserFinder.findFileChooser().using(robot);
  }

  public void shouldFindFileChooserByName() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser("fileChooser").using(robot);
    assertThat(found.target).isSameAs(window.fileChooser());
  }

  public void shouldFindFileChooserUsingMatcher() {
    clickBrowseButton();
    GenericTypeMatcher<JFileChooser> matcher = new GenericTypeMatcher<JFileChooser>(JFileChooser.class) {
      protected boolean isMatching(JFileChooser fileChooser) {
        return "fileChooser".equals(fileChooser.getName());
      }
    };
    JFileChooserFixture found = JFileChooserFinder.findFileChooser(matcher).using(robot);
    assertThat(found.target).isSameAs(window.fileChooser());
  }

  void clickBrowseButton() {
    frameFixture.button("browse").click();
  }
}
