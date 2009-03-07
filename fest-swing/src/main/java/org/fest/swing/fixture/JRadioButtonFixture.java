/*
 * Created on Sep 18, 2007
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

import javax.swing.JRadioButton;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JRadioButton}</code> and verification of the state of such 
 * <code>{@link JRadioButton}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JRadioButtonFixture extends TwoStateButtonFixture<JRadioButton> {

  private AbstractButtonDriver driver;

  /**
   * Creates a new <code>{@link JRadioButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JRadioButton</code>.
   * @param target the <code>JRadioButton</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JRadioButtonFixture(Robot robot, JRadioButton target) {
    super(robot, target);
    createDriver();
  }
  
  /**
   * Creates a new <code>{@link JRadioButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JRadioButton</code>.
   * @param buttonName the name of the <code>JRadioButton</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JRadioButton</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JRadioButton</code> is found.
   */
  public JRadioButtonFixture(Robot robot, String buttonName) {
    super(robot, buttonName, JRadioButton.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new AbstractButtonDriver(robot));
  }
  
  void updateDriver(AbstractButtonDriver newDriver) {
    driver = newDriver;
  }

  /**
   * Returns the text of this fixture's <code>{@link JRadioButton}</code>. 
   * @return the text of this fixture's <code>JRadioButton</code>. 
   */
  public String text() {
    return driver.textOf(target);
  }

  /**
   * Checks (or selects) this fixture's <code>{@link JRadioButton}</code> only it is not already checked.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture check() {
    driver.select(target);
    return this;
  }

  /**
   * Unchecks this fixture's <code>{@link JRadioButton}</code> only if it is checked.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture uncheck() {
    driver.unselect(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JRadioButton}</code>.
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JRadioButton}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   */
  public JRadioButtonFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JRadioButton}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JRadioButtonFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JRadioButton}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JRadioButtonFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JRadioButton}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JRadioButtonFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JRadioButton}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JRadioButton</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JRadioButtonFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> does not have input focus.
   */
  public JRadioButtonFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError is this fixture's <code>JRadioButton</code> is disabled.
   */
  public JRadioButtonFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JRadioButton</code> is never enabled.
   */
  public JRadioButtonFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError is this fixture's <code>JRadioButton</code> is enabled.
   */
  public JRadioButtonFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JRadioButton}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is not selected.
   */
  public JRadioButtonFixture requireSelected() {
    driver.requireSelected(target);
    return this;
  }
  
  /**
   * Verifies that this fixture's <code>{@link JRadioButton}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is selected.
   */
  public JRadioButtonFixture requireNotSelected() {
    driver.requireNotSelected(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is not visible.
   */
  public JRadioButtonFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is visible.
   */
  public JRadioButtonFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
  
  /**
   * Asserts that the text of this fixture's <code>{@link JRadioButton}</code> is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JRadioButton is not equal to the given one.
   */
  public JRadioButtonFixture requireText(String expected) {
    driver.requireText(target, expected);
    return this;
  }
}
