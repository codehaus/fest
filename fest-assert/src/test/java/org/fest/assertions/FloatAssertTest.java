/*
 * Created on Jun 18, 2007
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

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.assertions.FloatAssert.delta;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Test for <code>{@link FloatAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class FloatAssertTest {

  @Test public void shouldSetTextDescription() {
    FloatAssert assertion = new FloatAssert(8.0f);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    FloatAssert assertion = new FloatAssert(8.0f);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    FloatAssert assertion = new FloatAssert(8.0f);
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    FloatAssert assertion = new FloatAssert(8.0f);
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfValuesAreEqualUsingDelta() {
    new FloatAssert(6.6f).isEqualTo(6.6f, delta(0.0f));
  }

  @Test public void shouldPassIfActualIsEqualToExpectedUsingDelta() {
    new FloatAssert(8.688f).isEqualTo(8.68f, delta(0.009f));
  }

  @Test public void shouldFailIfValuesAreNotEqualUsingDeltaAndExpectingEqual() {
    expectAssertionError("expected:<6.8> but was:<6.6> using delta:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).isEqualTo(6.8f, delta(0.0f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualUsingDeltaAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<6.8> but was:<6.6> using delta:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).as("A Test").isEqualTo(6.8f, delta(0.0f));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new FloatAssert(0.0f).isNotEqualTo(-0.0f);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingNotEqual() {
    expectAssertionError("actual value:<0.0> should not be equal to:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isNotEqualTo(0.0f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<0.0> should not be equal to:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isNotEqualTo(0.0f);
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpected() {
    new FloatAssert(0.0f).isGreaterThan(-0.0f);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<-0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).isGreaterThan(0.0f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<-0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).as("A Test").isGreaterThan(0.0f);
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8.0> should be greater than:<8.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(8f).isGreaterThan(8f);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpected() {
    new FloatAssert(6.6f).isLessThan(6.8f);
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<0.0> should be less than:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isLessThan(-0.0f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<0.0> should be less than:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isLessThan(-0.0f);
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isLessThan(0.0f);
      }
    });
  }

  @Test public void shouldPassIfActualIsZero() {
    new FloatAssert(0.0f).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("expected:<0.0> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("[A Test] expected:<0.0> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualIsNaN() {
    new FloatAssert(Float.NaN).isNaN();
  }

  @Test public void shouldFailIfNotNaNAndExpectedNaN() {
    expectAssertionError("expected:<NaN> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).isNaN();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotNaNAndExpectedNaN() {
    expectAssertionError("[A Test] expected:<NaN> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).as("A Test").isNaN();
      }
    });
  }

  @Test public void shouldPassIfActualIsPositive() {
    new FloatAssert(6.6f).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectedPositive() {
    expectAssertionError("actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectedPositive() {
    expectAssertionError("[A Test] actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanZeroAndExpectedPositive() {
    expectAssertionError("actual value:<-6.6> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-6.6f).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanZeroAndExpectedPositive() {
    expectAssertionError("[A Test] actual value:<-6.6> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-6.6f).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegative() {
    new FloatAssert(-6.6f).isNegative();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("actual value:<6.6> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<6.6> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpected() {
    new FloatAssert(8.8f).isGreaterThanOrEqualTo(8.8f).isGreaterThanOrEqualTo(6.6f);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).isGreaterThanOrEqualTo(8.8f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).as("A Test").isGreaterThanOrEqualTo(8.8f);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpected() {
    new FloatAssert(6.6f).isLessThanOrEqualTo(6.6f).isLessThanOrEqualTo(8.8f);
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(8.8f).isLessThanOrEqualTo(6.6f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(8.8f).as("A Test").isLessThanOrEqualTo(6.6f);
      }
    });
  }
}
