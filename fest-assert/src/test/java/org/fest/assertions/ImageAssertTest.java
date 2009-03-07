/*
 * Created on Jun 9, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static java.awt.Color.*;

import static org.fest.assertions.CommonFailures.*;
import static org.fest.assertions.Resources.file;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.concat;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link ImageAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssertTest {

  @AfterClass public void tearDown() {
    ImageAssert.imageReader(new ImageReader());
  }

  @Test public void shouldReadImageFile() throws IOException {
    ImageReaderStub imageReader = new ImageReaderStub();
    BufferedImage read = fivePixelBlueImage();
    imageReader.imageRead(read);
    ImageAssert.imageReader(imageReader);
    String filePath = file("red.png").getPath();
    BufferedImage image = ImageAssert.read(filePath);
    assertSame(image, read);
  }

  @Test public void shouldReturnNullImageFileIfIOExceptionThrown() {
    ImageReaderStub imageReader = new ImageReaderStub();
    IOException toThrow = new IOException();
    imageReader.toThrow(toThrow);
    ImageAssert.imageReader(imageReader);
    String filePath = file("red.png").getPath();
    try {
      ImageAssert.read(filePath);
      fail();
    } catch (IOException e) {
      assertSame(e.getCause(), toThrow);
    }
  }

  @Test public void shouldFailIfImagePathIsNotFileWhenReadingImage() {
    expectIllegalArgumentException("The path 'blah' does not belong to a file").on(new CodeToTest() {
      public void run() throws Throwable {
        ImageAssert.read("blah");
      }
    });
  }

  @Test public void shouldSetTextDescription() {
    ImageAssert assertion = new ImageAssert(fivePixelBlueImage());
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetTextDescriptionSafelyForGroovy() {
    ImageAssert assertion = new ImageAssert(fivePixelBlueImage());
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescription() {
    ImageAssert assertion = new ImageAssert(fivePixelBlueImage());
    assertNull(assertion.description());
    assertion.as(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ImageAssert assertion = new ImageAssert(fivePixelBlueImage());
    assertNull(assertion.description());
    assertion.describedAs(new BasicDescription("A Test"));
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNull extends Condition<BufferedImage> {
    @Override public boolean matches(BufferedImage image) {
      return image != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ImageAssert(fivePixelBlueImage()).satisfies(new NotNull());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("actual value:<null> should satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).satisfies(new NotNull());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition:<NotNull>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").satisfies(new NotNull());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("actual value:<null> should satisfy condition:<Non-null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).satisfies(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] actual value:<null> should satisfy condition:<Non-null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").satisfies(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldPassIfConditionNotSatisfied() {
    new ImageAssert(null).doesNotSatisfy(new NotNull());
  }

  @Test public void shouldThrowErrorIfConditionIsNullWhenCheckingIfNotSatisfied() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).doesNotSatisfy(null);
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfied() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("actual value:<", image, "> should not satisfy condition:<NotNull>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).doesNotSatisfy(new NotNull());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfied() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("[A Test] actual value:<", image, "> should not satisfy condition:<NotNull>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).as("A Test").doesNotSatisfy(new NotNull());
      }
    });
  }

  @Test public void shouldFailIfConditionSatisfiedShowingDescriptionOfCondition() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("actual value:<", image, "> should not satisfy condition:<Non-null>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).doesNotSatisfy(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionSatisfiedShowingDescriptionOfCondition() {
    final BufferedImage image = fivePixelBlueImage();
    String message = concat("[A Test] actual value:<", image, "> should not satisfy condition:<Non-null>");
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).as("A Test").doesNotSatisfy(new NotNull().as("Non-null"));
      }
    });
  }

  @Test public void shouldPassIfImagesAreEqual() {
    new ImageAssert(fivePixelBlueImage()).isEqualTo(fivePixelBlueImage());
  }

  @Test public void shouldPassIfImagesNullAndCheckingEqual() {
    new ImageAssert(null).isEqualTo(null);
  }

  @Test public void shouldFailIfExpectedIsNullWhenCheckingEqual() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("expected:<null> but was:<", a, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isEqualTo(null);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfExpectedIsNullWhenCheckingEqual() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] expected:<null> but was:<", a, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).as("A Test").isEqualTo(null);
      }
    });
  }

  @Test public void shouldFailIfImageWidthsAreNotEqualAndExpectingEqual() {
    expectAssertionError("image size, expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(3, 5, BLUE);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageWidthsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] image size, expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(3, 5, BLUE);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailIfImageHeightsAreNotEqualAndExpectingEqual() {
    expectAssertionError("image size, expected:<(5, 2)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(5, 2, BLUE);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageHeightsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] image size, expected:<(5, 2)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(5, 2, BLUE);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailIfImageColorsAreNotEqualAndExpectingEqual() {
    expectAssertionError("images do not have the same color(s)").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = fivePixelYellowImage();
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageColorsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] images do not have the same color(s)").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(5, 5, YELLOW);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldPassIfImageWidthsAreNotEqual() {
    BufferedImage a = image(3, 5, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldPassIfActualIsNotNullAndExpectedIsNullWhenCheckingNotEqual() {
    BufferedImage a = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(null);
  }

  @Test public void shouldPassIfImageHeightsAreNotEqual() {
    BufferedImage a = image(5, 3, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldPassIfImageColorsAreNotEqual() {
    BufferedImage a = fivePixelBlueImage();
    BufferedImage e = image(5, 5, YELLOW);
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldFailIfImagesAreNullAndExpectingNotEqual() {
    expectAssertionError("actual value:<null> should not be equal to:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).isNotEqualTo(null);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImagesAreNullAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<null> should not be equal to:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").isNotEqualTo(null);
      }
    });
  }

  @Test public void shouldFailIfImagesAreEqualAndExpectingNotEqual() {
    expectAssertionError("images are equal").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).isNotEqualTo(fivePixelBlueImage());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImagesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] images are equal").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).as("A Test").isNotEqualTo(fivePixelBlueImage());
      }
    });
  }

  @Test public void shouldPassIfObjectsAreSame() {
    BufferedImage image = fivePixelBlueImage();
    new ImageAssert(image).isSameAs(image);
  }

  @Test public void shouldFailIfObjectsAreNotSameAndExpectingSame() {
    final BufferedImage a = fivePixelBlueImage();
    final BufferedImage e = fivePixelBlueImage();
    expectAssertionError(concat("expected same instance but found:<", a, "> and:<", e, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isSameAs(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreNotSameAndExpectingSame() {
    final BufferedImage a = fivePixelBlueImage();
    final BufferedImage e = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] expected same instance but found:<", a, "> and:<", e, ">")).on(
        new CodeToTest() {
          public void run() {
            new ImageAssert(a).as("A Test").isSameAs(e);
          }
        });
  }

  @Test public void shouldPassIfObjectsAreNotSame() {
    new ImageAssert(fivePixelBlueImage()).isNotSameAs(fivePixelBlueImage());
  }

  @Test public void shouldFailIfObjectsAreSameAndExpectingNotSame() {
    final BufferedImage image = fivePixelBlueImage();
    expectAssertionError(concat("given objects are same:<", image, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).isNotSameAs(image);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreSameAndExpectingNotSame() {
    final BufferedImage image = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] given objects are same:<", image, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).as("A Test").isNotSameAs(image);
      }
    });
  }

  @Test public void shouldPassIfEqualSize() {
    new ImageAssert(fivePixelBlueImage()).hasSize(new Dimension(5, 5));
  }

  @Test public void shouldThrowErrorIfGivenSizeIsNull() {
    expectIllegalArgumentException("The size to compare to should not be null").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).hasSize(null);
      }
    });
  }

  @Test public void shouldFailIfWidthNotEqual() {
    expectAssertionError("expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).hasSize(new Dimension(3, 5));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfWidthNotEqual() {
    expectAssertionError("[A Test] expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).as("A Test").hasSize(new Dimension(3, 5));
      }
    });
  }

  @Test public void shouldFailIfHeightNotEqual() {
    expectAssertionError("expected:<(5, 3)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).hasSize(new Dimension(5, 3));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfHeightNotEqual() {
    expectAssertionError("[A Test] expected:<(5, 3)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).as("A Test").hasSize(new Dimension(5, 3));
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new ImageAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("<", a, "> should be null")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] <", a, "> should be null")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new ImageAssert(fivePixelBlueImage()).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ImageAssert(null).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").isNotNull();
      }
    });
  }

  private BufferedImage fivePixelBlueImage() {
    return image(5, 5, BLUE);
  }

  private BufferedImage fivePixelYellowImage() {
    return image(5, 5, YELLOW);
  }

  private BufferedImage image(int width, int height, Color color) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = image.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, width, height);
    return image;
  }

  private static class ImageReaderStub extends ImageReader {
    private BufferedImage imageRead;
    private IOException toThrow;

    void imageRead(BufferedImage imageRead) {
      this.imageRead = imageRead;
    }

    void toThrow(IOException toThrow) {
      this.toThrow = toThrow;
    }

    @Override BufferedImage read(File imageFile) throws IOException {
      if (toThrow != null) throw toThrow;
      return imageRead;
    }
  }
}
