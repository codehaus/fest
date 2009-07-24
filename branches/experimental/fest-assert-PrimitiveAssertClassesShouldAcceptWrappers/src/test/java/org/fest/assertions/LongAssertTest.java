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
 * Tests for <code>{@link LongAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class LongAssertTest {

  @Test public void shouldSetTextDescription() {
    LongAssert assertion = new LongAssert(8L);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    LongAssert assertion = new LongAssert(8L);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    LongAssert assertion = new LongAssert(8L);
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    LongAssert assertion = new LongAssert(8L);
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new LongAssert(8L).isEqualTo(8L);
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectedEqual() {
    expectAssertionError("expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6L).isEqualTo(8L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectedEqual() {
    expectAssertionError("[A Test] expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6L).as("A Test").isEqualTo(8L);
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new LongAssert(6L).isNotEqualTo(8L);
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).isNotEqualTo(8L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).as("A Test").isNotEqualTo(8L);
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpected() {
    new LongAssert(8L).isGreaterThan(6L);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).isGreaterThan(8L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<8> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).as("A Test").isGreaterThan(8L);
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<6> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6L).isGreaterThan(8L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<6> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6L).as("A Test").isGreaterThan(8L);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpected() {
    new LongAssert(6L).isLessThan(8L);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<8> should be less than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).isLessThan(8L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<8> should be less than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).as("A Test").isLessThan(8L);
      }
    });
  }

  @Test public void shouldFailIfGreaterThanAndExpectedLessThan() {
    expectAssertionError("actual value:<8> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).isLessThan(6L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGreaterThanAndExpectedLessThan() {
    expectAssertionError("[A Test] actual value:<8> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).as("A Test").isLessThan(6L);
      }
    });
  }

  @Test public void shouldPassIfActualIsPositive() {
    new LongAssert(6L).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(0L).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(0L).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualLessToZeroAndExpectingPositive() {
    expectAssertionError("actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(-8L).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualLessToZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(-8L).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegative() {
    new LongAssert(-6L).isNegative();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(0L).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(0L).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanZeroAndExpectedNegative() {
    expectAssertionError("actual value:<8> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectedNegative() {
    expectAssertionError("[A Test] actual value:<8> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsZero() {
    new LongAssert(0L).isZero();
  }

  @Test public void shouldFailIfNotZeroAndExpectedZero() {
    expectAssertionError("expected:<0> but was:<9>").on(new CodeToTest() {
      public void run() {
        new LongAssert(9L).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotZeroAndExpectedZero() {
    expectAssertionError("[A Test] expected:<0> but was:<9>").on(new CodeToTest() {
      public void run() {
        new LongAssert(9L).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualGreaterThanOrEqualToExpected() {
    new LongAssert(8L).isGreaterThanOrEqualTo(8L).isGreaterThanOrEqualTo(6L);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6L).isGreaterThanOrEqualTo(8L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6L).as("A Test").isGreaterThanOrEqualTo(8L);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpected() {
    new LongAssert(6L).isLessThanOrEqualTo(6L).isLessThanOrEqualTo(8L);
  }

  @Test public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    expectAssertionError("actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).isLessThanOrEqualTo(6L);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGreaterOrEqualToAndExpectedLessThan() {
    expectAssertionError("[A Test] actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8L).as("A Test").isLessThanOrEqualTo(6L);
      }
    });
  }
  
  @Test public void shouldPassIfNullReferenceComparedToNullReference() {
    new LongAssert(null).isEqualTo(null);
  }
  
}
