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
 * Tests for <code>{@link ImageAssert#isSameAs(BufferedImage)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssert_isSameAs_Test implements GenericAssert_isSameAs_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_same() {
    BufferedImage image = fivePixelBlueImage();
    new ImageAssert(image).isSameAs(image);
  }

  @Test
  public void should_fail_if_actual_and_expected_are_not_same() {
    final BufferedImage a = fivePixelBlueImage();
    final BufferedImage e = fivePixelBlueImage();
    expectAssertionError(concat("expected same instance but found:<", a, "> and:<", e, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isSameAs(e);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_not_same() {
    final BufferedImage a = fivePixelBlueImage();
    final BufferedImage e = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] expected same instance but found:<", a, "> and:<", e, ">")).on(
      new CodeToTest() {
        public void run() {
          new ImageAssert(a).as("A Test").isSameAs(e);
        }
      });
  }
}
