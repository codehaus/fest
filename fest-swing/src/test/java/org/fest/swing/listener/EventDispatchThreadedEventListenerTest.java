/*
 * Created on Apr 6, 2008
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
package org.fest.swing.listener;

import java.awt.AWTEvent;

import javax.swing.SwingUtilities;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.timing.Pause.pause;

/**
 * Tests for <code>{@link EventDispatchThreadedEventListener}</code>.
 *
 * @author Alex Ruiz
 */
public class EventDispatchThreadedEventListenerTest {

  private AWTEvent event;
  private Listener listener;
  
  @BeforeMethod public void setUp() {
    event = createMock(AWTEvent.class);
    listener = new Listener();
  }

  @Test public void shouldProcessEventInEDTEvenIfNotCalledFromEDT() {
    listener.eventDispatched(event);
    pause(200);
    assertEventProcessInEDT();
  }
  
  @Test public void shouldProcessEventDirectlyIfCalledFromEDT() throws Exception {
    SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        listener.eventDispatched(event);
      }
    });
    assertEventProcessInEDT();
  }

  private void assertEventProcessInEDT() {
    assertThat(listener.event).isSameAs(event);
    assertThat(listener.inEventDispatchThread).isTrue();
  }
  
  private static class Listener extends EventDispatchThreadedEventListener {
    AWTEvent event;
    boolean inEventDispatchThread;
    
    Listener() {}
    
    protected void processEvent(AWTEvent newEvent) {
      this.event = newEvent;
      inEventDispatchThread = SwingUtilities.isEventDispatchThread();
    }
  }
}
