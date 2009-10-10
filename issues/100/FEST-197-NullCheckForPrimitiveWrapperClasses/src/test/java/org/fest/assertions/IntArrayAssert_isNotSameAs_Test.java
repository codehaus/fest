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

import static org.fest.assertions.EmptyArrays.emptyIntArray;
import static org.fest.assertions.ArrayFactory.intArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link IntArrayAssert#isNotSameAs(int[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class IntArrayAssert_isNotSameAs_Test implements GenericAssert_isNotSameAs_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_not_same() {
    new IntArrayAssert(6).isNotSameAs(emptyIntArray());
  }

  @Test
  public void should_fail_if_actual_and_expected_are_same() {
    expectAssertionError("given objects are same:<[6, 8]>").on(new CodeToTest() {
      public void run() {
        int[] array = intArray(6, 8);
        new IntArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_same() {
    expectAssertionError("[A Test] given objects are same:<[6, 8]>").on(new CodeToTest() {
      public void run() {
        int[] array = intArray(6, 8);
        new IntArrayAssert(array).as("A Test")
                                 .isNotSameAs(array);
      }
    });
  }
}
