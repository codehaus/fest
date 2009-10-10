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

import static java.awt.Color.BLUE;
import static java.awt.Color.YELLOW;
import static org.fest.assertions.Images.fivePixelBlueImage;
import static org.fest.assertions.Images.image;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import java.awt.image.BufferedImage;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageAssert#isNotEqualTo(BufferedImage)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssert_isNotEqualTo_Test implements Assert_isNotEqualTo_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_not_equal() {
    BufferedImage a = image(3, 5, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test
  public void should_pass_if_actual_is_not_null_and_expected_is_null() {
    BufferedImage a = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(null);
  }

  @Test
  public void should_pass_if_width_is_not_equal() {
    BufferedImage a = image(3, 5, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test
  public void should_pass_if_height_is_not_equal() {
    BufferedImage a = image(5, 3, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test
  public void should_pass_if_color_is_not_equal() {
    BufferedImage a = fivePixelBlueImage();
    BufferedImage e = image(5, 5, YELLOW);
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test
  public void should_fail_if_both_actual_and_expected_are_null() {
    expectAssertionError("actual value:<null> should not be equal to:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).isNotEqualTo(null);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_both_actual_and_expected_are_null() {
    expectAssertionError("[A Test] actual value:<null> should not be equal to:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test")
                             .isNotEqualTo(null);
      }
    });
  }

  @Test
  public void should_fail_if_actual_and_expected_are_equal() {
    expectAssertionError("images are equal").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).isNotEqualTo(fivePixelBlueImage());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_equal() {
    expectAssertionError("[A Test] images are equal").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).as("A Test")
                                             .isNotEqualTo(fivePixelBlueImage());
      }
    });
  }
}
