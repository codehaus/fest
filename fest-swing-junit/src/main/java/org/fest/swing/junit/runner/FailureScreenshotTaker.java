/*
 * Created on Mar 13, 2009
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
package org.fest.swing.junit.runner;

import static java.io.File.separator;
import static java.util.logging.Level.WARNING;
import static org.fest.swing.image.ScreenshotTaker.PNG_EXTENSION;
import static org.fest.util.Files.currentFolder;
import static org.fest.util.Files.delete;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.fest.swing.image.ScreenshotTaker;
import org.fest.util.FilesException;

/**
 * Understands taking a screenshot of the desktop when a GUI test fails.
 *
 * @author Alex Ruiz
 */
public class FailureScreenshotTaker {

  private static final String FAILED_GUI_TESTS_FOLDER = "failed-gui-tests";

  private static Logger logger = Logger.getAnonymousLogger();

  private final ScreenshotTaker screenshotTaker = new ScreenshotTaker();

  private final File imageFolder;

  /**
   * Creates a new </code>{@link FailureScreenshotTaker}</code>.
   */
  public FailureScreenshotTaker() {
    imageFolder = createImageFolder();
  }

  private File createImageFolder() {
    try {
      File images = new File(concat(currentFolder().getCanonicalPath(), separator, FAILED_GUI_TESTS_FOLDER));
      delete(images);
      images.mkdir();
      return images;
    } catch (IOException e) {
      throw new FilesException(concat("Unable to create directory ", quote(FAILED_GUI_TESTS_FOLDER)), e);
    }
  }

  /**
   * Saves a screenshot of the desktop using the given description as the file name.
   * @param failedTest the description of the test failure.
   */
  public void saveScreenshot(String failedTest) {
    try {
      String fileName = concat(imageFolder.getCanonicalPath(), separator, failedTest, ".",  PNG_EXTENSION);
      screenshotTaker.saveDesktopAsPng(fileName);
      logger.info(concat("Screenshot of failed test saved as ", quote(fileName)));
    } catch (Exception e) {
      logger.log(WARNING, concat("Unable to take screenshot of failed test ", quote(failedTest)), e);
    }
  }
}
