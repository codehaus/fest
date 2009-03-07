/*
 * Created on Feb 13, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JOptionPaneDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JOptionPanes.optionPane;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link JOptionPaneFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFixtureTest extends CommonComponentFixtureTestCase<JOptionPane> {

  private JOptionPaneDriver driver;
  private JOptionPane target;
  private JOptionPaneFixture fixture;

  void onSetUp() {
    driver = createMock(JOptionPaneDriver.class);
    target = optionPane().createNew();
    fixture = new JOptionPaneFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureByType() {
    Robot robot = robot();
    ComponentFinder finder = finder();
    expect(robot.finder()).andReturn(finder);
    expect(finder.findByType(JOptionPane.class, true)).andReturn(target());
    replay(robot, finder);
    verifyLookup(new JOptionPaneFixture(robot));
  }

  @Test public void shouldRequireTitle() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireTitle(target, "A Title");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireTitle("A Title"));
      }
    }.run();
  }

  @Test public void shouldRequireMessage() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireMessage(target, "A Message");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireMessage("A Message"));
      }
    }.run();
  }

  @Test public void shouldRequireOptions() {
    final Object[] options = new Object[0];
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireOptions(target, options);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireOptions(options));
      }
    }.run();
  }

  @Test public void shouldReturnOkButton() {
    final JButton button = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.okButton(target)).andReturn(button);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.okButton();
        assertThat(result.target).isSameAs(button);
      }
    }.run();
  }

  @Test public void shouldReturnCancelButton() {
    final JButton button = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.cancelButton(target)).andReturn(button);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.cancelButton();
        assertThat(result.target).isSameAs(button);
      }
    }.run();
  }

  @Test public void shouldReturnYesButton() {
    final JButton button = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.yesButton(target)).andReturn(button);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.yesButton();
        assertThat(result.target).isSameAs(button);
      }
    }.run();
  }

  @Test public void shouldReturnNoButton() {
    final JButton button = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.noButton(target)).andReturn(button);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.noButton();
        assertThat(result.target).isSameAs(button);
      }
    }.run();
  }

  @Test public void shouldReturnButtonWithText() {
    final JButton button = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.buttonWithText(target, "A Button")).andReturn(button);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.buttonWithText("A Button");
        assertThat(result.target).isSameAs(button);
      }
    }.run();
  }

  @Test public void shouldReturnButton() {
    final JButton button = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.button(target)).andReturn(button);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.button();
        assertThat(result.target).isSameAs(button);
      }
    }.run();
  }

  @Test public void shouldReturnTextBox() {
    final JTextField textBox = textField().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.textBox(target)).andReturn(textBox);
      }

      protected void codeToTest() {
        JTextComponentFixture result = fixture.textBox();
        assertThat(result.target).isSameAs(textBox);
      }
    }.run();
  }

  @Test public void shouldRequireErrorMessage() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireErrorMessage(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireErrorMessage());
      }
    }.run();
  }

  @Test public void shouldRequireInformationMessage() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireInformationMessage(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireInformationMessage());
      }
    }.run();
  }

  @Test public void shouldRequireWarningMessage() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireWarningMessage(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireWarningMessage());
      }
    }.run();
  }

  @Test public void shouldRequireQuestionMessage() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireQuestionMessage(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireQuestionMessage());
      }
    }.run();
  }

  @Test public void shouldRequirePlainMessage() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requirePlainMessage(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requirePlainMessage());
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JOptionPane target() { return target; }
  JOptionPaneFixture fixture() { return fixture; }
}
