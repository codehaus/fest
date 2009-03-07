/*
 * Created on Mar 22, 2008
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
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.EventQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.test.awt.ToolkitStub;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link EventQueueMapping}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class EventQueueMappingTest {

  private EventQueue eventQueue;
  private ToolkitStub toolkit;
  private ComponentWithCustomEventQueue component;
  private EventQueueMapping mapping;
  private Map<Component, WeakReference<EventQueue>> queueMap;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    eventQueue = new EventQueue();
    toolkit = ToolkitStub.createNew(eventQueue);
    component = new ComponentWithCustomEventQueue(toolkit);
    mapping = new EventQueueMapping();
    queueMap = mapping.queueMap;
  }

  public void shouldAddEventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = queueMap.get(component).get();
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  public void shouldReturnEventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  public void shouldReturnEventQueueInComponentIfNoMappingFound() {
    assertThat(queueMap.keySet()).excludes(eventQueue);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  public void shouldReturnStoredEventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  public void shouldReturnNullIfEventQueueNotStored() {
    assertThat(queueMap.keySet()).excludes(eventQueue);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }

  public void shouldReturnNullIfEventQueueReferenceIsNull() {
    queueMap.put(component, null);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }

  public void shouldReturnAllQueues() {
    EventQueue anotherEventQueue = new EventQueue();
    ToolkitStub anotherToolkit = ToolkitStub.createNew(anotherEventQueue);
    ComponentWithCustomEventQueue anotherComponent = new ComponentWithCustomEventQueue(anotherToolkit);
    mapping.addQueueFor(component);
    mapping.addQueueFor(anotherComponent);
    Collection<EventQueue> allEventQueues = mapping.eventQueues();
    assertThat(allEventQueues).containsOnly(eventQueue, anotherEventQueue);
  }

  public void shouldNotFailIfMappingHasNullReference() {
    mapping.addQueueFor(component);
    queueMap.put(textField().createNew(), null);
    Collection<EventQueue> allEventQueues = mapping.eventQueues();
    assertThat(allEventQueues).containsOnly(eventQueue);
  }
}
