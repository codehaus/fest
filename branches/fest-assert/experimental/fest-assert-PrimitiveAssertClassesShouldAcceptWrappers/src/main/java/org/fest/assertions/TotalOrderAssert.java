/*
 * Created on Jul 23, 2009
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
 * Copyright (c) 2009 the original author or authors.
 */

package org.fest.assertions;

/**
 * Defines methods to assert how the actual and expected value relate to each other
 * according to the basic total orders <code>&lt; &gt; &lt;= &gt;=</code>. 
 *
 * @author Ansgar Konermann 
 *
 */
public interface TotalOrderAssert<LEAFCLASS_TYPE> {
  
  /**
   * Verifies that the actual <code>byte</code> value is greater than the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not greater than the given one.
   */
  public LEAFCLASS_TYPE isGreaterThan(byte value);

  /**
   * Verifies that the actual <code>byte</code> value is greater or equal to the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not greater than or equal to the given one.
   */
  public LEAFCLASS_TYPE isGreaterThanOrEqualTo(byte value);

  
  /**
   * Verifies that the actual <code>byte</code> value is less than the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not less than the given one.
   */
  public LEAFCLASS_TYPE isLessThan(byte value);

  /**
   * Verifies that the actual <code>byte</code> value is less or equal to the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not less than or equal to the given one.
   */
  public LEAFCLASS_TYPE isLessThanOrEqualTo(byte value);
  
}
