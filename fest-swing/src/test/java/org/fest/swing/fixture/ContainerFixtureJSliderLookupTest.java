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

import static javax.swing.SwingConstants.HORIZONTAL;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JSlider;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JSlider}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJSliderLookupTest {

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

  public void shouldFindJSliderByType() {
    JSliderFixture slider = fixture.slider();
    assertThatFixtureHasCorrectJSlider(slider);
  }

  public void shouldFailIfJSliderCannotBeFoundByType() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.remove(window.slider);
      }
    });
    robot.waitForIdle();
    try {
      fixture.slider();
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("type=javax.swing.JSlider, requireShowing=true");
    }
  }

  public void shouldFindJSliderByName() {
    JSliderFixture slider = fixture.slider("slideMeSlider");
    assertThatFixtureHasCorrectJSlider(slider);
  }

  public void shouldFailIfJSliderCannotBeFoundByName() {
    try {
      fixture.slider("mySlider");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='mySlider', type=javax.swing.JSlider, requireShowing=true");
    }
  }

  public void shouldFindJSliderWithCustomMatcher() {
    JSliderFixture slider = fixture.slider(new GenericTypeMatcher<JSlider>(JSlider.class) {
      protected boolean isMatching(JSlider s) {
        return s.getOrientation() == HORIZONTAL && s.getValue() == 8;
      }
    });
    assertThatFixtureHasCorrectJSlider(slider);
  }

  private void assertThatFixtureHasCorrectJSlider(JSliderFixture sliderFixture) {
    assertThat(sliderFixture.component()).isSameAs(window.slider);
  }

  public void shouldFailIfJSliderCannotBeFoundWithCustomMatcher() {
    try {
      fixture.slider(new GenericTypeMatcher<JSlider>(JSlider.class) {
        protected boolean isMatching(JSlider s) {
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

    final JSlider slider = new JSlider(HORIZONTAL, 6, 10, 8);

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJSliderLookupTest.class);
      slider.setName("slideMeSlider");
      addComponents(slider);
    }
  }
}
