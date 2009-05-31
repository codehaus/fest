/*
 * Created on Jul 30, 2008
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

import java.awt.Container;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for issue <a href="http://code.google.com/p/fest/issues/detail?id=109" target="_blank">109</a>: Add support for
 * extension.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ExtensionTest {

  private FrameFixture fixture;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    fixture = new FrameFixture(TestWindow.createNewWindow(ExtensionTest.class));
  }

  @AfterMethod public void tearDown() {
    fixture.cleanUp();
  }

  public void shouldCreateFixtureUsingExtension() {
    JTextFieldFixture textField = fixture.with(JTextFieldFixtureExtension.textFieldWithName("hello"));
    assertThat(textField).isNotNull();
  }

  static class JTextFieldFixtureExtension extends ComponentFixtureExtension<JTextField, JTextFieldFixture> {
    static JTextFieldFixtureExtension textFieldWithName(String name) {
      return new JTextFieldFixtureExtension();
    }

    public JTextFieldFixture createFixture(Robot robot, Container root) {
      return new JTextFieldFixture(robot, textField().createNew());
    }
  }

  static class JTextFieldFixture extends ComponentFixture<JTextField> {
    public JTextFieldFixture(Robot robot, JTextField target) {
      super(robot, target);
    }
  }
}
