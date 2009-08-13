/*
 * Created on Jun 11, 2008
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
package org.fest.swing.keystroke;

import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;

import java.util.Collection;

import javax.swing.KeyStroke;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for <code>{@link KeyStrokeMappingProvider_en}</code>.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class DefaultKeyStrokeMappingProviderTest extends KeyStrokeMappingProviderTestCase {

  public DefaultKeyStrokeMappingProviderTest(char character, KeyStroke keyStroke) {
    super(character, keyStroke);
  }

  @Parameters
  public static Collection<Object[]> keyStrokes() {
    Collection<KeyStrokeMapping> mappings = new DefaultKeyStrokeMappingProvider().keyStrokeMappings();
    return keyStrokesFrom(mappings);
  }

  @Test
  public void shouldProvideKeyStrokesForDefaultKeyboard() {
    if (character == BACKSPACE) {
      pressKeyStrokeAndVerify(VK_BACK_SPACE);
      return;
    }
    if (character == CR) {
      pressKeyStrokeAndVerify(VK_ENTER);
      return;
    }
    if (character == DELETE) {
      pressKeyStrokeAndVerify(VK_DELETE);
      return;
    }
    if (character == ESCAPE) {
      pressKeyStrokeAndVerify(VK_ESCAPE);
      return;
    }
    if (character == LF) {
      pressKeyStrokeAndVerify(VK_ENTER);
      return;
    }
  }

  Collection<KeyStrokeMapping> keyStrokeMappingsToTest() {
    return new DefaultKeyStrokeMappingProvider().keyStrokeMappings();
  }
}
