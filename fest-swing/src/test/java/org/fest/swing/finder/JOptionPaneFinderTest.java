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

import javax.swing.JOptionPane;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.JOptionPaneLauncherWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link JOptionPaneFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JOptionPaneFinderTest extends RobotBasedTestCase {

  private JOptionPaneLauncherWindow window;
  private FrameFixture frameFixture;

  @Override protected void onSetUp() {
    window = JOptionPaneLauncherWindow.createNew(JOptionPaneFinderTest.class);
    frameFixture = new FrameFixture(robot, window);
    frameFixture.show();
  }

  @Test
  public void shouldFindJOptionPane() {
    clickMessageButton();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().using(robot);
    assertThat(found.target).isNotNull();
  }

  @Test
  public void shouldFindJOptionPaneUsingGivenMatcher() {
    clickMessageButton();
    GenericTypeMatcher<JOptionPane> matcher = new GenericTypeMatcher<JOptionPane>(JOptionPane.class) {
      protected boolean isMatching(JOptionPane optionPane) {
        return optionPane.isShowing();
      }
    };
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane(matcher).using(robot);
    assertThat(found.target).isNotNull();
  }

  @Test
  public void shouldFindJOptionPaneBeforeGivenTimeoutExpires() {
    window.launchDelay(2000);
    clickMessageButton();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().withTimeout(5, SECONDS).using(robot);
    assertThat(found.target).isNotNull();
  }

  void clickMessageButton() {
    frameFixture.button("message").click();
  }

  @Test(expected = WaitTimedOutError.class)
  public void shouldFailIfJOptionPaneNotFound() {
    JOptionPaneFinder.findOptionPane().using(robot);
  }

}