/*
 * Created on Jul 22, 2008
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
package org.fest.swing.driver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.exception.ActionFailedException;

import static javax.swing.JOptionPane.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JOptionPaneMessageTypes}</code>.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneMessageTypesTest {

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorWhenReturningMessageTypeTextIfGivenMessageTypeValueIsInvalid() {
    JOptionPaneMessageTypes.messageTypeAsText(Integer.MIN_VALUE);
  }
  
  @Test(dataProvider = "messageTypes")
  public void shouldReturnTextForGivenMessageTypeValue(int messageType, String expected) {
    assertThat(JOptionPaneMessageTypes.messageTypeAsText(messageType)).isEqualTo(expected);
  }
  
  @DataProvider(name = "messageTypes") public Object[][] messageTypes() {
    return new Object[][] {
        { ERROR_MESSAGE, "Error Message" },
        { INFORMATION_MESSAGE, "Information Message" },
        { WARNING_MESSAGE, "Warning Message" },
        { QUESTION_MESSAGE, "Question Message" },
        { PLAIN_MESSAGE, "Plain Message" }
    };
  }
}
