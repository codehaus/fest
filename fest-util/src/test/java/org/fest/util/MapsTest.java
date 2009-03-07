/*
 * Created on Apr 29, 2007
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

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Maps}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class MapsTest {

  @Test public void shouldReturnTrueIfMapIsEmpty() {
    assertTrue(Maps.isEmpty(new HashMap<String, String>()));
  }

  @Test public void shouldReturnTrueIfMapIsNull() {
    assertTrue(Maps.isEmpty(null));
  }

  @Test public void shouldReturnFalseIfMapHasElements() {
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("First", 1);
    assertFalse(Maps.isEmpty(map));
  }

  @Test public void shouldReturnNullIfMapToFormatIsNull() {
    assertNull(Maps.format(null));
  }

  @Test public void shouldReturnEmptyBracketsIfMapToFormatIsEmpty() {
    assertEquals(Maps.format(new HashMap<String, String>()), "{}");
  }

  @Test public void shouldFormatMap() {
    Map<String, Integer> map = new LinkedHashMap<String, Integer>();
    map.put("First", 1);
    map.put("Second", 2);
    assertEquals(Maps.format(map), "{'First'=1, 'Second'=2}");
  }
}
