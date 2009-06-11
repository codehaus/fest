/*
 * Created on Mar 4, 2007
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

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.test.builder.JMenuItems.menuItem;
import static org.fest.swing.timing.Timeout.timeout;

import javax.swing.JMenuItem;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.*;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JMenuItemDriver;
import org.fest.swing.timing.Timeout;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JMenuItemFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JMenuItemFixtureTest extends ComponentFixtureTestCase<JMenuItem> implements
    KeyboardInputSimulationFixtureTestCase, StateVerificationFixtureTestCase {

  private JMenuItemDriver driver;
  private JMenuItem target;
  private JMenuItemFixture fixture;

  void onSetUp() {
    driver = createMock(JMenuItemDriver.class);
    target = menuItem().withText("A MenuItem").createNew();
    fixture = new JMenuItemFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "menuItem";
    Robot robot = robot();
    ComponentFinder finder = finder();
    expect(robot.finder()).andReturn(finder);
    expect(finder.findByName(name, JMenuItem.class, false)).andReturn(target());
    replay(robot, finder);
    verifyLookup(new JMenuItemFixture(robot, name));
  }

  public void shouldClickMenuItem() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.click(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.click());
      }
    }.run();
  }

  public void shouldGiveFocus() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.focus(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.focus());
      }
    }.run();
  }

  public void shouldPressAndReleaseKey() {
    final KeyPressInfo keyPressInfo = keyCode(VK_A).modifiers(SHIFT_MASK);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.pressAndReleaseKey(target, keyPressInfo);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressAndReleaseKey(keyPressInfo));
      }
    }.run();
  }

  public void shouldPressAndReleaseKeys() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.pressAndReleaseKeys(target, VK_A, VK_B, VK_C);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressAndReleaseKeys(VK_A, VK_B, VK_C));
      }
    }.run();
  }

  public void shouldPressKey() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.pressKey(target, VK_A);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressKey(VK_A));
      }
    }.run();
  }

  public void shouldReleaseKey() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.releaseKey(target, VK_A);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.releaseKey(VK_A));
      }
    }.run();
  }

  public void shouldRequireDisabled() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireDisabled(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireDisabled());
      }
    }.run();
  }

  public void shouldRequireEnabled() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEnabled(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEnabled());
      }
    }.run();
  }

  public void shouldRequireEnabledUsingTimeout() {
    final Timeout timeout = timeout(2000);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEnabled(target, timeout);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEnabled(timeout));
      }
    }.run();
  }

  public void shouldRequireNotVisible() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotVisible(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotVisible());
      }
    }.run();
  }

  public void shouldRequireVisible() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireVisible(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireVisible());
      }
    }.run();
  }

  private void assertThatReturnsThis(JMenuItemFixture result) {
    assertThat(result).isSameAs(fixture);
  }

  ComponentDriver driver() { return driver; }

  JMenuItem target() { return target; }
}
