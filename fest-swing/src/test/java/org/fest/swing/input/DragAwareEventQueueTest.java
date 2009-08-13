/*
 * Created on Apr 4, 2008
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
package org.fest.swing.input;

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.EventQueue;
import java.awt.event.AWTEventListener;
import java.util.EmptyStackException;

import org.fest.swing.test.awt.ToolkitStub;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link DragAwareEventQueue}</code>.
 *
 * @author Alex Ruiz
 */
public class DragAwareEventQueueTest {

  private ToolkitStub toolkit;
  private int mask;
  private AWTEventListener listener;
  private DragAwareEventQueue queue;

  @Before public void setUp() {
    toolkit = ToolkitStub.createNew();
    listener = createMock(AWTEventListener.class);
    queue = new DragAwareEventQueue(toolkit, mask, listener);
    toolkit.eventQueue(queue);
  }

  @Test(expected = EmptyStackException.class)
  public void shouldPopIfToolkitSystemEventQueueIsSameAsQueueUnderTest() {
    queue.pop();
  }

  @Test public void shouldNotPopIfToolkitSystemEventQueueIsNotSameAsQueueUnderTest() {
    toolkit.eventQueue(new EventQueue());
    queue.pop(); // if really pops should throw an EmptyStackException
  }

  @Test public void shouldDispatchEventIfEventIsNotNativeDragAndDrop() {
    MyEvent event = new MyEvent();
    queue.dispatchEvent(event);
    assertThat(event.dispatched).isTrue();
  }

  private static class MyEvent extends AWTEvent implements ActiveEvent {
    private static final long serialVersionUID = 1L;

    private boolean dispatched;

    public MyEvent() {
      super(new Object(), 0);
    }

    public void dispatch() {
      dispatched = true;
    }
  }
}
