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
public class TableCellByColumnIdTest implements EqualsHashCodeContractTestCase {

  private TableCellByColumnId cell;

  @Before public void setUp() {
    cell = TableCellByColumnId.row(6).columnId("id");
  }

  @Test
  public void shouldHaveConsistentEquals() {
    TableCellByColumnId other = TableCellByColumnId.row(6).columnId("id");
    assertThat(cell.equals(other)).isTrue();
  }

  @Test
  public void shouldHaveReflexiveEquals() {
    assertEqualsIsReflexive(cell);
  }

  @Test
  public void shouldHaveSymmetricEquals() {
    TableCellByColumnId other = TableCellByColumnId.row(6).columnId("id");
    assertEqualsIsSymmetric(cell, other);
  }

  @Test
  public void shouldHaveTransitiveEquals() {
    TableCellByColumnId other1 = TableCellByColumnId.row(6).columnId("id");
    TableCellByColumnId other2 = TableCellByColumnId.row(6).columnId("id");
    assertEqualsIsTransitive(cell, other1, other2);
  }

  @Test
  public void shouldMaintainEqualsAndHashCodeContract() {
    TableCellByColumnId other = TableCellByColumnId.row(6).columnId("id");
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
    TableCellByColumnId other = TableCellByColumnId.row(8).columnId("id");
    assertThat(cell.equals(other)).isFalse();
  }

  @Test
  public void shouldReturnNotEqualIfColumnValuesAreNotEqual() {
    TableCellByColumnId other = TableCellByColumnId.row(6).columnId("anotherId");
    assertThat(cell.equals(other)).isFalse();
  }

  @Test
  public void shouldImplementToString() {
    assertThat(cell.toString()).isEqualTo("[row=6, columnId='id']");
  }
}
