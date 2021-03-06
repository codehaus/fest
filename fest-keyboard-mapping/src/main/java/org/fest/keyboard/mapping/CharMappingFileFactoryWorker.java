/*
 * Created on Apr 30, 2010
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

import java.awt.Window;
import java.io.File;
import java.util.List;

import javax.swing.*;

import static javax.swing.SwingUtilities.*;

/**
 * Understands a <code>{@link SwingWorker}</code> that displays the progress of saving a file using a
 * <code>{@link CharMappingFileFactory}</code>.
 * 
 * @author Alex Ruiz
 */
class CharMappingFileFactoryWorker extends SwingWorker<Void, Integer> implements CharMappingFileCreationListener {

  private final File file;
  private final CharMappingTableModel model;
  private final CharMappingFileFactory fileFactory;
  private final CharMappingFileCreationProgressPanel progressPanel;

  CharMappingFileFactoryWorker(File file, CharMappingTableModel model, CharMappingFileFactory fileFactory,
      CharMappingFileCreationProgressPanel progressPanel) {
    this.file = file;
    this.model = model;
    this.fileFactory = fileFactory;
    this.progressPanel = progressPanel;
  }

  @Override protected Void doInBackground() throws Exception {
    updateAndShowProgressWindow();
    fileFactory.add(this);
    fileFactory.createMappingFile(file, model); // TODO test what happens in case of failure
    return null;
  }

  private void updateAndShowProgressWindow() {
    invokeLater(new Runnable() {
      @Override public void run() {
        progressPanel.updateFileName(file.getName());
        setProgressWindowVisible(true);
      }
    });
  }

  @Override
  protected void process(List<Integer> chunks) {
    if (chunks.isEmpty()) return;
    int last = chunks.get(chunks.size() - 1);
    progressPanel.updateProgress(last);
  }
  
  @Override protected void done() {
    fileFactory.remove(this);
    invokeLater(new Runnable() {
      @Override public void run() {
        setProgressWindowVisible(false);
      }
    });
  }
  
  private void setProgressWindowVisible(boolean visible) {
    Window w = getWindowAncestor(progressPanel);
    if (w.isShowing() != visible) w.setVisible(visible);
  }
  
  @Override public void creationStarted(final int mappingCount) {
    invokeLater(new Runnable() {
      @Override public void run() {
        progressPanel.updateMappingCount(mappingCount);
      }
    });
  }

  @Override public void charMappingsProcessed(int count) {
    publish(count);
  }
}
