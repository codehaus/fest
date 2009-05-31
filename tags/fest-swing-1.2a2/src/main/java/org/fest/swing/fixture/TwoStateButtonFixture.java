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

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a two-state button and verification of the state of such button.
 * @param <T> the specific type of two-state button this fixture can handle.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class TwoStateButtonFixture<T extends AbstractButton> extends JPopupMenuInvokerFixture<T> 
    implements CommonComponentFixture, TextDisplayFixture {

  /**
   * Creates a new <code>{@link TwoStateButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param type the type of the <code>JToggleButton</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public TwoStateButtonFixture(Robot robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new <code>{@link TwoStateButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param name the name of the <code>JToggleButton</code> to find using the given <code>Robot</code>.
   * @param type the type of the <code>JToggleButton</code> to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public TwoStateButtonFixture(Robot robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }
  
  /**
   * Creates a new <code>{@link TwoStateButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToggleButton</code>.
   * @param target the <code>JToggleButton</code> to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public TwoStateButtonFixture(Robot robot, T target) {
    super(robot, target);
  }

  /**
   * Verifies that this fixture's <code>{@link JToggleButton}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is not selected.
   */
  protected abstract TwoStateButtonFixture<T> requireSelected();

  /**
   * Verifies that this fixture's <code>{@link JToggleButton}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is selected.
   */
  protected abstract TwoStateButtonFixture<T> requireNotSelected();
}
