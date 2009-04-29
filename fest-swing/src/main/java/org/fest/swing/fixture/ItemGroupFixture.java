/*
 * Created on Jun 12, 2007
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

import java.awt.Component;

/**
 * Understands simulation of user events on a <code>{@link Component}</code> that contains or displays a group of items,
 * and verification of the state of such <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface ItemGroupFixture {

  /**
   * Returns the <code>String</code> representation of the elements in this fixture's <code>{@link Component}</code>.
   * @return the <code>String</code> representation of the elements in this fixture's <code>Component</code>.
   */
  String[] contents();

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link Component}</code>.
   * @param index the index of the item to select.
   * @return this fixture.
   */
  ItemGroupFixture selectItem(int index);

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link Component}</code>.
   * @param value the value of the item to select.
   * @return this fixture.
   */
  ItemGroupFixture selectItem(String value);

  /**
   * Returns the value of an item in the <code>{@link Component}</code> managed by this fixture. If the value is not
   * meaningful, this method will return <code>null</code>.
   * @param index the index of the item to return.
   * @return the value of the item under the given index, or <code>null</code> if nothing meaningful.
   */
  Object valueAt(int index);

  /**
   * Verifies that the value the selected item in this fixture's <code>{@link Component}</code> matches the given
   * value.
   * @param value the value to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given value.
   */
  ItemGroupFixture requireSelection(String value);

  /**
   * Verifies that the index of the selected item in this fixture's <code>{@link Component}</code> is equal to the given
   * value.
   * @param index the expected selection index.
   * @return this fixture.
   * @throws AssertionError if the selection index is not equal to the given value.
   * @since 1.2
   */
  ItemGroupFixture requireSelection(int index);

  /**
   * Verifies that this fixture's <code>{@link Component}</code> does not have a selection.
   * @return this fixture.
   * @throws AssertionError if the this fixture's <code>Component</code> has a selection.
   */
  ItemGroupFixture requireNoSelection();
}