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

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link WindowStatus}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowStatusTest {

  private static Logger logger = Logger.getAnonymousLogger();

  private WindowStatus status;
  private TestWindow window;
  private Windows windows;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() throws Exception {
    window = TestWindow.createNewWindow(getClass());
    windows = createMock(Windows.class);
    status = new WindowStatus(windows);
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock lock = ScreenLock.instance();
      if (lock.acquiredBy(this)) lock.release(this);
    }
  }

  @Test public void shouldMoveMouseToCenterWithFrameWidthGreaterThanHeight() {
    ScreenLock.instance().acquire(this);
    window.display();
    Point center = new WindowMetrics(window).center();
    center.x += WindowStatus.sign();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expectFrameIsReady();
      }

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    assertThat(mousePointerLocation()).isEqualTo(center);
  }

  @Test public void shouldMoveMouseToCenterWithFrameHeightGreaterThanWidth() {
    ScreenLock.instance().acquire(this);
    window.display(new Dimension(200, 400));
    Point center = new WindowMetrics(window).center();
    center.y += WindowStatus.sign();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expectFrameIsReady();
      }

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    assertThat(mousePointerLocation()).isEqualTo(center);
  }

  @Test public void shouldResizeWindowToReceiveEvents() {
    ScreenLock.instance().acquire(this);
    window.display(new Dimension(0 ,0));
    Dimension original = sizeOf(window);
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expect(windows.isShowingButNotReady(window)).andReturn(true);
      }

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    // wait till frame is resized
    final int timeToPause = 5000;
    logger.info(concat("Pausing for ", timeToPause, " ms"));
    pause(timeToPause);
    assertThat(sizeOf(window).height).isGreaterThan(original.height);
  }

  @Test public void shouldNotCheckIfRobotIsNull() {
    status = new WindowStatus(windows, null);
    Point before = mousePointerLocation();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {}

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    // mouse pointer should not have moved
    assertThat(mousePointerLocation()).isEqualTo(before);
  }

  private void expectFrameIsReady() {
    expect(windows.isShowingButNotReady(window)).andReturn(false);
  }

  private Point mousePointerLocation() {
    return MouseInfo.getPointerInfo().getLocation();
  }
}
