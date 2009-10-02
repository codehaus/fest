/*
 * Created on Jun 9, 2007
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

import static org.fest.assertions.CommonFailures.*;
import static org.fest.assertions.Images.*;
import static org.fest.assertions.NotNull.notNullImage;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.concat;

import java.awt.image.BufferedImage;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageAssert#doesNotSatisfy(Condition)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssert_doesNotSatisfy_Test implements GenericAssert_doesNotSatisfy_TestCase {

  @Test
  public void should_pass_if_condition_is_not_satisfied() {
    new ImageAssert(null).doesNotSatisfy(notNullImage());
  }

  @Test
  public void should_throw_error_if_condition_is_null() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).doesNotSatisfy(null);
      }
    });
  }

  @Test
  public void should_fail_if_condition_is_satisfied() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("actual value:<", image, "> should not satisfy condition:<NotNull>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).doesNotSatisfy(notNullImage());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_condition_is_satisfied() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("[A Test] actual value:<", image, "> should not satisfy condition:<NotNull>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).as("A Test").doesNotSatisfy(notNullImage());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_condition_if_condition_is_satisfied() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("actual value:<", image, "> should not satisfy condition:<Not Null>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).doesNotSatisfy(notNullImage().as("Not Null"));
      }
    });
  }

  @Test
  public void should_fail_and_display_descriptions_of_assertion_and_condition_if_condition_is_satisfied() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("[A Test] actual value:<", image, "> should not satisfy condition:<Not Null>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).as("A Test").doesNotSatisfy(notNullImage().as("Not Null"));
      }
    });
  }
}
