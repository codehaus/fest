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

import static java.util.Locale.*;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import org.testng.annotations.*;

/**
 * Tests for <code>{@link KeyStrokeMappingProviderPicker}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class KeyStrokeMappingProviderPickerTest {

  private KeyStrokeMappingProviderPicker picker;

  @BeforeClass public void setUpOnce() {
    picker = new KeyStrokeMappingProviderPicker();
  }

  public void shouldPickProviderForGermanIfLocaleHasGermanLanguage() {
    KeyStrokeMappingProvider provider = picker.providerFor(GERMAN);
    assertThat(provider).isInstanceOf(KeyStrokeMappingProvider_de.class);
  }

  @Test(dataProvider = "locales")
  public void shouldPickProviderForEnglishForAnyOtherLocale(Locale locale) {
    KeyStrokeMappingProvider provider = picker.providerFor(locale);
    assertThat(provider).isInstanceOf(KeyStrokeMappingProvider_en.class);
  }

  @DataProvider(name = "locales") public Object[][] locales() {
    return new Object[][] { { FRENCH }, { ENGLISH }, { CHINESE }, { ITALIAN }, { SIMPLIFIED_CHINESE } };
  }
}
