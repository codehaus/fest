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
import static org.fest.assertions.Index.atIndex;
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

  private static final ArrayList<String> EMPTY_LIST = new ArrayList<String>();

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
    new ListAssert(EMPTY_LIST).satisfies(new EmptyList());
  }

  public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).satisfies(null);
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
        new ListAssert(EMPTY_LIST).doesNotSatisfy(null);
      }
    });
  }

  public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<EmptyList>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).doesNotSatisfy(new EmptyList());
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionSatisfied() {
    String message = "[A Test] actual value:<[]> should not satisfy condition:<EmptyList>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").doesNotSatisfy(new EmptyList());
      }
    });
  }

  public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).doesNotSatisfy(new EmptyList().as("Empty"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<[]> should not satisfy condition:<Empty>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").doesNotSatisfy(new EmptyList().as("Empty"));
      }
    });
  }

  public void shouldPassIfActualIsNotNull() {
    new ListAssert(EMPTY_LIST).isNotNull();
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
        new ListAssert(EMPTY_LIST).contains(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).as("A Test").contains(objects);
      }
    });
  }

  public void shouldFailIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("list:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).contains("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("[A Test] list:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").contains("Luke");
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
        new ListAssert(EMPTY_LIST).excludes(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).as("A Test").excludes(objects);
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
    new ListAssert(EMPTY_LIST).doesNotHaveDuplicates();
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
    new ListAssert(EMPTY_LIST).isEmpty();
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
        new ListAssert(EMPTY_LIST).isNotEmpty();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("[A Test] expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").isNotEmpty();
      }
    });
  }

  public void shouldPassIfActualIsNull() {
    new ListAssert(null).isNull();
  }

  public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).isNull();
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").isNull();
      }
    });
  }

  public void shouldFailIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("list:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).containsOnly("Sam");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("[A Test] list:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").containsOnly("Sam");
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

  public void shouldPassIfListsAreEqual() {
    new ListAssert(list("Luke", "Leia")).isEqualTo(list("Luke", "Leia"));
  }

  public void shouldFailIfListsAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).isEqualTo(list("Anakin"));
      }
    });
  }

  public void shouldFailShowingDescriptionIfListsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).as("A Test").isEqualTo(list("Anakin"));
      }
    });
  }

  public void shouldPassIfListsAreNotEqual() {
    new ListAssert(list("Luke", "Leia")).isNotEqualTo(list("Yoda"));
  }

  public void shouldFailIfListsAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ListAssert(list("Luke", "Leia")).isNotEqualTo(list("Luke", "Leia"));
          }
        });
  }

  public void shouldFailShowingDescriptionIfListsAreEqualAndExpectingNotEqual() {
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
        new ListAssert(list("Leia")).isSameAs(EMPTY_LIST);
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<['Leia']> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Leia")).as("A Test").isSameAs(EMPTY_LIST);
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
    new ListAssert(list("Leia")).isNotSameAs(EMPTY_LIST);
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

  public void shouldPassIfListIsNullAndExpectingNullOrEmpty() {
    new ListAssert(null).isNullOrEmpty();
  }

  public void shouldPassIfListIsEmptyAndExpectingNullOrEmpty() {
    new ListAssert(EMPTY_LIST).isNullOrEmpty();
  }

  public void shouldFailIfListIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("expecting a null or empty list, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list(8)).isNullOrEmpty();
      }
    });
  }

  public void shouldFailShowingDescritptionIfListIsNotNullOrEmptyAndExpectingNullOrEmpty() {
    expectAssertionError("[A Test] expecting a null or empty list, but was:<[8]>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list(8)).as("A Test").isNullOrEmpty();
      }
    });
  }

  public void shouldPassIfActualContainsSequence() {
    new ListAssert(list("Anakin", "Leia", "Han")).containsSequence("Anakin", "Leia")
                                                 .containsSequence("Leia", "Han");
  }

  public void shouldPassIfActualAndSequenceAreEqual() {
    new ListAssert(list("Anakin", "Leia", "Han")).containsSequence("Anakin", "Leia", "Han");
  }

  public void shouldPassIfActualIsNotEmptyAndSequenceIsEmpty() {
    Object[] sequence = {};
    new ListAssert(list("Anakin", "Leia", "Han")).containsSequence(sequence);
  }

  public void shouldFailIfActualDoesNotContainSequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not contain the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).containsSequence("Leia", "Anakin");
        }
      });
  }

  public void shouldFailIfActualDoesNotContainCompletelyDifferentSequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not contain the sequence:<['Han', 'Luke']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).containsSequence("Han", "Luke");
        }
      });
  }

  public void shouldFailIfActualDoesNotContainSomeElementsInSequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not contain the sequence:<['Anakin', 'Han']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).containsSequence("Anakin", "Han");
        }
      });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotContainSequence() {
    expectAssertionError("[A Test] list:<['Anakin', 'Leia']> does not contain the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).as("A Test").containsSequence("Leia", "Anakin");
        }
      });
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualContainsSequence() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).containsSequence("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualContainsSequence() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").containsSequence("Luke");
      }
    });
  }

  public void shouldFailIfSequenceToContainIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).containsSequence(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfSequenceToContainIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).as("A Test").containsSequence(objects);
      }
    });
  }

  public void shouldPassIfActualStartsWithSequence() {
    new ListAssert(list("Anakin", "Leia", "Han")).startsWith("Anakin", "Leia");
  }

  public void shouldPassIfActualIsEmptyAndExpectedSequenceToStartWithIsEmpty() {
    new ListAssert(list()).startsWith();
  }

  public void shouldPassIfActualAndSequenceToStartWithAreEqual() {
    new ListAssert(list("Anakin", "Leia", "Han")).startsWith("Anakin", "Leia", "Han");
  }

  public void shouldFailIfActualIsSmallerThanExpectedSequenceToStartWith() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not start with the sequence:<['Anakin', 'Leia', 'Han']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).startsWith("Anakin", "Leia", "Han");
        }
      });
  }

  public void shouldFailIfActualIsNotEmptyAndSequenceToStartWithIsEmpty() {
    final Object[] sequence = {};
    expectAssertionError("list:<['Anakin', 'Leia']> does not start with the sequence:<[]>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).startsWith(sequence);
        }
      });
  }

  public void shouldFailIfActualDoesNotStartWithSequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not start with the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).startsWith("Leia", "Anakin");
        }
      });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotStartWithSequence() {
    expectAssertionError("[A Test] list:<['Anakin', 'Leia']> does not start with the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).as("A Test").startsWith("Leia", "Anakin");
        }
      });
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualStartsWithSequence() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).startsWith("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualStartsWithSequence() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").startsWith("Luke");
      }
    });
  }

  public void shouldFailIfSequenceToStartWithIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).startsWith(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfSequenceToStartWithIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).as("A Test").startsWith(objects);
      }
    });
  }

  public void shouldPassIfActualEndsWithSequence() {
    new ListAssert(list("Anakin", "Leia", "Han")).endsWith("Leia", "Han");
  }

  public void shouldPassIfActualIsEmptyAndExpectedSequenceToEndWithIsEmpty() {
    new ListAssert(list()).endsWith();
  }

  public void shouldPassIfActualAndSequenceToEndWithAreEqual() {
    new ListAssert(list("Anakin", "Leia", "Han")).endsWith("Anakin", "Leia", "Han");
  }

  public void shouldFailIfActualIsSmallerThanExpectedSequenceToEndWith() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not end with the sequence:<['Han', 'Anakin', 'Leia']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).endsWith("Han", "Anakin", "Leia");
        }
      });
  }

  public void shouldFailIfActualIsNotEmptyAndSequenceToEndWithIsEmpty() {
    final Object[] sequence = {};
    expectAssertionError("list:<['Anakin', 'Leia']> does not end with the sequence:<[]>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).endsWith(sequence);
        }
      });
  }

  public void shouldFailIfActualDoesNotEndWithSequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not end with the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).endsWith("Leia", "Anakin");
        }
      });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotEndWithSequence() {
    expectAssertionError("[A Test] list:<['Anakin', 'Leia']> does not end with the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).as("A Test").endsWith("Leia", "Anakin");
        }
      });
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualEndsWithSequence() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).endsWith("Luke");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualEndsWithSequence() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").endsWith("Luke");
      }
    });
  }

  public void shouldFailIfSequenceToEndWithIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).endsWith(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfSequenceToEndWithIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).as("A Test").endsWith(objects);
      }
    });
  }

  public void shouldPassIfActualContainsObjectAtIndex() {
    new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(0))
                                          .contains("Leia", atIndex(1));
  }

  public void shouldFailIfIndexIsNull() {
    final Index index = null;
    expectAssertionError("The given index should not be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", index);
      }
    });
  }

  public void shouldFailShowingDescriptionIfIndexIsNull() {
    final Index index = null;
    expectAssertionError("[A Test] The given index should not be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Anakin", index);
      }
    });
  }

  public void shouldFailIfIndexIsNegative() {
    expectAssertionError("The index <-1> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(-1));
      }
    });
  }

  public void shouldFailIfActualIsNullWhenCheckingIfActualContainsElement() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).contains("Anakin", atIndex(3));
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualContainsElement() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").contains("Anakin", atIndex(3));
      }
    });
  }

  public void shouldFailIfActualIsEmptyWhenCheckingItContainsElementAtIndex() {
    expectAssertionError("expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).contains("Anakin", atIndex(3));
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualIsEmptyWhenCheckingItContainsElementAtIndex() {
    expectAssertionError("[A Test] expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(EMPTY_LIST).as("A Test").contains("Anakin", atIndex(3));
      }
    });
  }

  public void shouldFailShowingDescriptionIfIndexIsNegative() {
    String msg = "[A Test] The index <-1> should be greater than or equal to zero and less than 2";
    expectAssertionError(msg).on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Anakin", atIndex(-1));
      }
    });
  }

  public void shouldFailIfIndexIsEqualToListSize() {
    expectAssertionError("The index <2> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(2));
      }
    });
  }

  public void shouldFailShowingDescriptionIfIndexIsEqualToListSize() {
    String msg = "[A Test] The index <2> should be greater than or equal to zero and less than 2";
    expectAssertionError(msg).on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Anakin", atIndex(2));
      }
    });
  }

  public void shouldFailIfIndexIsGreaterThanListSize() {
    expectAssertionError("The index <3> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(3));
      }
    });
  }

  public void shouldFailShowingDescriptionIfIndexIsGreaterThanListSize() {
    String msg = "[A Test] The index <3> should be greater than or equal to zero and less than 2";
    expectAssertionError(msg).on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Anakin", atIndex(3));
      }
    });
  }

  public void shouldFailIfActualDoesNotContainElementAtIndex() {
    expectAssertionError("expecting <'Han'> at index <1> but found <'Leia'>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Han", atIndex(1));
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotContainElementAtIndex() {
    expectAssertionError("[A Test] expecting <'Han'> at index <1> but found <'Leia'>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Han", atIndex(1));
      }
    });
  }

  public void shouldPassIfActualContainsExactlyTheGivenObjects() {
    new ListAssert(list("Anakin", "Leia")).containsExactly("Anakin", "Leia");
  }

  public void shouldFailIfArrayToCheckForEqualityIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).containsExactly(objects);
      }
    });
  }

  public void shouldFailShowingDescriptionIfArrayToCheckForEqualityIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(EMPTY_LIST).as("A Test").containsExactly(objects);
      }
    });
  }

  public void shouldFailIfActualDoesNotContainExactlyTheGivenObjects() {
    expectAssertionError("expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).containsExactly("Anakin");
      }
    });
  }

  public void shouldFailShowingDescriptionIfActualDoesNotContainExactlyTheGivenObjects() {
    expectAssertionError("[A Test] expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Luke", "Leia")).as("A Test").containsExactly("Anakin");
      }
    });
  }
}
