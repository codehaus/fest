/*
 * Created on Feb 8, 2007
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

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.ComponentDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;

/**
 * Tests for <code>{@link JButtonFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class JButtonFixtureTest extends CommonComponentFixtureTestCase<JButton> {

  private AbstractButtonDriver driver;
  private JButton target;
  private JButtonFixture fixture;
  
  void onSetUp() {
    driver = createMock(AbstractButtonDriver.class);
    target = button().createNew();
    fixture = new JButtonFixture(robot(), target);
    fixture.updateDriver(driver);
  }
  
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "button";
    expectLookupByName(name, JButton.class);
    verifyLookup(new JButtonFixture(robot(), name));
  }

  public void shouldReturnText() {
    final String text = "A Button";
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
        driver.requireText(target, "A Button");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A Button"));
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JButton target() { return target; }
  JButtonFixture fixture() { return fixture; }
}
