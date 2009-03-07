/*
 * Created on Nov 1, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JPanel;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JPanel}</code> and verification of the state of such
 * <code>{@link JPanel}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JPanelFixture extends ContainerFixture<JPanel> implements CommonComponentFixture {

  private JComponentDriver driver;
  
  /**
   * Creates a new <code>{@link JPanelFixture}</code>.
   * @param robot performs simulation of user events on a <code>JPanel</code>.
   * @param panelName the name of the <code>JPanel</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JPanel</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JPanel</code> is found.
   */
  public JPanelFixture(Robot robot, String panelName) {
    super(robot, panelName, JPanel.class);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JPanelFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JPanel</code>.
   * @param target the <code>JPanel</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JPanelFixture(Robot robot, JPanel target) {
    super(robot, target);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JComponentDriver(robot));
  }
  
  final void updateDriver(JComponentDriver newDriver) {
    driver = newDriver;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JPanel}</code>.
   * @return this fixture.
   */
  public JPanelFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JPanel}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JPanelFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JPanel}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  public JPanelFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JPanel}</code>.
   * @return this fixture.
   */
  public JPanelFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JPanel}</code>.
   * @return this fixture.
   */
  public JPanelFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JPanel}</code>.
   * @return this fixture.
   */
  public JPanelFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JPanel}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see KeyPressInfo
   */
  public JPanelFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JPanel}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JPanelFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JPanel}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JPanelFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JPanel}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JPanelFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JPanel}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPanel</code> does not have input focus.
   */
  public JPanelFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JPanel}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPanel</code> is disabled.
   */
  public JPanelFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JPanel}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JPanel</code> is never enabled.
   */
  public JPanelFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JPanel}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPanel</code> is enabled.
   */
  public JPanelFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JPanel}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPanel</code> is not visible.
   */
  public JPanelFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JPanel}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPanel</code> is visible.
   */
  public JPanelFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
}
