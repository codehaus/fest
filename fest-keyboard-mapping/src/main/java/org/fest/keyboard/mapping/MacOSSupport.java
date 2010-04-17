/*
 * Created on Apr 17, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.keyboard.mapping;

/**
 * Understands support for Mac OS X-specific features.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class MacOSSupport {

  static void setUpForMacOS() {
    if (!isMacOS()) return;
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "FEST Keyboard Mapping Tool");
  }

  static boolean isMacOS() {
    return System.getProperty("os.name").startsWith("Mac") || System.getProperty("mrj.version") != null;
  }

  private MacOSSupport() {}
}
