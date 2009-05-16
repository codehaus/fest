/*
 * Created on May 16, 2009
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
package org.fest.swing.keystroke;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.KeyStroke;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link KeyStrokeMap}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class KeyStrokeMapTest {

  private KeyStrokeMappingProvider provider;
  private KeyStroke keyStroke;
  private KeyStrokeMapping mapping;
  private Collection<KeyStrokeMapping> mappings;

  @BeforeMethod public void setUp() {
    provider = createMock(KeyStrokeMappingProvider.class);
    keyStroke = KeyStroke.getKeyStroke(VK_A, SHIFT_MASK);
    mapping = new KeyStrokeMapping('A', keyStroke);
    mappings = new ArrayList<KeyStrokeMapping>();
    mappings.add(mapping);
    KeyStrokeMap.clearKeyStrokes();
    assertThat(KeyStrokeMap.hasKeyStrokes()).isFalse();
  }

  @AfterMethod public void tearDown() {
    KeyStrokeMap.reloadFromLocale();
  }

  public void shouldReturnKeyStrokeFromChar() {
    new EasyMockTemplate(provider) {
      protected void expectations() {
        expect(provider.keyStrokeMappings()).andReturn(mappings);
      }

      protected void codeToTest() {
        KeyStrokeMap.addKeyStrokesFrom(provider);
        assertThat(KeyStrokeMap.keyStrokeFor('A')).isSameAs(keyStroke);
      }
    }.run();
  }

  public void shouldReturnCharForKeyStroke() {
    new EasyMockTemplate(provider) {
      protected void expectations() {
        expect(provider.keyStrokeMappings()).andReturn(mappings);
      }

      protected void codeToTest() {
        KeyStrokeMap.addKeyStrokesFrom(provider);
        assertThat(KeyStrokeMap.charFor(keyStroke)).isEqualTo('A');
      }
    }.run();
  }
}
