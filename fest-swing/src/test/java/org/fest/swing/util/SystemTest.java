/*
 * Created on May 1, 2009
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
package org.fest.swing.util;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link System}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class SystemTest {

  public void shouldReturnLineSeparatorFromSystem() {
    assertThat(System.LINE_SEPARATOR).isEqualTo(java.lang.System.getProperty("line.separator"));
  }

  public void shouldReturnNewLineAsLineSeparatorIfCannotGetItFromSystem() {
    final SystemPropertyReader reader = createMock(SystemPropertyReader.class);
    new EasyMockTemplate(reader) {
      protected void expectations() {
        expect(reader.systemProperty("line.separator")).andThrow(new RuntimeException("On purpose"));
      }

      protected void codeToTest() {
        assertThat(System.lineSeparator(reader)).isEqualTo("\n");
      }
    }.run();
  }
}
