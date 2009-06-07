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

import javax.swing.JOptionPane;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.finder.WindowFinderTest;
import org.fest.swing.test.swing.JOptionPaneLauncherWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JOptionPane}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJOptionPaneLookupTest {

  private ConcreteContainerFixture launcher;
  private Robot robot;
  private JOptionPaneLauncherWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = JOptionPaneLauncherWindow.createNew(WindowFinderTest.class);
    launcher = new ConcreteContainerFixture(robot, window);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindJOptionPaneByType() {
    launchJOptionPane(0);
    JOptionPaneFixture optionPane = launcher.optionPane();
    assertCorrectJOptionPaneWasFound(optionPane);
  }

  public void shouldFindJOptionPaneByTypeUsingTimeout() {
    launchJOptionPane(200);
    JOptionPaneFixture optionPane = launcher.optionPane(timeout(300));
    assertCorrectJOptionPaneWasFound(optionPane);
  }

  public void shouldFailIfJOptionPaneNotFoundAfterTimeout() {
    try {
      launcher.optionPane(timeout(100));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertErrorMessageIsCorrect(e);
    }
  }

  private void assertCorrectJOptionPaneWasFound(JOptionPaneFixture optionPane) {
    assertThat(optionPane.component()).isNotNull();
  }

  private void assertErrorMessageIsCorrect(WaitTimedOutError e) {
    assertThat(e.getMessage()).contains("Timed out waiting for option pane to be found");
  }

  private void launchJOptionPane(int delay) {
    window.launchDelay(delay);
    launcher.button("message").click();
  }
}
