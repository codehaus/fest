/*
 * Created on Oct 13, 2008
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

import org.testng.annotations.Test;

import org.fest.swing.data.TableCell;

import static org.fest.swing.test.builder.JTables.table;

/**
 * Tests for <code>{@link JTableCellValidator}</code>.
 *
 * @author Alex Ruiz
 */
public class JTableCellValidatorTest {

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfTableIsEmpty() {
    TableCell cell = TableCell.row(2).column(3);
    JTableCellValidator.validateCellIndices(table().createNew(), cell);
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowIndexIsNegative() {
    TableCell cell = TableCell.row(-2).column(3);
    JTableCellValidator.validateCellIndices(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfColumnIndexIsNegative() {
    TableCell cell = TableCell.row(2).column(-3);
    JTableCellValidator.validateCellIndices(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }
  
  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowIsOutOfBounds() {
    TableCell cell = TableCell.row(4).column(2);
    JTableCellValidator.validateCellIndices(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }  
  
  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfColumnIsOutOfBounds() {
    TableCell cell = TableCell.row(0).column(3);
    JTableCellValidator.validateCellIndices(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }  
}
