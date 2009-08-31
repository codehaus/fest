/*
 * Created on Mar 24, 2009
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
package org.fest.assertions;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Resources.file;
import static org.fest.assertions.Threshold.threshold;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-107" target="_blank">FEST-107</a>.
 *
 * @author Alex Ruiz
 */
@Test public class FEST107_EqualImagesAreConsideredDifferent {

  private ImageReader reader;

  @BeforeClass public void setUpOnce() {
    reader = new ImageReader();
  }

  public void imagesShouldBeEqualWhenUsingThreshold() throws IOException {
    BufferedImage actual = reader.read(file("Basics1SimpleGridActual.png"));
    BufferedImage expected = reader.read(file("Basics1SimpleGridExpected.png"));
    assertThat(actual).isEqualTo(expected, threshold(1));
  }
}
