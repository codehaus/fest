/*
 * Created on Mar 11, 2008
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
 * Copyright @2008-2010 the original author or authors.
 */
package org.fest.swing.driver;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.swing.JOptionPaneLauncher.pack;

import javax.swing.JOptionPane;

import org.fest.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for <code>{@link JOptionPaneDriver#requireWarningMessage(JOptionPane)}</code>.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneDriver_requireWarningMessage_Test extends JOptionPaneDriver_TestCase {

  @Test
  public void should_pass_if_error_type_is_equal_to_expected() {
    JOptionPane optionPane = warningMessage();
    pack(optionPane, title());
    driver.requireWarningMessage(optionPane);
  }

  @RunsInEDT
  private static JOptionPane warningMessage() {
    return messageOfType(WARNING_MESSAGE);
  }

  @Test
  public void should_fail_if_error_type_is_not_equal_to_expected() {
    JOptionPane optionPane = errorMessage();
    pack(optionPane, title());
    try {
      driver.requireWarningMessage(optionPane);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'messageType'")
                                .contains("expected:<'[Warning] Message'> but was:<'[Error] Message'>");
    }
  }
}
