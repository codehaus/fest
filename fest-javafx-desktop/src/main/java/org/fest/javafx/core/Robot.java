/*
 * Created on May 26, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.javafx.core;

import javafx.stage.Stage;

/**
 * Understands simulation of user events on a JavaFX UI.
 *
 * @author Alex Ruiz
 */
public interface Robot {

  /**
   * Launches a JavaFX UI from the given type. All launches UIs are added to this <code>{@link Robot}</code>'s
   * <code>{@link NodeHierarchy}</code> and they will be closed and disposed automatically when
   * <code>{@link #cleanUp()}</code> is executed.
   * @param guiSource the class defining the JavaFX UI to launch.
   * @return the {@code Stage} of the launched UI.
   */
  Stage launchGui(Class<?> guiSource);

//  void click(Node n);
//
//  void rightClick(Node n);
//
//  void doubleClick(Node n);
//
//  void click(MouseButton button, Node n);
//
//  void click(MouseButton button, Node n, int times);
//
//  void click(MouseButton button, Node n, Point where, int times);

  /**
   * Cleans up any used resources (keyboard, mouse, open windows and the
   * <code>{@link org.fest.ui.testing.lock.ScreenLock}</code>) used by this robot.
   */
  void cleanUp();
}
