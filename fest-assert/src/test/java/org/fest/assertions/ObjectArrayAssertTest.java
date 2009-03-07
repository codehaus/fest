/*
 * Created on Mar 1, 2007
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

import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Arrays.array;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link ObjectArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssertTest {

  private static final Object[] NULL_ARRAY = null;
  private static final Object[] EMPTY_ARRAY = new Object[0];

  @Test public void shouldSetTextDescription() {
    ObjectArrayAssert assertion = new ObjectArrayAssert("Anakin");
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    ObjectArrayAssert assertion = new ObjectArrayAssert("Anakin");
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    ObjectArrayAssert assertion = new ObjectArrayAssert("Anakin");
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ObjectArrayAssert assertion = new ObjectArrayAssert("Anakin");
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyArray extends Condition<Object[]> {
    @Override public boolean matches(Object[] array) {
      return array != null && array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ObjectArrayAssert(EMPTY_ARRAY).satisfies(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<['Han']> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Han").satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<['Han']> should satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Han").as("A Test").satisfies(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<['Han']> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Han").satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<['Han']> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Han").as("A Test").satisfies(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldPassIfConditionNotSatisfied() {
    new ObjectArrayAssert("Leia").doesNotSatisfy(new EmptyArray());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<EmptyArray>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").doesNotSatisfy(new EmptyArray().as("Empty"));
      }
    });
  }


  @Test public void shouldPassIfGivenObjectIsInArray() {
    new ObjectArrayAssert("Luke", "Leia").contains("Luke");
  }

  @Test public void shouldPassIfGivenObjectsAreInArray() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").contains("Luke", "Leia");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        Object[] array = null;
        new ObjectArrayAssert(array).as("A Test").contains("Luke");
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").contains("Luke");
      }
    });
  }

  @Test public void shouldPassIfGivenObjectIsNotInArray() {
    new ObjectArrayAssert("Luke", "Leia").excludes("Anakin");
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").excludes("Han", "Yoda");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).excludes("Han");
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").excludes("Han");
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<['Luke']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke").excludes("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<['Luke']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke").as("A Test").excludes("Luke");
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new ObjectArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new ObjectArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new ObjectArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new ObjectArrayAssert("Luke", "Leia").isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Luke", "Leia"));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Anakin"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").as("A Test").isEqualTo(array("Anakin"));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Yoda"));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Luke", "Leia"));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia").as("A Test").isNotEqualTo(array("Luke", "Leia"));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new ObjectArrayAssert("Luke").containsOnly("Luke");
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<['Anakin']> in array:<['Luke', 'Leia', 'Anakin']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia", "Anakin").containsOnly("Luke", "Leia");
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<['Anakin']> in array:<['Luke', 'Leia', 'Anakin']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia", "Anakin").as("A Test").containsOnly("Luke", "Leia");
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<['Luke', 'Leia']> does not contain element(s):<['Anakin']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia", "Anakin");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<['Luke', 'Leia']> does not contain element(s):<['Anakin']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia").as("A Test").containsOnly("Luke", "Leia", "Anakin");
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    Object[] array = array("Gandalf", "Frodo", "Sam");
    new ObjectArrayAssert(array).hasSize(3);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<['Gandalf']>").on(new CodeToTest() {
      public void run() {
        Object[] array = array("Gandalf");
        new ObjectArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<['Gandalf']>").on(new CodeToTest() {
      public void run() {
        Object[] array = array("Gandalf");
        new ObjectArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    Object[] array = array("Leia");
    new ObjectArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Leia").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Leia").as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        Object[] array = array("Leia");
        new ObjectArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        Object[] array = array("Leia");
        new ObjectArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new ObjectArrayAssert("Leia").isNotSameAs(EMPTY_ARRAY);
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia");
  }

  @Test public void shouldPassIfElementsBelongToGivenType() {
    new ObjectArrayAssert(2, 4, 5).hasAllElementsOfType(Number.class);
  }

  @Test public void shouldPassIfElementsBelongToSubtypeOfGivenType() {
    new ObjectArrayAssert(2, 4, 5).hasAllElementsOfType(Integer.class);
  }

  @Test public void shouldFailIfOneOrMoreElementsDoNotBelongToGivenType() {
    expectAssertionError("not all elements in array:<[2.0, 4, 5]> belong to the type:<java.lang.Double>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert(2d, 4, 5).hasAllElementsOfType(Double.class);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfOneOrMoreElementsDoNotBelongToGivenType() {
    expectAssertionError("[A Test] not all elements in array:<[2.0, 4, 5]> belong to the type:<java.lang.Double>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert(2d, 4, 5).as("A Test").hasAllElementsOfType(Double.class);
          }
        });
  }

  @Test public void shouldPassIfAllElementsBelongToGivenType() {
    new ObjectArrayAssert(2, 4, 5).hasAtLeastOneElementOfType(Integer.class);
  }

  @Test public void shouldPassIfOneElementBelongToSubtypeOfGivenType() {
    new ObjectArrayAssert(2, 4.0f, 5).hasAtLeastOneElementOfType(Integer.class);
  }

  @Test public void shouldFailIfElementsDoNotBelongToGivenType() {
    expectAssertionError("array:<[2, 4, 5]> does not have any elements of type:<java.lang.Double>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert(2, 4, 5).hasAtLeastOneElementOfType(Double.class);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfElementsDoNotBelongToGivenType() {
    expectAssertionError("[A Test] array:<[2, 4, 5]> does not have any elements of type:<java.lang.Double>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert(2, 4, 5).as("A Test").hasAtLeastOneElementOfType(Double.class);
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).doesNotHaveDuplicates();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").doesNotHaveDuplicates();
      }
    });
  }

  @Test public void shouldFailIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("array:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Yoda", "Luke").doesNotHaveDuplicates();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("[A Test] array:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Yoda", "Luke").as("A Test").doesNotHaveDuplicates();
          }
        });
  }

  @Test public void shouldPassIfActualContainsNoDuplicates() {
    new ObjectArrayAssert("Luke", "Yoda").doesNotHaveDuplicates();
  }

  @Test public void shouldPassIfEmptyActualContainsNoDuplicates() {
    new ObjectArrayAssert(EMPTY_ARRAY).doesNotHaveDuplicates();
  }
}
