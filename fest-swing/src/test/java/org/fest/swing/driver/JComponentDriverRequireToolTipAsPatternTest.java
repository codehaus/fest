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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.util.regex.Pattern;

import javax.swing.JComponent;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JComponentDriver#requireToolTip(JComponent, java.util.regex.Pattern)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JComponentDriverRequireToolTipAsPatternTest extends JComponentDriverTestCase {

  public void shouldPassIfToolTipMatchesPattern() {
    driver().requireToolTip(button(), regex("A Tool.*"));
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPatternToMatchIsNull() {
    Pattern p = null;
    driver().requireToolTip(button(), p);
  }

  public void shouldFailIfToolTipIsNotEqualToExpected() {
    try {
      driver().requireToolTip(button(), regex("Hello"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'toolTipText'")
                                .contains("actual value:<'A ToolTip'> does not match pattern:<'Hello'>");
    }
  }
}
