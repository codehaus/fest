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
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;

import static java.lang.String.valueOf;
import static java.util.logging.Logger.getAnonymousLogger;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=76">Bug 76</a>.
 *
 * @author Wim Deblauwe
 * @author Yvonne Wang
 */
@Test(groups = { GUI, BUG })
public class Bug76_JOptionPaneTest {

  private static Logger logger = getAnonymousLogger();

  private DialogFixture starter;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  public void shouldFindOptionPane() {
    JOptionPaneStarter optionPaneStarter = JOptionPaneStarter.createNew(null, "Message 1");
    starter = new DialogFixture(optionPaneStarter);
    starter.show();
    starter.requireVisible();
    starter.button().click();
    pauseForOneSecond();
    JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(starter.robot);
    fixture.requireMessage("Message 1");
    fixture.button().click();
  }

  public void shouldFindOptionPaneAgain() {
    JOptionPaneStarter optionPaneStarter = JOptionPaneStarter.createNew(null, "Message 2");
    starter = new DialogFixture(optionPaneStarter);
    starter.show();
    starter.requireVisible();
    starter.button().click();
    pauseForOneSecond();
    JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(starter.robot);
    fixture.requireMessage("Message 2");
    fixture.button().click();
  }

  private void pauseForOneSecond() {
    int timeToPause = 1000;
    logger.info(concat("Pausing for ", valueOf(timeToPause), " ms"));
    pause(timeToPause);
  }

  @AfterMethod public void stopGui() {
    starter.cleanUp();
  }

  private static class JOptionPaneStarter extends JDialog {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static JOptionPaneStarter createNew(final Frame owner, final String message) {
      return execute(new GuiQuery<JOptionPaneStarter>() {
        protected JOptionPaneStarter executeInEDT() {
          return new JOptionPaneStarter(owner, message);
        }
      });
    }

    private JOptionPaneStarter(Frame owner, String message) {
      super(owner, "JOptionPane Starter");
      setContentPane(createContentPane(message));
    }

    private Container createContentPane(String message) {
      JPanel panel = new JPanel();
      panel.add(new JButton(new OpenJOptionPaneAction(message)));
      return panel;
    }

    private class OpenJOptionPaneAction extends AbstractAction {
      private static final long serialVersionUID = 1L;

      private final String m_message;

      OpenJOptionPaneAction(String message) {
        super("Start!");
        m_message = message;
      }

      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(JOptionPaneStarter.this, m_message);
      }
    }
  }
}
