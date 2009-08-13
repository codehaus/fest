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

import static java.util.Locale.CHINESE;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.FRENCH;
import static java.util.Locale.GERMAN;
import static java.util.Locale.ITALIAN;
import static java.util.Locale.SIMPLIFIED_CHINESE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Collections.list;

import java.util.Collection;
import java.util.Locale;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for <code>{@link KeyStrokeMappingProviderPicker}</code>.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class KeyStrokeMappingProviderPickerTest {

  private KeyStrokeMappingProviderPicker picker;

  private final Locale locale;

  @Parameters
  public static Collection<Object[]> locales() {
    return list(new Object[][] { { FRENCH }, { ENGLISH }, { CHINESE }, { ITALIAN }, { SIMPLIFIED_CHINESE } });
  }

  public KeyStrokeMappingProviderPickerTest(Locale locale) {
    this.locale = locale;
  }
  
  @Before public void setUp() {
    picker = new KeyStrokeMappingProviderPicker();
  }

  @Ignore(value = "German-specific mapping disabled till FEST-175 is fixed")
  public void shouldPickProviderForGermanIfLocaleHasGermanLanguage() {
    KeyStrokeMappingProvider provider = picker.providerFor(GERMAN);
    assertThat(provider).isInstanceOf(KeyStrokeMappingProvider_de.class);
  }

  @Test
  public void shouldPickProviderForEnglishForAnyOtherLocale() {
    KeyStrokeMappingProvider provider = picker.providerFor(locale);
    assertThat(provider).isInstanceOf(KeyStrokeMappingProvider_en.class);
  }
}
