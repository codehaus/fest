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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.*;
import static org.testng.Assert.*;

import org.fest.test.CodeToTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

  @Test(dataProvider = "messageProvider")
  public void shouldThrowErrorWithMessageIfValuesAreEqual(final String message) {
    String expectedMessage = concat(format(message), "actual value:<'Yoda'> should not be equal to:<'Yoda'>");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        Fail.failIfEqual(message, "Yoda", "Yoda");
      }
    });
  }

  @Test public void shouldNotThrowErrorIfValuesAreNotEqualAndExpectingEqual() {
    Fail.failIfEqual("", "Yoda", "Ben");
  }

  @Test(dataProvider = "messageProvider")
  public void shouldFailIfValuesAreNotEqual(final String message) {
    String expectedMessage = concat(format(message), "expected:<'Luke'> but was:<'Yoda'>");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        Fail.failIfNotEqual(message, "Yoda", "Luke");
      }
    });
  }

  @Test void shouldPassIfValuesAreEqualAndExpectingNotEqual() {
    Fail.failIfNotEqual("", "Yoda", "Yoda");
  }

  @Test(dataProvider = "messageProvider")
  public void failIfNullShouldFailIfNull(final String message) {
    String expectedMessage = concat(format(message), "expecting a non-null object, but it was null");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        Fail.failIfNull(message, null);
      }
    });
  }

  @Test void shouldPassIfValueIsNotNullAndExpectingNull() {
    Fail.failIfNull("", "Luke");
  }

  @Test(dataProvider = "messageProvider")
  public void shouldFailIfValueIsNotNull(final String message) {
    String expectedMessage = concat(format(message), "<'Leia'> should be null");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        Fail.failIfNotNull(message, "Leia");
      }
    });
  }

  @Test void shouldPassIfValueIsNullAndExpectingNotNull() {
    Fail.failIfNotNull("", null);
  }

  @Test(dataProvider = "messageProvider")
  public void shouldFailIfValuesAreSame(final String message) {
    final Object o = new Object();
    String expectedMessage = concat(format(message), "given objects are same:<", o, ">");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        Fail.failIfSame(message, o, o);
      }
    });
  }

  @Test void shouldPassIfValuesAreNotSameAndExpectingSame() {
    Fail.failIfSame("", "Luke", "Anakin");
  }

  @Test(dataProvider = "messageProvider")
  public void failIfNotSameShouldFailIfNotSame(final String message) {
    String expectedMessage = concat(format(message), "expected same instance but found:<'Ben'> and:<'Han'>");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        Fail.failIfNotSame(message, "Ben", "Han");
      }
    });
  }

  @Test void shouldPassIfValuesAreSameAndExpectingNotSame() {
    Object o = new Object();
    Fail.failIfNotSame("", o, o);
  }

  @Test public void shouldIncludeMessageWhenFailing() {
    final String message = "Failed :(";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        Fail.fail(message);
      }
    });
  }

  @DataProvider(name = "messageProvider")
  public Object[][] messageProvider() {
    return new Object[][] {
        { "a message" },
        { "" },
        { null }
    };
  }

  private String format(String s) {
    if (isEmpty(s)) return "";
    return concat("[", s, "] ");
  }
}
