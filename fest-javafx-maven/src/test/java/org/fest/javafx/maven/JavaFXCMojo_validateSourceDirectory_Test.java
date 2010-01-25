/*
 * Created on Jan 24, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.javafx.maven;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.javafx.maven.CommonAssertions.failWhenExpectingUnexpectedError;
import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.fest.mocks.EasyMockTemplate;
import org.fest.mocks.UnexpectedError;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link JavaFXCMojo#validateSourceDirectory()}</code>.
 *
 * @author Alex Ruiz
 */
public class JavaFXCMojo_validateSourceDirectory_Test {

  private JavaFXCMojo mojo;
  private File folder;

  @Before
  public void setUp() {
    mojo = new JavaFXCMojo();
    mojo.setLog(new LogStub());
    folder = createMock(File.class);
    mojo.sourceDirectory = folder;
  }

  @Test
  public void should_throw_error_if_source_directory_is_not_existing_directory() {
    try {
      new EasyMockTemplate(folder) {
        protected void expectations() throws MojoExecutionException {
          expect(folder.isDirectory()).andReturn(false);
        }

        protected void codeToTest() throws MojoExecutionException {
          mojo.validateSourceDirectory();
        }
      }.run();
      failWhenExpectingUnexpectedError();
    } catch (UnexpectedError e) {
      assertThat(e.getCause()).isInstanceOf(MojoExecutionException.class);
    }
  }
}