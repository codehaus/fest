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
 * Declares methods to assert how the actual value relates to zero.
 *
 * @author Ansgar Konermann 
 *
 */
public interface RelateToZeroAssert<LEAFCLASS_TYPE> {

  /**
   * Verifies that the actual <code>byte</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not negative.
   */
  public LEAFCLASS_TYPE isNegative();
  
  /**
   * Verifies that the actual <code>byte</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not positive.
   */
  public LEAFCLASS_TYPE isPositive();
  
  /**
   * Verifies that the actual <code>byte</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not equal to zero.
   */
  public LEAFCLASS_TYPE isZero();
}
