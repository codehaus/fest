/*
 * Created on Oct 18, 2007
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
package org.fest.swing.monitor;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.test.swing.TestWindow.createAndShowNewWindow;

/**
 * Tests for <code>{@link WindowMetrics}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMetricsTest {

  private WindowMetrics metrics;
  private TestWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = createAndShowNewWindow(getClass());
    metrics = createNewWindowMetrics(window);
  }

  @RunsInEDT
  private static WindowMetrics createNewWindowMetrics(final Window window) {
    return execute(new GuiQuery<WindowMetrics>() {
      protected WindowMetrics executeInEDT() {
        return new WindowMetrics(window);
      }
    });
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  @Test public void shouldObtainInsets() {
    assertThat(metrics.insets).isEqualTo(insetsOf(window));
  }

  @Test public void shouldCalculateCenter() {
    Point center = centerOf(metrics);
    assertThat(center).isEqualTo(expectedCenterOf(window));
  }

  @RunsInEDT
  private static Point centerOf(final WindowMetrics metrics) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        return metrics.center();
      }
    });
  }

  @RunsInEDT
  private static Point expectedCenterOf(final Window w) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        Insets insets = w.getInsets();
        int x = w.getX() + insets.left + ((w.getWidth() - (insets.left + insets.right)) / 2);
        int y = w.getY() + insets.top + ((w.getHeight() - (insets.top + insets.bottom)) / 2);
        return new Point(x, y);
      }
    });
  }

  @Test public void shouldAddVerticalInsets() {
    Insets insets = insetsOf(window);
    assertThat(metrics.leftAndRightInsets()).isEqualTo(insets.right + insets.left);
  }

  @Test public void shouldAddHorizontalInsets() {
    Insets insets = insetsOf(window);
    assertThat(metrics.topAndBottomInsets()).isEqualTo(insets.top + insets.bottom);
  }
}
