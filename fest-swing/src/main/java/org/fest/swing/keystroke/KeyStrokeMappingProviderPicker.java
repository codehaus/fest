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

import java.util.Locale;

/**
 * Understands how to choose a <code>{@link KeyStrokeMappingProvider}</code> based on a locale.
 *
 * @author Alex Ruiz
 */
class KeyStrokeMappingProviderPicker {

//  private static final String GERMAN = "de";

  KeyStrokeMappingProvider providerFor(Locale locale) {
    // removed support for German-specific mapping, until FEST-175 is fixed.
    // if (isGerman(locale)) return new KeyStrokeMappingProvider_de();
    return new KeyStrokeMappingProvider_en();
  }

//  private boolean isGerman(Locale locale) {
//    return GERMAN.equalsIgnoreCase(locale.getDisplayLanguage()) || GERMAN.equalsIgnoreCase(locale.getLanguage());
//  }
}
