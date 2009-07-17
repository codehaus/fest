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

import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTextComponentDriver#setText(javax.swing.text.JTextComponent, String)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTextComponentDriverSetTextTest extends JTextComponentDriverTestCase {

  public void shouldSetText() {
    setTextFieldText("");
    driver().setText(textField(), "Entering text");
    requireTextInTextField("Entering text");
  }

  public void shouldThrowErrorWhenSettingTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver().setText(textField(), "Entering text");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSettingTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver().setText(textField(), "Entering text");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
