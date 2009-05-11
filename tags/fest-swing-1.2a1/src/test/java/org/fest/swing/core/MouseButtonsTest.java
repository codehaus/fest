/*
 * Created on Sep 21, 2007
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
package org.fest.swing.core;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.awt.event.InputEvent.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link MouseButton}</code>.
 *
 * @author Alex Ruiz
 */
public class MouseButtonsTest {

  @Test(dataProvider = "masks") 
  public void shouldContainCorrectMouseButtonMask(MouseButton target, int expectedMask) {
    assertThat(target.mask).isEqualTo(expectedMask);
  }
  
  @Test(dataProvider = "masks") 
  public void shouldLookupButtonGivenMask(MouseButton button, int mask) {
    assertThat(MouseButton.lookup(mask)).isEqualTo(button);
  }

  @DataProvider(name = "masks") public Object[][] masks() {
    return new Object[][] {
        { MouseButton.LEFT_BUTTON, BUTTON1_MASK },
        { MouseButton.MIDDLE_BUTTON, BUTTON2_MASK },
        { MouseButton.RIGHT_BUTTON, BUTTON3_MASK },
    };
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorInLookupIfMaskIsInvalid() {
    MouseButton.lookup(Integer.MIN_VALUE);
  }
}
