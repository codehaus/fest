/*
 * Created on Jan 10, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.CommonFailures.expectIllegalArgumentExceptionIfConditionIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.*;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link CollectionAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class CollectionAssertTest {

  private static final ArrayList<String> EMPTY_COLLECTION = new ArrayList<String>();

  public void shouldSetTextDescription() {
    CollectionAssert assertion = new CollectionAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  public void shouldSetTextDescriptionSafelyForGroovy() {
    CollectionAssert assertion = new CollectionAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  public void shouldSetDescription() {
    CollectionAssert assertion = new CollectionAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  public void shouldSetDescriptionSafelyForGroovy() {
    CollectionAssert assertion = new CollectionAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyCollection extends Condition<Collection<?>> {
    @Override public boolean matches(Collection<?> c) {
      return c != null && c.isEmpty();
    }
  }

  public void shouldPassIfConditionSatisfied() {
    new CollectionAssert(EMPTY_COLLECTION).satisfies(new EmptyCollection());
  }

  public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).satisfies(null);
      }
    });
  }

  public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<['Han']> should satisfy condition:<EmptyCollection>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Han")).satisfies(new EmptyCollection());
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    String message = "[A Test] actual value:<['Han']> should satisfy condition:<EmptyCollection>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Han")).as("A Test").satisfies(new EmptyCollection());
      }
    });
  }

  public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<['Han']> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Han")).satisfies(new EmptyCollection().as("Empty"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<['Han']> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Han")).as("A Test").satisfies(new EmptyCollection().as("Empty"));
      }
    });
  }

  public void shouldPassIfConditionNotSatisfied() {
    new CollectionAssert(list("Leia")).doesNotSatisfy(new EmptyCollection());
  }

  public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).doesNotSatisfy(null);
      }
    });
  }

  public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyCollection>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).doesNotSatisfy(new EmptyCollection());
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionSatisfied() {
    String message = "[A Test] actual value:<[]> should not satisfy condition:<EmptyCollection>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").doesNotSatisfy(new EmptyCollection());
      }
    });
  }

  public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).doesNotSatisfy(new EmptyCollection().as("Empty"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").doesNotSatisfy(new EmptyCollection().as("Empty"));
      }
    });
  }

  public void shouldPassIfActualIsNotNull() {
    new CollectionAssert(EMPTY_COLLECTION).isNotNull();
  }

  public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isNotNull();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").isNotNull();
      }
    });
  }

  public void shouldPassIfActualContainsValue() {
    new CollectionAssert(list("Luke", "Leia")).contains("Luke");
  }

  public void shouldPassIfActualContainsValues() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).contains("Luke", "Leia");
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualContainsObjects() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).contains("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualContainsObjects() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").contains("Luke");
      }
    });
  }

  public void shouldFailIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("the given collection of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).contains(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("[A Test] the given collection of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").contains(objects);
      }
    });
  }

  public void shouldFailIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("collection:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).contains("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("[A Test] collection:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").contains("Luke");
      }
    });
  }

  public void shouldPassIfActualExcludesValue() {
    new CollectionAssert(list("Luke", "Leia")).excludes("Anakin");
  }

  public void shouldPassIfActualExcludesGivenValues() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).excludes("Han", "Yoda");
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).excludes("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").excludes("Luke");
      }
    });
  }

  public void shouldFailIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("the given collection of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).excludes(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("[A Test] the given collection of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").excludes(objects);
      }
    });
  }

  public void shouldFailIfActualContainsValueAndExpectingToExclude() {
    expectAssertionError("collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Leia")).excludes("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualContainsValueAndExpectingToExclude() {
    expectAssertionError("[A Test] collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new CollectionAssert(list("Luke", "Leia")).as("A Test").excludes("Luke");
          }
        });
  }

  public void shouldFailIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).doesNotHaveDuplicates();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").doesNotHaveDuplicates();
      }
    });
  }

  public void shouldFailIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Yoda", "Luke")).doesNotHaveDuplicates();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("[A Test] collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new CollectionAssert(list("Luke", "Yoda", "Luke")).as("A Test").doesNotHaveDuplicates();
          }
        });
  }

  public void shouldPassIfActualContainsNoDuplicates() {
    new CollectionAssert(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  public void shouldPassIfEmptyActualContainsNoDuplicates() {
    new CollectionAssert(EMPTY_COLLECTION).doesNotHaveDuplicates();
  }

  public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty collection, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Yoda")).isEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty collection, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Yoda")).as("A Test").isEmpty();
      }
    });
  }

  public void shouldPassIfActualIsEmpty() {
    new CollectionAssert(EMPTY_COLLECTION).isEmpty();
  }

  public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        List<String> nullList = null;
        new CollectionAssert(nullList).as("A Test").isEmpty();
      }
    });
  }

  public void shouldPassIfActualHasExpectedSize() {
    new CollectionAssert(list("Gandalf", "Frodo", "Sam")).hasSize(3);
  }

  public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new CollectionAssert(names).hasSize(2);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new CollectionAssert(names).as("A Test").hasSize(2);
      }
    });
  }

  public void shouldFailIfActualIsNullAndExpectingSomeSize() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).hasSize(0);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingSomeSize() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").hasSize(0);
      }
    });
  }

  public void shouldPassIfActualIsNotEmpty() {
    List<String> names = list("Frodo", "Sam");
    new CollectionAssert(names).isNotEmpty();
  }

  public void shouldFailIfActualIsNullAndExpectingNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isNotEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").isNotEmpty();
      }
    });
  }

  public void shouldFailIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("expecting a non-empty collection, but it was empty").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).isNotEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("[A Test] expecting a non-empty collection, but it was empty").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").isNotEmpty();
      }
    });
  }

  public void shouldPassIfActualIsNull() {
    new CollectionAssert(null).isNull();
  }

  public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).isNull();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").isNull();
      }
    });
  }

  public void shouldFailIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).containsOnly("Sam");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("[A Test] collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").containsOnly("Sam");
      }
    });
  }

  public void shouldFailIfActualHasNotExpectedValues() {
    expectAssertionError("unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Gandalf", "Frodo", "Sam");
        new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualHasNotExpectedValues() {
    expectAssertionError("[A Test] unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo", "Sam");
            new CollectionAssert(names).as("A Test").containsOnly("Gandalf", "Frodo");
          }
        });
  }

  public void shouldFailIfActualDoesNotContainExpectedElement() {
    expectAssertionError("collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo");
            new CollectionAssert(names).containsOnly("Gandalf", "Frodo", "Sam");
          }
        });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotContainExpectedElement() {
    expectAssertionError("[A Test] collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo");
            new CollectionAssert(names).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
          }
        });
  }

  public void shouldFailIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  public void shouldPassIfArraysAreEqual() {
    new CollectionAssert(list("Luke", "Leia")).isEqualTo(list("Luke", "Leia"));
  }

  public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Leia")).isEqualTo(list("Anakin"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Leia")).as("A Test").isEqualTo(list("Anakin"));
      }
    });
  }

  public void shouldPassIfArraysAreNotEqual() {
    new CollectionAssert(list("Luke", "Leia")).isNotEqualTo(list("Yoda"));
  }

  public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new CollectionAssert(list("Luke", "Leia")).isNotEqualTo(list("Luke", "Leia"));
          }
        });
  }

  public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new CollectionAssert(list("Luke", "Leia")).as("A Test").isNotEqualTo(list("Luke", "Leia"));
          }
        });
  }

  public void shouldPassIfCollectionsAreSame() {
    List<String> list = list("Leia");
    new CollectionAssert(list).isSameAs(list);
  }

  public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Leia")).isSameAs(EMPTY_COLLECTION);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Leia")).as("A Test").isSameAs(EMPTY_COLLECTION);
      }
    });
  }

  public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        List<String> list = list("Leia");
        new CollectionAssert(list).isNotSameAs(list);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        List<String> list = list("Leia");
        new CollectionAssert(list).as("A Test").isNotSameAs(list);
      }
    });
  }

  public void shouldPassIfCollectionsAreNotSame() {
    new CollectionAssert(list("Leia")).isNotSameAs(EMPTY_COLLECTION);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null collection, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null collection, but it was null").on(codeToTest);
  }

  public void shouldPassIfActualHasExpectedElementsOnly() {
    List<String> names = list("Gandalf", "Frodo");
    new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
  }

  public void shouldPassIfArrayIsNullAndExpectingNullOrEmpty() {
    new CollectionAssert(null).isNullOrEmpty();
  }

  public void shouldPassIfArrayIsEmptyAndExpectingNullOrEmpty() {
    new CollectionAssert(EMPTY_COLLECTION).isNullOrEmpty();
  }

  public void shouldFailIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("expecting a null or empty collection, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list(8)).isNullOrEmpty();
      }
    });
  }

  public void shouldFailShowingDescritptionIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("[A Test] expecting a null or empty collection, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list(8)).as("A Test").isNullOrEmpty();
      }
    });
  }
}
