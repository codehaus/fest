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

import javax.swing.JSlider;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JSliderDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.test.builder.JSliders.slider;

/**
 * Tests for <code>{@link JSliderFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JSliderFixtureTest extends CommonComponentFixtureTestCase<JSlider> {

  private JSliderDriver driver;
  private JSlider target;
  private JSliderFixture fixture;

  void onSetUp() {
    driver = createMock(JSliderDriver.class);
    target = slider().createNew();
    fixture = new JSliderFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    String name = "slider";
    expectLookupByName(name, JSlider.class);
    verifyLookup(new JSliderFixture(robot(), name));
  }

  @Test public void shouldSlideToValue() {
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

  @Test public void shouldSlideToMax() {
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

  @Test public void shouldSlideToMin() {
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

  ComponentDriver driver() { return driver; }
  JSlider target() { return target; }
  JSliderFixture fixture() { return fixture; }
}
