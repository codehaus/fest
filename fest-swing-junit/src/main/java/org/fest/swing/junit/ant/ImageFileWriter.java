/*
 * Created on Apr 27, 2009
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
package org.fest.swing.junit.ant;

import static org.fest.swing.image.ScreenshotTaker.PNG_EXTENSION;
import static org.fest.util.Files.newFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Understands how to write an image as a file in the file system.
 *
 * @author Alex Ruiz
 */
class ImageFileWriter {

  void saveAsPng(BufferedImage image, String path) throws IOException {
    File newFile = new File(path);
    if (newFile.exists()) return;
    newFile = newFile(path);
    ImageIO.write(image, PNG_EXTENSION, newFile);
  }
}
