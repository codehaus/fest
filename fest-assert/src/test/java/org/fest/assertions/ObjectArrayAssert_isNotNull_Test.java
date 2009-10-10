/*
 * Created on Mar 1, 2007
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
package org.fest.assertions;

import static org.fest.assertions.CommonFailures.expectErrorIfArrayIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfArrayIsNull;
import static org.fest.assertions.EmptyArrays.emptyObjectArray;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ObjectArrayAssert#isNotNull()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssert_isNotNull_Test implements GenericAssert_isNotNull_TestCase {

  @Test
  public void should_pass_if_actual_is_not_null() {
    new ObjectArrayAssert(emptyObjectArray()).isNotNull();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        Object[] actual = null;
        new ObjectArrayAssert(actual).isNotNull();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        Object[] actual = null;
        new ObjectArrayAssert(actual).as("A Test")
                                     .isNotNull();
      }
    });
  }
}