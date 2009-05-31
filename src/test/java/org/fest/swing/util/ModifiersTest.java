/*
 * Created on Mar 24, 2008
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

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Modifiers}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ModifiersTest {

  public void shouldReturnKeyForAltMask() {
    int[] keys = Modifiers.keysFor(ALT_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_ALT);
  }

  public void shouldReturnKeyForAltGraphMask() {
    int[] keys = Modifiers.keysFor(ALT_GRAPH_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_ALT_GRAPH);
  }

  public void shouldReturnKeyForCtrlMask() {
    int[] keys = Modifiers.keysFor(CTRL_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_CONTROL);
  }

  public void shouldReturnKeyForMetaMask() {
    int[] keys = Modifiers.keysFor(META_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_META);
  }

  public void shouldReturnKeyForShiftMask() {
    int[] keys = Modifiers.keysFor(SHIFT_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_SHIFT);
  }

  public void shouldReturnKeyForMoreThanOneMask() {
    int[] keys = Modifiers.keysFor(ALT_MASK | ALT_GRAPH_MASK | CTRL_MASK | META_MASK | SHIFT_MASK);
    assertThat(keys).hasSize(5).containsOnly(VK_ALT, VK_ALT_GRAPH, VK_CONTROL, VK_META, VK_SHIFT);
  }

  public void shouldNotReturnKeyIsMaskIsZero() {
    int[] keys = Modifiers.keysFor(0);
    assertThat(keys).isEmpty();
  }

  public void shouldUpdateModifierIfKeyCodeIsAlt() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_ALT, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & ALT_MASK).isNotEqualTo(0);
  }

  public void shouldUpdateModifierIfKeyCodeIsAltGraph() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_ALT_GRAPH, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & ALT_GRAPH_MASK).isNotEqualTo(0);
  }

  public void shouldUpdateModifierIfKeyCodeIsCtrl() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_CONTROL, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & CTRL_MASK).isNotEqualTo(0);
  }

  public void shouldUpdateModifierIfKeyCodeIsMeta() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_META, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & META_MASK).isNotEqualTo(0);
  }

  public void shouldUpdateModifierIfKeyCodeIsShift() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_SHIFT, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & SHIFT_MASK).isNotEqualTo(0);
  }

  public void shouldNotUpdateModifierIfKeyCodeIsNotModifier() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_A, modifierMask);
    assertThat(updatedModifierMask).isEqualTo(modifierMask);
    assertThat(updatedModifierMask & ALT_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & ALT_GRAPH_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & CTRL_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & META_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & SHIFT_MASK).isEqualTo(0);
  }

  @Test(dataProvider = "modifierKeyCodes")
  public void shouldReturnTrueIfGivenKeyIsModifier(int keyCode) {
    assertThat(Modifiers.isModifier(keyCode)).isTrue();
  }

  @DataProvider(name = "modifierKeyCodes") public Object[][] modifierKeyCodes() {
   return new Object[][] { { VK_ALT_GRAPH }, { VK_ALT }, { VK_SHIFT }, { VK_CONTROL }, { VK_META } };
  }

  public void shouldReturnFalseIfGivenKeyIsNotModifier() {
    assertThat(Modifiers.isModifier(VK_A)).isFalse();
  }

  @Test(dataProvider = "modifierKeyCodesAndMasks")
  public void shouldReturnMaskForModifier(int keyCode, int mask) {
    assertThat(Modifiers.maskFor(keyCode)).isEqualTo(mask);
  }

  @DataProvider(name = "modifierKeyCodesAndMasks") public Object[][] modifierKeyCodesAndMasks() {
    return new Object[][] {
        { VK_ALT_GRAPH, ALT_GRAPH_MASK },
        { VK_ALT, ALT_MASK },
        { VK_SHIFT, SHIFT_MASK },
        { VK_CONTROL, CTRL_MASK },
        { VK_META, META_MASK },
    };
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfKeyIsNotModifier() {
    Modifiers.maskFor(VK_A);
  }
}
