/*
 * Created on Feb 7, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.test;

import static org.fest.test.ExpectedFailure.expect;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ExpectedFailure}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ExpectedFailureTest {

  public void shouldPassIfErrorTypeAndMessageMatchExpected() {
    expect(IllegalArgumentException.class).withMessage("A Test").on(new CodeToTest() {
      public void run() throws Exception {
        throw new IllegalArgumentException("A Test");
      }
    });
  }

  public void shouldFailIfErrorTypeIsNotEqualToExpected() {
    try {
      expect(IndexOutOfBoundsException.class).withMessage("A Test").on(new CodeToTest() {
        public void run() throws Exception {
          throw new IllegalArgumentException("A Test");
        }
      });
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(),
          "Expecting exception of type:<java.lang.IndexOutOfBoundsException> but was:<java.lang.IllegalArgumentException>");
    }
  }

  public void shouldFailIfMessageIsNotEqualToExpected() {
    try {
      expect(IllegalArgumentException.class).withMessage("Some Test").on(new CodeToTest() {
        public void run() throws Exception {
          throw new IllegalArgumentException("A Test");
        }
      });
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(),"Expecting message:<'Some Test'> but was:<'A Test'>");
    }
  }
}
