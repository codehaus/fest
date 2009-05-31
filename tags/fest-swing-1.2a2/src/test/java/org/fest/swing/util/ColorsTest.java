/*
 * Created on Apr 15, 2008
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
package org.fest.swing.util;

import java.awt.Color;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.awt.Color.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link Colors}</code>.
 *
 * @author Alex Ruiz
 */
public class ColorsTest {

  @Test(dataProvider = "colors")
  public void shouldReturnColorFromHexString(String hexString, Color expected) {
    Color color = Colors.colorFromHexString(hexString);
    assertThat(color).isEqualTo(expected);
  }
  
  @DataProvider(name = "colors") public Object[][] colors() {
    return new Object[][] {
        { "000000", BLACK },
        { "FF0000", RED },
        { "00FF00", GREEN },
        { "0000FF", BLUE },
        { "FFFF00", YELLOW }
    };
  }
  
  @Test(expectedExceptions = NumberFormatException.class)
  public void shouldThrowErrorIfHexStringIsNotValid() {
    Colors.colorFromHexString("zz");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfHexStringIsNull() {
    Colors.colorFromHexString(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfHexStringIsEmpty() {
    Colors.colorFromHexString("");
  }
}
