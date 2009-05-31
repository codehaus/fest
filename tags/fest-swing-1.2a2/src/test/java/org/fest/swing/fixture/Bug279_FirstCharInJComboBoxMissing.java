/*
 * Created on Jan 11, 2009
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
package org.fest.swing.fixture;

import javax.swing.JComboBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.event.KeyEvent.VK_ENTER;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=279">Bug 279</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug279_FirstCharInJComboBoxMissing {

  private FrameFixture window;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    window = new FrameFixture(MyWindow.createNew());
    window.show();
  }
  
  @AfterMethod public void tearDown() {
    window.cleanUp();
  }
  
  public void shouldEnterTextInEditableComboBox() {
    window.comboBox("comboBox").doubleClick().enterText("hey").pressAndReleaseKeys(VK_ENTER);
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
    
    final JComboBox comboBox = new JComboBox(array("One", "Two", "Three"));
    
    private MyWindow() {
      super(Bug279_FirstCharInJComboBoxMissing.class);
      comboBox.setName("comboBox");
      comboBox.setEditable(true);
      addComponents(comboBox);
    }
  }
}
