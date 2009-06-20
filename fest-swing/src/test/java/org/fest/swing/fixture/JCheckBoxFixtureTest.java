/*
 * Created on Jun 10, 2007
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

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JCheckBoxes.checkBox;

import java.util.regex.Pattern;

import javax.swing.JCheckBox;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.ComponentDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JCheckBoxFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JCheckBoxFixtureTest extends CommonComponentFixtureTestCase<JCheckBox> {

  private AbstractButtonDriver driver;
  private JCheckBox target;
  private JCheckBoxFixture fixture;

  void onSetUp() {
    driver = createMock(AbstractButtonDriver.class);
    target = checkBox().createNew();
    fixture = new JCheckBoxFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "checkBox";
    expectLookupByName(name, JCheckBox.class);
    verifyLookup(new JCheckBoxFixture(robot(), name));
  }

  public void shouldSelectCheckBox() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.select(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.check());
      }
    }.run();
  }

  public void shoulUnselectCheckBox() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unselect(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.uncheck());
      }
    }.run();
  }

  public void shouldReturnText() {
    final String text = "A CheckBox";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.textOf(target)).andReturn(text);
      }

      protected void codeToTest() {
        assertThat(fixture.text()).isEqualTo(text);
      }
    }.run();
  }

  public void shouldRequireText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, "A CheckBox");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A CheckBox"));
      }
    }.run();
  }

  public void shouldRequireTextToMatchRegex() {
    final Pattern pattern = Pattern.compile(".");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText(pattern));
      }
    }.run();
  }

  public void shouldRequireNotSelected() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotSelected(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotSelected());
      }
    }.run();
  }

  public void shouldRequireSelected() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelected(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelected());
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JCheckBox target() { return target; }
  JCheckBoxFixture fixture() { return fixture; }
}
