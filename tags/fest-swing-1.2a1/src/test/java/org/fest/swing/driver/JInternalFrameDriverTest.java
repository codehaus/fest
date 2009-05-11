/*
 * Created on Feb 24, 2008
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

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.test.awt.FluentDimension;
import org.fest.swing.test.awt.FluentPoint;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.util.StopWatch;

import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.ComponentLocationQuery.locationOf;
import static org.fest.swing.driver.JInternalFrameAction.*;
import static org.fest.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.fest.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JInternalFrameDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JInternalFrameDriverTest {

  private Robot robot;
  private MyWindow window;
  private JInternalFrame internalFrame;
  private JDesktopPane desktopPane;
  private JInternalFrameDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JInternalFrameDriver(robot);
    window = MyWindow.createNew();
    internalFrame = window.internalFrame;
    desktopPane = window.desktopPane;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorIfSetPropertyIsVetoed() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    JInternalFrameAction action = MAXIMIZE;
    try {
      driver.failIfVetoed(internalFrame, action, new UnexpectedException(vetoed));
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertThat(e.getMessage()).contains(action.name)
                                .contains("was vetoed: <Test>");
    }
  }

  public void shouldNotThrowErrorIfSetPropertyIsNotVetoed() {
    driver.failIfVetoed(internalFrame, MAXIMIZE, new UnexpectedException(new Exception()));
  }

  public void shouldNotIconifyAlreadyIconifiedInternalFrame() {
    iconifyJInternalFrame();
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
  }

  public void shouldNotDeiconifyAlreadyDeiconifiedInternalFrame() {
    setIcon(internalFrame, DEICONIFY);
    robot.waitForIdle();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  public void shouldIconifyAndDeiconifyInternalFrame() {
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  public void shouldThrowErrorIfIconifyingFrameThatIsNotIconifiable() {
    setNotIconifiable(internalFrame);
    robot.waitForIdle();
    try {
      driver.iconify(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("The JInternalFrame <")
                                .contains("> is not iconifiable");
    }
  }

  private static void setNotIconifiable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setIconifiable(false);
      }
    });
  }

  public void shouldMaximizeJInternalFrame() {
    driver.maximize(internalFrame);
    assertThat(isMaximized(internalFrame)).isTrue();
  }

  public void shouldMaximizeIconifiedJInternalFrame() {
    StopWatch stopWatch = StopWatch.startNewStopWatch();
    iconifyJInternalFrame();
    stopWatch.stop();
    System.out.println("iconified in " + stopWatch.ellapsedTime() + " ms");
    stopWatch = StopWatch.startNewStopWatch();
    driver.maximize(internalFrame);
    stopWatch.stop();
    System.out.println("maximized in " + stopWatch.ellapsedTime() + " ms");
    assertThat(isMaximized(internalFrame)).isTrue();
  }
  
  public void shouldMaximizeDisabledJInternalFrame() {
    disableInternalFrame();
    driver.maximize(internalFrame);
    assertThat(isMaximized(internalFrame)).isTrue();
  }

  private void disableInternalFrame() {
    disable(internalFrame);
    robot.waitForIdle();
  }

  public void shouldThrowErrorWhenMaximizingNotShowingJInternalFrame() {
    hideWindow();
    try {
      driver.maximize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenMaximizingHiddenJInternalFrame() {
    hideInternalJFrame();
    try {
      driver.maximize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  private void hideInternalJFrame() {
    hide(internalFrame);
    robot.waitForIdle();
  }

  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }
  
  public void shouldThrowErrorIfMaximizingNotMaximizableJInternalFrame() {
    setNotMaximizable(internalFrame);
    robot.waitForIdle();
    try {
      driver.maximize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("The JInternalFrame <")
                                .contains("> is not maximizable");
    }
  }

  private static void setNotMaximizable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setMaximizable(false);
      }
    });
  }

  public void shouldNormalizeInternalFrame() {
    driver.maximize(internalFrame);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  public void shouldNormalizeIconifiedInternalFrame() {
    iconifyJInternalFrame();
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  private void iconifyJInternalFrame() {
    setIcon(internalFrame, ICONIFY);
    robot.waitForIdle();
  }

  private void assertIsNormalized() {
    assertThat(isIconified(internalFrame)).isFalse();
    assertThat(isMaximized(internalFrame)).isFalse();
  }

  private static boolean isMaximized(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return internalFrame.isMaximum();
      }
    });
  }

  public void shouldMoveInternalFrameToFront() {
    driver.moveToFront(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(0);
  }

  public void shouldMoveInternalFrameToBack() {
    driver.moveToBack(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(1);
  }

  public void shouldResizeInternalFrameToGivenSize() {
    FluentDimension newSize = internalFrameSize().addToWidth(20).addToHeight(40);
    driver.resize(internalFrame, newSize.width, newSize.height);
    assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
  }

  private FluentDimension internalFrameSize() {
    return new FluentDimension(sizeOf(internalFrame));
  }

  public void shouldMoveInternalFrame() {
    Point p = internalFrameLocation().addToX(10).addToY(10);
    driver.moveTo(internalFrame, p);
    assertThat(internalFrameLocation()).isEqualTo(p);
  }

  private FluentPoint internalFrameLocation() {
    return new FluentPoint(locationOf(internalFrame));
  }

  public void shouldCloseInternalFrame() {
    driver.close(internalFrame);
    boolean closed = isClosed(internalFrame);
    assertThat(closed).isTrue();
  }

  private static boolean isClosed(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return internalFrame.isClosed();
      }
    });
  }
  
  public void shouldThrowErrorIfClosingFrameThatIsNotClosable() {
    setNotClosable(internalFrame);
    robot.waitForIdle();
    try {
      driver.close(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("The JInternalFrame <")
                                .contains("> is not closable");
    }
  }

  private static void setNotClosable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setClosable(false);
      }
    });
  }

  public void shouldResizeInternalFrame() {
    Dimension newSize = new FluentDimension(sizeOf(internalFrame)).addToWidth(60).addToHeight(60);
    driver.resizeTo(internalFrame, newSize);
    assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
  }

  public void shouldResizeWidth() {
    int newWidth = 600;
    assertThat(widthOf(internalFrame)).isNotEqualTo(newWidth);
    driver.resizeWidthTo(internalFrame, newWidth);
    assertThat(widthOf(internalFrame)).isEqualTo(newWidth);
  }

  private static int widthOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return internalFrame.getWidth();
      }
    });
  }

  public void shouldResizeHeight() {
    int newHeight = 600;
    assertThat(heightOf(internalFrame)).isNotEqualTo(newHeight);
    driver.resizeHeightTo(internalFrame, newHeight);
    assertThat(heightOf(internalFrame)).isEqualTo(newHeight);
  }

  private static int heightOf(JInternalFrame internalFrame) {
    return sizeOf(internalFrame).height;
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JDesktopPane desktopPane;
    final JInternalFrame internalFrame;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JInternalFrameDriverTest.class);
      MyInternalFrame.resetIndex();
      desktopPane = new JDesktopPane();
      internalFrame = new MyInternalFrame();
      addInternalFrames();
      setContentPane(desktopPane);
      setPreferredSize(new Dimension(400, 300));
    }

    private void addInternalFrames() {
      addInternalFrame(new MyInternalFrame());
      addInternalFrame(internalFrame);
    }

    private void addInternalFrame(JInternalFrame f) {
      desktopPane.add(f);
      f.toFront();
      f.setVisible(true);
    }
  }

  private static class MyInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private static int index;

    static void resetIndex() {
      index = 0;
    }

    MyInternalFrame() {
      super(concat("Internal Frame ", valueOf(index++)), true, true, true, true);
      setSize(200, 100);
    }
  }
}
