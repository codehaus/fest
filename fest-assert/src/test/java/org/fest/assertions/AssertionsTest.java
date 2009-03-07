/*
 * Created on Jan 10, 2007
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

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import org.fest.util.Files;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import static org.fest.assertions.Assertions.assertThat;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link Assertions}</code>.
 *
 * @author Yvonne Wang
 */
public class AssertionsTest {

  private void assertIsInstanceOf(Object target, Class<?> expectedType) {
    assertEquals(target.getClass(), expectedType);
  }

  @Test public void shouldReturnBigDecimalAssertIfArgumentIsBigDecimal() {
    assertIsInstanceOf(assertThat(new BigDecimal("8")), BigDecimalAssert.class);
  }

  @Test public void shouldReturnBooleanArrayAssertIfArgumentIsBooleanArray() {
    boolean[] booleans = new boolean[] { false };
    assertIsInstanceOf(assertThat(booleans), BooleanArrayAssert.class);
  }

  @Test public void shouldReturnBooleanAssertIfArgumentIsBoolean() {
    assertIsInstanceOf(assertThat(false), BooleanAssert.class);
  }

  @Test public void shouldReturnBooleanAssertIfArgumentIsBooleanWrapper() {
    assertIsInstanceOf(assertThat(Boolean.FALSE), BooleanAssert.class);
  }

  @Test public void shouldReturnByteArrayAssertIfArgumentIsByteArray() {
    byte[] bytes = new byte[] { 0 };
    assertIsInstanceOf(assertThat(bytes), ByteArrayAssert.class);
  }

  @Test public void shouldReturnByteAssertIfArgumentIsByte() {
    byte b = 0;
    assertIsInstanceOf(assertThat(b), ByteAssert.class);
  }

  @Test public void shouldReturnByteAssertIfArgumentIsByteWrapper() {
    byte b = 0;
    assertIsInstanceOf(assertThat(new Byte(b)), ByteAssert.class);
  }

  @Test public void shouldReturnCharArrayAssertIfArgumentIsCharArray() {
    char[] chars = new char[] { 0 };
    assertIsInstanceOf(assertThat(chars), CharArrayAssert.class);
  }

  @Test public void shouldReturnCharAssertIfArgumentIsChar() {
    assertIsInstanceOf(assertThat('a'), CharAssert.class);
  }

  @Test public void shouldReturnCharAssertIfArgumentIsCharacter() {
    assertIsInstanceOf(assertThat(new Character('a')), CharAssert.class);
  }

  @Test public void shouldReturnCollectionAssertIfArgumentIsCollection() {
    assertIsInstanceOf(assertThat(new ArrayList<Object>()), CollectionAssert.class);
  }

  @Test public void shouldReturnCollectionAssertIfArgumentIsIterator() {
    List<String> list = new ArrayList<String>();
    list.add("Frodo");
    CollectionAssert assertion = assertThat(list.iterator());
    assertEquals(list, assertion.actual);
  }

  @Test public void shouldReturnDoubleArrayAssertIfArgumentIsDoubleArray() {
    double[] doubles = new double[] { 0 };
    assertIsInstanceOf(assertThat(doubles), DoubleArrayAssert.class);
  }

  @Test public void shouldReturnDoubleAssertIfArgumentIsDouble() {
    assertIsInstanceOf(assertThat(86.0d), DoubleAssert.class);
  }

  @Test public void shouldReturnDoubleAssertIfArgumentIsDoubleWrapper() {
    assertIsInstanceOf(assertThat(new Double(86.0d)), DoubleAssert.class);
  }

  @Test public void shouldReturnFileAssertIfArgumentIsFile() {
    assertIsInstanceOf(assertThat(Files.temporaryFolder()), FileAssert.class);
  }

  @Test public void shouldReturnFloatArrayAssertIfArgumentIsFloatArray() {
    float[] floats = new float[] { 0f };
    assertIsInstanceOf(assertThat(floats), FloatArrayAssert.class);
  }

  @Test public void shouldReturnFloatAssertIfArgumentIsFloat() {
    assertIsInstanceOf(assertThat(86.0f), FloatAssert.class);
  }

  @Test public void shouldReturnFloatAssertIfArgumentIsFloatWrapper() {
    assertIsInstanceOf(assertThat(new Float(86.0f)), FloatAssert.class);
  }

  @Test public void shouldReturnGivenAssertExtension() {
    AssertExtension extension = new AssertExtension() {};
    assertSame(assertThat(extension), extension);
  }

  @Test public void shouldReturnImageAssertIfArgumentIsBufferedImage() {
    BufferedImage image = new BufferedImage(10, 10, TYPE_INT_RGB);
    assertIsInstanceOf(assertThat(image), ImageAssert.class);
  }

  @Test public void shouldReturnIntArrayAssertIfArgumentIsIntArray() {
    int[] ints = new int[] { 0 };
    assertIsInstanceOf(assertThat(ints), IntArrayAssert.class);
  }

  @Test public void shouldReturnIntAssertIfArgumentIsInt() {
    assertIsInstanceOf(assertThat(8), IntAssert.class);
  }

  @Test public void shouldReturnIntAssertIfArgumentIsInteger() {
    assertIsInstanceOf(assertThat(new Integer(8)), IntAssert.class);
  }

  @Test public void shouldReturnLongArrayAssertIfArgumentIsLongArray() {
    long[] longs = new long[] { 0 };
    assertIsInstanceOf(assertThat(longs), LongArrayAssert.class);
  }

  @Test public void shouldReturnLongAssertIfArgumentIsLong() {
    assertIsInstanceOf(assertThat(8l), LongAssert.class);
  }

  @Test public void shouldReturnLongAssertIfArgumentIsLongWrapper() {
    assertIsInstanceOf(assertThat(new Long(86)), LongAssert.class);
  }

  @Test public void shouldReturnMapAssertIfArgumentIsThrowable() {
    assertIsInstanceOf(assertThat(new HashMap<Object, Object>()), MapAssert.class);
  }

  @Test public void shouldReturnObjectArrayAssertIfArgumentIsObjectArray() {
    String[] objects = new String[] { "One" };
    assertIsInstanceOf(assertThat(objects), ObjectArrayAssert.class);
  }

  @Test public void shouldReturnObjectAssertIfArgumentIsObject() {
    assertIsInstanceOf(assertThat(new Object()), ObjectAssert.class);
  }

  @Test public void shouldReturnShortArrayAssertIfArgumentIsShortArray() {
    short[] shorts = new short[] { 0 };
    assertIsInstanceOf(assertThat(shorts), ShortArrayAssert.class);
  }

  @Test public void shouldReturnShortAssertIfArgumentIsShort() {
    short s = 8;
    assertIsInstanceOf(assertThat(s), ShortAssert.class);
  }

  @Test public void shouldReturnShortAssertIfArgumentIsShortWrapper() {
    short s = 8;
    assertIsInstanceOf(assertThat(new Short(s)), ShortAssert.class);
  }

  @Test public void shouldReturnStringAssertIfArgumentIsString() {
    assertIsInstanceOf(assertThat(""), StringAssert.class);
  }

  @Test public void shouldReturnThrowableAssertIfArgumentIsThrowable() {
    assertIsInstanceOf(assertThat(new Exception()), ThrowableAssert.class);
  }
}
