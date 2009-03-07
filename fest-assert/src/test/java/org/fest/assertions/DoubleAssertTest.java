package org.fest.assertions;

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.assertions.DoubleAssert.delta;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Test for <code>{@link DoubleAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class DoubleAssertTest {

  @Test public void shouldSetTextDescription() {
    DoubleAssert assertion = new DoubleAssert(8.0);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    DoubleAssert assertion = new DoubleAssert(8.0);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    DoubleAssert assertion = new DoubleAssert(8.0);
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    DoubleAssert assertion = new DoubleAssert(8.0);
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new DoubleAssert(8.68).isEqualTo(8.680);
  }

  @Test public void shouldFailIfNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<-0.0> but was:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).isEqualTo(-0.0);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<-0.0> but was:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).as("A Test").isEqualTo(-0.0);
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new DoubleAssert(8.88).isNotEqualTo(8.68);
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<8.88> should not be equal to:<8.88>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.88).isNotEqualTo(8.88);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<8.88> should not be equal to:<8.88>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.88).as("A Test").isNotEqualTo(8.88);
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpected() {
    new DoubleAssert(0.00).isGreaterThan(-0.00);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8.68> should be greater than:<8.88>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.68).isGreaterThan(8.88);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<8.68> should be greater than:<8.88>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.68).as("A Test").isGreaterThan(8.88);
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8.68> should be greater than:<8.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.68).isGreaterThan(8.68);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<8.68> should be greater than:<8.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.68).as("A Test").isGreaterThan(8.68);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpected() {
    new DoubleAssert(-0.0).isLessThan(0.0);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<6.68> should be less than:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).isLessThan(6.68);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<6.68> should be less than:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).as("A Test").isLessThan(6.68);
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<6.88> should be less than:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.88).isLessThan(6.68);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<6.88> should be less than:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.88).as("A Test").isLessThan(6.68);
      }
    });
  }

  @Test public void shouldPassIfActualIsPositive() {
    new DoubleAssert(6.68).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanZeroAndExpectingPositive() {
    expectAssertionError("actual value:<-6.68> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(-6.68).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriiptionIfActualIsLessThanZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<-6.68> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(-6.68).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegative() {
    new DoubleAssert(-6.68).isNegative();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("actual value:<6.68> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<6.68> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsNaN() {
    new DoubleAssert(Double.NaN).isNaN();
  }

  @Test public void shouldFailIfActualIsNotNaNAndExpectingNaN() {
    expectAssertionError("expected:<NaN> but was:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).isNaN();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNaNAndExpectingNaN() {
    expectAssertionError("[A Test] expected:<NaN> but was:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).as("A Test").isNaN();
      }
    });
  }

  @Test public void shouldPassIfActualIsEqaulToExpectedUsingZeroAsDelta() {
    new DoubleAssert(8.0).isEqualTo(8.0, delta(0.0));
  }

  @Test public void shouldPassIfActualIsEqualToExpectedUsingDelta() {
    new DoubleAssert(8.688).isEqualTo(8.68, delta(0.009));
  }

  @Test public void shouldFailIfValuesNotEqualUsingDeltaAndExpectingEqual() {
    expectAssertionError("expected:<8.888> but was:<8.688> using delta:<0.0090>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.688).isEqualTo(8.888, delta(0.009));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesNotEqualUsingDeltaAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<8.888> but was:<8.688> using delta:<0.0090>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.688).as("A Test").isEqualTo(8.888, delta(0.009));
      }
    });
  }

  @Test public void shouldPassIfActualIsZero() {
    new DoubleAssert(0).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("expected:<0.0> but was:<9.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(9).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("[A Test] expected:<0.0> but was:<9.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(9).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpected() {
    new DoubleAssert(8.8).isGreaterThanOrEqualTo(8.8).isGreaterThanOrEqualTo(6.6);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqual() {
    expectAssertionError("actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.6).isGreaterThanOrEqualTo(8.8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqual() {
    expectAssertionError("[A Test] actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.6).as("A Test").isGreaterThanOrEqualTo(8.8);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpected() {
    new DoubleAssert(6.6).isLessThanOrEqualTo(6.6).isLessThanOrEqualTo(8.8);
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.8).isLessThanOrEqualTo(6.6);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.8).as("A Test").isLessThanOrEqualTo(6.6);
      }
    });
  }
}
