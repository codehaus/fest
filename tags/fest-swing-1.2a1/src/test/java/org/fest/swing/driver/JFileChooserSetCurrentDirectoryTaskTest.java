/*
 * Created on Aug 8, 2008
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
import java.io.IOException;

import javax.swing.JFileChooser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.UnexpectedException.unexpected;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Files.temporaryFolder;

/**
 * Tests for <code>{@link JFileChooserSetCurrentDirectoryTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JFileChooserSetCurrentDirectoryTaskTest {

  private Robot robot;
  private JFileChooser fileChooser;
  private File directoryToSelect;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    directoryToSelect = temporaryFolder();
    MyWindow window = MyWindow.createNew();
    fileChooser = window.fileChooser;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
    directoryToSelect.delete();
  }

  public void shouldSetCurrentDirectory() {
    JFileChooserSetCurrentDirectoryTask.validateAndSetCurrentDirectory(fileChooser, directoryToSelect);
    robot.waitForIdle();
    assertThat(currentDirectoryPath()).isEqualTo(canonicalPathOf(directoryToSelect));
  }

  private String currentDirectoryPath() {
    return canonicalPathOf(currentDirectoryOf(fileChooser));
  }

  @RunsInEDT
  private static File currentDirectoryOf(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getCurrentDirectory();
      }
    });
  }

  private static String canonicalPathOf(File file) {
    try {
      return file.getCanonicalPath();
    } catch (IOException e) {
      throw unexpected(e);
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JFileChooser fileChooser = new JFileChooser();

    private MyWindow() {
      super(JFileChooserSelectFileTask.class);
      add(fileChooser);
    }
  }
}
