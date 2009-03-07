/*
 * Created on Mar 19, 2007
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

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link BooleanAssert}</code>.
 *
 * @author Alex Ruiz
 * @author David DIDIER
 */
public class BooleanAssertTest {

  @Test public void shouldSetTextDescription() {
    BooleanAssert assertion = new BooleanAssert(true);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    BooleanAssert assertion = new BooleanAssert(true);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    BooleanAssert assertion = new BooleanAssert(true);
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    BooleanAssert assertion = new BooleanAssert(true);
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfActualValueIsTrue() {
    new BooleanAssert(true).isTrue();
  }

  @Test public void shouldFailIfActualValueIsTrueAndExpectingFalse() {
    expectAssertionError("expected:<false> but was:<true>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(true).isFalse();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsTrueAndExpectingFalse() {
    expectAssertionError("[A Test] expected:<false> but was:<true>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(true).as("A Test").isFalse();
      }
    });
  }

  @Test public void shouldPassIfActualValueIsFalse() {
    new BooleanAssert(false).isFalse();
  }

  @Test public void shouldFailIfActualValueIsFalseAndExpectingTrue() {
    expectAssertionError("expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).isTrue();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsFalseAndExpectingTrue() {
    expectAssertionError("[A Test] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).as("A Test").isTrue();
      }
    });
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).isEqualTo(true);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).as("A Test").isEqualTo(true);
      }
    });
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectedToBeNotEqual() {
    expectAssertionError("actual value:<false> should not be equal to:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).isNotEqualTo(false);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectedToBeNotEqual() {
    expectAssertionError("[A Test] actual value:<false> should not be equal to:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).as("A Test").isNotEqualTo(false);
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new BooleanAssert(false).isNotEqualTo(true);
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new BooleanAssert(false).isEqualTo(false);
  }
}
