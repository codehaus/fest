/*
 * Created on Mar 24, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.format;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link IntEnum}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class IntEnumTest {

  private IntEnum intEnum;
  
  @BeforeClass public void setUp() {
    intEnum = new IntEnum();
    intEnum.put(0, "Zero");
  }
  
  public void shouldReturnValueForExistingKey() {
    assertThat(intEnum.get(0)).isEqualTo("Zero");
  }

  public void shouldReturnKeyForNotExistingKey() {
    assertThat(intEnum.get(1)).isEqualTo("1");
  }
}
