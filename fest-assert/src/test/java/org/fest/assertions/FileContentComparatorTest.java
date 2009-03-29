/*
 * Created on Feb 15, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Resources.file;
import static org.fest.util.Strings.concat;
import static org.testng.Assert.assertEquals;

import org.fest.assertions.FileContentComparator.LineDiff;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FileContentComparator}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FileContentComparatorTest {

  private FileContentComparator comparator;

  @BeforeClass public void setUp() {
    comparator = new FileContentComparator();
  }

  @Test(dataProvider = "files") public void shouldReturnNoDiffsForEqualFiles(String fileName) throws Exception {
    LineDiff[] diffs = comparator.compareContents(file(fileName), file(fileName));
    assertEquals(0, diffs.length);
  }

  @DataProvider(name = "files") public Object[][] files() {
    return new Object[][] {
        { "fileAssertTest2.txt" },
        { "fileAssertTest3.txt" },
        { "fileAssertTest4.txt" },
        { "fileAssertTest5.txt" },
    };
  }

  @Test(dataProvider = "differentFiles")
  public void shouldReturnDiffsForNotEqualFiles(String actual, String expected, LineDiff[] diffs) throws Exception {
    LineDiff[] actualDiffs = comparator.compareContents(file(actual), file(expected));
    verifyIfEqual(actualDiffs, diffs);
  }

  private void verifyIfEqual(LineDiff[] actual, LineDiff[] expected) {
    int expectedSize = expected.length;
    assertEquals(actual.length, expectedSize, "diff count");
    for (int i = 0; i < expectedSize; i++) verifyIfEqual(i, actual[i], expected[i]);
  }

  private void verifyIfEqual(int index, LineDiff actual, LineDiff expected) {
    assertEquals(actual.lineNumber, expected.lineNumber, concat("[", index ,"] line number"));
    assertEquals(actual.actual, expected.actual, concat("[", index ,"] actual"));
    assertEquals(actual.expected, expected.expected, concat("[", index ,"] expected"));
  }

  @DataProvider(name = "differentFiles") public Object[][] differentFiles() {
    return new Object[][] {
        { "fileAssertTest2.txt", "fileAssertTest3.txt",
          diffs(diff(1, "abcde fghij abcde fghij", "abcde fghij abcde fghij z")) },
        { "fileAssertTest1.txt", "fileAssertTest2.txt",
          diffs(diff(0, "this file is 22 bytes.", "abcde fghij"), diff(0, "EOF", "abcde fghij")) },
        { "fileAssertTest2.txt", "fileAssertTest1.txt",
          diffs(diff(0, "abcde fghij", "this file is 22 bytes."), diff(0, "abcde fghij", "EOF")) }
    };
  }

  private LineDiff[] diffs(LineDiff...diffs) {
    return diffs;
  }

  private LineDiff diff(int lineNumber, String actual, String expected) {
    return new LineDiff(lineNumber, actual, expected);
  }

}
