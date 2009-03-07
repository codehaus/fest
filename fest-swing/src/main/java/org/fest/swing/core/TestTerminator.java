/*
 * Created on Jul 19, 2008
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
package org.fest.swing.core;

import java.awt.Frame;

/**
 * Understands terminating running FEST-Swing tests.
 *
 * @author <a href="mailto:simeon.fitch@mseedsoft.com">Simeon H.K. Fitch</a>
 */
class TestTerminator {

  /*
   * We do three things to signal an abort. 
   * 1) sent an interrupt signal to main thread 
   * 2) dispose all available frames.
   * 3) throw RuntimeException on AWT event thread
   */  
  void terminateTests() {
    pokeMainThread();
    disposeFrames();
    throw new RuntimeException("User aborted FEST-Swing tests.");
  }

  /*
   * Calls {@link Thread#interrupt()} on main thread in attempt to interrupt current FEST operation. Only affects thread
   * if it is in a {@link Object#wait()} or {@link Thread#sleep(long)} method.
   */
  private void pokeMainThread() {
    for (Thread t : allThreads()) if (isMain(t)) t.interrupt();
  }

  private Thread[] allThreads() {
    Thread[] all = new Thread[Thread.activeCount()];
    Thread.enumerate(all);
    return all;
  }
  
  private boolean isMain(Thread thread) {
    return "main".equalsIgnoreCase(thread.getName());
  }

  private void disposeFrames() {
    for (Frame f : Frame.getFrames()) f.dispose();
  }

  static {
    // Make sure there's an exception handler that will dump a stack trace on abort.
    System.setProperty("sun.awt.exception.handler", SimpleFallbackExceptionHandler.class.getName());
  }
}
