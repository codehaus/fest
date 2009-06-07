/*
 * Created on Jun 7, 2009
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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JButton;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JButton}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJButtonLookupTest {

  private ConcreteContainerFixture fixture;
  private Robot robot;
  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    fixture = new ConcreteContainerFixture(robot, window);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindJButtonByType() {
    JButtonFixture button = fixture.button();
    assertThatFixtureHasCorrectJButton(button);
  }

  public void shouldFailIfJButtonCannotBeFoundByType() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.remove(window.button);
      }
    });
    robot.waitForIdle();
    try {
      fixture.button();
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("type=javax.swing.JButton, requireShowing=true");
    }
  }

  public void shouldFindJButtonByName() {
    JButtonFixture button = fixture.button("clickMeButton");
    assertThatFixtureHasCorrectJButton(button);
  }

  public void shouldFailIfJButtonCannotBeFoundByName() {
    try {
      fixture.button("myButton");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='myButton', type=javax.swing.JButton, requireShowing=true");
    }
  }

  public void shouldFindJButtonWithCustomMatcher() {
    JButtonFixture button = fixture.button(new GenericTypeMatcher<JButton>(JButton.class) {
      protected boolean isMatching(JButton b) {
        return "Click Me".equals(b.getText());
      }
    });
    assertThatFixtureHasCorrectJButton(button);
  }

  private void assertThatFixtureHasCorrectJButton(JButtonFixture buttonFixture) {
    assertThat(buttonFixture.component()).isSameAs(window.button);
  }

  public void shouldFailIfJButtonCannotBeFoundWithCustomMatcher() {
    try {
      fixture.button(new GenericTypeMatcher<JButton>(JButton.class) {
        protected boolean isMatching(JButton b) {
          return false;
        }
      });
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher");
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click Me");

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJButtonLookupTest.class);
      button.setName("clickMeButton");
      addComponents(button);
    }
  }
}
