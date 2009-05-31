/*
 * Created on Dec 21, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;

import static java.lang.String.valueOf;
import static java.util.logging.Logger.getAnonymousLogger;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=80">Bug 80</a>.
 *
 * @author Wim Deblauwe
 * @author Yvonne Wang
 */
@Test(groups = { GUI, BUG })
public class Bug80_JDialogTest {

  private static Logger logger = getAnonymousLogger();

  private DialogFixture starter;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    JDialogStarter optionPaneStarter = JDialogStarter.createNew();
    starter = new DialogFixture(optionPaneStarter);
  }

  @AfterMethod public void tearDown() {
    starter.cleanUp();
  }

  public void shouldFindNestedDialogTwoTimes() {
    starter.show();
    starter.requireVisible();
    starter.button("start").click();
    showAndHideNestedDialog();
    starter.button("start").click();
    int timeToPause = 3000;
    logger.info(concat("Pausing for ", valueOf(timeToPause), " ms"));
    pause(timeToPause);
    showAndHideNestedDialog();
  }

  private void showAndHideNestedDialog() {
    DialogFixture nested = starter.dialog("NestedDialog");
    nested.requireVisible();
    nested.requireModal();
    nested.button().click();
    nested.requireNotVisible();
  }

  static class JDialogStarter extends JDialog {
    private static final long serialVersionUID = 1L;

    static JDialogStarter createNew() {
      return execute(new GuiQuery<JDialogStarter>() {
        protected JDialogStarter executeInEDT() {
          return new JDialogStarter();
        }
      });
    }
    
    private JDialogStarter() {
      setTitle(Bug80_JDialogTest.class.getSimpleName());
      setContentPane(createContentPane());
    }

    private Container createContentPane() {
      JPanel panel = new JPanel();
      JButton startButton = new JButton(new OpenJDialogAction());
      startButton.setName("start");
      panel.add(startButton);
      return panel;
    }

    private class OpenJDialogAction extends AbstractAction {
      private static final long serialVersionUID = 1L;

      OpenJDialogAction() { super("Start!"); }

      public void actionPerformed(ActionEvent e) {
        NestedJDialog dialog = new NestedJDialog(JDialogStarter.this);
        dialog.pack();
        dialog.setVisible(true);
      }
    }

    private static class NestedJDialog extends JDialog {
      private static final long serialVersionUID = 1L;

      NestedJDialog(JDialog owner) {
        super(owner, true);
        setContentPane(createContentPane());
        setName("NestedDialog");
      }

      private JPanel createContentPane() {
        JPanel result = new JPanel();
        result.add(new JLabel("Nested dialog"));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            NestedJDialog.this.dispose();
          }
        });
        closeButton.setName("close");
        result.add(closeButton);
        return result;
      }
    }
  }
}
