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
import static org.fest.swing.test.builder.JSliders.slider;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JSlider;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JSliderDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSliderFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test
public class JSliderFixtureTest extends CommonComponentFixtureTestCase<JSlider> {

  private JSliderDriver driver;
  private JSlider target;
  private JSliderFixture fixture;

  void onSetUp() {
    driver = createMock(JSliderDriver.class);
    target = slider().createNew();
    fixture = new JSliderFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "slider";
    expectLookupByName(name, JSlider.class);
    verifyLookup(new JSliderFixture(robot(), name));
  }

  public void shouldSlideToValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.slide(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.slideTo(8));
      }
    }.run();
  }

  public void shouldSlideToMax() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.slideToMaximum(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.slideToMaximum());
      }
    }.run();
  }

  public void shouldSlideToMin() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.slideToMinimum(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.slideToMinimum());
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
  JSlider target() { return target; }
  JSliderFixture fixture() { return fixture; }
}
