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


/**
 * Tests for <code>{@link DoubleArrayAssert#isEqualTo(double[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class DoubleArrayAssert_isEqualTo_Test extends GenericAssert_isEqualTo_TestCase<double[]> {

  private static final double[] ACTUAL = { 6d, 8d };
  private static final double[] NOT_EQUAL = { 6d };

  protected DoubleArrayAssert assertObject() {
    return new DoubleArrayAssert(ACTUAL);
  }

  protected DoubleArrayAssert assertObjectWithNullTarget() {
    return new DoubleArrayAssert(null);
  }

  protected double[] notEqualValue() {
    return NOT_EQUAL;
  }
}
