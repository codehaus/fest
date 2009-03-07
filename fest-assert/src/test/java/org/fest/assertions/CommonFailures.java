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

import static org.fest.test.ExpectedFailure.*;

import org.fest.test.CodeToTest;
import org.fest.test.ExpectedFailure.Message;

/**
 * Understands common, expected test failures.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class CommonFailures {

  public static void expectAssertionErrorIfObjectIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null object, but it was null").on(codeToTest);
  }

  public static void expectAssertionErrorWithDescriptionIfObjectIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null object, but it was null").on(codeToTest);
  }

  public static void expectAssertionErrorIfArrayIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null array, but it was null").on(codeToTest);
  }

  public static void expectAssertionErrorWithDescriptionIfArrayIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null array, but it was null").on(codeToTest);
  }

  public static Message expectIllegalArgumentExceptionIfConditionIsNull() {
    return expectIllegalArgumentException("Condition to check should be null");
  }

  public static Message expectIllegalArgumentException(String message) {
    return expect(IllegalArgumentException.class).withMessage(message);
  }

  private CommonFailures() {}

}
