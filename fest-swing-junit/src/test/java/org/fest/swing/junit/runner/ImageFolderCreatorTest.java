/*
 * Created on Mar 18, 2009
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
package org.fest.swing.junit.runner;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Files.currentFolder;

import java.io.File;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ImageFolderCreator}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ImageFolderCreatorTest {

  private FolderCreator folderCreator;
  private ImageFolderCreator imageFolderCreator;

  @BeforeMethod public void setUp() {
    folderCreator = createMock(FolderCreator.class);
    imageFolderCreator = new ImageFolderCreator(folderCreator);
  }

  public void shouldCreateImageFolderUnderCurrentFolder() {
    final File createdFolder = new File("fake");
    new EasyMockTemplate(folderCreator) {
      protected void expectations() {
        expect(folderCreator.createFolder(currentFolder(), "failed-gui-tests")).andReturn(createdFolder);
      }

      protected void codeToTest() {
        assertThat(imageFolderCreator.createImageFolder()).isSameAs(createdFolder);
      }
    }.run();
  }
}
