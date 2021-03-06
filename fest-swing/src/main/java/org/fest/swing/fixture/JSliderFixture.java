/*
 * Created on Jul 1, 2007
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
package org.fest.swing.fixture;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JSlider;

import org.fest.swing.core.*;
import org.fest.swing.driver.JSliderDriver;
import org.fest.swing.exception.*;
import org.fest.swing.timing.Timeout;

/**
 * Understands functional testing of <code>{@link JSlider}</code>s:
 * <ul>
 * <li>user input simulation</li>
 * <li>state verification</li>
 * <li>property value query</li>
 * </ul>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSliderFixture extends ComponentFixture<JSlider> implements CommonComponentFixture,
    JComponentFixture, JPopupMenuInvokerFixture {

  private JSliderDriver driver;

  /**
   * Creates a new <code>{@link JSliderFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JSlider</code>.
   * @param target the <code>JSlider</code> to be managed <code>{@link JSlider}</code> by this fixture.
   * @throws NullPointerException if <code>robot</code> is {@code null}.
   * @throws NullPointerException if <code>target</code> is {@code null}.
   */
  public JSliderFixture(Robot robot, JSlider target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JSliderFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSlider</code>.
   * @param sliderName the name of the <code>JSlider</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is {@code null}.
   * @throws ComponentLookupException if a matching <code>JSlider</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JSlider</code> is found.
   */
  public JSliderFixture(Robot robot, String sliderName) {
    super(robot, sliderName, JSlider.class);
    createDriver();
  }

  private void createDriver() {
    driver(new JSliderDriver(robot));
  }

  /**
   * Sets the <code>{@link JSliderDriver}</code> to be used by this fixture.
   * @param newDriver the new <code>JSliderDriver</code>.
   * @throws NullPointerException if the given driver is {@code null}.
   */
  protected final void driver(JSliderDriver newDriver) {
    validateNotNull(newDriver);
    driver = newDriver;
  }

  /**
   * Simulates a user sliding this fixture's <code>{@link JSlider}</code> to the given value.
   * @param value the value to slide the <code>JSlider</code> to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @throws IllegalArgumentException if the given position is not within the <code>JSlider</code> bounds.
   */
  public JSliderFixture slideTo(int value) {
    driver.slide(target, value);
    return this;
  }

  /**
   * Simulates a user sliding this fixture's <code>{@link JSlider}</code> to its maximum value.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture slideToMaximum() {
    driver.slideToMaximum(target);
    return this;
  }

  /**
   * Simulates a user sliding this fixture's <code>{@link JSlider}</code> to its minimum value.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture slideToMinimum() {
    driver.slideToMinimum(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSlider}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSlider}</code>.
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseButton</code> is {@code null}.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSlider}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is {@code null}.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JSlider}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JSlider}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JSlider}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   */
  public JSliderFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JSlider}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is {@code null}.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JSliderFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JSlider}</code>. This method
   * does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is {@code null}.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JSliderFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JSlider}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JSliderFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JSlider}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JSliderFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JSlider}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSlider</code> does not have input focus.
   */
  public JSliderFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JSlider}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError is this fixture's <code>JSlider</code> is disabled.
   */
  public JSliderFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JSlider}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JSlider</code> is never enabled.
   */
  public JSliderFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JSlider}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError is this fixture's <code>JSlider</code> is enabled.
   */
  public JSliderFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JSlider}</code>.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSlider</code> is not visible.
   */
  public JSliderFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JSlider}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSlider</code> is visible.
   */
  public JSliderFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that the toolTip in this fixture's <code>{@link JSlider}</code> matches the given value.
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's <code>JSlider</code> does not match the given value.
   * @since 1.2
   */
  public JSliderFixture requireToolTip(String expected) {
    driver.requireToolTip(target, expected);
    return this;
  }

  /**
   * Asserts that the toolTip in this fixture's <code>{@link JSlider}</code> matches the given regular expression
   * pattern.
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the toolTip in this fixture's <code>JSlider</code> does not match the given regular
   * expression pattern.
   * @since 1.2
   */
  public JSliderFixture requireToolTip(Pattern pattern) {
    driver.requireToolTip(target, pattern);
    return this;
  }

  /**
   * Returns the client property stored in this fixture's <code>{@link JSlider}</code>, under the given key.
   * @param key the key to use to retrieve the client property.
   * @return the value of the client property stored under the given key, or {@code null} if the property was
   * not found.
   * @throws NullPointerException if the given key is {@code null}.
   * @since 1.2
   */
  public Object clientProperty(Object key) {
    return driver.clientProperty(target, key);
  }

  /**
   * Shows a pop-up menu using this fixture's <code>{@link JSlider}</code> as the invoker of the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot, driver.invokePopupMenu(target));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's <code>{@link JSlider}</code> as the invoker of the
   * pop-up menu.
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JSlider</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(Point p) {
    return new JPopupMenuFixture(robot, driver.invokePopupMenu(target, p));
  }
}
