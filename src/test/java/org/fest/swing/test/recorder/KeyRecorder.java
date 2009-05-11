/*
 * Created on Sep 24, 2007
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
package org.fest.swing.test.recorder;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.AssertExtension;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Understands a listener that records key events.
 *
 * @author Alex Ruiz
 */
public class KeyRecorder extends KeyAdapter implements AssertExtension {

  public static KeyRecorder attachTo(Component target) {
    return new KeyRecorder(target);
  }

  private static class KeyEventRecord {
    final int keyCode;

    KeyEventRecord(int keyCode) {
      this.keyCode = keyCode;
    }
  }

  private final List<KeyEventRecord> keysPressed = new ArrayList<KeyEventRecord>();
  private final List<KeyEventRecord> keysReleased = new ArrayList<KeyEventRecord>();

  protected KeyRecorder(Component target) {
    attach(this, target);
  }

  private static void attach(KeyRecorder keyListener, Component target) {
    target.addKeyListener(keyListener);
    if (!(target instanceof Container)) return;
    for (Component c : ((Container)target).getComponents()) attach(keyListener, c);
  }

  @Override public void keyPressed(KeyEvent e) {
    keysPressed.add(new KeyEventRecord(e.getKeyCode()));
  }

  @Override public void keyReleased(KeyEvent e) {
    keysReleased.add(new KeyEventRecord(e.getKeyCode()));
  }

  public KeyRecorder keysPressed(int...expected) {
    assertEqualKeyCodes(keysPressed, expected);
    return this;
  }
  
  public KeyRecorder keysReleased(int... expected) {
    assertEqualKeyCodes(keysReleased, expected);
    return this;
  }
  
  public KeyRecorder noKeysReleased() {
    assertThat(keysReleased.size()).isZero();
    return this;
  }

  private void assertEqualKeyCodes(List<KeyEventRecord> actual, int[] expected) {
    int actualSize = actual.size();
    assertThat(actualSize).isEqualTo(expected.length);
    for (int i = 0; i < actualSize; i++) 
      assertThat(actual.get(i).keyCode).isEqualTo(expected[i]);
  }
}
