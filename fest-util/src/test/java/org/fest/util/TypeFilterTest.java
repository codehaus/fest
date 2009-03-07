/*
 * Created on Nov 1, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.util;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TypeFilter}</code>.
 *
 * @author Yvonne Wang
 */
public class TypeFilterTest {

  @Test public void shouldFilterCollection() {
    List<Object> original = new ArrayList<Object>();
    original.add(1);
    original.add("Frodo");
    original.add(5);
    List<String> filtered = new TypeFilter<String>(String.class).filter(original);
    assertEquals(filtered.size(), 1);
    assertEquals(filtered.get(0), "Frodo");
  }
}
