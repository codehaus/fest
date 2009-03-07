/*
 * Created on Jun 24, 2008
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

import java.awt.EventQueue;
import java.awt.Toolkit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.awt.ToolkitStub;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link DragAwareEventNormalizer}</code>.
 *
 * @author Alex Ruiz
 */
public class DragAwareEventNormalizerTest extends EventNormalizerTestCase {

  private static final String START_LISTENING = "DragAwareEventNormalizer#startListening";
  
  private DragAwareEventNormalizer eventNormalizer;
  
  @BeforeMethod public void setUp() {
    eventNormalizer = new DragAwareEventNormalizer();
  }
  
  @Test(groups = START_LISTENING)
  public void shouldReplaceEventQueueWhenStartListening() {
    ToolkitStub toolkit = ToolkitStub.createNew();
    EventQueueStub eventQueue = new EventQueueStub();
    toolkit.eventQueue(eventQueue);
    int mask = 8;
    eventNormalizer.startListening(toolkit, mockDelegateEventListener(), mask);
    assertEventNormalizerIsInToolkit(toolkit, eventNormalizer, mask);
    assertThat(eventQueue.pushedEventQueue).isInstanceOf(DragAwareEventQueue.class);
  }

  @Test(dependsOnGroups = START_LISTENING)
  public void shouldDisposeEventQueueWhenStopListening() {
    final DragAwareEventQueue dragAwareEventQueue = createMock(DragAwareEventQueue.class);
    eventNormalizer = new DragAwareEventNormalizer() {
      @Override DragAwareEventQueue newDragAwareEventQueue(Toolkit toolkit, long mask) {
        return dragAwareEventQueue;
      }
    };
    ToolkitStub toolkit = ToolkitStub.createNew();
    EventQueueStub eventQueue = new EventQueueStub();
    toolkit.eventQueue(eventQueue);
    int mask = 8;
    eventNormalizer.startListening(toolkit, mockDelegateEventListener(), mask);
    new EasyMockTemplate(dragAwareEventQueue) {
      protected void expectations() {
        dragAwareEventQueue.pop();
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        eventNormalizer.stopListening();
      }
    }.run();
    assertEventNormalizerIsNotInToolkit(toolkit, mask);
  }  
  
  @Test(dependsOnGroups = START_LISTENING)
  public void shouldGracefullyStopListeningIfDragAwareQueueIsNull() {
    eventNormalizer = new DragAwareEventNormalizer() {
      @Override DragAwareEventQueue newDragAwareEventQueue(Toolkit toolkit, long mask) {
        throw new RuntimeException("Thrown on purpose");
      }
    };
    ToolkitStub toolkit = ToolkitStub.createNew();
    EventQueueStub eventQueue = new EventQueueStub();
    toolkit.eventQueue(eventQueue);
    int mask = 8;
    eventNormalizer.startListening(toolkit, mockDelegateEventListener(), mask);
    eventNormalizer.stopListening();
    assertEventNormalizerIsNotInToolkit(toolkit, mask);
  }

  private static class EventQueueStub extends EventQueue {
    EventQueue pushedEventQueue;

    EventQueueStub() {}
    
    @Override public synchronized void push(EventQueue newEventQueue) {
      this.pushedEventQueue = newEventQueue;
      super.push(newEventQueue);
    }
  }
}
