/*
 * Created on Apr 8, 2010
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

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Understands a file filter for text files.
 *
 * @author Alex Ruiz
 */
class TextFileFilter extends FileFilter {

  @Override public boolean accept(File f) {
    return f != null && f.getName().endsWith(".txt");
  }

  @Override public String getDescription() {
    return "Text Files (*.txt)";
  }

}
