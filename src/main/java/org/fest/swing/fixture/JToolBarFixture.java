/*
 * Created on Jul 5, 2007
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

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JToolBar;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JToolBarDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JToolBar}</code> and verification of the state of such
 * <code>{@link JToolBar}</code>.
 *
 * @author Alex Ruiz
 */
public class JToolBarFixture extends ContainerFixture<JToolBar> implements CommonComponentFixture {

  /**
   * Understands constraints used to unfloat a floating <code>{@link JToolBar}</code>.
   *
   * @author Alex Ruiz
   */
  public enum UnfloatConstraint {
    NORTH(BorderLayout.NORTH), EAST(BorderLayout.EAST), SOUTH(BorderLayout.SOUTH), WEST(BorderLayout.WEST);

    public final String value;

    UnfloatConstraint(String value) {
      this.value = value;
    }
  }

  private JToolBarDriver driver;

  /**
   * Creates a new <code>{@link JToolBarFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToolBar</code>.
   * @param target the <code>JToolBar</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JToolBarFixture(Robot robot, JToolBar target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JToolBarFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToolBar</code>.
   * @param toolbarName the name of the <code>JToolBar</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JToolBar</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JToolBar</code> is found.
   */
  public JToolBarFixture(Robot robot, String toolbarName) {
    super(robot, toolbarName, JToolBar.class);
    createDriver();
  }

  private void createDriver() {
    driver(new JToolBarDriver(robot));
  }

  /**
   * Sets the <code>{@link JToolBarDriver}</code> to be used by this fixture.
   * @param newDriver the new <code>JToolBarDriver</code>.
   * @throws NullPointerException if the given driver is <code>null</code>.
   */
  protected final void driver(JToolBarDriver newDriver) {
    validateNotNull(newDriver);
    driver = newDriver;
  }

  /**
   * Simulates a user dragging this fixture's <code>{@link JToolBar}</code> to the given location, causing it to float.
   * @param point the point where the <code>JToolBar</code> will be floating to.
   * @return this fixture.
   * @throws ActionFailedException if the <code>JToolBar</code> is not floatable.
   * @throws ActionFailedException if the <code>JToolBar</code> cannot be dragged.
   */
  public JToolBarFixture floatTo(Point point) {
    driver.floatTo(target, point.x, point.y);
    return this;
  }

  /**
   * Simulates a user unfloating this fixture's <code>{@link JToolBar}</code>.
   * @return this fixture.
   * @throws ActionFailedException if the dock container cannot be found.
   */
  public JToolBarFixture unfloat() {
    driver.unfloat(target);
    return this;
  }

  /**
   * Simulates a user dropping this fixture's  {@link JToolBar} to the requested constraint position.
   * @param constraint the constraint position.
   * @return this fixture.
   * @throws ActionFailedException if the dock container cannot be found.
   */
  public JToolBarFixture unfloat(UnfloatConstraint constraint) {
    driver.unfloat(target, constraint.value);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JToolBar}</code>.
   * @return this fixture.
   */
  public JToolBarFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JToolBar}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JToolBarFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JToolBar}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  public JToolBarFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JToolBar}</code>.
   * @return this fixture.
   */
  public JToolBarFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JToolBar}</code>.
   * @return this fixture.
   */
  public JToolBarFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JToolBar}</code>.
   * @return this fixture.
   */
  public JToolBarFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JToolBar}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see KeyPressInfo
   */
  public JToolBarFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys in this fixture's <code>{@link JToolBar}</code>. This method
   * does not affect the current focus.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JToolBarFixture pressAndReleaseKeys(int...keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JToolBar}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JToolBarFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JToolBar}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public JToolBarFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToolBar}</code> has input focus.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToolBar</code> does not have input focus.
   */
  public JToolBarFixture requireFocused() {
    driver.requireFocused(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToolBar}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToolBar</code> is disabled.
   */
  public JToolBarFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToolBar}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JToolBar</code> is never enabled.
   */
  public JToolBarFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToolBar}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToolBar</code> is enabled.
   */
  public JToolBarFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToolBar}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToolBar</code> is not visible.
   */
  public JToolBarFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToolBar}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToolBar</code> is visible.
   */
  public JToolBarFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }
}
