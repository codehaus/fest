/*
 * Created on Jun 10, 2007
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

import java.util.regex.Pattern;

import javax.swing.JCheckBox;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JCheckBox}</code> and verification of the state of such
 * <code>{@link JCheckBox}</code>.
 *
 * @author Alex Ruiz
 */
public class JCheckBoxFixture extends TwoStateButtonFixture<JCheckBox> implements JComponentFixture {

  private AbstractButtonDriver driver;

  /**
   * Creates a new <code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JCheckBox</code>.
   * @param target the <code>JCheckBox</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JCheckBoxFixture(Robot robot, JCheckBox target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JCheckBox</code>.
   * @param checkBoxName the name of the <code>JCheckBox</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JCheckBox</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JCheckBox</code> is found.
   */
  public JCheckBoxFixture(Robot robot, String checkBoxName) {
    super(robot, checkBoxName, JCheckBox.class);
    createDriver();
  }

  private void createDriver() {
    driver(new AbstractButtonDriver(robot));
  }

  /**
   * Sets the <code>{@link AbstractButtonDriver}</code> to be used by this fixture.
   * @param newDriver the new <code>AbstractButtonDriver</code>.
   * @throws NullPointerException if the given driver is <code>null</code>.
   */
  protected final void driver(AbstractButtonDriver newDriver) {
    validateNotNull(newDriver);
    driver = newDriver;
  }

  /**
   * Returns the text of this fixture's <code>{@link JCheckBox}</code>.
   * @return the text of this fixture's <code>JCheckBox</code>.
   */
  public String text() {
    return driver.textOf(target);
  }

  /**
   * Checks (or selects) this fixture's <code>{@link JCheckBox}</code> only it is not already checked.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture check() {
    driver.select(target);
    return this;
  }

  /**
   * Unchecks this fixture's <code>{@link JCheckBox}</code> only if it is checked.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture uncheck() {
    driver.unselect(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   */
  public JCheckBoxFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JCheckBox}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JCheckBoxFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JCheckBoxFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JCheckBoxFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JCheckBox</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JCheckBoxFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> does not have input focus.
   */
  public JCheckBoxFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is disabled.
   */
  public JCheckBoxFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JCheckBox</code> is never enabled.
   */
  public JCheckBoxFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is enabled.
   */
  public JCheckBoxFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JCheckBox}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JCheckBox</code> managed by this fixture is not selected.
   */
  public JCheckBoxFixture requireSelected() {
    driver.requireSelected(target);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JCheckBox}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JCheckBox</code> managed by this fixture is selected.
   */
  public JCheckBoxFixture requireNotSelected() {
    driver.requireNotSelected(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is not visible.
   */
  public JCheckBoxFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is visible.
   */
  public JCheckBoxFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that the text of this fixture's <code>{@link JCheckBox}</code> is equal to or matches the specified
   * <code>String</code>.
   * @param expected the text to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the text of the target JCheckBox is not equal to or does not match the given one.
   */
  public JCheckBoxFixture requireText(String expected) {
    driver.requireText(target, expected);
    return this;
  }

  /**
   * Asserts that the text of this fixture's <code>{@link JCheckBox}</code> matches the given regular expression pattern.
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is <code>null</code>.
   * @throws AssertionError if the text of the target <code>JCheckBox</code> does not match the given regular expression
   * pattern.
   * @since 1.2
   */
  public JCheckBoxFixture requireText(Pattern pattern) {
    driver.requireText(target, pattern);
    return this;
  }

  /**
   * Asserts that the toolTip in this fixture's <code>{@link JCheckBox}</code> matches the given value.
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's <code>JCheckBox</code> does not match the given value.
   * @since 1.2
   */
  public JCheckBoxFixture requireToolTip(String expected) {
    driver.requireToolTip(target, expected);
    return this;
  }

  /**
   * Asserts that the toolTip in this fixture's <code>{@link JCheckBox}</code> matches the given regular expression
   * pattern.
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is <code>null</code>.
   * @throws AssertionError if the toolTip in this fixture's <code>JCheckBox</code> does not match the given regular 
   * expression.
   * @since 1.2
   */
  public JCheckBoxFixture requireToolTip(Pattern pattern) {
    driver.requireToolTip(target, pattern);
    return this;
  }
}
