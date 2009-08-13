/*
 * Created on Aug 1, 2009
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
package org.fest.swing.test.core;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.test.EqualsHashCodeContractTestCase;

/**
 * Understands assert methods for <code>{@link EqualsHashCodeContractTestCase}</code>.
 *
 * @author Alex Ruiz
 */
public final class EqualsHashCodeContractAssert {

  public static void assertIsNotEqualToNull(Object obj) {
    assertThat(obj).isNotNull();
  }

  public static void assertEqualsIsReflexive(Object obj) {
    assertThat(obj).isEqualTo(obj);
  }

  public static void assertEqualsIsSymmetric(Object obj1, Object obj2) {
    assertThat(obj1).isEqualTo(obj2);
    assertThat(obj2).isEqualTo(obj1);
  }

  public static void assertEqualsIsTransitive(Object obj1, Object obj2, Object obj3) {
    assertThat(obj1).isEqualTo(obj2);
    assertThat(obj2).isEqualTo(obj3);
    assertThat(obj1).isEqualTo(obj3);
  }

  public static void assertMaintainsEqualsAndHashCodeContract(Object obj1, Object obj2) {
    assertThat(obj1).isEqualTo(obj2);
    assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode());
  }

  private EqualsHashCodeContractAssert() {}
}
