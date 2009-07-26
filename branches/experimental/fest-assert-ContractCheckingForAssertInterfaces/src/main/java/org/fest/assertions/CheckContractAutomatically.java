/*
 * Created on 25.07.2009
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Presence of this annotation on any interface states that 
 * the interface defines a certain contract all implementing
 * classes must obey. There exists a TestNG test factory 
 * <code>{@link TestFactoryToCheckInterfaceContracts}</code>
 * which checks for this annotation and automatically verifies
 * the contract. For each interface which carries this annotation,
 * a verification strategy must be registered with the factory.  
 *
 * @author Ansgar Konermann 
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CheckContractAutomatically {

}
