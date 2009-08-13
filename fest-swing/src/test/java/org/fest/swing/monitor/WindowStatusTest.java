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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.logging.Logger;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.core.SequentialTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.util.RobotFactory;
import org.junit.Test;

/**
 * Tests for <code>{@link WindowStatus}</code>.
 * TODO: Split
 *
 * @author Alex Ruiz
 */
public class WindowStatusTest extends SequentialTestCase {

  private static Logger logger = Logger.getAnonymousLogger();

  private WindowStatus status;
  private TestWindow window;
  private Windows windows;

  @Override protected final void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
    windows = createMock(Windows.class);
    status = new WindowStatus(windows);
  }

  @Override protected final void onTearDown() {
    window.destroy();
  }

  @Test
  public void shouldHaveRobotEqualToNullIfRobotCannotBeCreated() {
    final RobotFactory factory = createMock(RobotFactory.class);
    new EasyMockTemplate(factory) {
      protected void expectations() throws Throwable {
        expect(factory.newRobotInPrimaryScreen()).andThrow(new AWTException("Thrown on purpose"));
      }

      protected void codeToTest() {
        status = new WindowStatus(windows, factory);
        assertThat(status.robot).isNull();
      }
    }.run();
  }

  @Test
  public void shouldMoveMouseToCenterWithFrameWidthGreaterThanHeight() {
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

  @Test
  public void shouldMoveMouseToCenterWithFrameHeightGreaterThanWidth() {
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

  @Test
  public void shouldResizeWindowToReceiveEvents() {
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

  @Test
  public void shouldNotCheckIfRobotIsNull() {
    final RobotFactory factory = createMock(RobotFactory.class);
    Point before = mousePointerLocation();
    new EasyMockTemplate(windows, factory) {
      @Override protected void expectations() throws Throwable {
        expect(factory.newRobotInPrimaryScreen()).andReturn(null);
      }

      @Override protected void codeToTest() {
        status = new WindowStatus(windows, factory);
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
