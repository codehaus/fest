/*
 * Created on Jun 21, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.exception;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link ActionFailedException}</code>.
 *
 * @author Alex Ruiz
 */
public class ActionFailedExceptionTest {

  @Test public void shouldReturnCause() {
    Throwable cause = new Throwable();
    ActionFailedException actionFailure = ActionFailedException.actionFailure("A Failure", cause);
    assertThat(actionFailure.getCause()).isSameAs(cause);
  }
  
}
