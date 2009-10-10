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

import static org.fest.assertions.Images.fivePixelBlueImage;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.concat;

import java.awt.image.BufferedImage;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageAssert#isNull()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssert_isNull_Test implements NullableAssert_isNull_TestCase {

  @Test
  public void should_pass_if_actual_is_null() {
    new ImageAssert(null).isNull();
  }

  @Test
  public void should_fail_if_actual_is_not_null() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("<", a, "> should be null")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isNull();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_null() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] <", a, "> should be null")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).as("A Test")
                          .isNull();
      }
    });
  }
}
