/*
 * Created on 25 Jul, 2009
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
 * Interface of any strategy used by <code>{@link TestFactoryToCheckInterfaceContracts}</code>
 * to actually verify the contract of Assert-interfaces like <code>{@link NullableAssert}</code>.
 * 
 * Verification strategies may be implemented statefully. Before each check, the client code will
 * call {@link #reset()}.
 *
 * @author Ansgar Konermann 
 *
 */
interface VerificationStrategy<ASSERT_INTERFACE> {
  
  /**
   * Resets this verification strategy instance. May be implemented empty if this
   * strategy is stateless.
   */
  public void reset();
  
  /**
   * Checks whether the given interfaceImplementer instance behaves correctly
   * according to the contract defined by ASSERT_INTERFACE.
   * 
   * @param interfaceImplementer the object to check; is an instance of a class
   *    implementing ASSERT_INTERFACE.
   *     
   * @return true if instance obeys contract, false otherwise.
   */
  // TODO AK: return a result object allowing for more detailed failure information 
  public boolean isObeyingContract(ASSERT_INTERFACE interfaceImplementer);
  

}
