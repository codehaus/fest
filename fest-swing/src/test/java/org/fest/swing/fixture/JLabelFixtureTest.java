/*
 * Created on Feb 9, 2007
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
import static org.fest.swing.test.builder.JLabels.label;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JLabelDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JLabelFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class JLabelFixtureTest extends CommonComponentFixtureTestCase<JLabel> {

  private JLabelDriver driver;
  private JLabel target;
  private JLabelFixture fixture;

  void onSetUp() {
    driver = createMock(JLabelDriver.class);
    target = label().createNew();
    fixture = new JLabelFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "label";
    expectLookupByName(name, JLabel.class);
    verifyLookup(new JLabelFixture(robot(), name));
  }

  public void shouldReturnText() {
    final String text = "A Label";
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
        driver.requireText(target, "A Label");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A Label"));
      }
    }.run();
  }

  public void shouldRequireTextToMatchPattern() {
    final Pattern pattern = regex(".");
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

  public void shouldShowPopupMenu() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target())).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
        assertThat(popupMenuFixture.robot).isSameAs(robot());
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  public void shouldShowPopupMenuAtPoint() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final Point p = new Point();
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target(), p)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
        assertThat(popupMenuFixture.robot).isSameAs(robot());
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  JComponentDriver driver() { return driver; }
  JLabel target() { return target; }
  JLabelFixture fixture() { return fixture; }
}
