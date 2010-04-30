/*
 * Created on Dec 21, 2009
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
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.security;

import static org.fest.test.ExpectedFailure.expect;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link NoExitSecurityManager#NoExitSecurityManager(ExitCallHook)}</code>.
 *
 * @author Alex Ruiz
 */
public class NoExitSecurityManager_constructor_withHook_Test {

  @Test
  public void should_throw_error_if_hook_is_null() {
    expect(NullPointerException.class).withMessage("The given ExitCallHook should not be null").on(new CodeToTest() {
      public void run() {
        new NoExitSecurityManager(null);
      }
    });
  }
}
