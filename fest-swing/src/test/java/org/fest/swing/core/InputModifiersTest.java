/*
 * Created on Jul 19, 2008
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
package org.fest.swing.core;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.VK_A;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Collections.list;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link InputModifiers}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class InputModifiersTest {

  @Test(dataProvider = "modifiers")
  public void shouldUnifyModifiers(int[] modifiers) {
    int unified = InputModifiers.unify(modifiers);
    for (int modifier : modifiers)
      assertThat((unified & modifier) != 0).isTrue();
    for (int modifier : missingModifiers(modifiers))
      assertThat((unified & modifier) != 0).isFalse();
  }

  private List<Integer> missingModifiers(int[] existingModifiers) {
    List<Integer> allModifiers = allModifiers();
    for (Integer modifier : existingModifiers) allModifiers.remove(modifier);
    return allModifiers;
  }

  private List<Integer> allModifiers() {
    return list(ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK, META_MASK, SHIFT_MASK);
  }

  @DataProvider(name = "modifiers") public Object[][] modifiers() {
    return new Object[][] {
        { new int[] { ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK, META_MASK, SHIFT_MASK } },
        { new int[] { ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK, META_MASK } },
        { new int[] { ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK } },
        { new int[] { ALT_MASK, ALT_GRAPH_MASK } },
        { new int[] { ALT_MASK } }
    };
  }

  public void shouldReturnZeroIfArrayOfModifiersIsEmpty() {
    assertThat(InputModifiers.unify(new int[0])).isZero();
  }

  public void shouldReturnZeroIfArrayOfModifiersIsNull() {
    int[] modifiers = null;
    assertThat(InputModifiers.unify(modifiers)).isZero();
  }

  public void shouldReturnTrueIfAltMaskIsPresent() {
    assertThat(InputModifiers.isAltDown(ALT_MASK | ALT_GRAPH_MASK)).isTrue();
  }

  public void shouldReturnFalseIfAltMaskIsPresent() {
    assertThat(InputModifiers.isAltDown(ALT_GRAPH_MASK)).isFalse();
  }

  public void shouldReturnTrueIfAltGraphMaskIsPresent() {
    assertThat(InputModifiers.isAltGraphDown(ALT_GRAPH_MASK | CTRL_MASK)).isTrue();
  }

  public void shouldReturnFalseIfAltGraphMaskIsPresent() {
    assertThat(InputModifiers.isAltGraphDown(CTRL_MASK)).isFalse();
  }

  public void shouldReturnTrueIfCtrlMaskIsPresent() {
    assertThat(InputModifiers.isControlDown(CTRL_MASK | META_MASK)).isTrue();
  }

  public void shouldReturnFalseIfCtrlMaskIsPresent() {
    assertThat(InputModifiers.isControlDown(META_MASK)).isFalse();
  }

  public void shouldReturnTrueIfMetaMaskIsPresent() {
    assertThat(InputModifiers.isMetaDown(META_MASK | SHIFT_MASK)).isTrue();
  }

  public void shouldReturnFalseIfMetaMaskIsPresent() {
    assertThat(InputModifiers.isMetaDown(SHIFT_MASK)).isFalse();
  }

  public void shouldReturnTrueIfShiftMaskIsPresent() {
    assertThat(InputModifiers.isShiftDown(SHIFT_MASK | ALT_MASK)).isTrue();
  }

  public void shouldReturnFalseIfShiftMaskIsPresent() {
    assertThat(InputModifiers.isShiftDown(ALT_MASK)).isFalse();
  }

  public void shouldReturnTrueIfAltModifiersMatch() {
    KeyEvent e = keyEventWithModifiers(ALT_MASK);
    assertThat(InputModifiers.modifiersMatch(e, ALT_MASK)).isTrue();
  }

  @Test(dataProvider = "modifier")
  public void shouldReturnTrueIfModifiersMatch(int modifier) {
    KeyEvent e = keyEventWithModifiers(modifier);
    assertThat(InputModifiers.modifiersMatch(e, modifier)).isTrue();
  }

  @Test(dataProvider = "modifier")
  public void shouldReturnFalseIfModifiersDoNotMatch(int modifier) {
    KeyEvent e = keyEventWithModifiers(0);
    assertThat(InputModifiers.modifiersMatch(e, modifier)).isFalse();
  }

  @DataProvider(name = "modifier") public Object[][] modifier() {
    return new Object[][] {
        { ALT_MASK },
        { ALT_GRAPH_MASK },
        { CTRL_MASK },
        { META_MASK },
        { SHIFT_MASK },
    };
  }

  private static KeyEvent keyEventWithModifiers(int modifiers) {
    return new KeyEvent(mockComponent(), 0, 0, modifiers, VK_A, 'a');
  }

  private static Component mockComponent() {
    return createMock(Component.class);
  }
}
