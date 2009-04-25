/*
 * Created on Apr 24, 2009
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
package org.fest.swing.junit.runner;

import org.junit.internal.runners.InitializationError;

/**
 * Understands a JUnit 4.3.1 test runner that takes a screenshot of a failed GUI test.
 * @deprecated use <code>{@link org.fest.swing.junit.v4_3_1.runner.GUITestRunner}</code> instead.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Deprecated
public class GUITestRunner extends org.fest.swing.junit.v4_3_1.runner.GUITestRunner {

  /**
   * Creates a new <code>{@link GUITestRunner}</code>.
   * @param testClass the class containing the tests to run.
   * @throws InitializationError if something goes wrong when creating this runner.
   */
  @Deprecated
  public GUITestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

}
