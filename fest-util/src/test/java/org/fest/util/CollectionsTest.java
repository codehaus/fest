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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Collections}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionsTest {

  @Test public void shouldReturnNullIfArrayToConvertToListIsNull() {
    assertNull(Collections.list((Object[])null));
  }

  @Test public void shouldReturnEmptyListIfArrayIsEmpty() {
    assertTrue(Collections.list(new Object[0]).isEmpty());
  }

  @Test public void shouldReturnTrueIfCollectionIsEmpty() {
    assertTrue(Collections.isEmpty(new ArrayList<String>()));
  }

  @Test public void shouldReturnTrueIfCollectionIsNull() {
    assertTrue(Collections.isEmpty(null));
  }

  @Test public void shouldReturnFalseIfCollectionHasElements() {
    assertFalse(Collections.isEmpty(Collections.list("Frodo")));
  }

  @Test public void shouldReturnNullIfCollectionToFormatIsNull() {
    assertNull(Collections.format(null));
  }

  @Test public void shouldReturnEmptyBracketsIfCollectionToFormatIsEmpty() {
    assertEquals(Collections.format(new ArrayList<String>()), "[]");
  }

  @Test public void shouldFormatCollection() {
    List<Object> list = new ArrayList<Object>();
    list.add("First");
    list.add(3);
    assertEquals(Collections.format(list), "['First', 3]");
  }

  @Test public void shouldReturnDuplicatesIfTheyExist() {
    Collection<String> duplicates = Collections.duplicatesFrom(Collections.list("Frodo", "Sam", "Frodo"));
    assertEquals(duplicates.size(), 1);
    assertTrue(duplicates.contains("Frodo"));
  }

  @Test public void shouldNotReturnDuplicatesIfTheyDoNotExist() {
    Collection<String> duplicates = Collections.duplicatesFrom(Collections.list("Frodo", "Sam", "Gandalf"));
    assertTrue(duplicates.isEmpty());
  }

  @Test public void shouldNotReturnDuplicatesIfCollectionIsEmpty() {
    Collection<String> duplicates = Collections.duplicatesFrom(new ArrayList<String>());
    assertTrue(duplicates.isEmpty());
  }

  @Test public void shouldNotReturnDuplicatesIfCollectionIsNull() {
    Collection<String> duplicates = Collections.duplicatesFrom(null);
    assertTrue(duplicates.isEmpty());
  }

  @Test public void shouldFilterCollection() {
    class FilterStub implements CollectionFilter<Object> {
      private final List<Object> expectedResult;

      FilterStub(List<Object> expectedResult) {
        this.expectedResult = expectedResult;
      }

      public List<Object> filter(Collection<?> target) {
        return expectedResult;
      }
    }
    List<Object> expectedResult = new ArrayList<Object>();
    FilterStub filter = new FilterStub(expectedResult);
    List<Object> filtered = Collections.filter(new ArrayList<Object>(), filter);
    assertSame(filtered, expectedResult);
  }
}
