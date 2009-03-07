/*
 * Created on Dec 25, 2007
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

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JScrollPaneDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JScrollPane}</code> and verification of the state of such
 * <code>{@link JScrollPane}</code>.
 *
 * @author Yvonne Wang
 */
public class JScrollPaneFixture extends JPopupMenuInvokerFixture<JScrollPane> implements CommonComponentFixture {

  private JScrollPaneDriver driver;

  /**
   * Creates a new <code>{@link JScrollPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JScrollPane</code>.
   * @param target the <code>JScrollPane</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>target</code> is <code>null</code>.
   */
  public JScrollPaneFixture(Robot robot, JScrollPane target) {
    super(robot, target);
    updateDriver(newComponentDriver());
  }

  final void updateDriver(JScrollPaneDriver newDriver) {
    driver = newDriver;
  }

  /**
   * Creates a new <code>{@link JScrollPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JScrollPane</code>.
   * @param panelName the name of the <code>JScrollPane</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JScrollPane</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JScrollPane</code> is found.
   */
  public JScrollPaneFixture(Robot robot, String panelName) {
    super(robot, panelName, JScrollPane.class);
    driver = newComponentDriver();
  }

  private JScrollPaneDriver newComponentDriver() {
    return new JScrollPaneDriver(robot);
  }

  /**
   * Returns a <code>{@link JScrollBarFixture}</code> managing the horizontal <code>{@link JScrollBar}</code> of this
   * target's <code>{@link JScrollPane}</code>.
   * @return a fixture managing the horizontal <code>JScrollBar</code> of this target's <code>JScrollPane</code>.
   */
  public JScrollBarFixture horizontalScrollBar() {
    return scrollBarFixture(driver.horizontalScrollBarIn(target));
  }

  /**
   * Returns a <code>{@link JScrollBarFixture}</code> managing the vertical <code>{@link JScrollBar}</code> of this
   * target's <code>{@link JScrollPane}</code>.
   * @return a fixture managing the vertical <code>JScrollBar</code> of this target's <code>JScrollPane</code>.
   */
  public JScrollBarFixture verticalScrollBar() {
    return scrollBarFixture(driver.verticalScrollBarIn(target));
  }

  private JScrollBarFixture scrollBarFixture(JScrollBar scrollBar) {
    return new JScrollBarFixture(robot, scrollBar);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   */
  public JScrollPaneFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   */
  public JScrollPaneFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   */
  public JScrollPaneFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   */
  public JScrollPaneFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   */
  public JScrollPaneFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   */
  public JScrollPaneFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JScrollPane}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   * @see KeyPressInfo
   */
  public JScrollPaneFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JScrollPane}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JScrollPaneFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JScrollPane}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JScrollPaneFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JScrollPane}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JScrollPane</code> is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public JScrollPaneFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> does not have input focus.
   */
  public JScrollPaneFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is disabled.
   */
  public JScrollPaneFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JScrollPane</code> is never enabled.
   */
  public JScrollPaneFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is enabled.
   */
  public JScrollPaneFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is not visible.
   */
  public JScrollPaneFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is visible.
   */
  public JScrollPaneFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
}
