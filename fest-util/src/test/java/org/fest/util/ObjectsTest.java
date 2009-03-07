/*
 * Created on Sep 23, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.util;

import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;
import static org.testng.Assert.*;

import java.util.Arrays;

import org.testng.annotations.Test;

/**
 * Tests for {@link Objects}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectsTest {

  @Test public void shouldReturnAreEqualIfBothObjectsAreNull() {
    assertTrue(Objects.areEqual(null, null));
  }
  
  @Test public void shouldReturnAreEqualIfObjectsAreEqual() {
    assertTrue(Objects.areEqual("Yoda", "Yoda"));
  }
  
  @Test public void shouldReturnAreNotEqualIfFirstObjectIsNullAndSecondIsNot() {
    assertFalse(Objects.areEqual(null, "Yoda"));
  }
  
  @Test public void shouldReturnAreNotEqualIfSecondObjectIsNullAndFirstIsNot() {
    assertFalse(Objects.areEqual("Yoda", null));
  }
  
  @Test public void shouldReturnAreNotEqualIfObjectsAreNotEqual() {
    assertFalse(Objects.areEqual("Yoda", 2));
  }
  
  @Test public void shouldReturnEmptyStringArrayIfClassArrayIsNull() {
    assertEquals(Objects.namesOf((Class<?>[])null).length, 0);
  }

  @Test public void shouldReturnEmptyStringArrayIfClassArrayIsEmpty() {
    assertEquals(Objects.namesOf(new Class<?>[0]).length, 0);
  }
  
  @Test public void shouldReturnClassNames() {
    String[] expected = { String.class.getName(), Integer.class.getName() };
    String[] actual = Objects.namesOf(String.class, Integer.class);
    assertTrue(Arrays.equals(expected, actual), concat("expected:<", Arrays.toString(expected), "> actual:<", 
        Arrays.toString(actual), ">"));
  }
  
  @Test public void shouldReturnSameArrayAsTheOnePassed() {
    String[] array = array("Yoda", "Luke");
    assertEquals(array.length, 2);
    assertEquals(array[0], "Yoda");
    assertEquals(array[1], "Luke");
  }

  @Test public void shouldReturnHashCodeFromObjectIfObjectIsNotNull() {
    String s = "Yoda";
    assertEquals(Objects.hashCodeFor(s), s.hashCode());
  }
  
  @Test public void shouldReturnZeroIfObjectIsNull() {
    assertEquals(Objects.hashCodeFor(null), 0);
  }

  @Test public void shouldReturnCastedObjectIfIsOfType() {
    Object o = "Frodo";
    String casted = Objects.castIfBelongsToType(o, String.class);
    assertSame(casted, o);
  }

  @Test public void shouldReturnNullIfObjectIsNotOfType() {
    Object o = 4;
    String casted = Objects.castIfBelongsToType(o, String.class);
    assertNull(casted);
  }
}
