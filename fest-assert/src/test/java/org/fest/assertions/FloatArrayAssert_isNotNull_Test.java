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

import static org.fest.assertions.CommonFailures.expectErrorIfArrayIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfArrayIsNull;
import static org.fest.assertions.EmptyArrays.emptyFloatArray;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link FloatArrayAssert#isNotNull()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssert_isNotNull_Test implements GenericAssert_isNotNull_TestCase {

  @Test
  public void should_pass_if_actual_is_not_null() {
    new FloatArrayAssert(emptyFloatArray()).isNotNull();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(null).isNotNull();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(null).as("A Test")
                                  .isNotNull();
      }
    });
  }
}