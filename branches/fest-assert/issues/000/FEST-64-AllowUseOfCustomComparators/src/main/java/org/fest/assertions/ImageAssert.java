/*
 * Created on Jun 7, 2007
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
package org.fest.assertions;

import static java.lang.String.valueOf;
import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.assertions.Threshold.threshold;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Understands assertion methods for images. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(BufferedImage)}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssert extends GenericAssert<BufferedImage> {

  private static final Threshold ZERO_THRESHOLD = threshold(0);

  private static ImageReader imageReader = new ImageReader();

  /**
   * Reads the image in the specified path.
   * @param imageFilePath the path of the image to read.
   * @return the read image.
   * @throws IllegalArgumentException if the given path does not belong to a file.
   * @throws IOException if any I/O error occurred while reading the image.
   */
  public static BufferedImage read(String imageFilePath) throws IOException {
    File imageFile = new File(imageFilePath);
    if (!imageFile.isFile())
      throw new IllegalArgumentException(concat("The path ", quote(imageFilePath), " does not belong to a file"));
    return imageReader.read(imageFile);
  }

  protected ImageAssert(BufferedImage actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(picture).<strong>as</strong>(&quot;Vacation Picture&quot;).hasSize(new Dimension(800, 600));
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ImageAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a href="http://groovy.codehaus.org/"
   * target="_blank">Groovy</a>. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(picture).<strong>describedAs</strong>(&quot;Vacation Picture&quot;).hasSize(new Dimension(800, 600));
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ImageAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(picture).<strong>as</strong>(new BasicDescription(&quot;Vacation Picture&quot;)).hasSize(new Dimension(800, 600));
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ImageAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(picture).<strong>describedAs</strong>(new BasicDescription(&quot;Vacation Picture&quot;)).hasSize(new Dimension(800, 600));
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ImageAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual image satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual image does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public ImageAssert satisfies(Condition<BufferedImage> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual image does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual image satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public ImageAssert doesNotSatisfy(Condition<BufferedImage> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if they have the same size and the
   * pixels at the same coordinates have the same color.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   */
  public ImageAssert isEqualTo(BufferedImage expected) {
    return isEqualTo(expected, ZERO_THRESHOLD);
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if:
   * <ol>
   * <li>they have the same size</li>
   * <li>the difference between the RGB values of the color of each pixel is less than or equal to the given threshold</li>
   * </ol>
   * @param expected the given image to compare the actual image to.
   * @param threshold the threshold to use to decide if the color of two pixels are similar: two pixels that are
   *          identical to the human eye may still have slightly different color values. For example, by using a
   *          threshold of 1 we can indicate that a blue value of 60 is similar to a blue value of 61.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   * @since 1.1
   */
  public ImageAssert isEqualTo(BufferedImage expected, Threshold threshold) {
    if (areEqual(actual, expected)) return this;
    failIfNull(expected);
    failIfNotEqual(sizeOf(actual), sizeOf(expected));
    failIfNotEqualColor(expected, threshold);
    return this;
  }

  private void failIfNull(BufferedImage expected) {
    if (expected != null) return;
    fail(errorMessageIfNotEqual(actual, null));
  }

  private void failIfNotEqual(Dimension a, Dimension e) {
    if (!areEqual(a, e)) fail(concat("image size, expected:", inBrackets(e), " but was:", inBrackets(a)));
  }

  private void failIfNotEqualColor(BufferedImage expected, Threshold threshold) {
    int w = actual.getWidth();
    int h = actual.getHeight();
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        failIfNotEqual(new RGBColor(actual.getRGB(x, y)), new RGBColor(expected.getRGB(x, y)), threshold, x, y);
  }

  private void failIfNotEqual(RGBColor a, RGBColor e, Threshold threshold, int x, int y) {
    if (a.isEqualTo(e, threshold.value())) return;
    fail(concat("expected:", inBrackets(a), " but was:", inBrackets(e), " at pixel [", valueOf(x), ",", valueOf(y), "]"));
  }

  /**
   * Verifies that the actual image is not equal to the given one. Two images are equal if they have the same size and
   * the pixels at the same coordinates have the same color.
   * @param image the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is equal to the given one.
   */
  public ImageAssert isNotEqualTo(BufferedImage image) {
    if (areEqual(actual, image)) fail(errorMessageIfEqual(actual, image));
    if (image == null) return this;
    if (areEqual(sizeOf(actual), sizeOf(image)) && hasEqualColor(image)) fail("images are equal");
    return this;
  }

  private static Dimension sizeOf(BufferedImage image) {
    return new Dimension(image.getWidth(), image.getHeight());
  }

  private boolean hasEqualColor(BufferedImage expected) {
    int w = actual.getWidth();
    int h = actual.getHeight();
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        if (actual.getRGB(x, y) != expected.getRGB(x, y)) return false;
    return true;
  }

  /**
   * Verifies that the actual image is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual image is <code>null</code>.
   */
  public ImageAssert isNotNull() {
    assertNotNull();
    return this;
  }

  /**
   * Verifies that the actual image is not the same as the given one.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is the same as the given one.
   */
  public ImageAssert isNotSameAs(BufferedImage expected) {
    assertNotSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual image is the same as the given one.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not the same as the given one.
   */
  public ImageAssert isSameAs(BufferedImage expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the size of the actual image is equal to the given one.
   * @param expected the expected size of the actual image.
   * @return this assertion object.
   * @throws AssertionError if the actual image is <code>null</code>.
   * @throws AssertionError if the size of the actual image is not equal to the given one.
   */
  public ImageAssert hasSize(Dimension expected) {
    isNotNull();
    if (expected == null) throw new IllegalArgumentException("The size to compare to should not be null");
    Dimension actualDimension = new Dimension(actual.getWidth(), actual.getHeight());
    Fail.failIfNotEqual(description(), actualDimension, expected);
    return this;
  }

  static void imageReader(ImageReader newImageReader) {
    imageReader = newImageReader;
  }

  /** {@inheritDoc} */
  @Override
  public void isNull() {
    assertNull();
  }
}
