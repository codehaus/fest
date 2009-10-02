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

import static java.util.Collections.emptyList;
import static org.fest.assertions.CommonFailures.*;
import static org.fest.assertions.Index.atIndex;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ListAssert#contains(Object, Index)}</code>.
 *
 * @author Alex Ruiz
 */
public class ListAssert_contains_withIndex_Test {

  @Test
  public void should_pass_if_actual_contains_Object_at_index() {
    new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(0))
                                          .contains("Leia", atIndex(1));
  }

  @Test
  public void should_throw_error_if_expected_Index_is_null() {
    final Index index = null;
    // TODO should be NullPointerException
    expectAssertionError("The given index should not be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", index);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_Index_is_null() {
    final Index index = null;
    expectAssertionError("[A Test] The given index should not be null").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test")
                                              .contains("Anakin", index);
      }
    });
  }

  @Test
  public void should_throw_error_if_index_is_negative() {
    // TODO should be IllegalArgumentException
    expectAssertionError("The index <-1> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(-1));
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_index_is_negative() {
    // TODO should be IllegalArgumentException
    expectAssertionError("[A Test] The index <-1> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test")
                                              .contains("Anakin", atIndex(-1));
      }
    });
  }


  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfListIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).contains("Anakin", atIndex(0));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfListIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test")
                            .contains("Anakin", atIndex(0));
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_empty() {
    expectAssertionError("expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(emptyList()).contains("Anakin", atIndex(3));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty() {
    expectAssertionError("[A Test] expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(emptyList()).as("A Test")
                                   .contains("Anakin", atIndex(3));
      }
    });
  }

  @Test
  public void should_throw_error_if_Index_value_is_equal_to_size_of_actual() {
    // TODO should be IndexOutOfBoundsException
    expectAssertionError("The index <2> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(2));
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_Index_value_is_equal_to_size_of_actual() {
    String msg = "[A Test] The index <2> should be greater than or equal to zero and less than 2";
    expectAssertionError(msg).on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Anakin", atIndex(2));
      }
    });
  }

  @Test
  public void should_throw_error_if_Index_value_is_greater_than_size_of_actual() {
    // TODO should be IndexOutOfBoundsException
    expectAssertionError("The index <3> should be greater than or equal to zero and less than 2").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Anakin", atIndex(3));
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_Index_value_is_greater_than_size_of_actual() {
    String msg = "[A Test] The index <3> should be greater than or equal to zero and less than 2";
    expectAssertionError(msg).on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test")
                                              .contains("Anakin", atIndex(3));
      }
    });
  }

  @Test
  public void should_fail_if_actual_does_not_contain_Object_at_index() {
    expectAssertionError("expecting <'Han'> at index <1> but found <'Leia'>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).contains("Han", atIndex(1));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_contain_Object_at_index() {
    expectAssertionError("[A Test] expecting <'Han'> at index <1> but found <'Leia'>").on(new CodeToTest() {
      public void run() {
        new ListAssert(list("Anakin", "Leia")).as("A Test").contains("Han", atIndex(1));
      }
    });
  }
}
