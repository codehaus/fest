/*
 * Created on Apr 2, 2009
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

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link StandardOutputStreams}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class StandardOutputStreamsTest {

  private StandardOutputStreams streams;

  @BeforeClass public void setUpOnce() {
    streams = new StandardOutputStreams();
  }

  public void shouldReturnTrueIfOutIsSystemOut() {
    assertThat(streams.isStandardOutOrErr(System.out)).isTrue();
  }

  public void shouldReturnTrueIfOutIsSystemErr() {
    assertThat(streams.isStandardOutOrErr(System.err)).isTrue();
  }

  public void shouldReturnFalseIfOutIsNotStandard() {
    OutputStream out = new ByteArrayOutputStream();
    assertThat(streams.isStandardOutOrErr(out)).isFalse();
  }
}
