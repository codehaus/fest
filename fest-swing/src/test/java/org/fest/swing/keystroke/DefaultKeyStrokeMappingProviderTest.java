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

import java.util.Collection;

import javax.swing.KeyStroke;

import org.testng.annotations.Test;

import static java.awt.event.KeyEvent.*;

import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link KeyStrokeMappingProvider_en}</code>.
 *
 * @author Alex Ruiz
 */
public class DefaultKeyStrokeMappingProviderTest extends KeyStrokeMappingProviderTestCase {

  @Test(groups = GUI, dataProvider = "keyStrokeMappings")
  public void shouldProvideKeyStrokesForDefaultKeyboard(char character, KeyStroke keyStroke) {
    if (character == BACKSPACE) {
      pressKeyStrokeAndVerify(keyStroke, VK_BACK_SPACE);
      return;
    }
    if (character == CR) {
      pressKeyStrokeAndVerify(keyStroke, VK_ENTER);
      return;
    }
    if (character == DELETE) {
      pressKeyStrokeAndVerify(keyStroke, VK_DELETE);
      return;
    }
    if (character == ESCAPE) {
      pressKeyStrokeAndVerify(keyStroke, VK_ESCAPE);
      return;
    }
    if (character == LF) {
      pressKeyStrokeAndVerify(keyStroke, VK_ENTER);
      return;
    }
  }

  Collection<KeyStrokeMapping> keyStrokeMappingsToTest() {
    return new DefaultKeyStrokeMappingProvider().keyStrokeMappings();
  }
}
