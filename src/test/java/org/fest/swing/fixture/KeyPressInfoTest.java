/*
 * Created on Jun 23, 2008
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

import org.testng.annotations.Test;

import org.fest.swing.core.KeyPressInfo;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.VK_C;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link KeyPressInfo}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class KeyPressInfoTest {

  public void shouldCreateKeyPressInfoWithKeyAndModifiers() {
    KeyPressInfo keyPressInfo = KeyPressInfo.keyCode(VK_C).modifiers(SHIFT_MASK, CTRL_MASK);
    assertThat(keyPressInfo.keyCode()).isEqualTo(VK_C);
    assertThat(keyPressInfo.modifiers()).containsOnly(SHIFT_MASK, CTRL_MASK);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldShowErrorIfModifierArrayIsNull() {
    int[] modifiers = null;
    KeyPressInfo.keyCode(VK_C).modifiers(modifiers);
  }
}
