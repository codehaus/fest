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
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.driver;

import static org.easymock.classextension.EasyMock.createMock;

import javax.swing.text.JTextComponent;

import org.fest.swing.core.Robot;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link JTextComponentDriver#replaceText(javax.swing.text.JTextComponent, String)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_replaceText_withInvalidInput_Test extends EDTSafeTestCase {

  private JTextComponentDriver driver;
  private JTextComponent textField;

  @Before public void setUp() {
    driver = new JTextComponentDriver(createMock(Robot.class));
    textField = createMock(JTextComponent.class);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_replacement_text_is_null() {
    driver.replaceText(textField, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_replacement_text_is_empty() {
    driver.replaceText(textField, "");
  }
}