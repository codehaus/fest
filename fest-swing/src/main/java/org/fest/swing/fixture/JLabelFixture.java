/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JLabel;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JLabelDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JLabel}</code> and verification of the state of such
 * <code>{@link JLabel}</code>.
 *
 * @author Alex Ruiz
 */
public class JLabelFixture extends JPopupMenuInvokerFixture<JLabel> implements CommonComponentFixture,
    TextDisplayFixture {

  private JLabelDriver driver;

  /**
   * Creates a new <code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JLabel</code>.
   * @param target the <code>JLabel</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JLabelFixture(Robot robot, JLabel target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on a <code>JLabel</code>.
   * @param labelName the name of the <code>JLabel</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JLabel</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JLabel</code> is found.
   */
  public JLabelFixture(Robot robot, String labelName) {
    super(robot, labelName, JLabel.class);
    createDriver();
  }

  private void createDriver() {
    driver(new JLabelDriver(robot));
  }

  /**
   * Sets the <code>{@link JLabelDriver}</code> to be used by this fixture.
   * @param newDriver the new <code>JLabelDriver</code>.
   * @throws NullPointerException if the given driver is <code>null</code>.
   */
  protected final void driver(JLabelDriver newDriver) {
    validateNotNull(newDriver);
    driver = newDriver;
  }

  /**
   * Returns the text of this fixture's <code>{@link JLabel}</code>.
   * @return the text of this fixture's <code>JLabel</code>.
   */
  public String text() {
    return driver.textOf(target);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   */
  public JLabelFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JLabel}</code>.
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   */
  public JLabelFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JLabel}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   */
  public JLabelFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   */
  public JLabelFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   */
  public JLabelFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   */
  public JLabelFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JLabel}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JLabelFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JLabel}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JLabelFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JLabel}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JLabelFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JLabel}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JLabel</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JLabelFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that the text of this fixture's <code>{@link JLabel}</code> is equal to the specified <code>String</code>.
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target component is not equal to the given one.
   */
  public JLabelFixture requireText(String expected) {
    driver.requireText(target, expected);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> does not have input focus.
   */
  public JLabelFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is disabled.
   */
  public JLabelFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JLabel</code> is never enabled.
   */
  public JLabelFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is enabled.
   */
  public JLabelFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is not visible.
   */
  public JLabelFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is visible.
   */
  public JLabelFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
}
