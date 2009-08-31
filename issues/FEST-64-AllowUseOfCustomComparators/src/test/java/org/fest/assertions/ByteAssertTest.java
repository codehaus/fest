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

import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link ByteAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ByteAssertTest {

  @Test public void shouldSetTextDescription() {
    ByteAssert assertion = new ByteAssert(asByte(8));
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    ByteAssert assertion = new ByteAssert(asByte(8));
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    ByteAssert assertion = new ByteAssert(asByte(8));
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ByteAssert assertion = new ByteAssert(asByte(8));
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new ByteAssert(asByte(6)).isEqualTo(asByte(6));
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectedEqual() {
    expectAssertionError("expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isEqualTo(asByte(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectedEqual() {
    expectAssertionError("[A Test] expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isEqualTo(asByte(8));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new ByteAssert(asByte(6)).isNotEqualTo(asByte(8));
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<6> should not be equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isNotEqualTo(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<6> should not be equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isNotEqualTo(asByte(6));
      }
    });
  }

  @Test public void shouldPassIfActualIsZero() {
    new ByteAssert(asByte(0)).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("expected:<0> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("[A Test] expected:<0> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpected() {
    new ByteAssert(asByte(6)).isGreaterThan(asByte(2));
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<6> should be greater than:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isGreaterThan(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<6> should be greater than:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isGreaterThan(asByte(6));
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<6> should be greater than:<10>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isGreaterThan(asByte(10));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<6> should be greater than:<10>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isGreaterThan(asByte(10));
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpected() {
    new ByteAssert(asByte(2)).isLessThan(asByte(6));
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<6> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isLessThan(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<6> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isLessThan(asByte(6));
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<10> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(10)).isLessThan(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<10> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(10)).as("A Test").isLessThan(asByte(6));
      }
    });
  }

  @Test public void shouldPassIfActualIsPositive() {
    new ByteAssert(asByte(6)).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(0)).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(0)).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanZeroAndExpectingPositive() {
    expectAssertionError("actual value:<-2> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(-2)).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<-2> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(-2)).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegative() {
    new ByteAssert(asByte(-2)).isNegative();
  }

  @Test public void shoudlFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(0)).isNegative();
      }
    });
  }

  @Test public void shoudlFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(0)).as("A Test").isNegative();
      }
    });
  }

  @Test public void shoudlFailIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("actual value:<6> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isNegative();
      }
    });
  }

  @Test public void shoudlFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<6> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpected() {
    new ByteAssert(asByte(8)).isGreaterThanOrEqualTo(asByte(8)).isGreaterThanOrEqualTo(asByte(6));
  }

  @Test public void shouldFailIfActualValueIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isGreaterThanOrEqualTo(asByte(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isGreaterThanOrEqualTo(asByte(8));
      }
    });
  }

  @Test public void shouldPassIfActualValueIsLessThanOrEqualToExpected() {
    new ByteAssert(asByte(6)).isLessThanOrEqualTo(asByte(8)).isLessThanOrEqualTo(asByte(6));
  }

  @Test public void shouldFailIfActualValueIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(8)).isLessThanOrEqualTo(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(8)).as("A Test").isLessThanOrEqualTo(asByte(6));
      }
    });
  }
  
  @Test public void shouldPassIfNullReferenceComparedToNullReference() {
    new ByteAssert(null).isEqualTo(null);
  }
  

  private static byte asByte(int i) {
    return (byte)i;
  }
}
