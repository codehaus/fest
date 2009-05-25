/*
 * Created on May 22, 2009
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
package org.fest.swing.core;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;

import java.awt.*;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.test.util.StopWatch;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link AWTEventPoster}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class AWTEventPosterTest {

  private static final int WAIT_DELAY = 1000;
  private Toolkit toolkit;
  private InputState inputState;
  private WindowMonitor monitor;
  private Settings settings;
  private EventQueue eventQueue;
  private AWTEvent event;
  private AWTEventPoster poster;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    toolkit = createMock(Toolkit.class);
    inputState = createMock(InputState.class);
    monitor = createMock(WindowMonitor.class);
    settings = createMock(Settings.class);
    eventQueue = createMock(EventQueue.class);
    event = createMock(AWTEvent.class);
    poster = new AWTEventPoster(toolkit, inputState, monitor, settings);
  }

  public void shouldPostEventInComponentEventQueueIfComponentNotNull() {
    final Component c = createMock(Component.class);
    new EasyMockTemplate(toolkit, inputState, monitor, settings, eventQueue) {
      protected void expectations() {
        expectInputStateToBeUpdatedWithEvent();
        expect(monitor.eventQueueFor(c)).andReturn(eventQueue);
        expectEventQueueToPostEvent();
        expect(settings.delayBetweenEvents()).andReturn(WAIT_DELAY);
      }

      protected void codeToTest() {
        postEventAndAssertItWaited(c);
      }
    }.run();
  }

  public void shouldPostEventInSystemEventQueueIfComponentIsNull() {
    new EasyMockTemplate(toolkit, inputState, monitor, settings, eventQueue) {
      protected void expectations() {
        expectInputStateToBeUpdatedWithEvent();
        expect(toolkit.getSystemEventQueue()).andReturn(eventQueue);
        expectEventQueueToPostEvent();
        expect(settings.delayBetweenEvents()).andReturn(WAIT_DELAY);
      }

      protected void codeToTest() {
        postEventAndAssertItWaited(null);
      }
    }.run();
  }

  private void expectInputStateToBeUpdatedWithEvent() {
    inputState.update(event);
    expectLastCall().once();
  }

  private void expectEventQueueToPostEvent() {
    eventQueue.postEvent(event);
    expectLastCall().once();
  }

  private void postEventAndAssertItWaited(Component c) {
    StopWatch stopWatch = startNewStopWatch();
    poster.postEvent(c, event);
    stopWatch.stop();
    assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(WAIT_DELAY);
  }
}
