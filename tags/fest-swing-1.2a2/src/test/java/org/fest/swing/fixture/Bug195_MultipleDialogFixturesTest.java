/*
 * Created on Oct 13, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;

import static java.awt.BorderLayout.*;

import static org.fest.swing.core.BasicRobot.robotWithCurrentAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Strings.*;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=195" target="_blank">Bug 195</a>.
 *
 * @author Bryan Shannon
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug195_MultipleDialogFixturesTest {

  private Robot robot;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithCurrentAwtHierarchy();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  // Runs the test (that fails for me. :( )
  public void shouldFindDialogMultipleTimes() {
    JDialog dialog = TestDialog.createNew("title 1");
    robot.showWindow(dialog);
    DialogFixture fixtureOne = WindowFinder.findDialog(new DialogTitleStartsWithMatcher("title")).using(robot);
    fixtureOne.button("OK").click();

    dialog = TestDialog.createNew("title 2");
    robot.showWindow(dialog);
    DialogFixture fixtureTwo = WindowFinder.findDialog(new DialogTitleStartsWithMatcher("title")).using(robot);
    fixtureTwo.button("OK").click();
  }

  // A GenericTypeMatcher that matches a Dialog title that starts with a string.
  private static class DialogTitleStartsWithMatcher extends GenericTypeMatcher<Dialog> {
    private final String matchString;

    public DialogTitleStartsWithMatcher(String s) {
      super(Dialog.class, false);
      this.matchString = s != null ? s.toUpperCase() : null;
    }

    protected boolean isMatching(Dialog dialog) {
      String title = dialog.getTitle();
      return title != null && title.toUpperCase().startsWith(matchString) && isShowing(dialog);
    }

    @RunsInEDT
    private static boolean isShowing(final Dialog dialog) {
      return execute(new GuiQuery<Boolean>() {
        protected Boolean executeInEDT() {
          return dialog.isShowing();
        }
      });
    }

    public String toString() {
      return concat(DialogTitleStartsWithMatcher.class.getSimpleName(), "(", quote(matchString), ")");
    }
  }

  // A subclass of dialog that has a label and a simple button.
  private static class TestDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    JButton okayButton = new JButton();

    @RunsInEDT
    static TestDialog createNew(final String title) {
      return execute(new GuiQuery<TestDialog>() {
        protected TestDialog executeInEDT() {
          return new TestDialog(title);
        }
      });
    }
    
    private TestDialog(String title) {
      super();
      setName(title);
      setLayout(new BorderLayout());
      add(new JLabel("Test Dialog"), CENTER);
      okayButton.setAction(new OKAction(this));
      okayButton.setName("OK");
      getContentPane().add(okayButton, SOUTH);
      setTitle(title);
    }
  }

  // Simple action for the dialog that closes the dialog.
  private static class OKAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    private final JDialog dialog;

    public OKAction(JDialog dialog) {
      super("OK");
      this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
      dialog.setVisible(false);
      dialog.dispose();
    }
  }
}
