/*
 * Created on Jul 30, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.assertions.extensionapi;

import org.testng.annotations.*;

import org.fest.test.*;

import static org.fest.test.ExpectedFailure.*;
import static org.fest.util.Strings.*;

/**
 * Unit test for {@link FailConditional}.
 * 
 * @author Ansgar Konermann
 * 
 */
@Test
public class FailConditionalTest {

  @Test(dataProvider = "messageProvider")
  public void shouldThrowErrorWithMessageIfValuesAreEqual(final String message) {
    String expectedMessage = concat(format(message), "actual value:<'Yoda'> should not be equal to:<'Yoda'>");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        FailConditional.failIfEqual(message, "Yoda", "Yoda");
      }
    });
  }

  @Test
  public void shouldNotThrowErrorIfValuesAreNotEqualAndExpectingEqual() {
    FailConditional.failIfEqual("", "Yoda", "Ben");
  }

  @Test(dataProvider = "messageProvider")
  public void shouldFailIfValuesAreNotEqual(final String message) {
    String expectedMessage = concat(format(message), "expected:<'Luke'> but was:<'Yoda'>");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        FailConditional.failIfNotEqual(message, "Yoda", "Luke");
      }
    });
  }

  @Test
  void shouldPassIfValuesAreEqualAndExpectingNotEqual() {
    FailConditional.failIfNotEqual("", "Yoda", "Yoda");
  }

  @Test(dataProvider = "messageProvider")
  public void failIfNullShouldFailIfNull(final String message) {
    String expectedMessage = concat(format(message), "expecting a non-null object, but it was null");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        FailConditional.failIfNull(message, null);
      }
    });
  }

  @Test
  void shouldPassIfValueIsNotNullAndExpectingNull() {
    FailConditional.failIfNull("", "Luke");
  }

  @Test(dataProvider = "messageProvider")
  public void shouldFailIfValueIsNotNull(final String message) {
    String expectedMessage = concat(format(message), "<'Leia'> should be null");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        FailConditional.failIfNotNull(message, "Leia");
      }
    });
  }

  @Test
  void shouldPassIfValueIsNullAndExpectingNotNull() {
    FailConditional.failIfNotNull("", null);
  }

  @Test(dataProvider = "messageProvider")
  public void shouldFailIfValuesAreSame(final String message) {
    final Object o = new Object();
    String expectedMessage = concat(format(message), "given objects are same:<", o, ">");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        FailConditional.failIfSame(message, o, o);
      }
    });
  }

  @Test
  void shouldPassIfValuesAreNotSameAndExpectingSame() {
    FailConditional.failIfSame("", "Luke", "Anakin");
  }

  @Test(dataProvider = "messageProvider")
  public void failIfNotSameShouldFailIfNotSame(final String message) {
    String expectedMessage = concat(format(message), "expected same instance but found:<'Ben'> and:<'Han'>");
    expectAssertionError(expectedMessage).on(new CodeToTest() {
      public void run() {
        FailConditional.failIfNotSame(message, "Ben", "Han");
      }
    });
  }

  @Test
  void shouldPassIfValuesAreSameAndExpectingNotSame() {
    Object o = new Object();
    FailConditional.failIfNotSame("", o, o);
  }

  @DataProvider(name = "messageProvider")
  public Object[][] messageProvider() {
    return new Object[][] { { "a message" }, { "" }, { null } };
  }

  private String format(String s) {
    if (isEmpty(s)) return "";
    return concat("[", s, "] ");
  }
}
