/*
 * Created on Jul 22, 2009
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
 * Declares methods to assert a reference for nullness or non-nullness.
 *
 * @author Ansgar Konermann 
 *
 */
public interface NullableAssert<LEAFCLASS_TYPE> {

  /**
   * Asserts that the actual value is a <code>null</code> pointer.
   * @throws AssertionError if the actual value is not a <code>null</code> pointer.
   */
  public void isNull();

  /**
   * Verifies that the actual value is not a <code>null</code> pointer.
   * @return this assertion object.
   * @throws AssertionError if the actual value is a <code>null</code> pointer.
   */
  public LEAFCLASS_TYPE isNotNull();

}
