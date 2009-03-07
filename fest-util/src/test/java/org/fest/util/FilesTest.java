/*
 * Created on Sep 23, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.util;

import static java.io.File.separator;
import static org.fest.util.Collections.list;
import static org.fest.util.Strings.*;
import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Files}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FilesTest {

  private FolderFixture root;
  
  @BeforeClass public void setUp() throws Exception {
    root = new FolderFixture("root");
    root.addFolder("dir_1").addFiles("file_1_1", "file_1_2").addFolder("dir_1_1").addFiles("file_1_1_1");
    root.addFolder("dir_2").addFiles("file_2_1", "file_2_2", "file_2_3");
  }
  
  @AfterClass public void tearDown() {
    root.delete();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGettingFilesNameInNotExistingDirectory() {
    String path = concat("root", separator, "not_existing_dir");
    Files.fileNamesIn(path, false);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGettingFilesNameAndGivenPathIsNotDirectory() throws Exception {
    String fileName = "file_1";
    root.addFiles(fileName);
    String path = concat("root", separator, fileName);
    Files.fileNamesIn(path, false);
  }

  @Test public void shouldReturnNamesOfFilesInGivenDirectoryWithoutLookingInSubdirectories() {
    String path = concat("root", separator, "dir_1");
    assertContainsFiles(Files.fileNamesIn(path, false), list("file_1_1", "file_1_2"));
  }
  
  @Test public void shouldReturnNamesOfFilesInGivenDirectoryAndItsSubdirectories() {
    String path = concat("root", separator, "dir_1");
    assertContainsFiles(Files.fileNamesIn(path, true), list("file_1_1", "file_1_2", "file_1_1_1"));
  }

  private void assertContainsFiles(List<String> actualFiles, List<String> expectedFiles) {
    assertNoDuplicates(actualFiles);
    for (String fileName : actualFiles) {
      String name = new File(fileName).getName();
      assertTrue(expectedFiles.remove(name), concat("The file ", quote(name), " should not be in the list"));
    }
    assertTrue(expectedFiles.isEmpty(), concat("Actual list of files should contain ", expectedFiles));
  }
  
  private void assertNoDuplicates(List<String> actualFiles) {
    if (actualFiles == null || actualFiles.isEmpty()) return;
    HashSet<String> withoutDuplicates = new HashSet<String>(actualFiles);
    assertEquals(actualFiles.size(), withoutDuplicates.size(), concat(actualFiles, " contains duplicates"));
  }
  
  @Test public void shouldFindTemporaryFolderPath() {
    String actualPath = Files.temporaryFolderPath();
    String expectedPath = append(separator).to(System.getProperty("java.io.tmpdir"));
    assertEquals(actualPath, expectedPath);
  }
  
  @Test(dependsOnMethods = "shouldFindTemporaryFolderPath")
  public void shouldFindTemporaryFolder() {
    File temporaryFolder = Files.temporaryFolder();
    assertTrue(temporaryFolder.isDirectory());
    String actualPath = append(separator).to(temporaryFolder.getAbsolutePath());
    assertEquals(actualPath, Files.temporaryFolderPath());  
  }
  
  @Test(dependsOnMethods = "shouldFindTemporaryFolderPath")
  public void shouldCreateNewTemporaryFile() {
    File temporaryFile = Files.newTemporaryFile();
    assertTrue(temporaryFile.isFile());
    temporaryFile.delete();
  }
  
  @Test(dependsOnMethods = "shouldFindTemporaryFolderPath")
  public void shouldCreateNewTemporaryFolder() {
    File temporaryFolder = Files.newTemporaryFolder();
    assertTrue(temporaryFolder.isDirectory());
    temporaryFolder.delete();
  }

  @Test(expectedExceptions = FilesException.class)
  public void shouldThrowErrorIfNewFilePathIsNonEmptyDirectory() {
    Files.newFile("root");
  }

  @Test(expectedExceptions = FilesException.class)
  public void shouldThrowErrorIfNewFilePathIsExistingFile() {
    String path = join("root", "dir_1", "file_1_1").with(separator);
    Files.newFile(path);
  }
  
  @Test public void shouldCreateNewFile() {
    File newFile = Files.newFile("file");
    assertTrue(newFile.isFile());
    assertTrue(newFile.delete());
  }
  
  @Test public void shouldCreateNewFolder() {
    File newFolder = Files.newFolder("folder");
    assertTrue(newFolder.isDirectory());
    assertTrue(newFolder.delete());
  }
  
  @Test public void shouldReturnCurrentFolder() throws IOException {
    File expected = new File(".");
    File actual = Files.currentFolder();
    assertEquals(actual.getCanonicalPath(), expected.getCanonicalPath());
  }
  
  @Test public void shouldDeleteFolder() throws IOException {
    FolderFixture dir3 = new FolderFixture("dir_3");
    dir3.addFiles("file_3_1").addFiles("file_3_2").addFiles("file_3_2");
    dir3.addFolder("dir_3_1").addFiles("file_3_1_1").addFiles("file_3_1_2");
    String path = dir3.dir().getCanonicalPath();
    assertTrue(new File(path).exists());
    Files.delete(dir3.dir());
    assertFalse(new File(path).exists());
  }
}
