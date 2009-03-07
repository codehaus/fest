/*
 * Created on Feb 15, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.util.Arrays.array;
import static org.testng.Assert.*;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComparisonFailureFactory}</code>.
 *
 * @author Alex Ruiz
 */
public class ComparisonFailureFactoryTest {

  @AfterClass public void tearDown() {
    ComparisonFailureFactory.constructorInvoker(new ConstructorInvoker());
  }

  @Test public void shouldNotCreateExceptionIfActualIsNotString() {
    AssertionError error = ComparisonFailureFactory.comparisonFailure("message", "expected", 3);
    assertNull(error);
  }

  @Test public void shouldNotCreateExceptionIfExpectedIsNotString() {
    AssertionError error = ComparisonFailureFactory.comparisonFailure("message", 6, "actual");
    assertNull(error);
  }

  @Test public void shouldCreateExceptionIfActualAndExpectedAreString() {
    final ConstructorInvoker invoker = createMock(ConstructorInvoker.class);
    final AssertionError toReturn = new AssertionError();
    ComparisonFailureFactory.constructorInvoker(invoker);
    new EasyMockTemplate(invoker) {

      protected void expectations() {
        expect(createComparisonFailure(invoker)).andReturn(toReturn);
      }

      protected void codeToTest() {
        AssertionError created = ComparisonFailureFactory.comparisonFailure("message", "expected", "actual");
        assertSame(created, toReturn);
      }
    }.run();
  }

  @Test public void shouldReturnNullIfCreatedObjectIsNotAssertionError() {
    final ConstructorInvoker invoker = createMock(ConstructorInvoker.class);
    final Object toReturn = new Object();
    ComparisonFailureFactory.constructorInvoker(invoker);
    new EasyMockTemplate(invoker) {

      protected void expectations() {
        expect(createComparisonFailure(invoker)).andReturn(toReturn);
      }

      protected void codeToTest() {
        AssertionError created = ComparisonFailureFactory.comparisonFailure("message", "expected", "actual");
        assertNull(created);
      }
    }.run();
  }

  @Test public void shouldNotCreateExceptionIfExceptionConstructorCannotBeInvoked() {
    final ConstructorInvoker invoker = createMock(ConstructorInvoker.class);
    ComparisonFailureFactory.constructorInvoker(invoker);
    new EasyMockTemplate(invoker) {

      protected void expectations() {
        expect(createComparisonFailure(invoker)).andThrow(new Exception());
      }

      protected void codeToTest() {
        AssertionError created = ComparisonFailureFactory.comparisonFailure("message", "expected", "actual");
        assertNull(created);
      }
    }.run();
  }

  @SuppressWarnings("unchecked")
  private Object createComparisonFailure(final ConstructorInvoker invoker) {
    try {
      return invoker.newInstance(
          eq("org.junit.ComparisonFailure"),
          aryEq(array(String.class, String.class, String.class)),
          aryEq(array("message", "expected", "actual")));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
