/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link FloatArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssertTest {

  private static final float[] NULL_ARRAY = null;
  private static final float[] EMPTY_ARRAY = new float[0];

  @Test public void shouldSetTextDescription() {
    FloatArrayAssert assertion = new FloatArrayAssert(36.9f);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    FloatArrayAssert assertion = new FloatArrayAssert(36.9f);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    FloatArrayAssert assertion = new FloatArrayAssert(36.9f);
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    FloatArrayAssert assertion = new FloatArrayAssert(36.9f);
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyArray extends Condition<float[]> {
    @Override public boolean matches(float[] array) {
      return array != null && array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new FloatArrayAssert(EMPTY_ARRAY).satisfies(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<[36.9]> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<[36.9]> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[36.9]> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[36.9]> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfConditionNotSatisfied() {
    new FloatArrayAssert(36.9f).doesNotSatisfy(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new FloatArrayAssert(36.9f).contains(36.9f);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).contains(36.9f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").contains(36.9f);
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).contains(36.9f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").contains(36.9f);
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new FloatArrayAssert(36.9f).excludes(88.43f);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).excludes(36.9f);
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").excludes(36.9f);
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[36.9]> does not exclude element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).excludes(36.9f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[36.9]> does not exclude element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").excludes(36.9f);
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new FloatArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new FloatArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new FloatArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new FloatArrayAssert(36.9f).isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new FloatArrayAssert(36.9f).isEqualTo(array(36.9f));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[88.43]> but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).isEqualTo(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[88.43]> but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").isEqualTo(array(88.43f));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new FloatArrayAssert(36.9f).isNotEqualTo(array(886.8f));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[36.9]> should not be equal to:<[36.9]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f).isNotEqualTo(array(36.9f));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[36.9]> should not be equal to:<[36.9]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f).as("A Test").isNotEqualTo(array(36.9f));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new FloatArrayAssert(98.6f).containsOnly(98.6f);
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[88.43]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[88.43]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[86.2]> in array:<[36.9, 86.2]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f, 86.2f).containsOnly(array(36.9f));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[86.2]> in array:<[36.9, 86.2]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f, 86.2f).as("A Test").containsOnly(array(36.9f));
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[36.9]> does not contain element(s):<[88.43]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[36.9]> does not contain element(s):<[88.43]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f).as("A Test").containsOnly(array(88.43f));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    float[] array = array(36.9f, 89.36f);
    new FloatArrayAssert(array).hasSize(2);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    float[] array = array(36.9f);
    new FloatArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[36.9]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[36.9]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new FloatArrayAssert(36.9f).isNotSameAs(EMPTY_ARRAY);
  }

  @Test public void shouldPassIfArrayIsNullAndExpectingNullOrEmpty() {
    new FloatArrayAssert(null).isNullOrEmpty();
  }

  @Test public void shouldPassIfArrayIsEmptyAndExpectingNullOrEmpty() {
    new FloatArrayAssert(EMPTY_ARRAY).isNullOrEmpty();
  }

  @Test public void shouldFailIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("expecting a null or empty array, but was:<[66.8]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(66.8f).isNullOrEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescritptionIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("[A Test] expecting a null or empty array, but was:<[66.8]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(66.8f).as("A Test").isNullOrEmpty();
      }
    });
  }

  private float[] array(float... args) { return args; }
}
