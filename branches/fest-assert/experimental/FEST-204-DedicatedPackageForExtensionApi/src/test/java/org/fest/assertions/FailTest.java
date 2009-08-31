/*
 * Created on Sep 16, 2007
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

import org.testng.annotations.*;

import org.fest.test.*;

import static org.fest.test.ExpectedFailure.*;
import static org.fest.util.Strings.*;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link Fail}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FailTest {

  @Test(expectedExceptions = AssertionError.class)
  public void shouldThrowExceptionWhenFailing() {
    Fail.fail();
  }

  @Test public void shouldThrowErrorWithGivenMessageAndCause() {
    Throwable t = new Throwable();
    String message = "Some Throwable";
    try {
      Fail.fail(message, t);
      fail(concat(AssertionError.class.getSimpleName(), " should have been thrown"));
    } catch (AssertionError e) {
      assertSame(e.getCause(), t);
      assertEquals(e.getMessage(), message);
    }
  }
  
  @Test
  public void shouldIncludeMessageWhenFailing() {
    final String message = "Failed :(";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        Fail.fail(message);
      }
    });
  }


}
