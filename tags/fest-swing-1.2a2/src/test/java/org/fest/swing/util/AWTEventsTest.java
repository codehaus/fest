/*
 * Created on Nov 11, 2007
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
package org.fest.swing.util;

import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.event.ComponentEvent.*;
import static java.awt.event.WindowEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLabels.label;

/**
 * Tests for <code>{@link AWTEvents}</code>.
 *
 * @author Alex Ruiz
 */
public class AWTEventsTest {

  private TestWindow source;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    source = TestWindow.createNewWindow(getClass());
  }

  @AfterMethod public void tearDown() {
    source.destroy();
  }

  @Test public void shouldReturnTrueIfWindowOpened() {
    AWTEvent event = new WindowEvent(source, WINDOW_OPENED);
    assertThat(AWTEvents.windowOpened(event)).isTrue();    
  }

  @Test public void shouldReturnFalseIfWindowNotOpened() {
    AWTEvent event = new WindowEvent(source, WINDOW_CLOSING);
    assertThat(AWTEvents.windowOpened(event)).isFalse();
  }

  @Test public void shouldReturnTrueIfWindowShown() {
    AWTEvent event = new ComponentEvent(source, COMPONENT_SHOWN);
    assertThat(AWTEvents.windowShown(event)).isTrue();
  }

  @Test public void shouldReturnFalseIfComponentShownIsNotWindow() {
    AWTEvent event = new ComponentEvent(label().createNew(), COMPONENT_SHOWN);
    assertThat(AWTEvents.windowShown(event)).isFalse();
  }

  @Test public void shouldReturnFalseIfWindowNotShown() {
    AWTEvent event = new ComponentEvent(source, COMPONENT_HIDDEN);
    assertThat(AWTEvents.windowShown(event)).isFalse();    
  }

  @Test public void shouldReturnTrueIfWindowClosed() {
    AWTEvent event = new WindowEvent(source, WINDOW_CLOSED);
    assertThat(AWTEvents.windowClosed(event)).isTrue();
  }

  @Test public void shouldReturnFalseIfWindowNotClosed() {
    AWTEvent event = new WindowEvent(source, WINDOW_CLOSING);
    assertThat(AWTEvents.windowClosed(event)).isFalse();
  }
}
