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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.assertions;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.fest.assertions.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.*;

import org.fest.util.Files;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Assertions}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class AssertionsTest {

  private void assertIsInstanceOf(Object target, Class<?> expectedType) {
    assertEquals(target.getClass(), expectedType);
  }

  public void shouldReturnBigDecimalAssertIfArgumentIsBigDecimal() {
    assertIsInstanceOf(assertThat(new BigDecimal("8")), BigDecimalAssert.class);
  }

  public void shouldReturnBooleanArrayAssertIfArgumentIsBooleanArray() {
    boolean[] booleans = new boolean[] { false };
    assertIsInstanceOf(assertThat(booleans), BooleanArrayAssert.class);
  }

  public void shouldReturnBooleanAssertIfArgumentIsBoolean() {
    assertIsInstanceOf(assertThat(false), BooleanAssert.class);
  }

  public void shouldReturnBooleanAssertIfArgumentIsBooleanWrapper() {
    assertIsInstanceOf(assertThat(Boolean.FALSE), BooleanAssert.class);
  }

  public void shouldReturnByteArrayAssertIfArgumentIsByteArray() {
    byte[] bytes = new byte[] { 0 };
    assertIsInstanceOf(assertThat(bytes), ByteArrayAssert.class);
  }

  public void shouldReturnByteAssertIfArgumentIsByte() {
    byte b = 0;
    assertIsInstanceOf(assertThat(b), ByteAssert.class);
  }

  public void shouldReturnByteAssertIfArgumentIsByteWrapper() {
    byte b = 0;
    assertIsInstanceOf(assertThat(new Byte(b)), ByteAssert.class);
  }

  public void shouldReturnCharArrayAssertIfArgumentIsCharArray() {
    char[] chars = new char[] { 0 };
    assertIsInstanceOf(assertThat(chars), CharArrayAssert.class);
  }

  public void shouldReturnCharAssertIfArgumentIsChar() {
    assertIsInstanceOf(assertThat('a'), CharAssert.class);
  }

  public void shouldReturnCharAssertIfArgumentIsCharacter() {
    assertIsInstanceOf(assertThat(new Character('a')), CharAssert.class);
  }

  public void shouldReturnCollectionAssertIfArgumentIsCollection() {
    assertIsInstanceOf(assertThat(new HashSet<Object>()), CollectionAssert.class);
  }

  public void shouldReturnListAssertIfArgumentIsList() {
    assertIsInstanceOf(assertThat(new ArrayList<Object>()), ListAssert.class);
  }

  public void shouldReturnCollectionAssertIfArgumentIsIterator() {
    List<String> list = new ArrayList<String>();
    list.add("Frodo");
    CollectionAssert assertion = assertThat(list.iterator());
    assertEquals(list, assertion.actual);
  }

  public void shouldReturnDoubleArrayAssertIfArgumentIsDoubleArray() {
    double[] doubles = new double[] { 0 };
    assertIsInstanceOf(assertThat(doubles), DoubleArrayAssert.class);
  }

  public void shouldReturnDoubleAssertIfArgumentIsDouble() {
    assertIsInstanceOf(assertThat(86.0d), DoubleAssert.class);
  }

  public void shouldReturnDoubleAssertIfArgumentIsDoubleWrapper() {
    assertIsInstanceOf(assertThat(new Double(86.0d)), DoubleAssert.class);
  }

  public void shouldReturnFileAssertIfArgumentIsFile() {
    assertIsInstanceOf(assertThat(Files.temporaryFolder()), FileAssert.class);
  }

  public void shouldReturnFloatArrayAssertIfArgumentIsFloatArray() {
    float[] floats = new float[] { 0f };
    assertIsInstanceOf(assertThat(floats), FloatArrayAssert.class);
  }

  public void shouldReturnFloatAssertIfArgumentIsFloat() {
    assertIsInstanceOf(assertThat(86.0f), FloatAssert.class);
  }

  public void shouldReturnFloatAssertIfArgumentIsFloatWrapper() {
    assertIsInstanceOf(assertThat(new Float(86.0f)), FloatAssert.class);
  }

  public void shouldReturnGivenAssertExtension() {
    AssertExtension extension = new AssertExtension() {};
    assertSame(assertThat(extension), extension);
  }

  public void shouldReturnImageAssertIfArgumentIsBufferedImage() {
    BufferedImage image = new BufferedImage(10, 10, TYPE_INT_RGB);
    assertIsInstanceOf(assertThat(image), ImageAssert.class);
  }

  public void shouldReturnIntArrayAssertIfArgumentIsIntArray() {
    int[] ints = new int[] { 0 };
    assertIsInstanceOf(assertThat(ints), IntArrayAssert.class);
  }

  public void shouldReturnIntAssertIfArgumentIsInt() {
    assertIsInstanceOf(assertThat(8), IntAssert.class);
  }

  public void shouldReturnIntAssertIfArgumentIsInteger() {
    assertIsInstanceOf(assertThat(new Integer(8)), IntAssert.class);
  }

  public void shouldReturnLongArrayAssertIfArgumentIsLongArray() {
    long[] longs = new long[] { 0 };
    assertIsInstanceOf(assertThat(longs), LongArrayAssert.class);
  }

  public void shouldReturnLongAssertIfArgumentIsLong() {
    assertIsInstanceOf(assertThat(8l), LongAssert.class);
  }

  public void shouldReturnLongAssertIfArgumentIsLongWrapper() {
    assertIsInstanceOf(assertThat(new Long(86)), LongAssert.class);
  }

  public void shouldReturnMapAssertIfArgumentIsThrowable() {
    assertIsInstanceOf(assertThat(new HashMap<Object, Object>()), MapAssert.class);
  }

  public void shouldReturnObjectArrayAssertIfArgumentIsObjectArray() {
    String[] objects = new String[] { "One" };
    assertIsInstanceOf(assertThat(objects), ObjectArrayAssert.class);
  }

  public void shouldReturnObjectAssertIfArgumentIsObject() {
    assertIsInstanceOf(assertThat(new Object()), ObjectAssert.class);
  }

  public void shouldReturnShortArrayAssertIfArgumentIsShortArray() {
    short[] shorts = new short[] { 0 };
    assertIsInstanceOf(assertThat(shorts), ShortArrayAssert.class);
  }

  public void shouldReturnShortAssertIfArgumentIsShort() {
    short s = 8;
    assertIsInstanceOf(assertThat(s), ShortAssert.class);
  }

  public void shouldReturnShortAssertIfArgumentIsShortWrapper() {
    short s = 8;
    assertIsInstanceOf(assertThat(new Short(s)), ShortAssert.class);
  }

  public void shouldReturnStringAssertIfArgumentIsString() {
    assertIsInstanceOf(assertThat(""), StringAssert.class);
  }

  public void shouldReturnThrowableAssertIfArgumentIsThrowable() {
    assertIsInstanceOf(assertThat(new Exception()), ThrowableAssert.class);
  }
  
  public static class DerivedAssertionsClass extends Assertions {
    private DerivedAssertionsClass() {
      super();
    }
    public static boolean doesThisWork() {
      return true;
    }
  }
  public void shouldAllowExtensionOfAssertionsClass() {
    assertThat(DerivedAssertionsClass.doesThisWork()).isTrue();
  }
  
}
