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

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.BorderLayout.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.ComponentLocationQuery.locationOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link JToolBarDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JToolBarDriverTest {

  private Robot robot;
  private MyWindow window;
  private JToolBar toolBar;
  private JToolBarDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JToolBarDriver(robot);
    window = MyWindow.createNew();
    toolBar = window.toolBar;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorWhenFloatingNotFloatableToolBar() {
    setNotFloatable(toolBar);
    robot.waitForIdle();
    try {
      driver.makeFloat(toolBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("is not floatable");
    }
  }

  @RunsInEDT
  private static void setNotFloatable(final JToolBar toolBar) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        toolBar.setFloatable(false);
      }
    });
  }

  public void shouldFloatToolbar() {
    Window oldAncestor = ancestorOf(toolBar);
    driver.makeFloat(toolBar);
    Window newAncestor = ancestorOf(toolBar);
    assertThat(newAncestor).isNotSameAs(oldAncestor);
  }

  public void shouldFloatToolbarToPoint() {
    Window oldAncestor = ancestorOf(toolBar);
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    assertToolBarIsFloating(oldAncestor);
  }

  private void assertToolBarIsFloating(Window oldAncestor) {
    Window newAncestor = ancestorOf(toolBar);
    assertThat(newAncestor).isNotSameAs(oldAncestor);
    Point newAncestorLocation = locationOf(newAncestor);
    Point oldAncestorLocation = locationOf(oldAncestor);
    assertThat(newAncestorLocation.x).isGreaterThan(oldAncestorLocation.x);
    assertThat(newAncestorLocation.y).isGreaterThan(oldAncestorLocation.y);
  }

  public void shouldUnfloatToolbar() {
    Window oldAncestor = ancestorOf(toolBar);
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    driver.unfloat(toolBar);
    assertThat(ancestorOf(toolBar)).isSameAs(oldAncestor);
  }

  @Test(dataProvider = "unfloatConstraints")
  public void shouldUnfloatToolbarToGivenPosition(String constraint) {
    Window originalAncestor = ancestorOf(toolBar);
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    driver.unfloat(toolBar, constraint);
    assertThat(ancestorOf(toolBar)).isSameAs(originalAncestor);
    assertThat(window.componentAt(constraint)).isSameAs(toolBar);
  }

  @RunsInEDT
  private Point whereToFloatTo() {
    Rectangle bounds = boundsOfWindowAncestor(toolBar);
    int x = bounds.x + bounds.width + 10;
    int y = bounds.y + bounds.height + 10;
    return new Point(x, y);
  }

  @RunsInEDT
  private static Rectangle boundsOfWindowAncestor(final JToolBar toolBar) {
    return execute(new GuiQuery<Rectangle>() {
      protected Rectangle executeInEDT() {
        return getWindowAncestor(toolBar).getBounds();
      }
    });
  }

  @DataProvider(name = "unfloatConstraints") 
  public Object[][] unfloatConstraints() {
    return new Object[][] { { NORTH }, { EAST }, { SOUTH }, { WEST } };
  }

  @RunsInEDT
  private static Window ancestorOf(final JToolBar toolBar) {
    return execute(new GuiQuery<Window>() {
      protected Window executeInEDT() {
        return getWindowAncestor(toolBar);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final BorderLayout borderLayout = new BorderLayout();
    final JToolBar toolBar = new JToolBar();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JToolBarDriverTest.class);
      toolBar.setFloatable(true);
      setLayout(borderLayout);
      add(toolBar, NORTH);
      toolBar.add(new JLabel("Hello"));
      setPreferredSize(new Dimension(300, 200));
    }

    @RunsInEDT
    Component componentAt(final String constraint) {
      return execute(new GuiQuery<Component>() {
        protected Component executeInEDT() {
          return borderLayout.getLayoutComponent(constraint);
        }
      });
    }
  }
}
