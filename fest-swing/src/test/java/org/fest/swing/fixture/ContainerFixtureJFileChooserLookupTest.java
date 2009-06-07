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

import javax.swing.JFileChooser;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.finder.WindowFinderTest;
import org.fest.swing.test.swing.JFileChooserLauncherWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JFileChooser}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJFileChooserLookupTest {

  private ConcreteContainerFixture fixture;
  private Robot robot;
  private JFileChooserLauncherWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = JFileChooserLauncherWindow.createNew(WindowFinderTest.class);
    fixture = new ConcreteContainerFixture(robot, window);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindJFileChooserByType() {
    launchJFileChooser(0);
    JFileChooserFixture fileChooser = fixture.fileChooser();
    assertCorrectJFileChooserWasFound(fileChooser);
  }

  public void shouldFindJFileChooserByTypeUsingTimeout() {
    launchJFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser(timeout(300));
    assertCorrectJFileChooserWasFound(fileChooser);
  }

  public void shouldFailIfJFileChooserNotFoundAfterTimeout() {
    try {
      fixture.fileChooser(timeout(100));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  public void shouldFindJFileChooserWithMatcher() {
    launchJFileChooser(0);
    GenericTypeMatcher<JFileChooser> matcher = new JFileChooserByTitleMatcher();
    JFileChooserFixture fileChooser = fixture.fileChooser(matcher);
    assertCorrectJFileChooserWasFound(fileChooser);
  }

  public void shouldFindJFileChooserWithMatcherUsingTimeout() {
    launchJFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser(new JFileChooserByTitleMatcher(), timeout(300));
    assertCorrectJFileChooserWasFound(fileChooser);
  }

  public void shouldFailIfJFileChooserNotFoundWithMatcherAfterTimeout() {
    try {
      fixture.fileChooser(new JFileChooserByTitleMatcher(), timeout(300));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  public void shouldFindJFileChooserByName() {
    launchJFileChooser(0);
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser");
    assertCorrectJFileChooserWasFound(fileChooser);
  }

  public void shouldFindJFileChooserByNameUsingTimeout() {
    launchJFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser", timeout(300));
    assertCorrectJFileChooserWasFound(fileChooser);
  }

  private void assertCorrectJFileChooserWasFound(JFileChooserFixture fileChooser) {
    assertThat(fileChooser.component()).isSameAs(window.fileChooser());
  }

  public void shouldFailIfJFileChooserNotFoundByNameAfterTimeout() {
    try {
      fixture.fileChooser("fileChooser", timeout(300));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  private void assertErrorMessageIsCorrect(WaitTimedOutError e) {
    assertThat(e.getMessage()).contains("Timed out waiting for file chooser to be found");
  }

  private void launchJFileChooser(int delay) {
    window.launchDelay(delay);
    fixture.button("browse").click();
  }

  private static class JFileChooserByTitleMatcher extends GenericTypeMatcher<JFileChooser> {
    private static final String TITLE = "Launched JFileChooser";

    private JFileChooserByTitleMatcher() {
      super(JFileChooser.class);
    }

    protected boolean isMatching(JFileChooser fileChooser) {
      return TITLE.equals(fileChooser.getDialogTitle());
    }

    @Override public String toString() {
      return concat("file chooser with title ", quote(TITLE));
    }
  }
}
