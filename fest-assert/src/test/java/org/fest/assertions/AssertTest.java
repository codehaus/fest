/*
 * Created on Oct 10, 2007
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
package org.fest.assertions;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests for {@link Assert}.
 *
 * @author Yvonne Wang
 */
public class AssertTest {

  private Assert assertion;

  @BeforeTest public void setUp() {
    assertion = new Assert() {};
  }

  @Test public void shouldThrowErrorIfEqualsCalled() {
    try {
      assertion.equals(null);
      fail();
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "'equals' is not supported...maybe you intended to call 'isEqualTo'");
    }
  }

  @Test public void shouldReturnOneAsHashCode() {
    assertEquals(assertion.hashCode(), 1);
  }
}
