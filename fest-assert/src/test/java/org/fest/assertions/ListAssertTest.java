/*
 * Created on Mar 29, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.CommonFailures.expectIllegalArgumentExceptionIfConditionIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ListAssert}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ListAssertTest {

  private static final ArrayList<String> EMPTY_COLLECTION = new ArrayList<String>();

  public void shouldSetTextDescription() {
    ListAssert assertion = new ListAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  public void shouldSetTextDescriptionSafelyForGroovy() {
    ListAssert assertion = new ListAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  public void shouldSetDescription() {
    ListAssert assertion = new ListAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  public void shouldSetDescriptionSafelyForGroovy() {
    ListAssert assertion = new ListAssert(list("Anakin"));
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyList extends Condition<List<?>> {
    @Override public boolean matches(List<?> c) {
      return c != null && c.isEmpty();
    }
  }

  public void shouldPassIfConditionSatisfied() {
    new ListAssert(EMPTY_COLLECTION).satisfies(new EmptyList());
  }

  public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).satisfies(null);
      }
    });
  }

  public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<['Han']> should satisfy condition:<EmptyList>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Han")).satisfies(new EmptyList());
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    String message = "[A Test] actual value:<['Han']> should satisfy condition:<EmptyList>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Han")).as("A Test").satisfies(new EmptyList());
      }
    });
  }

  public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<['Han']> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Han")).satisfies(new EmptyList().as("Empty"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<['Han']> should satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Han")).as("A Test").satisfies(new EmptyList().as("Empty"));
      }
    });
  }

  public void shouldPassIfConditionNotSatisfied() {
    new ListAssert(list("Leia")).doesNotSatisfy(new EmptyList());
  }

  public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).doesNotSatisfy(null);
      }
    });
  }

  public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyList>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).doesNotSatisfy(new EmptyList());
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionSatisfied() {
    String message = "[A Test] actual value:<[]> should not satisfy condition:<EmptyList>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).as("A Test").doesNotSatisfy(new EmptyList());
      }
    });
  }

  public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).doesNotSatisfy(new EmptyList().as("Empty"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).as("A Test").doesNotSatisfy(new EmptyList().as("Empty"));
      }
    });
  }

  public void shouldPassIfActualIsNotNull() {
    new ListAssert(EMPTY_COLLECTION).isNotNull();
  }

  public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).isNotNull();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").isNotNull();
      }
    });
  }

  public void shouldPassIfActualContainsValue() {
    new ListAssert(list("Luke", "Leia")).contains("Luke");
  }

  public void shouldPassIfActualContainsValues() {
    new ListAssert(list("Luke", "Leia", "Anakin")).contains("Luke", "Leia");
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualContainsObjects() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).contains("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualContainsObjects() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").contains("Luke");
      }
    });
  }

  public void shouldFailIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_COLLECTION).contains(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_COLLECTION).as("A Test").contains(objects);
      }
    });
  }

  public void shouldFailIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("list:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).contains("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("[A Test] list:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).as("A Test").contains("Luke");
      }
    });
  }

  public void shouldPassIfActualExcludesValue() {
    new ListAssert(list("Luke", "Leia")).excludes("Anakin");
  }

  public void shouldPassIfActualExcludesGivenValues() {
    new ListAssert(list("Luke", "Leia", "Anakin")).excludes("Han", "Yoda");
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).excludes("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").excludes("Luke");
      }
    });
  }

  public void shouldFailIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_COLLECTION).excludes(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_COLLECTION).as("A Test").excludes(objects);
      }
    });
  }

  public void shouldFailIfActualContainsValueAndExpectingToExclude() {
    expectAssertionError("list:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).excludes("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualContainsValueAndExpectingToExclude() {
    expectAssertionError("[A Test] list:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new ListAssert(list("Luke", "Leia")).as("A Test").excludes("Luke");
          }
        });
  }

  public void shouldFailIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).doesNotHaveDuplicates();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").doesNotHaveDuplicates();
      }
    });
  }

  public void shouldFailIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("list:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Yoda", "Luke")).doesNotHaveDuplicates();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("[A Test] list:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new ListAssert(list("Luke", "Yoda", "Luke")).as("A Test").doesNotHaveDuplicates();
          }
        });
  }

  public void shouldPassIfActualContainsNoDuplicates() {
    new ListAssert(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  public void shouldPassIfEmptyActualContainsNoDuplicates() {
    new ListAssert(EMPTY_COLLECTION).doesNotHaveDuplicates();
  }

  public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty list, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Yoda")).isEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty list, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Yoda")).as("A Test").isEmpty();
      }
    });
  }

  public void shouldPassIfActualIsEmpty() {
    new ListAssert(EMPTY_COLLECTION).isEmpty();
  }

  public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).isEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        List<String> nullList = null;
        new ListAssert(nullList).as("A Test").isEmpty();
      }
    });
  }

  public void shouldPassIfActualHasExpectedSize() {
    new ListAssert(list("Gandalf", "Frodo", "Sam")).hasSize(3);
  }

  public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for list:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new ListAssert(names).hasSize(2);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for list:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new ListAssert(names).as("A Test").hasSize(2);
      }
    });
  }

  public void shouldFailIfActualIsNullAndExpectingSomeSize() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).hasSize(0);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingSomeSize() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").hasSize(0);
      }
    });
  }

  public void shouldPassIfActualIsNotEmpty() {
    List<String> names = list("Frodo", "Sam");
    new ListAssert(names).isNotEmpty();
  }

  public void shouldFailIfActualIsNullAndExpectingNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).isNotEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").isNotEmpty();
      }
    });
  }

  public void shouldFailIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).isNotEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("[A Test] expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).as("A Test").isNotEmpty();
      }
    });
  }

  public void shouldPassIfActualIsNull() {
    new ListAssert(null).isNull();
  }

  public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).isNull();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).as("A Test").isNull();
      }
    });
  }

  public void shouldFailIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("list:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).containsOnly("Sam");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("[A Test] list:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_COLLECTION).as("A Test").containsOnly("Sam");
      }
    });
  }

  public void shouldFailIfActualHasNotExpectedValues() {
    expectAssertionError("unexpected element(s):<['Sam']> in list:<['Gandalf', 'Frodo', 'Sam']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Gandalf", "Frodo", "Sam");
        new ListAssert(names).containsOnly("Gandalf", "Frodo");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualHasNotExpectedValues() {
    expectAssertionError("[A Test] unexpected element(s):<['Sam']> in list:<['Gandalf', 'Frodo', 'Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo", "Sam");
            new ListAssert(names).as("A Test").containsOnly("Gandalf", "Frodo");
          }
        });
  }

  public void shouldFailIfActualDoesNotContainExpectedElement() {
    expectAssertionError("list:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo");
            new ListAssert(names).containsOnly("Gandalf", "Frodo", "Sam");
          }
        });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotContainExpectedElement() {
    expectAssertionError("[A Test] list:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo");
            new ListAssert(names).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
          }
        });
  }

  public void shouldFailIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  public void shouldPassIfArraysAreEqual() {
    new ListAssert(list("Luke", "Leia")).isEqualTo(list("Luke", "Leia"));
  }

  public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).isEqualTo(list("Anakin"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).as("A Test").isEqualTo(list("Anakin"));
      }
    });
  }

  public void shouldPassIfArraysAreNotEqual() {
    new ListAssert(list("Luke", "Leia")).isNotEqualTo(list("Yoda"));
  }

  public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ListAssert(list("Luke", "Leia")).isNotEqualTo(list("Luke", "Leia"));
          }
        });
  }

  public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ListAssert(list("Luke", "Leia")).as("A Test").isNotEqualTo(list("Luke", "Leia"));
          }
        });
  }

  public void shouldPassIfCollectionsAreSame() {
    List<String> list = list("Leia");
    new ListAssert(list).isSameAs(list);
  }

  public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Leia")).isSameAs(EMPTY_COLLECTION);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Leia")).as("A Test").isSameAs(EMPTY_COLLECTION);
      }
    });
  }

  public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        List<String> list = list("Leia");
        new ListAssert(list).isNotSameAs(list);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        List<String> list = list("Leia");
        new ListAssert(list).as("A Test").isNotSameAs(list);
      }
    });
  }

  public void shouldPassIfCollectionsAreNotSame() {
    new ListAssert(list("Leia")).isNotSameAs(EMPTY_COLLECTION);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null list, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null list, but it was null").on(codeToTest);
  }

  public void shouldPassIfActualHasExpectedElementsOnly() {
    List<String> names = list("Gandalf", "Frodo");
    new ListAssert(names).containsOnly("Gandalf", "Frodo");
  }

  public void shouldPassIfArrayIsNullAndExpectingNullOrEmpty() {
    new ListAssert(null).isNullOrEmpty();
  }

  public void shouldPassIfArrayIsEmptyAndExpectingNullOrEmpty() {
    new ListAssert(EMPTY_COLLECTION).isNullOrEmpty();
  }

  public void shouldFailIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("expecting a null or empty list, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list(8)).isNullOrEmpty();
      }
    });
  }

  public void shouldFailShowingDescritptionIfArrayIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("[A Test] expecting a null or empty list, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list(8)).as("A Test").isNullOrEmpty();
      }
    });
  }
}
