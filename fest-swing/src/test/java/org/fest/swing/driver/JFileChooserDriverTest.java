/*
 * Created on Feb 26, 2008
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
package org.fest.swing.driver;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static javax.swing.JFileChooser.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.AbstractButtonTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.util.Arrays.array;
import static org.fest.util.Files.*;
import static org.fest.util.Strings.isEmpty;

/**
 * Tests for <code>{@link JFileChooserDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JFileChooserDriverTest {

  private Robot robot;
  private JFileChooserDriver driver;
  private MyWindow window;
  private JFileChooser fileChooser;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JFileChooserDriver(robot);
    window = MyWindow.createNew();
    fileChooser = window.fileChooser;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindCancelButton() {
    JButton cancelButton = driver.cancelButton(fileChooser);
    assertThat(cancelButton).isNotNull();
    assertThat(textOf(cancelButton)).isEqualTo(cancelButtonText());
  }

  private String cancelButtonText() {
    return UIManager.getString("FileChooser.cancelButtonText");
  }

  public void shouldSelectFile() {
    File temporaryFile = newTemporaryFile();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      File selectedFile = selectedFileIn(fileChooser);
      assertThat(selectedFile).isSameAs(temporaryFile);
    } finally {
      temporaryFile.delete();
    }
  }

  @RunsInEDT
  private static File selectedFileIn(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getSelectedFile();
      }
    });
  }

  public void shouldThrowErrorWhenSelectingFileInDisabledJFileChooser() {
    File temporaryFile = newTemporaryFile();
    disableFileChooser();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    } finally {
      temporaryFile.delete();
    }
  }
  
  public void shouldThrowErrorIfFileToSelectIsNull() {
    try {
      driver.selectFile(fileChooser, null);
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).isEqualTo("The file to select should not be null");
    }
  }

  public void shouldThrowErrorWhenSelectingFileInNotShowingJFileChooser() {
    hideWindow();
    try {
      driver.selectFile(fileChooser, new File("Fake"));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorIfJFileChooserCanOnlySelectFoldersAndFileToSelectIsFile() {
    File temporaryFile = newTemporaryFile();
    setFileChooserToSelectDirectoriesOnly();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open directories");
    } finally {
      temporaryFile.delete();
    }
  }

  public void shouldThrowErrorIfJFileChooserCanOnlySelectFilesAndFileToSelectIsFolder() {
    File temporaryFolder = newTemporaryFolder();
    setFileChooserToSelectFilesOny();
    try {
      driver.selectFile(fileChooser, temporaryFolder);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser cannot open directories");
    } finally {
      temporaryFolder.delete();
    }
  }

  public void shouldThrowErrorIfFilesToSelectIsNull() {
    try {
      driver.selectFiles(fileChooser, null);
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).isEqualTo("The files to select should not be null");
    }
  }

  public void shouldThrowErrorIfFilesToSelectIsEmpty() {
    try {
      driver.selectFiles(fileChooser, new File[0]);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("The array of files to select should not be empty");
    }
  }
  
  public void shouldThrowErrorIfAnyFileToSelectIsNull() {
    try {
      driver.selectFiles(fileChooser, array(new File("Fake"), null));
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).isEqualTo("The array of files to select should not contain null elements");
    }
  }
  
  public void shouldThrowErrorWhenSelectingFilesInNotShowingJFileChooser() {
    hideWindow();
    try {
      driver.selectFiles(fileChooser, array(new File("Fake")));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    } 
  }
  
  public void shouldThrowErrorWhenSelectingFilesAndJFileChooserCannotHandleMultipleSelection() {
    disableMultipleSelection();
    try {
      driver.selectFiles(fileChooser, array(new File("Fake1"), new File("Fake2")));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("Expecting file chooser")
                                .contains("to handle multiple selection");
    }
  }

  private void disableMultipleSelection() {
    setMultipleSelectionEnabled(fileChooser, false);
    robot.waitForIdle();
  }

  public void shouldThrowErrorIfJFileChooserCanOnlySelectFoldersAndOneOfFilesToSelectIsFile() {
    enableMultipleSelection();
    File temporaryFolder = newTemporaryFolder();
    File temporaryFile = newTemporaryFile();
    setFileChooserToSelectDirectoriesOnly();
    try {
      driver.selectFiles(fileChooser, array(temporaryFolder, temporaryFile));
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open directories");
    } finally {
      temporaryFile.delete();
      temporaryFile.delete();
    }
  }

  private void setFileChooserToSelectDirectoriesOnly() {
    setFileSelectionMode(DIRECTORIES_ONLY);
  }

  public void shouldThrowErrorIfJFileChooserCanOnlySelectFilesAndOneOfFilesToSelectIsFolder() {
    enableMultipleSelection();
    File temporaryFolder = newTemporaryFolder();
    File temporaryFile = newTemporaryFile();
    setFileChooserToSelectFilesOny();
    try {
      driver.selectFiles(fileChooser, array(temporaryFolder, temporaryFile));
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser cannot open directories");
    } finally {
      temporaryFolder.delete();
      temporaryFile.delete();
    }
  }

  public void shouldNotThrowErrorIfJFileChooserCannotHandleMultipleSelectionAndArrayOfFilesToSelectHasOneElementOnly() {
    disableMultipleSelection();
    File temporaryFile = newTemporaryFile();
    try {
      driver.selectFiles(fileChooser, array(temporaryFile));
      File[] selectedFiles = selectedFilesIn(fileChooser);
      assertThat(selectedFiles).containsOnly(temporaryFile);
    } finally {
      temporaryFile.delete();
    }
  }
  
  public void shouldSelectFiles() {
    enableMultipleSelection();
    setFileChooserToSelectFilesAndDirectories();
    File temporaryFolder = newTemporaryFolder();
    File temporaryFile = newTemporaryFile();
    try {
      driver.selectFiles(fileChooser, array(temporaryFolder, temporaryFile));
      File[] selectedFiles = selectedFilesIn(fileChooser);
      assertThat(selectedFiles).containsOnly(temporaryFolder, temporaryFile);
    } finally {
      temporaryFolder.delete();
      temporaryFile.delete();
    }
  }

  private void setFileChooserToSelectFilesAndDirectories() {
    setFileSelectionMode(fileChooser, FILES_AND_DIRECTORIES);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static File[] selectedFilesIn(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File[]>() {
      protected File[] executeInEDT() {
        return fileChooser.getSelectedFiles();
      }
    });
  }

  private void enableMultipleSelection() {
    setMultipleSelectionEnabled(fileChooser, true);
    robot.waitForIdle();
  }
  
  @RunsInEDT
  private static void setMultipleSelectionEnabled(final JFileChooser fileChooser, final boolean b) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        fileChooser.setMultiSelectionEnabled(b);
      }
    });
  }
  
  private void setFileChooserToSelectFilesOny() {
    setFileSelectionMode(FILES_ONLY);
  }

  private void setFileSelectionMode(int filesOnly) {
    setFileSelectionMode(fileChooser, filesOnly);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setFileSelectionMode(final JFileChooser fileChooser, final int mode) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        fileChooser.setFileSelectionMode(mode);       
      }
    });
  }
  
  public void shouldFindApproveButton() {
    JButton approveButton = driver.approveButton(fileChooser);
    assertThat(approveButton).isNotNull();
    assertThat(textOf(approveButton)).isEqualTo(approveButtonText(fileChooser));
  }

  private static String approveButtonText(final JFileChooser fileChooser) {
    String text = fileChooser.getApproveButtonText();
    if (!isEmpty(text)) return text;
    return fileChooser.getUI().getApproveButtonText(fileChooser);
  }

  public void shouldFindFileNameTextBox() {
    JTextField fileNameTextBox = driver.fileNameTextBox(fileChooser);
    assertThat(fileNameTextBox).isNotNull();
  }

  public void shouldSetCurrentDirectory() {
    File userHome = userHomeDirectory();
    driver.setCurrentDirectory(fileChooser, userHome);
    assertThat(currentDirectoryAbsolutePath(fileChooser)).isEqualTo(userHome.getAbsolutePath());
  }

  public void shouldThrowErrorWhenSettingCurrentDirectoryInDisabledJFileChooser() {
    disableFileChooser();
    try {
      driver.setCurrentDirectory(fileChooser, userHomeDirectory());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @RunsInEDT
  private void disableFileChooser() {
    disable(fileChooser);
    robot.waitForIdle();
  }
  
  public void shouldThrowErrorWhenSettingCurrentDirectoryInNotShowingJFileChooser() {
    hideWindow();
    try {
      driver.setCurrentDirectory(fileChooser, userHomeDirectory());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
  
  private File userHomeDirectory() {
    String homePath = System.getProperty("user.home");
    File userHome = new File(homePath);
    assertThat(userHome.isDirectory()).isTrue();
    return userHome;
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static String currentDirectoryAbsolutePath(final JFileChooser fileChooser) {
    File currentDirectory = execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getCurrentDirectory();
      }
    });
    return currentDirectory.getAbsolutePath();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JFileChooser fileChooser = new JFileChooser();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
    
    private MyWindow() {
      super(JFileChooserDriverTest.class);
      fileChooser.setCurrentDirectory(temporaryFolder());
      fileChooser.setDialogType(OPEN_DIALOG);
      addComponents(fileChooser);
    }
  }
}
