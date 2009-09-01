/*
 * Created on Jul 1, 2007
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
import static org.fest.swing.test.builder.JSpinners.spinner;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JSpinner;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JSpinnerDriver;
import org.junit.Test;

/**
 * Tests for <code>{@link JSpinnerFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSpinnerFixtureTest extends CommonComponentFixture_TestCase<JSpinner> {

  private JSpinnerDriver driver;
  private JSpinner target;
  private JSpinnerFixture fixture;

  void onSetUp() {
    driver = createMock(JSpinnerDriver.class);
    target = spinner().createNew();
    fixture = new JSpinnerFixture(robot, target);
    fixture.driver(driver);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  @Test
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "spinner";
    expectLookupByName(name, JSpinner.class);
    verifyLookup(new JSpinnerFixture(robot, name));
  }

  @Test
  public void shouldRequireValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireValue(target, "A Value");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireValue("A Value"));
      }
    }.run();
  }

  @Test
  public void shouldIncrementTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.increment(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.increment(8));
      }
    }.run();
  }

  @Test
  public void shouldIncrement() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.increment(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.increment());
      }
    }.run();
  }

  @Test
  public void shouldDecrementTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.decrement(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.decrement(8));
      }
    }.run();
  }

  @Test
  public void shouldDecrement() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.decrement(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.decrement());
      }
    }.run();
  }

  @Test
  public void shouldReturnText() {
    final String text = "Some Text";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.textOf(target)).andReturn(text);
      }

      protected void codeToTest() {
        assertThat(fixture.text()).isEqualTo(text);
      }
    }.run();
  }

  @Test
  public void shouldEnterText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.enterText(target, "Some Text");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.enterText("Some Text"));
      }
    }.run();
  }

  @Test
  public void shouldSelectValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectValue(target, "Some Text");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.select("Some Text"));
      }
    }.run();
  }

  @Test
  public void shouldEnterTextAndCommit() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.enterTextAndCommit(target, "Some Text");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.enterTextAndCommit("Some Text"));
      }
    }.run();
  }

  @Test
  public void shouldRequireToolTip() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.requireToolTip(target(), "A ToolTip");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireToolTip("A ToolTip"));
      }
    }.run();
  }

  @Test
  public void shouldRequireToolTipToMatchPattern() {
    final Pattern pattern = regex(".");
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.requireToolTip(target(), pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireToolTip(pattern));
      }
    }.run();
  }

  @Test
  public void shouldShowPopupMenu() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target())).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
        assertThat(popupMenuFixture.robot).isSameAs(robot);
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  @Test
  public void shouldShowPopupMenuAtPoint() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final Point p = new Point();
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target(), p)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
        assertThat(popupMenuFixture.robot).isSameAs(robot);
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  @Test
  public void shouldReturnClientProperty() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.clientProperty(target, "name")).andReturn("Luke");
      }

      protected void codeToTest() {
        assertThat(fixture.clientProperty("name")).isEqualTo("Luke");
      }
    }.run();
  }
  
  JComponentDriver driver() { return driver; }
  JSpinner target() { return target; }
  JSpinnerFixture fixture() { return fixture; }
}
