/*
 * Created on Apr 30, 2009
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
package org.fest.swing.data;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.EqualsHashCodeContractAssert.*;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link TableCell}</code>
 *
 * @author Alex Ruiz
 */
public class TableCellTest implements EqualsHashCodeContractTestCase {

  private TableCell cell;

  @Before public void setUp() {
    cell = TableCell.row(6).column(8);
  }

  @Test
  public void shouldHaveConsistentEquals() {
    TableCell other = TableCell.row(6).column(8);
    assertThat(cell.equals(other)).isTrue();
  }

  @Test
  public void shouldHaveReflexiveEquals() {
    assertEqualsIsReflexive(cell);
  }

  @Test
  public void shouldHaveSymmetricEquals() {
    TableCell other = TableCell.row(6).column(8);
    assertEqualsIsSymmetric(cell, other);
  }

  @Test
  public void shouldHaveTransitiveEquals() {
    TableCell other1 = TableCell.row(6).column(8);
    TableCell other2 = TableCell.row(6).column(8);
    assertEqualsIsTransitive(cell, other1, other2);
  }

  @Test
  public void shouldMaintainEqualsAndHashCodeContract() {
    TableCell other = TableCell.row(6).column(8);
    assertMaintainsEqualsAndHashCodeContract(cell, other);
  }

  @Test
  public void shouldNotBeEqualToNull() {
    assertThat(cell.equals(null)).isFalse();
  }

  @Test
  public void shouldNotBeEqualToObjectNotBeingOfSameClass() {
    assertThat(cell.equals("Hello")).isFalse();
  }

  @Test
  public void shouldReturnNotEqualIfRowValuesAreNotEqual() {
    TableCell other = TableCell.row(8).column(8);
    assertThat(cell.equals(other)).isFalse();
  }

  @Test
  public void shouldReturnNotEqualIfColumnValuesAreNotEqual() {
    TableCell other = TableCell.row(6).column(6);
    assertThat(cell.equals(other)).isFalse();
  }

  @Test
  public void shouldImplementToString() {
    assertThat(cell.toString()).isEqualTo("[row=6, column=8]");
  }
}
