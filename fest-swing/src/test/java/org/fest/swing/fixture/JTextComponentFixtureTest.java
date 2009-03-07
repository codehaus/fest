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

import javax.swing.text.JTextComponent;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTextComponentDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link JTextComponentFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentFixtureTest extends CommonComponentFixtureTestCase<JTextComponent> {

  private JTextComponentDriver driver;
  private JTextComponent target;
  private JTextComponentFixture fixture;
  
  void onSetUp() {
    driver = createMock(JTextComponentDriver.class);
    target = textField().withText("a text field").createNew();
    fixture = new JTextComponentFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  Class<JTextComponent> targetType() {
    return JTextComponent.class;
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    String name = "textComponent";
    expectLookupByName(name, JTextComponent.class);
    verifyLookup(new JTextComponentFixture(robot(), name));
  }

  @Test public void shouldDeleteText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.deleteText(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.deleteText());
      }
    }.run();
  }

  @Test public void shouldEnterText() {
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
  
  @Test public void shouldSetText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.setText(target, "Some Text");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.setText("Some Text"));
      }
    }.run();
  }

  @Test public void shouldSelectText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectText(target, "Some Text");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.select("Some Text"));
      }
    }.run();
  }
  
  @Test public void shouldSelectTextInRange() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectText(target, 6, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectText(6, 8));
      }
    }.run();
  }

  @Test public void shouldSelectAll() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectAll(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectAll());
      }
    }.run();
  }

  @Test public void shouldReturnText() {
    assertThat(fixture.text()).isEqualTo(target.getText());
  }
  
  @Test public void shouldRequireText() {
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
  
  @Test public void shouldRequireEmpty() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEmpty(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEmpty());
      }
    }.run();
  }
  
  @Test public void shouldRequireEditable() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEditable(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEditable());
      }
    }.run();
  }
  
  @Test public void shouldRequireNotEditable() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotEditable(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotEditable());
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JTextComponent target() { return target; }
  JTextComponentFixture fixture() { return fixture; }
}

