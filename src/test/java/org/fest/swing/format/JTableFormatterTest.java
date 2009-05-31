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

import javax.swing.JTable;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTables.table;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link JTableFormatter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JTableFormatterTest {

  private JTable table;
  private JTableFormatter formatter;

  @BeforeClass public void setUp() {
    table = table().withRowCount(8)
                   .withColumnCount(6)
                   .withName("table")
                   .withSelectionMode(MULTIPLE_INTERVAL_SELECTION)
                   .createNew();
    formatter = new JTableFormatter();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJTable() {
    formatter.format(textField().createNew());
  }

  public void shouldFormatJTable() {
    String formatted = formatter.format(table);
    assertThat(formatted).contains(table.getClass().getName())
                         .contains("name='table'")
                         .contains("rowCount=8")
                         .contains("columnCount=6")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
