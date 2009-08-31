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
 * Tests for <code>{@link ShortArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ShortArrayAssertTest {

  private static final short[] NULL_ARRAY = null;
  private static final short[] EMPTY_ARRAY = new short[0];

  @Test public void shouldSetTextDescription() {
    ShortArrayAssert assertion = new ShortArrayAssert(asShort(459), asShort(23));
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    ShortArrayAssert assertion = new ShortArrayAssert(asShort(459), asShort(23));
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    ShortArrayAssert assertion = new ShortArrayAssert(asShort(459), asShort(23));
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ShortArrayAssert assertion = new ShortArrayAssert(asShort(459), asShort(23));
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyArray extends Condition<short[]> {
    @Override public boolean matches(short[] array) {
      return array != null && array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ShortArrayAssert(EMPTY_ARRAY).satisfies(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<[23]> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(23)).satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<[23]> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(23)).as("A Test").satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[23]> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(23)).satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[23]> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(23)).as("A Test").satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfConditionNotSatisfied() {
    new ShortArrayAssert(asShort(23)).doesNotSatisfy(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new ShortArrayAssert(asShort(459), asShort(23)).contains(asShort(459), asShort(23));
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(NULL_ARRAY).contains(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(NULL_ARRAY).as("A Test").contains(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).contains(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).as("A Test").contains(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new ShortArrayAssert(asShort(459), asShort(23)).excludes(asShort(90), asShort(82));
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ShortArrayAssert(NULL_ARRAY).excludes(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ShortArrayAssert(NULL_ARRAY).as("A Test").excludes(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[459, 23]> does not exclude element(s):<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).excludes(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[459, 23]> does not exclude element(s):<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").excludes(asShort(459), asShort(23));
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new ShortArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new ShortArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new ShortArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new ShortArrayAssert(asShort(459), asShort(23)).isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ShortArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ShortArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new ShortArrayAssert(asShort(459), asShort(23)).isEqualTo(array(asShort(459), asShort(23)));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[90, 82]> but was:<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).isEqualTo(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[90, 82]> but was:<[459, 23]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").isEqualTo(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new ShortArrayAssert(asShort(459), asShort(23)).isNotEqualTo(array(asShort(86)));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[459, 23]> should not be equal to:<[459, 23]>").on(
        new CodeToTest() {
          public void run() {
            new ShortArrayAssert(asShort(459), asShort(23)).isNotEqualTo(array(asShort(459), asShort(23)));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[459, 23]> should not be equal to:<[459, 23]>").on(
        new CodeToTest() {
          public void run() {
            new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").isNotEqualTo(array(asShort(459), asShort(23)));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new ShortArrayAssert(asShort(8)).containsOnly(asShort(8));
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[90, 82]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).containsOnly(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[90, 82]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ShortArrayAssert(NULL_ARRAY).containsOnly(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ShortArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[23]> in array:<[459, 23]>").on(
        new CodeToTest() {
          public void run() {
            new ShortArrayAssert(asShort(459), asShort(23)).containsOnly(array(asShort(459)));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[23]> in array:<[459, 23]>").on(
        new CodeToTest() {
          public void run() {
            new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").containsOnly(array(asShort(459)));
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[459, 23]> does not contain element(s):<[90, 82]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).containsOnly(array(asShort(90), asShort(82)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[459, 23]> does not contain element(s):<[90, 82]>").on(
        new CodeToTest() {
          public void run() {
            new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").containsOnly(array(asShort(90), asShort(82)));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    short[] array = array(asShort(86), asShort(59), asShort(98));
    new ShortArrayAssert(array).hasSize(3);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[459]>").on(new CodeToTest() {
      public void run() {
        short[] array = array(asShort(459));
        new ShortArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[459]>").on(new CodeToTest() {
      public void run() {
        short[] array = array(asShort(459));
        new ShortArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    short[] array = array(asShort(459), asShort(23));
    new ShortArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[459, 23]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[459, 23]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(459), asShort(23)).as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[459, 23]>").on(new CodeToTest() {
      public void run() {
        short[] array = array(asShort(459), asShort(23));
        new ShortArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[459, 23]>").on(new CodeToTest() {
      public void run() {
        short[] array = array(asShort(459), asShort(23));
        new ShortArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new ShortArrayAssert(asShort(459)).isNotSameAs(EMPTY_ARRAY);
  }

  @Test public void shouldPassIfArrayIsNullAndExpectingNullOrEmpty() {
    new ShortArrayAssert(null).isNullOrEmpty();
  }

  @Test public void shouldPassIfArrayIsEmptyAndExpectingNullOrEmpty() {
    new ShortArrayAssert(EMPTY_ARRAY).isNullOrEmpty();
  }

  @Test public void shouldFailIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("expecting a null or empty array, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(8)).isNullOrEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescritptionIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("[A Test] expecting a null or empty array, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(8)).as("A Test").isNullOrEmpty();
      }
    });
  }
  
  @Test public void shouldPassIfNullReferenceComparedToNullReference() {
    new ShortArrayAssert(null).isEqualTo(null);
  }


  private short asShort(int i) {
    return (short)i;
  }

  private short[] array(short... args) { return args; }
}
