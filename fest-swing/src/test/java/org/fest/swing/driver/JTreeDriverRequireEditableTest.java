/*
 * Created on Jul 16, 2009
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
import static org.fest.swing.driver.JTreeSetEditableTask.setEditable;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#requireEditable(javax.swing.JTree)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverRequireEditableTest extends JTreeDriverTestCase {

  public void shouldPassIfTreeIsEditable() {
    setEditable(tree(), true);
    robot().waitForIdle();
    driver().requireEditable(tree());
  }

  public void shouldFailIfTreeIsNotEditableAndExpectingEditable() {
    setEditable(tree(), false);
    robot().waitForIdle();
    try {
      driver().requireEditable(tree());
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'")
                                .contains("expected:<true> but was:<false>");
    }
  }
}
