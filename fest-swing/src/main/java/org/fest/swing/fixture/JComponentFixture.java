/*
 * Created on Jul 17, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.util.regex.Pattern;

import javax.swing.JComponent;

/**
 * Understands simulation of user events on a <code>{@link JComponent}</code> and verification of the state of such
 * <code>{@link JComponent}</code>.
 *
 * @author Alex Ruiz
 * @since 1.2
 */
public interface JComponentFixture {

  /**
   * Asserts that the toolTip in this fixture's <code>{@link JComponent}</code> matches the given value.
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's <code>JComponent</code> does not match the given value.
   */
  JComponentFixture requireToolTip(String expected);

  /**
   * Asserts that the toolTip in this fixture's <code>{@link JComponent}</code> matches the given regular expression
   * pattern.
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is <code>null</code>.
   * @throws AssertionError if the toolTip in this fixture's <code>JComponent</code> does not match the given value.
   */
  JComponentFixture requireToolTip(Pattern pattern);
}
