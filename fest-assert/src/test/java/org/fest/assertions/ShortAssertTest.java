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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link ShortAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ShortAssertTest {

  @Test public void shouldSetTextDescription() {
    ShortAssert assertion = new ShortAssert(asShort(8));
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    ShortAssert assertion = new ShortAssert(asShort(8));
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    ShortAssert assertion = new ShortAssert(asShort(8));
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ShortAssert assertion = new ShortAssert(asShort(8));
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new ShortAssert(asShort(8)).isEqualTo(asShort(8));
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectedEqual() {
    expectAssertionError("expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).isEqualTo(asShort(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectedEqual() {
    expectAssertionError("[A Test] expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).as("A Test").isEqualTo(asShort(8));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new ShortAssert(asShort(6)).isNotEqualTo(asShort(8));
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).isNotEqualTo(asShort(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).as("A Test").isNotEqualTo(asShort(8));
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpected() {
    new ShortAssert(asShort(8)).isGreaterThan(asShort(6));
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).isGreaterThan(asShort(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<8> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).as("A Test").isGreaterThan(asShort(8));
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<6> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).isGreaterThan(asShort(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<6> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).as("A Test").isGreaterThan(asShort(8));
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpected() {
    new ShortAssert(asShort(6)).isLessThan(asShort(8));
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<8> should be less than:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).isLessThan(asShort(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<8> should be less than:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).as("A Test").isLessThan(asShort(8));
      }
    });
  }

  @Test public void shouldFailIfGreaterThanAndExpectedLessThan() {
    expectAssertionError("actual value:<8> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).isLessThan(asShort(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGreaterThanAndExpectedLessThan() {
    expectAssertionError("[A Test] actual value:<8> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).as("A Test").isLessThan(asShort(6));
      }
    });
  }

  @Test public void shouldPassIfActualIsPositive() {
    new ShortAssert(asShort(6)).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(0)).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(0)).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualLessToZeroAndExpectingPositive() {
    expectAssertionError("actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(-8)).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualLessToZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(-8)).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegative() {
    new ShortAssert(asShort(-6)).isNegative();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(0)).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(0)).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanZeroAndExpectedNegative() {
    expectAssertionError("actual value:<8> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectedNegative() {
    expectAssertionError("[A Test] actual value:<8> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsZero() {
    new ShortAssert(asShort(0)).isZero();
  }

  @Test public void shouldFailIfNotZeroAndExpectedZero() {
    expectAssertionError("expected:<0> but was:<9>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(9)).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotZeroAndExpectedZero() {
    expectAssertionError("[A Test] expected:<0> but was:<9>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(9)).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualGreaterThanOrEqualToExpected() {
    new ShortAssert(asShort(8)).isGreaterThanOrEqualTo(asShort(8)).isGreaterThanOrEqualTo(asShort(6));
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).isGreaterThanOrEqualTo(asShort(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).as("A Test").isGreaterThanOrEqualTo(asShort(8));
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpected() {
    new ShortAssert(asShort(6)).isLessThanOrEqualTo(asShort(6)).isLessThanOrEqualTo(asShort(8));
  }

  @Test public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    expectAssertionError("actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).isLessThanOrEqualTo(asShort(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGreaterOrEqualToAndExpectedLessThan() {
    expectAssertionError("[A Test] actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(8)).as("A Test").isLessThanOrEqualTo(asShort(6));
      }
    });
  }


  private static short asShort(int i) {
    return (short)i;
  }
}
