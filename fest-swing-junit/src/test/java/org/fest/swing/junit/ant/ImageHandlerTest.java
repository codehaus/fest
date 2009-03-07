/*
 * Created on Jun 8, 2007
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
package org.fest.swing.junit.ant;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.image.BufferedImage;

import org.fest.swing.image.ScreenshotTaker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ImageHandler}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageHandlerTest {

  private ScreenshotTaker screenshotTaker;
  
  @BeforeClass public void setUp() {
    screenshotTaker = new ScreenshotTaker();
  }
  
  @Test public void shouldEncodeAndDecodeImage() {
    BufferedImage imageToEncode = screenshotTaker.takeDesktopScreenshot();
    String encoded = ImageHandler.encodeBase64(imageToEncode);
    assertThat(encoded).isNotEmpty();
    BufferedImage decodedImage = ImageHandler.decodeBase64(encoded);
    assertThat(decodedImage).isNotNull().isEqualTo(imageToEncode);
  }
  
}
