/*
 * Created on Mar 1, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ArrayFactory.objectArray;

import org.junit.BeforeClass;

/**
 * Tests for <code>{@link ObjectArrayAssert#isNotEqualTo(Object[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssert_isNotEqualTo_Test extends GenericAssert_isNotEqualTo_TestCase<Object[]> {

  private static Object[] actual;
  private static Object[] notEqualValue;

  @BeforeClass
  public static void setUpOnce() {
    actual = objectArray(6, 8);
    notEqualValue = objectArray(6);
  }

  protected ObjectArrayAssert assertObject() {
    return new ObjectArrayAssert(actual);
  }

  protected Object[] notEqualValue() {
    return notEqualValue;
  }
}
