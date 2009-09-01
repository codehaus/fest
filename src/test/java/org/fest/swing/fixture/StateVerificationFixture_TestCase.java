/*
 * Created on Jul 13, 2008
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
package org.fest.swing.fixture;


/**
 * Tests case for implementations of <code>{@link StateVerificationFixture}</code>
 * 
 * @author Alex Ruiz
 */
public interface StateVerificationFixture_TestCase {

  void should_require_enabled();

  void should_require_enabled_using_timeout();
  
  void should_require_disabled();
  
  void should_require_visible();

  void should_require_not_visible();

}