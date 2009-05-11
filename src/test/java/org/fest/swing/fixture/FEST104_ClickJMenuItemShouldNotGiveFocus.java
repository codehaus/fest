/*
 * Created on Apr 28, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.BUG;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.timing.Pause.pause;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-104" target="_blank">FEST-104</a>
 *
 * @author Alex Ruiz
 */
@Test(groups = { BUG, GUI })
public class FEST104_ClickJMenuItemShouldNotGiveFocus {

  private MyWindow window;
  private FrameFixture frameFixture;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    window = MyWindow.createNew();
    frameFixture = new FrameFixture(window);
    frameFixture.show();
  }

  @AfterMethod public void tearDown() {
    frameFixture.cleanUp();
  }

  public void shouldNotGiveFocusToJMenuItemWhenClickingIt() {
    FocusRecorder focusRecorder = new FocusRecorder();
    window.newMenu.addFocusListener(focusRecorder);
    frameFixture.menuItemWithPath("File", "New").click();
    frameFixture.optionPane().requireMessage("Hello World");
    pause(5000); // let the focus be set on the menu
    assertThat(focusRecorder.focusReceived).isFalse();
  }

  private static class FocusRecorder extends FocusAdapter {
    boolean focusReceived;

    @Override public void focusGained(FocusEvent e) {
      focusReceived = true;
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JMenuItem newMenu = new JMenuItem("New");

    private MyWindow() {
      super(FEST104_ClickJMenuItemShouldNotGiveFocus.class);
      JMenuBar menuBar = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      fileMenu.add(newMenu);
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);
      setPreferredSize(new Dimension(200, 100));
      newMenu.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(MyWindow.this, "Hello World");
        }
      });
      newMenu.setFocusable(true);
    }
  }
}
