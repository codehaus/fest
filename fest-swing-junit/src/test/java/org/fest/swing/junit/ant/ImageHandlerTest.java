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

import static java.io.File.separator;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.util.Files.newTemporaryFolder;
import static org.fest.util.Strings.concat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.image.ImageFileWriter;
import org.fest.swing.image.ScreenshotTaker;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link ImageHandler}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ImageHandlerTest {

  private ScreenshotTaker screenshotTaker;

  @BeforeClass public void setUp() {
    screenshotTaker = new ScreenshotTaker();
  }

  public void shouldEncodeAndDecodeImage() {
    BufferedImage imageToEncode = screenshotTaker.takeDesktopScreenshot();
    String encoded = ImageHandler.encodeBase64(imageToEncode);
    assertThat(encoded).isNotEmpty();
    BufferedImage decodedImage = ImageHandler.decodeBase64(encoded);
    assertThat(decodedImage).isNotNull().isEqualTo(imageToEncode);
  }

  public void shouldNotRethrowExceptionWhenEncodingImage() {
    final BufferedImage image = mockImage();
    final ImageEncoder encoder = createMock(ImageEncoder.class);
    new EasyMockTemplate(encoder) {
      protected void expectations() throws Throwable {
        expect(encoder.encodeBase64(image)).andThrow(thrownOnPurpose());
      }

      protected void codeToTest() {
        assertThat(ImageHandler.encodeBase64(image, encoder)).isNull();
      }
    }.run();
  }

  public void shouldNotRethrowExceptionWhenDecodingImage() {
    final String encoded = "Hello";
    final ImageDecoder decoder = mockImageDecoder();
    new EasyMockTemplate(decoder) {
      protected void expectations() throws Throwable {
        expect(decoder.decodeBase64(encoded)).andThrow(thrownOnPurpose());
      }

      protected void codeToTest() {
        assertThat(ImageHandler.decodeBase64(encoded, decoder)).isNull();
      }
    }.run();
  }

  public void shouldDecodeImageAndSaveAsFile() throws IOException {
    File folder = newTemporaryFolder();
    String path = concat(folder, separator, "image.png");
    BufferedImage imageToEncode = screenshotTaker.takeDesktopScreenshot();
    String encoded = ImageHandler.encodeBase64(imageToEncode);
    assertThat(ImageHandler.decodeBase64AndSaveAsPng(encoded, path)).isEmpty();
    BufferedImage savedImage = read(path);
    assertThat(savedImage).isEqualTo(imageToEncode);
  }

  @Test(dataProvider = "emptyOrNull")
  public void shouldReturnEmptyStringIfEncodedImageIsEmptyOrNullWhenDecodingAndSavingImage(String encoded) {
    assertThat(ImageHandler.decodeBase64AndSaveAsPng(encoded, "somePath"));
  }

  @Test(dataProvider = "emptyOrNull")
  public void shouldReturnEmptyStringIfFilePathIsEmptyOrNullWhenDecodingAndSavingImage(String path) {
    assertThat(ImageHandler.decodeBase64AndSaveAsPng("someImage", path));
  }

  @DataProvider(name = "emptyOrNull") public Object[][] emptyOrNull() {
    return new Object[][] { { "" }, { null } };
  }

  public void shouldNotRethrowIOExceptionWhenDecodingAndSavingImage() {
    final String encoded = "Hello";
    final String path = "somePath";
    final BufferedImage image = mockImage();
    final ImageDecoder decoder = mockImageDecoder();
    final ImageFileWriter writer = createMock(ImageFileWriter.class);
    new EasyMockTemplate(decoder, writer) {
      protected void expectations() throws Throwable {
        expect(decoder.decodeBase64(encoded)).andReturn(image);
        writer.writeAsPng(image, path);
        expectLastCall().andThrow(thrownOnPurpose());
      }

      protected void codeToTest() {
        assertThat(ImageHandler.decodeBase64AndSaveAsPng(encoded, path, decoder, writer)).isEmpty();
      }
    }.run();
  }

  private static IOException thrownOnPurpose() {
    return new IOException("Thrown on purpose");
  }

  private static BufferedImage mockImage() {
    return createMock(BufferedImage.class);
  }

  private static ImageDecoder mockImageDecoder() {
    return createMock(ImageDecoder.class);
  }
}
