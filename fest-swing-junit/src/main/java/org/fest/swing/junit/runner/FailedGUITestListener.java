/*
 * Created on Nov 18, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.runner;

import static java.io.File.separator;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static org.fest.swing.image.ScreenshotTaker.PNG_EXTENSION;
import static org.fest.swing.junit.runner.Formatter.format;
import static org.fest.util.Files.currentFolder;
import static org.fest.util.Files.delete;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.fest.swing.image.ScreenshotTaker;
import org.fest.util.FilesException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Understands a JUnit 4 <code>{@link RunListener}</code> that takes a screenshot of failed GUI tests.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FailedGUITestListener extends RunListener {

  private static final String FAILED_GUI_TESTS_FOLDER = "failed-gui-tests";

  private static Logger logger = Logger.getLogger(FailedGUITestListener.class.getName());
  
  private final ScreenshotTaker screenshotTaker = new ScreenshotTaker();
  
  private File images;
  
  /**
   * Creates a new <code>{@link FailedGUITestListener}</code>.
   */
  public FailedGUITestListener() {
    super();
    try {
      images = imageFolder();
    } catch (FilesException e) {
      logger.log(INFO, "Unable to take screenshots for failing GUI tests", e);
    }
  }
  
  private static File imageFolder() {
    try {
      File images = new File(concat(currentFolder().getCanonicalPath(), separator, FAILED_GUI_TESTS_FOLDER));
      delete(images);
      images.mkdir();
      return images;
    } catch (IOException e) {
      throw new FilesException(concat("Unable to create directory ", quote(FAILED_GUI_TESTS_FOLDER)), e);
    }
  }
  
  @Override public void testFailure(Failure failure) throws Exception {
    takeScreenshot(failure);
    super.testFailure(failure);
  }
  
  private void takeScreenshot(Failure failure) {
    if (images == null) return;
    GUITestDescription description = descriptionForGUITest(failure);
    if (description == null) return;
    String methodSignature = format(description.testClass(), description.testMethod());
    try {
      String fileName = concat(images.getCanonicalPath(), separator, methodSignature, ".",  PNG_EXTENSION);
      screenshotTaker.saveDesktopAsPng(fileName);
      logger.info(concat("Screenshot of failed test saved as ", quote(fileName)));
    } catch (Exception e) {
      logger.log(WARNING, concat("Unable to take screenshot of failed test ", quote(methodSignature)), e);
    }
  }
  
  private GUITestDescription descriptionForGUITest(Failure failure) {
    Description description = failure.getDescription();
    if (!(description instanceof GUITestDescription)) return null;
    GUITestDescription guiTestDescription = (GUITestDescription)description;
    if (!guiTestDescription.isGUITest()) return null;
    return guiTestDescription;
  }
}
