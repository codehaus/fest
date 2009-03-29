/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link DoubleArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class DoubleArrayAssertTest {

  private static final double[] NULL_ARRAY = null;
  private static final double[] EMPTY_ARRAY = new double[0];

  @Test public void shouldSetTextDescription() {
    DoubleArrayAssert assertion = new DoubleArrayAssert(55.03, 4345.91);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    DoubleArrayAssert assertion = new DoubleArrayAssert(55.03, 4345.91);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    DoubleArrayAssert assertion = new DoubleArrayAssert(55.03, 4345.91);
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    DoubleArrayAssert assertion = new DoubleArrayAssert(55.03, 4345.91);
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyArray extends Condition<double[]> {
    @Override public boolean matches(double[] array) {
      return array != null && array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new DoubleArrayAssert(EMPTY_ARRAY).satisfies(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<[6.88]> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(6.88).satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<[6.88]> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(6.88).as("A Test").satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[55.03]> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03).satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[55.03]> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03).as("A Test").satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfConditionNotSatisfied() {
    new DoubleArrayAssert(55.03).doesNotSatisfy(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new DoubleArrayAssert(55.03, 4345.91).contains(55.03, 4345.91);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(NULL_ARRAY).contains(55.03, 4345.91);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(NULL_ARRAY).as("A Test").contains(55.03, 4345.91);
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).contains(55.03, 4345.91);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).as("A Test").contains(55.03, 4345.91);
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new DoubleArrayAssert(55.03, 4345.91).excludes(5323.2);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new DoubleArrayAssert(NULL_ARRAY).excludes(55.03, 4345.91);
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new DoubleArrayAssert(NULL_ARRAY).as("A Test").excludes(55.03, 4345.91);
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[55.03, 4345.91]> does not exclude element(s):<[55.03]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).excludes(55.03);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[55.03, 4345.91]> does not exclude element(s):<[55.03]>").on(
        new CodeToTest() {
          public void run() {
            new DoubleArrayAssert(55.03, 4345.91).as("A Test").excludes(55.03);
          }
        });
  }

  @Test public void shouldPassIfActualIsNull() {
    new DoubleArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new DoubleArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new DoubleArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new DoubleArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new DoubleArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new DoubleArrayAssert(55.03, 4345.91).isEqualTo(array(55.03, 4345.91));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[5323.2]> but was:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).isEqualTo(array(5323.2));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[5323.2]> but was:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).as("A Test").isEqualTo(array(5323.2));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEqualTo(array(0.0));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[55.03, 4345.91]> should not be equal to:<[55.03, 4345.91]>").on(
        new CodeToTest() {
          public void run() {
            new DoubleArrayAssert(55.03, 4345.91).isNotEqualTo(array(55.03, 4345.91));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[55.03, 4345.91]> should not be equal to:<[55.03, 4345.91]>").on(
        new CodeToTest() {
          public void run() {
            new DoubleArrayAssert(55.03, 4345.91).as("A Test").isNotEqualTo(array(55.03, 4345.91));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new DoubleArrayAssert(98.6).containsOnly(98.6);
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[5323.2]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).containsOnly(array(5323.2));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[5323.2]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array(5323.2));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new DoubleArrayAssert(NULL_ARRAY).containsOnly(array(5323.2));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new DoubleArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array(5323.2));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[4345.91]> in array:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).containsOnly(array(55.03));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[4345.91]> in array:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).as("A Test").containsOnly(array(55.03));
      }
    });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[55.03, 4345.91]> does not contain element(s):<[5323.2]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).containsOnly(array(5323.2));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[55.03, 4345.91]> does not contain element(s):<[5323.2]>").on(
        new CodeToTest() {
          public void run() {
            new DoubleArrayAssert(55.03, 4345.91).as("A Test").containsOnly(array(5323.2));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    double[] array = array(55.03, 4345.91);
    new DoubleArrayAssert(array).hasSize(2);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[55.03]>").on(new CodeToTest() {
      public void run() {
        double[] array = array(55.03);
        new DoubleArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[55.03]>").on(new CodeToTest() {
      public void run() {
        double[] array = array(55.03);
        new DoubleArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    double[] array = array(55.03, 4345.91);
    new DoubleArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[55.03, 4345.91]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[55.03, 4345.91]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        double[] array = array(55.03, 4345.91);
        new DoubleArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        double[] array = array(55.03, 4345.91);
        new DoubleArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new DoubleArrayAssert(55.03).isNotSameAs(EMPTY_ARRAY);
  }

  @Test public void shouldPassIfArrayIsNullAndExpectingNullOrEmpty() {
    new DoubleArrayAssert(null).isNullOrEmpty();
  }

  @Test public void shouldPassIfArrayIsEmptyAndExpectingNullOrEmpty() {
    new DoubleArrayAssert(EMPTY_ARRAY).isNullOrEmpty();
  }

  @Test public void shouldFailIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("expecting a null or empty array, but was:<[66.88]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(66.88).isNullOrEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescritptionIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("[A Test] expecting a null or empty array, but was:<[66.88]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(66.88).as("A Test").isNullOrEmpty();
      }
    });
  }

  private double[] array(double... args) {
    return args;
  }
}
