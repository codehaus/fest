/*
 * Created on Jun 4, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper2.testClassName;
import static org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper2.testMethodName;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ERROR;
import static org.fest.swing.image.ScreenshotTaker.PNG_EXTENSION;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.join;

import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

import junit.framework.Test;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.swing.annotation.GUITestFinder;
import org.fest.swing.image.ImageException;
import org.fest.swing.image.ScreenshotTaker;
import org.w3c.dom.Element;

/**
 * Understands a JUnit XML report formatter that takes a screenshot when a GUI test fails.
 * <p>
 * <strong>Note:</strong> A test is consider a GUI test if it is marked with the annotation
 * <code>org.fest.swing.annotation.GUITest</code>.
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class ScreenshotOnFailureResultFormatter extends XmlJUnitResultFormatter {

  private static final String SCREENSHOT_ELEMENT = "screenshot";
  private static final String SCREENSHOT_FILE_ATTRIBUTE = "file";
  
  private ScreenshotTaker screenshotTaker;
  private boolean ready;

  private ImageException couldNotCreateScreenshotTaker;
  
  public ScreenshotOnFailureResultFormatter() {
    try {
      screenshotTaker = new ScreenshotTaker();
      ready = true;
    } catch (ImageException e) {
      couldNotCreateScreenshotTaker = e;
    }
  }

  @Override protected void onStartTestSuite(JUnitTest suite) {
    if (couldNotCreateScreenshotTaker == null) return;
    writeCouldNotCreateScreenshotTakerError();
    couldNotCreateScreenshotTaker = null;
    return;
  }

  private void writeCouldNotCreateScreenshotTakerError() {
    Element errorElement = document().createElement(ERROR);
    writeErrorAndStackTrace(couldNotCreateScreenshotTaker, errorElement);
    rootElement().appendChild(errorElement);
  }

  @Override protected void onFailureOrError(Test test, Throwable error, Element errorElement) {
    if (!ready) return;
    String className = testClassName(test);
    String methodName = testMethodName(test);
    if (!isGUITest(className, methodName)) return;
    String image = takeScreenshotAndReturnEncoded();
    if (isEmpty(image)) return;
    String imageFileName = join(className, methodName, PNG_EXTENSION).with(".");
    writeScreenshotFileName(image, imageFileName, errorElement);
  }

  private boolean isGUITest(String className, String methodName) {
    try {
      Class<?> testClass = Class.forName(className);
      Method testMethod = testClass.getDeclaredMethod(methodName, new Class<?>[0]);
      return GUITestFinder.isGUITest(testClass, testMethod);
    } catch (Exception e) {
      return false;
    }
  }

  private String takeScreenshotAndReturnEncoded() {
    BufferedImage image = screenshotTaker.takeDesktopScreenshot();
    return ImageHandler.encodeBase64(image);
  }
  
  private void writeScreenshotFileName(String encodedImage, String imageFileName, Element errorElement) {
    Element screenshotElement = document().createElement(SCREENSHOT_ELEMENT);
    screenshotElement.setAttribute(SCREENSHOT_FILE_ATTRIBUTE, imageFileName);
    writeText(encodedImage, screenshotElement);
    errorElement.getParentNode().appendChild(screenshotElement);
  }
}
