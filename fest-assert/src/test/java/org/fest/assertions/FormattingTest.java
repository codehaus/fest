/*
 * Created on Sep 16, 2007
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
package org.fest.assertions;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FormattingTest {

  @Test public void shouldReturnEmptyStringIfMessageIsNull() {
    assertEquals(Formatting.format(null), "");
  }
  
  @Test public void shouldReturnEmptyStringIfMessageIsEmpty() {
    assertEquals(Formatting.format(""), "");
  }
  
  @Test public void shouldReturnFormattedMessageIfMessageIsNotEmpty() {
    assertEquals(Formatting.format("some message"), "[some message] ");
  }
  
  @Test public void shouldFormatObject() {
    assertEquals(Formatting.inBrackets(new Integer(3)), "<3>");
  }
  
  @Test public void shouldFormatString() {
    assertEquals(Formatting.inBrackets("Yoda"), "<'Yoda'>");
  }

  @Test public void shouldFormatNullObject() {
    assertEquals(Formatting.inBrackets((Object)null), "<null>");
  }
  
  @Test public void shouldFormatObjectArray() {
    Object o = new Object[] { "First", 3 };
    assertEquals(Formatting.inBrackets(o), "<['First', 3]>");
  }
  
  @Test public void shouldFormatBooleanArray() {
    Object o = new boolean[] { true, false };
    assertEquals(Formatting.inBrackets(o), "<[true, false]>");
  }
  
  @Test public void shouldFormatByteArray() {
    Object o = new byte[] { (byte)3, (byte)8 };
    assertEquals(Formatting.inBrackets(o), "<[3, 8]>");
  }
  
  @Test public void shouldFormatCharArray() {
    Object o = new char[] { 'a', 'b' };
    assertEquals(Formatting.inBrackets(o), "<[a, b]>");
  }

  @Test public void shouldFormatDoubleArray() {
    Object o = new double[] { 6.8, 8.3 };
    assertEquals(Formatting.inBrackets(o), "<[6.8, 8.3]>");
  }

  @Test public void shouldFormatFloatArray() {
    Object o = new float[] { 6.1f, 8.6f };
    assertEquals(Formatting.inBrackets(o), "<[6.1, 8.6]>");
  }

  @Test public void shouldFormatIntArray() {
    Object o = new int[] { 78, 66 };
    assertEquals(Formatting.inBrackets(o), "<[78, 66]>");
  }

  @Test public void shouldFormatLongArray() {
    Object o = new long[] { 160l, 98l };
    assertEquals(Formatting.inBrackets(o), "<[160, 98]>");
  }

  @Test public void shouldFormatShortArray() {
    Object o = new short[] { (short)5, (short)8 };
    assertEquals(Formatting.inBrackets(o), "<[5, 8]>");
  }
}
