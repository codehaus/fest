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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link ObjectAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectAssertTest {

  @Test public void shouldSetTextDescription() {
    ObjectAssert assertion = new ObjectAssert("Anakin");
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    ObjectAssert assertion = new ObjectAssert("Anakin");
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    ObjectAssert assertion = new ObjectAssert("Anakin");
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ObjectAssert assertion = new ObjectAssert("Anakin");
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNull extends Condition<Object> {
    @Override public boolean matches(Object o) {
      return o != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ObjectAssert("Frodo").satisfies(new NotNull());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<null> should satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).satisfies(new NotNull());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test").satisfies(new NotNull());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<null> should satisfy condition:<Non-null>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).satisfies(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition:<Non-null>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test").satisfies(new NotNull().as("Non-null"));
      }
    });
  }

  //
  @Test public void shouldPassIfConditionNotSatisfied() {
    new ObjectAssert(null).doesNotSatisfy(new NotNull());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    expectAssertionError("actual value:<''> should not satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").doesNotSatisfy(new NotNull());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    expectAssertionError("[A Test] actual value:<''> should not satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").as("A Test").doesNotSatisfy(new NotNull());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<''> should not satisfy condition:<Non-null>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").doesNotSatisfy(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<''> should not satisfy condition:<Non-null>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").as("A Test").doesNotSatisfy(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new ObjectAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<''> should be null").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <''> should be null").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("").as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new ObjectAssert("").isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsInstanceOfExpectedClass() {
    new ObjectAssert(yoda()).isInstanceOf(Person.class);
  }

  @Test public void shouldThrowErrorIfGivenClassIsNullWhenCheckingIfInstanceOf() {
    shouldThrowErrorIfTypeIsNullWhenCheckingIfInstanceOf(new CodeToTest() {
      public void run() {
        new ObjectAssert("Yoda").isInstanceOf(null);
      }
    });
  }

  @Test public void shouldFailIfActualIsNotInstanceOfExpectedClass() {
    expectAssertionError(
        "expected instance of:<java.lang.String> but was instance of:<org.fest.assertions.ObjectAssertTest$Person>")
        .on(new CodeToTest() {
          public void run() {
            new ObjectAssert(yoda()).isInstanceOf(String.class);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotInstanceOfExpectedClass() {
    expectAssertionError(
        "[A Test] expected instance of:<java.lang.String> but was instance of:<org.fest.assertions.ObjectAssertTest$Person>")
        .on(new CodeToTest() {
          public void run() {
            new ObjectAssert(yoda()).as("A Test").isInstanceOf(String.class);
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIsInstanceOfExpectedClass() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).isInstanceOf(String.class);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIsInstanceOfExpectedClass() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test").isInstanceOf(String.class);
      }
    });
  }

  @Test public void shouldPassIfActualIsInstanceOfAnyExpectedClass() {
    new ObjectAssert(yoda()).isInstanceOfAny(Person.class, String.class, Integer.class);
  }

  @Test public void shouldThrowErrorIfArrayOfTypesIsNullWhenCheckingIfInstanceOfAny() {
    expectIllegalArgumentException("The given array of types to check against should not be null").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("Yoda").isInstanceOfAny((Class<?>[]) null);
      }
    });
  }

  @Test public void shouldThrowErrorIfTypeInArrayIsNullWhenCheckingIfInstanceOfAny() {
    shouldThrowErrorIfTypeIsNullWhenCheckingIfInstanceOf(new CodeToTest() {
      public void run() {
        new ObjectAssert("Yoda").isInstanceOfAny(new Class<?>[] { null });
      }
    });
  }

  private void shouldThrowErrorIfTypeIsNullWhenCheckingIfInstanceOf(CodeToTest codeToTest) {
    expectIllegalArgumentException("The given type to check against should not be null").on(codeToTest);
  }

  @Test public void shouldFailIfActualIsNotInstanceOfAnyExpectedClass() {
    expectAssertionError(
        "expected instance of any:<[java.lang.String, java.lang.Integer]> but was instance of:<org.fest.assertions.ObjectAssertTest$Person>")
        .on(new CodeToTest() {
          public void run() {
            new ObjectAssert(yoda()).isInstanceOfAny(String.class, Integer.class);
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotInstanceOfAnyExpectedClass() {
    expectAssertionError(
        "[A Test] expected instance of any:<[java.lang.String, java.lang.Integer]> but was instance of:<org.fest.assertions.ObjectAssertTest$Person>")
        .on(new CodeToTest() {
          public void run() {
            new ObjectAssert(yoda()).as("A Test").isInstanceOfAny(String.class, Integer.class);
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIsInstanceOfAnyExpectedClass() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).isInstanceOfAny(String.class, Integer.class);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIsInstanceOfAnyExpectedClass() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test").isInstanceOfAny(String.class, Integer.class);
      }
    });
  }

  @Test public void shouldPassIfObjectsAreSame() {
    Person yoda = yoda();
    new ObjectAssert(yoda).isSameAs(yoda);
  }

  @Test public void shouldFailIfObjectsAreNotSameAndExpectingSame() {
    expectAssertionError("expected same instance but found:<Person[name=Yoda, age=600]> and:<'Yoda'>").on(
        new CodeToTest() {
          public void run() {
            new ObjectAssert(yoda()).isSameAs("Yoda");
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreNotSameAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<Person[name=Yoda, age=600]> and:<'Yoda'>").on(
        new CodeToTest() {
          public void run() {
            new ObjectAssert(yoda()).as("A Test").isSameAs("Yoda");
          }
        });
  }

  @Test public void shouldPassIfObjectsAreNotSame() {
    new ObjectAssert(yoda()).isNotSameAs("yoda");
  }

  @Test public void shouldFailIfObjectsAreSameAndExpectingNotSame() {
    expectAssertionError("given objects are same:<Person[name=Yoda, age=600]>").on(new CodeToTest() {
      public void run() {
        Person yoda = yoda();
        new ObjectAssert(yoda).isNotSameAs(yoda);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreSameAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<Person[name=Yoda, age=600]>").on(new CodeToTest() {
      public void run() {
        Person yoda = yoda();
        new ObjectAssert(yoda).as("A Test").isNotSameAs(yoda);
      }
    });
  }

  @Test public void shouldPassIfObjectsAreEqual() {
    new ObjectAssert(yoda()).isEqualTo(yoda());
  }

  @Test public void shouldFailIfObjectsAreNotEqual() {
    expectAssertionError("expected:<'Yoda'> but was:<Person[name=Yoda, age=600]>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(yoda()).isEqualTo("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreNotEqual() {
    expectAssertionError("[A Test] expected:<'Yoda'> but was:<Person[name=Yoda, age=600]>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(yoda()).as("A Test").isEqualTo("Yoda");
      }
    });
  }

  @Test public void shouldPassIfObjectsAreNotEqual() {
    new ObjectAssert(yoda()).isNotEqualTo(new Person("Yoda", 900));
  }

  @Test public void shouldFailIfObjectsAreEqual() {
    expectAssertionError("actual value:<'Yoda'> should not be equal to:<'Yoda'>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("Yoda").isNotEqualTo("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreEqual() {
    expectAssertionError("[A Test] actual value:<'Yoda'> should not be equal to:<'Yoda'>").on(new CodeToTest() {
      public void run() {
        new ObjectAssert("Yoda").as("A Test").isNotEqualTo("Yoda");
      }
    });
  }

  private Person yoda() {
    return new Person("Yoda", 600);
  }

  private static class Person {
    final String name;
    final int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override public int hashCode() {
      final int PRIME = 31;
      int result = 1;
      result = PRIME * result + age;
      result = PRIME * result + ((name == null) ? 0 : name.hashCode());
      return result;
    }

    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (!(obj instanceof Person)) return false;
      Person other = (Person) obj;
      if (!areEqual(age, other.age)) return false;
      return areEqual(name, other.name);
    }

    @Override public String toString() {
      return concat(Person.class.getSimpleName(), "[name=", name, ", age=", age, "]");
    }
  }
}
