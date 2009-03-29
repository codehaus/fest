/*
 * Created on Mar 29, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.util;

import static org.fest.util.Strings.quote;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ArrayFormatter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ArrayFormatterTest {

  private ArrayFormatter formatter;

  @BeforeClass public void setUpOnce() {
    formatter = new ArrayFormatter();
  }

  public void shouldReturnNullIfArrayIsNull() {
    assertNull(formatter.format(null));
  }

  public void shouldReturnNullIfObjectIsNotArray() {
    assertNull(formatter.format("Hello"));
  }

  public void shouldFormatBooleanArray() {
    assertEquals(formatter.format(new boolean[] { true, false, true }), "[true, false, true]");
  }

  public void shouldFormatCharArray() {
    assertEquals(formatter.format(new char[] { 'a', 'b', 'c' }), "[a, b, c]");
  }

  public void shouldFormatByteArray() {
    assertEquals(formatter.format(new byte[] { (byte)6, (byte)8 }), "[6, 8]");
  }

  public void shouldFormatShortArray() {
    assertEquals(formatter.format(new short[] { (short)6, (short)8 }), "[6, 8]");
  }

  public void shouldFormatIntArray() {
    assertEquals(formatter.format(new int[] { 6, 8 }), "[6, 8]");
  }

  public void shouldFormatLongArray() {
    assertEquals(formatter.format(new long[] { 6l, 8l }), "[6, 8]");
  }

  public void shouldFormatFloatArray() {
    assertEquals(formatter.format(new float[] { 6f, 8f }), "[6.0, 8.0]");
  }

  public void shouldFormatDoubleArray() {
    assertEquals(formatter.format(new double[] { 6d, 8d }), "[6.0, 8.0]");
  }

  public void shouldFormatStringArray() {
    assertEquals(formatter.format(new Object[] { "Hello", "World" }), "['Hello', 'World']");
  }

  public void shouldFormatArrayWithNullElement() {
    assertEquals(formatter.format(new Object[] { "Hello", null }), "['Hello', null]");
  }

  public void shouldFormatObjectArray() {
    assertEquals(formatter.format(new Object[] { "Hello", new Person("Anakin") }), "['Hello', 'Anakin']");
  }

  public void shouldFormatObjectArrayHavingPrimitiveArrayAsElement() {
    boolean booleans[] = { true, false };
    Object[] array = { "Hello", booleans };
    assertEquals(formatter.format(array), "['Hello', [true, false]]");
  }

  public void shouldFormatObjectArrayHavingSelfAsElement() {
    Object[] array1 = { "Hello", "World" };
    Object[] array2 = { array1 };
    array1[1] = array2;
    assertEquals(formatter.format(array2), "[['Hello', [...]]]");
  }

  private static class Person {
    private final String name;

    Person(String name) {
      this.name = name;
    }

    @Override public String toString() {
      return quote(name);
    }
  }
}
