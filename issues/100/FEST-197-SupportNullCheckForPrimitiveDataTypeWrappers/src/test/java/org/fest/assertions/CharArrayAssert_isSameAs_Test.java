/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.EmptyArrays.emptyCharArray;
import static org.fest.assertions.ArrayFactory.charArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CharArrayAssert#isSameAs(char[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CharArrayAssert_isSameAs_Test implements GenericAssert_isSameAs_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_same() {
    char[] array = charArray('a', 'b');
    new CharArrayAssert(array).isSameAs(array);
  }

  @Test
  public void should_fail_if_actual_and_expected_are_not_same() {
    expectAssertionError("expected same instance but found:<[a, b]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').isSameAs(emptyCharArray());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_not_same() {
    expectAssertionError("[A Test] expected same instance but found:<[a, b]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test")
                                     .isSameAs(emptyCharArray());
      }
    });
  }
}
