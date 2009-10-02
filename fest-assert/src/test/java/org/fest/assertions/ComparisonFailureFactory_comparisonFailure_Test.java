/*
 * Created on Feb 15, 2008
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

import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests for <code>{@link ComparisonFailureFactory#comparisonFailure(String, Object, Object)}</code>.
 *
 * @author Alex Ruiz
 */
public class ComparisonFailureFactory_comparisonFailure_Test {

  @Test
  public void should_not_create_exception_if_actual_is_not_String() {
    AssertionError error = ComparisonFailureFactory.comparisonFailure("message", "expected", 3);
    assertNull(error);
  }

  @Test
  public void should_not_create_exception_if_expected_is_not_String() {
    AssertionError error = ComparisonFailureFactory.comparisonFailure("message", 6, "actual");
    assertNull(error);
  }
}
