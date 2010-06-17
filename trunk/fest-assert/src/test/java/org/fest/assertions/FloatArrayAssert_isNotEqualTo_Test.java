/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ArrayFactory.floatArray;

import org.junit.BeforeClass;

/**
 * Tests for <code>{@link FloatArrayAssert#isNotEqualTo(float[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssert_isNotEqualTo_Test extends GenericAssert_isNotEqualTo_TestCase<float[]> {

  private static float[] actual;
  private static float[] notEqualValue;

  @BeforeClass
  public static void setUpOnce() {
    actual = floatArray(6f);
    notEqualValue = floatArray(8f);
  }

  protected FloatArrayAssert assertObject() {
    return new FloatArrayAssert(actual);
  }

  protected float[] notEqualValue() {
    return notEqualValue;
  }
}
