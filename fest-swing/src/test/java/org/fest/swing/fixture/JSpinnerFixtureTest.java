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

import javax.swing.JSpinner;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JSpinnerDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.test.builder.JSpinners.spinner;

/**
 * Tests for <code>{@link JSpinnerFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class JSpinnerFixtureTest extends CommonComponentFixtureTestCase<JSpinner> {

  private JSpinnerDriver driver;
  private JSpinner target;
  private JSpinnerFixture fixture;

  void onSetUp() {
    driver = createMock(JSpinnerDriver.class);
    target = spinner().createNew();
    fixture = new JSpinnerFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "spinner";
    expectLookupByName(name, JSpinner.class);
    verifyLookup(new JSpinnerFixture(robot(), name));
  }

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

  ComponentDriver driver() { return driver; }
  JSpinner target() { return target; }
  JSpinnerFixture fixture() { return fixture; }
}
