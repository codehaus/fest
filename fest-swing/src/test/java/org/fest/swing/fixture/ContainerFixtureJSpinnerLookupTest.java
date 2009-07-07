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

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JSpinner}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJSpinnerLookupTest {

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

  public void shouldFindJSpinnerByType() {
    JSpinnerFixture spinner = fixture.spinner();
    assertThatFixtureHasCorrectJSpinner(spinner);
  }

  public void shouldFailIfJSpinnerCannotBeFoundByType() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.remove(window.spinner);
      }
    });
    robot.waitForIdle();
    try {
      fixture.spinner();
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("type=javax.swing.JSpinner, requireShowing=true");
    }
  }

  public void shouldFindJSpinnerByName() {
    JSpinnerFixture spinner = fixture.spinner("spinMeSpinner");
    assertThatFixtureHasCorrectJSpinner(spinner);
  }

  public void shouldFailIfJSpinnerCannotBeFoundByName() {
    try {
      fixture.spinner("mySpinner");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='mySpinner', type=javax.swing.JSpinner, requireShowing=true");
    }
  }

  public void shouldFindJSpinnerWithCustomMatcher() {
    JSpinnerFixture spinner = fixture.spinner(new GenericTypeMatcher<JSpinner>(JSpinner.class) {
      protected boolean isMatching(JSpinner s) {
        return s.getValue().equals(8);
      }
    });
    assertThatFixtureHasCorrectJSpinner(spinner);
  }

  private void assertThatFixtureHasCorrectJSpinner(JSpinnerFixture spinnerFixture) {
    assertThat(spinnerFixture.component()).isSameAs(window.spinner);
  }

  public void shouldFailIfJSpinnerCannotBeFoundWithCustomMatcher() {
    try {
      fixture.spinner(new GenericTypeMatcher<JSpinner>(JSpinner.class) {
        protected boolean isMatching(JSpinner s) {
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

    final JSpinner spinner = new JSpinner(new SpinnerNumberModel(8, 6, 10, 1));

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJSpinnerLookupTest.class);
      spinner.setName("spinMeSpinner");
      addComponents(spinner);
    }
  }
}