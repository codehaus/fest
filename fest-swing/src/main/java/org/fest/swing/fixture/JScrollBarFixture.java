/*
 * Created on Dec 25, 2007
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

import javax.swing.JScrollBar;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JScrollBarDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JScrollBar}</code> and verification of the state of such
 * <code>{@link JScrollBar}</code>.
 *
 * @author Alex Ruiz
 */
public class JScrollBarFixture extends JPopupMenuInvokerFixture<JScrollBar> implements CommonComponentFixture {

  private JScrollBarDriver driver;

  /**
   * Creates a new <code>{@link JScrollBarFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JScrollBar</code>.
   * @param target the <code>JScrollBar</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JScrollBarFixture(Robot robot, JScrollBar target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JScrollBarFixture}</code>.
   * @param robot performs simulation of user events on a <code>JScrollBar</code>.
   * @param scrollBarName the name of the <code>JScrollBar</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JScrollBar</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JScrollBar</code> is found.
   */
  public JScrollBarFixture(Robot robot, String scrollBarName) {
    super(robot, scrollBarName, JScrollBar.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JScrollBarDriver(robot));
  }
  
  final void updateDriver(JScrollBarDriver newDriver) {
    driver = newDriver;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollBar}</code>.
   * @param button the button to click.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @return this fixture.
   */
  public JScrollBarFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollBar}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JScrollBar}</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @return this fixture.
   */
  public JScrollBarFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JScrollBar}</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @return this fixture.
   */
  public JScrollBarFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JScrollBar}</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @return this fixture.
   */
  public JScrollBarFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JScrollBar}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JScrollBarFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JScrollBar}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JScrollBarFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JScrollBar}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JScrollBarFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JScrollBar}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JScrollBarFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user scrolling down one block (usually a page.)
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollBlockDown() {
    driver.scrollBlockDown(target);
    return this;
  }

  /**
   * Simulates a user scrolling down one block (usually a page,) the given number of times.
   * @param times the number of times to scroll down one block.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollBlockDown(int times) {
    driver.scrollBlockDown(target, times);
    return this;
  }

  /**
   * Simulates a user scrolling up one block (usually a page.)
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollBlockUp() {
    driver.scrollBlockUp(target);
    return this;
  }

  /**
   * Simulates a user scrolling up one block (usually a page,) the given number of times.
   * @param times the number of times to scroll up one block.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollBlockUp(int times) {
    driver.scrollBlockUp(target, times);
    return this;
  }

  /**
   * Simulates a user scrolling to the given position.
   * @param position the position to scroll to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   * @throws IllegalArgumentException if the given position is not within the <code>JScrollBar</code> bounds.
   */
  public JScrollBarFixture scrollTo(int position) {
    driver.scrollTo(target, position);
    return this;
  }

  /**
   * Simulates a user scrolling to the maximum position of this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollToMaximum() {
    driver.scrollToMaximum(target);
    return this;
  }

  /**
   * Simulates a user scrolling to the minimum position of this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollToMinimum() {
    driver.scrollToMinimum(target);
    return this;
  }
  
  /**
   * Simulates a user scrolling down one unit (usually a line.)
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollUnitDown() {
    driver.scrollUnitDown(target);
    return this;
  }

  /**
   * Simulates a user scrolling down one unit (usually a line,) the given number of times.
   * @param times the number of times to scroll down one unit.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollUnitDown(int times) {
    driver.scrollUnitDown(target, times);
    return this;
  }

  /**
   * Simulates a user scrolling up one unit (usually a line.)
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollUnitUp() {
    driver.scrollUnitUp(target);
    return this;
  }

  /**
   * Simulates a user scrolling up one unit (usually a line,) the given number of times.
   * @param times the number of times to scroll up one unit.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollBar</code> is not showing on the screen.
   */
  public JScrollBarFixture scrollUnitUp(int times) {
    driver.scrollUnitUp(target, times);
    return this;
  }

  /**
   * Asserts that the value of this fixture's <code>{@link JScrollBar}</code> is equal to the given one.
   * @param value the expected value.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's <code>JScrollBar</code> is not equal to the given one.
   */
  public JScrollBarFixture requireValue(int value) {
    driver.requireValue(target, value);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> does not have input focus.
   */
  public JScrollBarFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is disabled.
   */
  public JScrollBarFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JScrollBar</code> is never enabled.
   */
  public JScrollBarFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is enabled.
   */
  public JScrollBarFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is not visible.
   */
  public JScrollBarFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is visible.
   */
  public JScrollBarFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
}
