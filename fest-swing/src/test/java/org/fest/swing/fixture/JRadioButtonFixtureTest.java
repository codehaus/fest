/*
 * Created on Sep 18, 2007
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

import javax.swing.JRadioButton;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.ComponentDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JRadioButtons.radioButton;

/**
 * Tests for <code>{@link JRadioButtonFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JRadioButtonFixtureTest extends CommonComponentFixtureTestCase<JRadioButton> {

  private AbstractButtonDriver driver;
  private JRadioButton target;
  private JRadioButtonFixture fixture;
  
  void onSetUp() {
    driver = createMock(AbstractButtonDriver.class);
    target = radioButton().createNew();
    fixture = new JRadioButtonFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    String name = "radioButton";
    expectLookupByName(name, JRadioButton.class);
    verifyLookup(new JRadioButtonFixture(robot(), name));
  }
  
  @Test public void shouldReturnText() {
    final String text = "A Radio Button";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.textOf(target)).andReturn(text);
      }
      
      protected void codeToTest() {
        assertThat(fixture.text()).isEqualTo(text);
      }
    }.run();
  }
  
  @Test public void shouldRequireText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, "A Radio Button");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A Radio Button"));
      }
    }.run();
  }
  
  public void shouldSelectRadioButton() {
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
  
  public void shoulUnselectRadioButton() {
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
  
  @Test public void shouldRequireNotSelected() {
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
  
  @Test public void shouldRequireSelected() {
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
  JRadioButton target() { return target; }
  JRadioButtonFixture fixture() { return fixture; }
}
