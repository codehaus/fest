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
 * Tests for <code>{@link CharAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class CharAssertTest {

  @Test public void shouldSetTextDescription() {
    CharAssert assertion = new CharAssert('a');
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    CharAssert assertion = new CharAssert('a');
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    CharAssert assertion = new CharAssert('a');
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    CharAssert assertion = new CharAssert('a');
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldPassIfValuesAreEqual() {
    new CharAssert('a').isEqualTo('a');
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<b> but was:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isEqualTo('b');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<b> but was:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isEqualTo('b');
      }
    });
  }

  @Test public void shouldPassIfValueAreNotEqual() {
    new CharAssert('a').isNotEqualTo('b');
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<a> should not be equal to:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isNotEqualTo('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<a> should not be equal to:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isNotEqualTo('a');
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpected() {
    new CharAssert('a').isGreaterThan('A');
  }

  @Test public void shouldFailIfActualIsEqualtoExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<a> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isGreaterThan('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualtoExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<a> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isGreaterThan('a');
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<A> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').isGreaterThan('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<A> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').as("A Test").isGreaterThan('a');
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpected() {
    new CharAssert('A').isLessThan('a');
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<a> should be less than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isLessThan('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<a> should be less than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isLessThan('a');
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<a> should be less than:<A>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isLessThan('A');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<a> should be less than:<A>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isLessThan('A');
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpected() {
    new CharAssert('a').isGreaterThanOrEqualTo('a').isGreaterThanOrEqualTo('A');
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<A> should be greater than or equal to:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').isGreaterThanOrEqualTo('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<A> should be greater than or equal to:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').as("A Test").isGreaterThanOrEqualTo('a');
      }
    });
  }

  @Test public void shouldPassIfActualIsLessOrEqualToExpected() {
    new CharAssert('A').isLessThanOrEqualTo('a').isLessThanOrEqualTo('A');
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("actual value:<a> should be less than or equal to:<A>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isLessThanOrEqualTo('A');
      }
    });
  }

  @Test public void shouldFailShowingMessageIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<a> should be less than or equal to:<A>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isLessThanOrEqualTo('A');
      }
    });
  }

  @Test public void shouldPassIfActualIsUpperCase() {
    new CharAssert('A').isUpperCase();
  }

  @Test public void shouldFailIfActualIsNotUpperCaseAndExpectingUpperCase() {
    expectAssertionError("<a> should be an uppercase character").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isUpperCase();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotUpperCaseAndExpectingUpperCase() {
    expectAssertionError("[A Test] <a> should be an uppercase character").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isUpperCase();
      }
    });
  }

  @Test public void shouldPassIfActualIsLowerCase() {
    new CharAssert('a').isLowerCase();
  }

  @Test public void shouldFailIfActualIsNotLowerCaseAndExpectingLowerCase() {
    expectAssertionError("<A> should be a lowercase character").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').isLowerCase();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotLowerCaseAndExpectingLowerCase() {
    expectAssertionError("[A Test] <A> should be a lowercase character").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').as("A Test").isLowerCase();
      }
    });
  }
  
  @Test public void shouldPassIfNullReferenceComparedToNullReference() {
    new CharAssert(null).isEqualTo(null);
  }

}
