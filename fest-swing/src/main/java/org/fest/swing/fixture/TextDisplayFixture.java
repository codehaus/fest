/*
 * Created on Apr 10, 2007
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
 * Understands verification of the state of a <code>{@link Component}</code> that displays text.
 *
 * @author Alex Ruiz
 */
public interface TextDisplayFixture {

  /**
   * Returns the text of the <code>{@link Component}</code> managed by this fixture.
   * @return the text of the managed <code>Component</code>.
   */
  String text();

  /**
   * Asserts that the text of the <code>{@link Component}</code> managed by this fixture is equal to or matches the
   * specified <code>String</code>.
   * @param expected the text to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the text of the target component is not equal to or does not match the given one.
   */
  TextDisplayFixture requireText(String expected);
}
