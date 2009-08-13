/*
 * Created on Feb 25, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSliderValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;

import java.awt.Dimension;

import javax.swing.JSlider;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JSliderDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public abstract class JSliderDriverTestCase {

  private Robot robot;
  private MyWindow window;
  private JSliderDriver driver;
  private JSlider slider;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSliderDriver(robot);
    window = MyWindow.createNew(getClass(), orientation());
    slider = window.slider;
    robot.showWindow(window);
  }

  abstract int orientation();

  JSlider slider() { return slider; }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "valueProvider")
  public void shouldSlideToValue(int value) {
    driver.slide(slider, value);
    assertThatSliderValueIsEqualTo(value);
  }

  @DataProvider(name = "valueProvider")
  public Object[][] valueProvider() {
    return new Object[][] {
        { 5 }, { 10 }, { 28 }, { 20 }
    };
  }

  public void shouldThrowErrorWhenSlidingDisabledJSliderToValue() {
    disableJSlider();
    try {
      driver.slide(slider, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSlidingNotShowingJSliderToValue() {
    hideWindow();
    try {
      driver.slide(slider, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldSlideToMaximum() {
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(maximumOf(slider));
  }

  private static int maximumOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return slider.getMaximum();
      }
    });
  }

  public void shouldThrowErrorWhenSlidingDisabledJSliderToMaximum() {
    disableJSlider();
    try {
      driver.slideToMaximum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }
  
  public void shouldThrowErrorWhenSlidingNotShowingJSliderToMaximum() {
    hideWindow();
    try {
      driver.slideToMaximum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldSlideToMinimum() {
    driver.slideToMinimum(slider);
    assertThatSliderValueIsEqualTo(minimumOf(slider));
  }

  @RunsInEDT
  private static int minimumOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return slider.getMinimum();
      }
    });
  }
  
  public void shouldThrowErrorWhenSlidingDisabledJSliderToMinimum() {
    disableJSlider();
    try {
      driver.slideToMinimum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSlidingNotShowingJSliderToMinimum() {
    hideWindow();
    try {
      driver.slideToMinimum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @RunsInEDT
  private void assertThatSliderValueIsEqualTo(int expected) {
    assertThat(valueOf(slider)).isEqualTo(expected);
  }

  public void shouldThrowErrorIfValueIsLessThanMinimum() {
    try {
      driver.slide(slider, -1);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualTo("Value <-1> is not within the JSlider bounds of <0> and <30>");
    }
  }

  public void shouldThrowErrorIfValueIsGreaterThanMaximum() {
    try {
      driver.slide(slider, 31);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualTo("Value <31> is not within the JSlider bounds of <0> and <30>");
    }
  }

  @RunsInEDT
  private void disableJSlider() {
    disable(slider);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }
  
  protected int sliderOrientation() {
    return orientationOf(slider);
  }

  private static int orientationOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return slider.getOrientation();
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSlider slider = new JSlider();

    @RunsInEDT
    static MyWindow createNew(final Class<? extends JSliderDriverTestCase> testClass, final int orientation) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass, orientation);
        }
      });
    }

    private MyWindow(Class<? extends JSliderDriverTestCase> testClass, int orientation) {
      super(testClass);
      add(slider);
      slider.setOrientation(orientation);
      slider.setMinimum(0);
      slider.setMaximum(30);
      slider.setValue(15);
      setPreferredSize(new Dimension(300, 300));
    }
  }
}
