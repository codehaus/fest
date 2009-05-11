/*
 * Created on May 7, 2008
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
package org.fest.swing.fixture;

import java.awt.Color;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.awt.Color.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

/**
 * Tests for <code>{@link ColorFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ColorFixtureTest {

  private static final String BLUE_HEX_CODE = "0000FF";
  private static final String BLACK_HEX_CODE = "000000";
  
  private Color color;
  private ColorFixture fixture;
  
  @BeforeMethod public void setUp() {
    color = BLUE;
    fixture = new ColorFixture(color);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfColorIsNull() {
    new ColorFixture(null);
  }
  
  @Test public void shouldPassIfColorsAreEqualAsAnticipated() {
    fixture.requireEqualTo(BLUE);
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfColorsAreNotEqualAndExpectingEqual() {
    fixture.requireEqualTo(RED);
  }
  
  @Test public void shouldFailShowingDescriptionIfColorsAreNotEqualAndExpectingEqual() {
    fixture = new ColorFixture(color, "test");
    try {
      fixture.requireEqualTo(RED);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("test");
    }
  }

  @Test public void shouldPassIfColorsAreNotEqualAsAnticipated() {
    fixture.requireNotEqualTo(RED);
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfColorsAreEqualAndExpectingNotEqual() {
    fixture.requireNotEqualTo(BLUE);
  }

  @Test public void shouldFailShowingDescriptionIfColorsAreEqualAndExpectingNotEqual() {
    fixture = new ColorFixture(color, "test");
    try {
      fixture.requireNotEqualTo(BLUE);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("test");
    }
  }

  @Test public void shouldPassIfColorIsEqualToHexValue() {
    fixture.requireEqualTo(BLUE_HEX_CODE);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfColorNotEqualToHexValueAndExpectingEqual() {
    fixture.requireEqualTo(BLACK_HEX_CODE);
  }

  @Test public void shouldFailShowingDescriptionIfColorNotEqualToHexValueAndExpectingEqual() {
    fixture = new ColorFixture(color, "test");
    try {
      fixture.requireEqualTo(BLACK_HEX_CODE);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("test");
    }
  }

  @Test public void shouldPassIfColorIsNotEqualToHexValue() {
    fixture.requireNotEqualTo(BLACK_HEX_CODE);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfColorEqualToHexValueAndExpectingNotEqual() {
    fixture.requireNotEqualTo(BLUE_HEX_CODE);
  }

  @Test public void shouldFailShowingDescriptionIfColorEqualToHexValueAndExpectingNotEqual() {
    fixture = new ColorFixture(color, "test");
    try {
      fixture.requireNotEqualTo(BLUE_HEX_CODE);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("test");
    }
  }
}
