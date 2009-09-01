/*
 * Created on Mar 29, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.input;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import org.fest.swing.listener.WeakEventListener;

import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

/**
 * Understands an <code>{@link AWTEventListener}</code> which normalizes the event stream by sending a single 
 * <code>WINDOW_CLOSED</code>, instead of one every time dispose is called.
 */
class EventNormalizer implements AWTEventListener {

  private final DisposedWindowMonitor disposedWindowMonitor;

  private WeakEventListener weakEventListener;
  private AWTEventListener listener;

  EventNormalizer() {
    this(new DisposedWindowMonitor());
  }

  EventNormalizer(DisposedWindowMonitor disposedWindowMonitor) {
    this.disposedWindowMonitor = disposedWindowMonitor;
  }
  
  void startListening(final Toolkit toolkit, AWTEventListener delegate, long mask) {
    listener = delegate;
    weakEventListener = attachAsWeakEventListener(toolkit, this, mask);
  }

  void stopListening() {
    disposeWeakEventListener();
    listener = null;
  }

  private void disposeWeakEventListener() {
    if (weakEventListener == null) return;
    weakEventListener.dispose();
    weakEventListener = null;
  }

  /** Event reception callback. */
  public void eventDispatched(AWTEvent event) {
    boolean discard = disposedWindowMonitor.isDuplicateDispose(event);
    if (!discard && listener != null) delegate(event);
  }

  void delegate(AWTEvent e) {
    listener.eventDispatched(e);
  }
}