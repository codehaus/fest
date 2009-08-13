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
 * Tests for <code>{@link Index}</code>.
 *
 * @author Alex Ruiz
 */
public class IndexTest implements EqualsHashCodeContractTestCase {

  private Index index;

  @Before public void setUp() {
    index = Index.atIndex(8);
  }

  @Test
  public void shouldHaveConsistentEquals() {
    Index other = Index.atIndex(8);
    assertThat(index.equals(other)).isTrue();
  }

  @Test
  public void shouldHaveReflexiveEquals() {
    assertEqualsIsReflexive(index);
  }

  @Test
  public void shouldHaveSymmetricEquals() {
    Index other = Index.atIndex(8);
    assertEqualsIsSymmetric(index, other);
  }

  @Test
  public void shouldHaveTransitiveEquals() {
    Index other1 = Index.atIndex(8);
    Index other2 = Index.atIndex(8);
    assertEqualsIsTransitive(index, other1, other2);
  }

  @Test
  public void shouldMaintainEqualsAndHashCodeContract() {
    Index other = Index.atIndex(8);
    assertMaintainsEqualsAndHashCodeContract(index, other);
  }

  @Test
  public void shouldNotBeEqualToNull() {
    assertThat(index.equals(null)).isFalse();
  }

  @Test
  public void shouldNotBeEqualToObjectNotBeingOfSameClass() {
    assertThat(index.equals("Hello")).isFalse();
  }

  @Test
  public void shouldReturnNotEqualIfIndexValuesAreNotEqual() {
    Index other = Index.atIndex(6);
    assertThat(index.equals(other)).isFalse();
  }

  @Test
  public void shouldImplementToString() {
    assertThat(index.toString()).isEqualTo("org.fest.swing.data.Index[value=8]");
  }
}
