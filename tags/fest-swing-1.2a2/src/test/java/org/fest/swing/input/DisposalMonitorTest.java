/*
 * Created on Apr 3, 2008
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

import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link DisposalMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class DisposalMonitorTest {

  private JFrame frame;
  private Map<Window, Boolean> disposedWindows;
  private DisposalMonitor monitor;
  
  @BeforeMethod public void setUp() {
    frame = TestWindow.createNewWindow(DisposalMonitorTest.class);
    disposedWindows = new HashMap<Window, Boolean>();
    monitor = new DisposalMonitor(disposedWindows);
    frame.addComponentListener(monitor);
  }
  
  @Test public void shouldRemoveComponentWhenShown() {
    disposedWindows.put(frame, true);
    monitor.componentShown(new ComponentEvent(frame, 0));
    assertThat(disposedWindows).isEmpty();
    assertThat(frame.getComponentListeners()).isEmpty();
  }
}
