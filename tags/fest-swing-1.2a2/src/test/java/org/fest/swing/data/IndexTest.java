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
import static org.fest.test.EqualsHashCodeContractAssert.*;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Index}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class IndexTest implements EqualsHashCodeContractTestCase {

  private Index index;

  @BeforeMethod public void setUp() {
    index = Index.atIndex(8);
  }

  /** {@inheritDoc} */
  public void shouldHaveConsistentEquals() {
    Index other = Index.atIndex(8);
    assertThat(index.equals(other)).isTrue();
  }

  /** {@inheritDoc} */
  public void shouldHaveReflexiveEquals() {
    assertEqualsIsReflexive(index);
  }

  /** {@inheritDoc} */
  public void shouldHaveSymmetricEquals() {
    Index other = Index.atIndex(8);
    assertEqualsIsSymmetric(index, other);
  }

  /** {@inheritDoc} */
  public void shouldHaveTransitiveEquals() {
    Index other1 = Index.atIndex(8);
    Index other2 = Index.atIndex(8);
    assertEqualsIsTransitive(index, other1, other2);
  }

  /** {@inheritDoc} */
  public void shouldMaintainEqualsAndHashCodeContract() {
    Index other = Index.atIndex(8);
    assertMaintainsEqualsAndHashCodeContract(index, other);
  }

  /** {@inheritDoc} */
  public void shouldNotBeEqualToNull() {
    assertThat(index.equals(null)).isFalse();
  }

  /** {@inheritDoc} */
  public void shouldNotBeEqualToObjectNotBeingOfSameClass() {
    assertThat(index.equals("Hello")).isFalse();
  }

  public void shouldReturnNotEqualIfIndexValuesAreNotEqual() {
    Index other = Index.atIndex(6);
    assertThat(index.equals(other)).isFalse();
  }

  public void shouldImplementToString() {
    assertThat(index.toString()).isEqualTo("org.fest.swing.data.Index[value=8]");
  }
}