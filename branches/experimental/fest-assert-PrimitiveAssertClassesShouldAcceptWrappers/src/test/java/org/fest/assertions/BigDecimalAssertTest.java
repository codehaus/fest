/*
 * Created on Jan 10, 2007
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

import static org.fest.assertions.CommonFailures.*;
import org.fest.test.CodeToTest;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;

/**
 * Tests for <code>{@link BigDecimalAssert}</code>.
 *
 * @author David DIDIER
 * @author Ted M. Young
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BigDecimalAssertTest {

  @Test public void shouldSetTextDescription() {
    BigDecimalAssert assertion = new BigDecimalAssert(eight());
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    BigDecimalAssert assertion = new BigDecimalAssert(eight());
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    BigDecimalAssert assertion = new BigDecimalAssert(eight());
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    BigDecimalAssert assertion = new BigDecimalAssert(eight());
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNull extends Condition<BigDecimal> {
    @Override public boolean matches(BigDecimal o) {
      return o != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new BigDecimalAssert(eight()).satisfies(new NotNull());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<null> should satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).satisfies(new NotNull());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").satisfies(new NotNull());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<null> should satisfy condition:<non-null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).satisfies(new NotNull().as("non-null"));
      }
    });
  }


  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition:<non-null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").satisfies(new NotNull().as("non-null"));
      }
    });
  }

  //
  @Test public void shouldPassIfConditionNotSatisfied() {
    new BigDecimalAssert(null).doesNotSatisfy(new NotNull());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<8.0> should not satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).doesNotSatisfy(new NotNull());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    expectAssertionError("[A Test] actual value:<8.0> should not satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").doesNotSatisfy(new NotNull());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<8.0> should not satisfy condition:<non-null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).doesNotSatisfy(new NotNull().as("non-null"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<8.0> should not satisfy condition:<non-null>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").doesNotSatisfy(new NotNull().as("non-null"));
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new BigDecimalAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<8.0> should be null").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <8.0> should be null").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new BigDecimalAssert(eight()).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfValuesAreSame() {
    BigDecimal eight = eight();
    new BigDecimalAssert(eight).isSameAs(eight);
  }

  @Test public void shouldFailIfValuesAreNotSameAndExpectingSame() {
    expectAssertionError("expected same instance but found:<8.0> and:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isSameAs(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotSameAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<8.0> and:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isSameAs(eight());
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotSame() {
    new BigDecimalAssert(eight()).isNotSameAs(eight());
  }

  @Test public void shouldFailIfValuesAreSameAndExpectingNotSame() {
    expectAssertionError("given objects are same:<8.0>").on(new CodeToTest() {
      public void run() {
        BigDecimal eight = eight();
        new BigDecimalAssert(eight).isNotSameAs(eight);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreSameAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<8.0>").on(new CodeToTest() {
      public void run() {
        BigDecimal eight = eight();
        new BigDecimalAssert(eight).as("A Test").isNotSameAs(eight);
      }
    });
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new BigDecimalAssert(eight()).isEqualTo(eight());
  }

  @Test public void shouldFailIfValuesAreNotEqual() {
    expectAssertionError("expected:<8.00> but was:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isEqualTo(new BigDecimal("8.00"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqual() {
    expectAssertionError("[A Test] expected:<8.00> but was:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isEqualTo(new BigDecimal("8.00"));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqual() {
    new BigDecimalAssert(eight()).isNotEqualTo(nine());
  }

  @Test public void shouldFailIfValuesAreEqual() {
    expectAssertionError("actual value:<8.0> should not be equal to:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isNotEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqual() {
    expectAssertionError("[A Test] actual value:<8.0> should not be equal to:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isNotEqualTo(eight());
      }
    });
  }

  @Test public void shouldPassIfValuesAreEqualByComparison() {
    new BigDecimalAssert(eight()).isEqualByComparingTo(eight());
  }

  @Test public void shouldPassIfValuesAreNotEqualByComparison() {
    new BigDecimalAssert(eight()).isNotEqualByComparingTo(nine());
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfEqualByComparison() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isEqualByComparingTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfEqualByComparison() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isEqualByComparingTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfExpectedIsNullWhenCheckingIfNotEqualByComparison() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isNotEqualByComparingTo(null);
      }
    });
  }

   @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfNotEqualByComparison() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isNotEqualByComparingTo(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEqualToExpectedAndExpectingEqualByComparison() {
    expectAssertionError("expected:<9.0> but was:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isEqualByComparingTo(nine());
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingNotEqualByComparison() {
    expectAssertionError("actual value:<8.0> should not be equal to:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isNotEqualByComparingTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingNotEqualByComparison() {
    expectAssertionError("[Did not want eight] actual value:<8.0> should not be equal to:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight())
                .as("Did not want eight")
                .isNotEqualByComparingTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEqualToExpectedAndExpectingEqualByComparison() {
    expectAssertionError("[A Test] expected:<9.0> but was:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isEqualByComparingTo(nine());
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanGivenValue() {
    new BigDecimalAssert(eight()).isLessThan(nine());
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfLessThan() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isLessThan(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfLessThan() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isLessThan(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualToValueAndExpectingLessThan() {
    expectAssertionError("actual value:<8.0> should be less than:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).isLessThan(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToValueAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<8.0> should be less than:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).as("A Test").isLessThan(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanValueAndExpectingLessThan() {
    expectAssertionError("actual value:<9.0> should be less than:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(nine()).isLessThan(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanValueAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<9.0> should be less than:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(nine()).as("A Test").isLessThan(eight());
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanGivenValue() {
    new BigDecimalAssert(nine()).isGreaterThan(eight());
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfGreaterThan() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isGreaterThan(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfGreaterThan() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isGreaterThan(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualToValueAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8.0> should be greater than:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).isGreaterThan(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToValueAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<8.0> should be greater than:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).as("A Test").isGreaterThan(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanValueAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8.0> should be greater than:<9.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).isGreaterThan(nine());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanValueAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<8.0> should be greater than:<9.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).as("A Test").isGreaterThan(nine());
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToGivenValue() {
    new BigDecimalAssert(eight()).isLessThanOrEqualTo(eight()).isLessThanOrEqualTo(nine());
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfLessThanOrEqualTo() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isLessThanOrEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfLessThanOrEqualTo() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isLessThanOrEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanValueAndExpectingLessThanOrEqualTo() {
    expectAssertionError("actual value:<9.0> should be less than or equal to:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(nine()).isLessThanOrEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanValueAndExpectingLessThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<9.0> should be less than or equal to:<8.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(nine()).as("A Test").isLessThanOrEqualTo(eight());
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToGivenValue() {
    new BigDecimalAssert(nine()).isGreaterThanOrEqualTo(eight()).isGreaterThanOrEqualTo(nine());
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfGreaterThanOrEqualTo() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isGreaterThanOrEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfGreaterThanOrEqualTo() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isGreaterThanOrEqualTo(eight());
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanValueAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<8.0> should be greater than or equal to:<9.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).isGreaterThanOrEqualTo(nine());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanValueAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<8.0> should be greater than or equal to:<9.0>").on(new CodeToTest() {
      public void run() throws Throwable {
        new BigDecimalAssert(eight()).as("A Test").isGreaterThanOrEqualTo(nine());
      }
    });
  }

  @Test public void shouldPassIfActualIsPositive() {
    new BigDecimalAssert(eight()).isPositive();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsPositive() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsPositive() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualIsNegativeAndExpectingPositive() {
    expectAssertionError("actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(negativeEight()).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNegativeAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(negativeEight()).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(ZERO).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingPositive() {
    expectAssertionError("[A Test] actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(ZERO).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegative() {
    new BigDecimalAssert(negativeEight()).isNegative();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsNegative() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsNegative() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsPositiveAndExpectingNegative() {
    expectAssertionError("actual value:<8.0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsPositiveAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<8.0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(ZERO).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(ZERO).as("A Test").isNegative();
      }
    });
  }

  ///

  @Test public void shouldPassIfActualIsZero() {
    new BigDecimalAssert(ZERO).isZero();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIsZero() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfIsZero() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(null).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("expected:<0> but was:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("[A Test] expected:<0> but was:<8.0>").on(new CodeToTest() {
      public void run() {
        new BigDecimalAssert(eight()).as("A Test").isZero();
      }
    });
  }


  private BigDecimal eight() {
    return new BigDecimal("8.0");
  }

  private BigDecimal nine() {
    return new BigDecimal("9.0");
  }

  private BigDecimal negativeEight() {
    return new BigDecimal(-8);
  }
}
