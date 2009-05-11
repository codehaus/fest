/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JOptionPane;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static javax.swing.JOptionPane.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JOptionPanes.optionPane;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link JOptionPaneFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class JOptionPaneFormatterTest {

  private JOptionPane optionPane;
  private JOptionPaneFormatter formatter;
  
  @BeforeClass public void setUp() {
    optionPane = optionPane().withMessage("A message")
                             .withMessageType(ERROR_MESSAGE)
                             .withOptionType(DEFAULT_OPTION)
                             .createNew();
    formatter = new JOptionPaneFormatter();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJOptionPane() {
    formatter.format(textField().createNew());
  }

  public void shouldFormatJOptionPane() {
    String formatted = formatter.format(optionPane);
    assertThat(formatted).contains(optionPane.getClass().getName())
                         .contains("message='A message'")     
                         .contains("messageType=ERROR_MESSAGE")     
                         .contains("optionType=DEFAULT_OPTION")     
                         .contains("enabled=true")     
                         .contains("visible=true")     
                         .contains("showing=false");     
  }
}
