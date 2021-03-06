/*
 * Created on Apr 18, 2010
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

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.SAVE_DIALOG;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.fest.util.VisibleForTesting;

/**
 * Understands selection of a file to save as mapping file using a <code>{@link JFileChooser}</code>.
 *
 * @author Alex Ruiz
 */
class CharMappingFileChooser implements FileChooser {

  private final JFileChooser fileChooser;
  private final JFrame parent;

  CharMappingFileChooser(JFrame parent) {
    this(new JFileChooser(), parent);
  }

  @VisibleForTesting
  CharMappingFileChooser(JFileChooser fileChooser, JFrame parent) {
    this.parent = parent;
    this.fileChooser = fileChooser;
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setDialogTitle("Save As Mapping File");
    fileChooser.setDialogType(SAVE_DIALOG);
    fileChooser.setFileFilter(new TextFileFilter());
  }

  @Override public File fileToSave() {
    int selection = fileChooser.showSaveDialog(parent);
    if (selection != APPROVE_OPTION) return null;
    return fileChooser.getSelectedFile();
  }
}
