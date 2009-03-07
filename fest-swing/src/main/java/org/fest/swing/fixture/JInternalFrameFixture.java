/*
 * Created on Dec 8, 2007
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
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JInternalFrame;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JInternalFrameDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JInternalFrame}</code> and verification of the state of such
 * <code>{@link JInternalFrame}</code>.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixture extends ContainerFixture<JInternalFrame> implements CommonComponentFixture,
    FrameLikeFixture {
  private JInternalFrameDriver driver;
  
  /**
   * Creates a new <code>{@link JInternalFrameFixture}</code>.
   * @param robot performs simulation of user events on a <code>JInternalFrame</code>.
   * @param internalFrameName the name of the <code>JInternalFrame</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JInternalFrame</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JInternalFrame</code> is found.
   */
  public JInternalFrameFixture(Robot robot, String internalFrameName) {
    super(robot, internalFrameName, JInternalFrame.class);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JInternalFrameFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JInternalFrame</code>.
   * @param target the <code>JInternalFrame</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JInternalFrameFixture(Robot robot, JInternalFrame target) {
    super(robot, target);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JInternalFrameDriver(robot));
  }
  
  final void updateDriver(JInternalFrameDriver newDriver) {
    driver = newDriver;
  }
  
  /**
   * Brings this fixture's <code>{@link JInternalFrame}</code> to the front.
   * @return this fixture.
   */
  public JInternalFrameFixture moveToFront() {
    driver.moveToFront(target);
    return this;
  }

  /**
   * Brings this fixture's <code>{@link JInternalFrame}</code> to the back.
   * @return this fixture.
   */
  public JInternalFrameFixture moveToBack() {
    driver.moveToBack(target);
    return this;
  }

  /**
   * Simulates a user deiconifying this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public JInternalFrameFixture deiconify() {
    driver.deiconify(target);
    return this;
  }

  /**
   * Simulates a user iconifying this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not iconifiable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public JInternalFrameFixture iconify() {
    driver.iconify(target);
    return this;
  }

  /**
   * Simulates a user maximizing this fixture's <code>{@link JInternalFrame}</code>, deconifying it first if it is 
   * iconified.
   * @return this fixture.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not maximizable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public JInternalFrameFixture maximize() {
    driver.maximize(target);
    return this;
  }

  /**
   * Simulates a user normalizing this fixture's <code>{@link JInternalFrame}</code>, deconifying it first if it is 
   * iconified.
   * @return this fixture.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public JInternalFrameFixture normalize() {
    driver.normalize(target);
    return this;
  }

  /**
   * Simulates a user closing this fixture's <code>{@link JInternalFrame}</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> is not closable.
   */
  public void close() {
    driver.close(target);
  }

  /**
   * Asserts that the size of this fixture's <code>{@link JInternalFrame}</code> is equal to given one.
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's <code>JInternalFrame</code> is not equal to the given size.
   */
  public JInternalFrameFixture requireSize(Dimension size) {
    driver.requireSize(target, size);
    return this;
  }

  /**
   * Simulates a user resizing horizontally this fixture's <code>{@link JInternalFrame}</code>.
   * @param width the width that this fixture's <code>JInternalFrame</code> should have after being resized.
   * @return this fixture.
   */
  public JInternalFrameFixture resizeWidthTo(int width) {
    driver.resizeWidthTo(target, width);
    return this;
  }

  /**
   * Simulates a user resizing vertically this fixture's <code>{@link JInternalFrame}</code>.
   * @param height the height that this fixture's <code>JInternalFrame</code> should have after being resized.
   * @return this fixture.
   */
  public JInternalFrameFixture resizeHeightTo(int height) {
    driver.resizeHeightTo(target, height);
    return this;
  }

  /**
   * Simulates a user resizing this fixture's <code>{@link JInternalFrame}</code>.
   * @param size the size that the target <code>JInternalFrame</code> should have after being resized.
   * @return this fixture.
   */
  public JInternalFrameFixture resizeTo(Dimension size) {
    driver.resizeTo(target, size);
    return this;
  }

  /**
   * Simulates a user moving this fixture's <code>{@link JInternalFrame}</code> to the given point.
   * @param p the point to move this fixture's <code>JInternalFrame</code> to.
   * @return this fixture.
   */
  public JInternalFrameFixture moveTo(Point p) {
    driver.moveTo(target, p);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public JInternalFrameFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JInternalFrameFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  public JInternalFrameFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public JInternalFrameFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public JInternalFrameFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public JInternalFrameFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JInternalFrame}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see KeyPressInfo
   */
  public JInternalFrameFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JInternalFrame}</code> .
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JInternalFrameFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing given key on this fixture's <code>{@link JInternalFrame}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JInternalFrameFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JInternalFrame}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JInternalFrameFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JInternalFrame</code> does not have input focus.
   */
  public JInternalFrameFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is disabled.
   */
  public JInternalFrameFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>JInternalFrame</code> is never enabled.
   */
  public JInternalFrameFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is enabled.
   */
  public JInternalFrameFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is not visible.
   */
  public JInternalFrameFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is visible.
   */
  public JInternalFrameFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
}
