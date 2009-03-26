/*
 * Created on Mar 25, 2009
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
package org.fest.assertions;

import static org.testng.Assert.*;

import java.awt.Color;

import org.testng.annotations.*;

/**
 * Tests for <code>{@link RGBColor}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class RGBColorTet {

  private int red;
  private int green;
  private int blue;
  private RGBColor color;

  @BeforeClass public void setUpOnce() {
    red = 6;
    green = 8;
    blue = 10;
  }

  @BeforeMethod public void setUp() {
    color = new RGBColor(red, green, blue);
  }

  public void shouldSeparateRedGreenAndBlueWhenConstructed() {
    Color awtColor = new Color(red, green, blue);
    color = new RGBColor(awtColor.getRGB());
    assertEquals(color.red(), awtColor.getRed());
    assertEquals(color.green(), awtColor.getGreen());
    assertEquals(color.blue(), awtColor.getBlue());
  }

  public void shouldReturnIsEqualIfComponentsAreExactlyEqual() {
    assertTrue(color.isEqualTo(new RGBColor(red, green, blue), 0));
  }

  public void shouldReturnIsEqualIfRedsAreSimilarDueToThreshold() {
    assertTrue(color.isEqualTo(new RGBColor(red++, green, blue), 1));
  }

  public void shouldReturnIsEqualIfGreensAreSimilarDueToThreshold() {
    assertTrue(color.isEqualTo(new RGBColor(red, green++, blue), 1));
  }

  public void shouldReturnIsEqualIfBluesAreSimilarDueToThreshold() {
    assertTrue(color.isEqualTo(new RGBColor(red, green, blue++), 1));
  }

  public void shouldReturnIsNotEqualIfRedsNotEqual() {
    assertFalse(color.isEqualTo(new RGBColor(0, green, blue), 0));
  }

  public void shouldReturnIsNotEqualIfGreensNotEqual() {
    assertFalse(color.isEqualTo(new RGBColor(red, 0, blue), 0));
  }

  public void shouldReturnIsNotEqualIfBluesNotEqual() {
    assertFalse(color.isEqualTo(new RGBColor(red, green, 0), 0));
  }
}
