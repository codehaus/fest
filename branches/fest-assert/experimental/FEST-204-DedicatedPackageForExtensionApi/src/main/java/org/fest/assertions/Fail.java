/*
 * Created on Mar 19, 2007
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

/**
 * Provides failure methods for use in tests.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Ansgar Konermann
 */
public class Fail {

  /**
   * Fails with no message.
   * @throws AssertionError without any message.
   */
  public static void fail() {
    throw new AssertionError();
  }

  /**
   * Throws an {@link AssertionError} with the given message an with the <code>{@link Throwable}</code> that caused the
   * failure.
   * @param message error message.
   * @param realCause cause of the error.
   */
  public static void fail(String message, Throwable realCause) {
    AssertionError error = new AssertionError(message);
    error.initCause(realCause);
    throw error;
  }

  /**
   * Fails with the given message.
   * @param message error message.
   * @return the thrown <code>AssertionError</code>.
   * @throws AssertionError with the given message.
   */
  public static void fail(String message) {
    throw new AssertionError(message);
  }

  protected Fail() {}
}
