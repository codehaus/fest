/*
 * Created on Jun 19, 2009
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
package org.fest.swing.driver;

import static org.fest.swing.util.Strings.match;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.util.regex.Pattern;

import org.fest.assertions.Condition;

/**
 * Understands a condition that verifies that a <code>String</code> is equal to or matches another <code>String</code>.
 * The <code>String</code> to match to can be a regular expression pattern.
 *
 * @author Alex Ruiz
 */
class StringMatches extends Condition<String> {

  private final Pattern pattern;

  static StringMatches matches(Pattern pattern) {
    return new StringMatches(pattern);
  }

  private StringMatches(Pattern pattern) {
    this.pattern = pattern;
    if (pattern != null) as(concat("match pattern:", quote(pattern.pattern())));
  }

  public boolean matches(String actual) {
    return match(pattern, actual);
  }
}
