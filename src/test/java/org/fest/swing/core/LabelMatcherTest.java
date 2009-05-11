/*
 * Created on Jan 10, 2008
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
package org.fest.swing.core;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link LabelMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class LabelMatcherTest {
  
  private static final String LABEL_TEXT = "Hello";

  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
       ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnTrueIfJLabelTextMatches() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.buttonLabel)).isTrue();
  }

  public void shouldReturnFalseIfJLabelTextMatchesButJLabelDoesNotHaveComponentFor() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.label)).isFalse();
  }

  public void shouldReturnFalseIsJLabelTextDoesNotMatch() {
    LabelMatcher matcher = new LabelMatcher("Bye");
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  public void shouldReturnFalseIsComponentIsNotJLabel() {
    LabelMatcher matcher = new LabelMatcher("Hello");
    assertThat(matcher.matches(window.button)).isFalse();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfLabelIsNull() {
    new LabelMatcher(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfLabelIsEmpty() {
    new LabelMatcher("");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTypeIsNull() {
    new LabelMatcher(LABEL_TEXT, null);
  }

  public void shouldReturnTrueIfLabelMatchesWhenNotRequiringShowing() {
    window.display();
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.buttonLabel)).isTrue();
  }

  public void shouldReturnFalseIfLabelDoesNotMatchAndWhenRequiringShowing() {
    window.display();
    LabelMatcher matcher = new LabelMatcher("b", true);
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  public void shouldReturnFalseIfLabelMatchesAndIsShowingDoesNotMatch() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT, true);
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  public void shouldReturnFalseIfLabelAndIsShowingDoNotMatch() {
    LabelMatcher matcher = new LabelMatcher("b", true);
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  public void shouldReturnTrueIfLabelAndTypeMatchWhenNotRequiringShowing() {
    window.display();
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT, JButton.class);
    assertThat(matcher.matches(window.buttonLabel)).isTrue();
  }

  public void shouldReturnFalseIfTypeDoesNotMatchAndWhenRequiringShowing() {
    window.display();
    LabelMatcher matcher = new LabelMatcher("b", JTextField.class, true);
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  public void shouldReturnFalseIfLabelAndTypeMatchAndIsShowingDoesNotMatch() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT, JButton.class, true);
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  public void shouldReturnFalseIfNothingMatches() {
    LabelMatcher matcher = new LabelMatcher("b", JTextField.class, true);
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }
  
  public void shouldImplementToString() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.toString()).contains("label='Hello'")
                                  .contains("type=java.awt.Component")
                                  .contains("requireShowing=false");
  }

  protected static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JLabel buttonLabel= new JLabel(LABEL_TEXT);
    final JButton button = new JButton("A Button");
    final JLabel label = new JLabel(LABEL_TEXT);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(LabelMatcherTest.class);
      addComponents(buttonLabel, button, label);
      buttonLabel.setLabelFor(button);
    }
  }
}
