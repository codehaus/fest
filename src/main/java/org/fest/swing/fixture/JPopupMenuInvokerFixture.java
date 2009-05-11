/*
 * Created on Mar 1, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;

/**
 * Understands a fixture that can invoke a <code>{@link JPopupMenu}</code> on the target <code>{@link Component}</code>.
 * @param <T> the type of <code>Component</code> that this fixture can manage.
 *
 * @author Alex Ruiz
 */
public abstract class JPopupMenuInvokerFixture<T extends Component> extends ComponentFixture<T> {

  /**
   * Creates a new <code>{@link JPopupMenuInvokerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public JPopupMenuInvokerFixture(Robot robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new <code>{@link JPopupMenuInvokerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of the <code>Component</code> to find using the given <code>Robot</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public JPopupMenuInvokerFixture(Robot robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }

  /**
   * Creates a new <code>{@link JPopupMenuInvokerFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Component</code>.
   * @param target the <code>Component</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JPopupMenuInvokerFixture(Robot robot, T target) {
    super(robot, target);
  }

  /**
   * Shows a pop-up menu using this fixture's <code>{@link Component}</code> as the invoker of the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>Component</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>Component</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenu() {
    validateIsEnabledAndShowing(target);
    return new JPopupMenuFixture(robot, robot.showPopupMenu(target));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's <code>{@link Component}</code> as the invoker of the
   * pop-up menu.
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's <code>Component</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>Component</code> is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(Point p) {
    validateIsEnabledAndShowing(target);
    return new JPopupMenuFixture(robot, robot.showPopupMenu(target, p));
  }
}
