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

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.awt.ToolkitStub;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link EventNormalizer}</code>.
 *
 * @author Alex Ruiz
 */
public class EventNormalizerTest extends EventNormalizerTestCase {

  private static final String START_LISTENING = "START_LISTENING";
  
  private EventNormalizer eventNormalizer;
  
  @BeforeMethod public void setUp() {
    eventNormalizer = new EventNormalizer();
  }
  
  @Test(groups = START_LISTENING)
  public void shouldAttachToToolkitWhenStartListening() {
    ToolkitStub toolkit = ToolkitStub.createNew();
    int mask = 8;
    eventNormalizer.startListening(toolkit, mockDelegateEventListener(), mask);
    assertEventNormalizerIsInToolkit(toolkit, eventNormalizer, mask);
  }

  @Test(dependsOnGroups = START_LISTENING)
  public void shouldDetachFromToolkitWhenStopListening() {
    ToolkitStub toolkit = ToolkitStub.createNew();
    int mask = 8;
    eventNormalizer.startListening(toolkit, mockDelegateEventListener(), mask);
    eventNormalizer.stopListening();
    assertEventNormalizerIsNotInToolkit(toolkit, mask);
  }  
  
  @Test(dependsOnGroups = START_LISTENING)
  public void shouldDelegateEventIfIsNotDuplicateDispose() {
    final DisposedWindowMonitor disposedWindowMonitor = createMock(DisposedWindowMonitor.class);
    eventNormalizer = new EventNormalizer(disposedWindowMonitor);
    final AWTEventListener delegateEventListener = mockDelegateEventListener();
    eventNormalizer.startListening(ToolkitStub.createNew(), delegateEventListener, 8);
    final AWTEvent event = createMock(AWTEvent.class);
    new EasyMockTemplate(disposedWindowMonitor, delegateEventListener) {
      protected void expectations() {
        expect(disposedWindowMonitor.isDuplicateDispose(event)).andReturn(false);
        delegateEventListener.eventDispatched(event);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        eventNormalizer.eventDispatched(event);
      }
    }.run();
  }

  @Test(dependsOnGroups = START_LISTENING)
  public void shouldNotDelegateEventIfIsDuplicateDispose() {
    final DisposedWindowMonitor disposedWindowMonitor = createMock(DisposedWindowMonitor.class);
    eventNormalizer = new EventNormalizer(disposedWindowMonitor);
    final AWTEventListener delegateEventListener = mockDelegateEventListener();
    eventNormalizer.startListening(ToolkitStub.createNew(), delegateEventListener, 8);
    final AWTEvent event = createMock(AWTEvent.class);
    new EasyMockTemplate(disposedWindowMonitor, delegateEventListener) {
      protected void expectations() {
        expect(disposedWindowMonitor.isDuplicateDispose(event)).andReturn(true);
      }
      
      protected void codeToTest() {
        eventNormalizer.eventDispatched(event);
      }
    }.run();
  }
}
