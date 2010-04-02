/*
 * Created on Oct 14, 2007
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
 * Copyright @2007-2010 the original author or authors.
 */
package org.fest.swing.monitor;

import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.EventQueue;
import java.util.*;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for <code>{@link Context#allEventQueues()}</code>.
 *
 * @author Alex Ruiz
 */
public class Context_allEventQueues_Test extends Context_TestCase {

  @Test 
  public void should_return_all_EventQueues() {
    new EasyMockTemplate(windowEventQueueMapping, eventQueueMapping) {
      protected void expectations() {
        expect(windowEventQueueMapping.eventQueues()).andReturn(eventQueueInList());
        expect(eventQueueMapping.eventQueues()).andReturn(eventQueueInList());
      }

      protected void codeToTest() {
        Collection<EventQueue> allEventQueues = context.allEventQueues();
        assertThat(allEventQueues).containsOnly(eventQueue);
      }
    }.run();
  }

  private List<EventQueue> eventQueueInList() {
    final List<EventQueue> queues = new ArrayList<EventQueue>();
    queues.add(eventQueue);
    return queues;
  }
}