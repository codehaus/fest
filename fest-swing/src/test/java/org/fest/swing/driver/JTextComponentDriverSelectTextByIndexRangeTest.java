/*
 * Created on Jul 17, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.fest.swing.exception.ActionFailedException;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTextComponentDriver#selectText(javax.swing.text.JTextComponent, int, int)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTextComponentDriverSelectTextByIndexRangeTest extends JTextComponentDriverTestCase {

  public void shouldSelectTextRange() {
    driver().selectText(textField(), 8, 14);
    requireSelectedTextInTextField("a test");
  }

  public void shouldThrowErrorWhenSelectingTextRangeInDisabledJTextComponent() {
    disableTextField();
    try {
      driver().selectText(textField(), 8, 14);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingTextRangeInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver().selectText(textField(), 8, 14);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorIfIndicesAreOutOfBoundsWhenSelectingText() {
    try {
      driver().selectText(textField(), 20, 22);
      failWhenExpectingException();
    } catch (ActionFailedException expected) {
      assertThat(expected.getMessage()).contains("Unable to get location for index '20' in javax.swing.JTextField");
    }
  }
}
