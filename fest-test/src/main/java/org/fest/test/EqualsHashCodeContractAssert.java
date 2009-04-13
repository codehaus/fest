/*
 * Created on Apr 12, 2009
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
package org.fest.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * Understands assert methods for <code>{@link EqualsHashCodeContractTestCase}</code>.
 *
 * @author Alex Ruiz
 */
public final class EqualsHashCodeContractAssert {

  /**
   * @see EqualsHashCodeContractTestCase#shouldNotBeEqualToNull()
   */
  public static void assertIsNotEqualToNull(Object obj) {
    assertFalse(obj.equals(null));
  }

  /**
   * @see EqualsHashCodeContractTestCase#equalsShouldBeReflexive()
   */
  public static void assertEqualsIsReflexive(Object obj) {
    assertEquals(obj, obj);
  }

  /**
   * @see EqualsHashCodeContractTestCase#equalsShouldBeSymmetric()
   */
  public static void assertEqualsIsSymmetric(Object obj1, Object obj2) {
    assertEquals(obj1, obj2);
    assertEquals(obj2, obj1);
  }

  /**
   * @see EqualsHashCodeContractTestCase#equalsShouldBeTransitive()
   */
  public static void assertEqualsIsTransitive(Object obj1, Object obj2, Object obj3) {
    assertEquals(obj1, obj2);
    assertEquals(obj2, obj3);
    assertEquals(obj1, obj3);
  }

  /**
   * @see EqualsHashCodeContractTestCase#shouldMaintainEqualsAndHashCodeContract()
   */
  public static void assertMaintainsEqualsAndHashCodeContract(Object obj1, Object obj2) {
    assertEquals(obj1, obj2);
    assertEquals(obj1.hashCode(), obj2.hashCode());
  }

  private EqualsHashCodeContractAssert() {}
}
