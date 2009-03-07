/*
 * Created on Sep 4, 2007
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

import javax.swing.JSplitPane;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JSplitPaneDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.test.builder.JSplitPanes.splitPane;

/**
 * Tests for <code>{@link JSplitPaneFixture}</code>.
 *
 * @author Yvonne Wang
 */
@Test public class JSplitPaneFixtureTest extends CommonComponentFixtureTestCase<JSplitPane> {

  private JSplitPaneDriver driver;
  private JSplitPane target;
  private JSplitPaneFixture fixture;
  
  void onSetUp() {
    driver = createMock(JSplitPaneDriver.class);
    target = splitPane().createNew();
    fixture = new JSplitPaneFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "splitPane";
    expectLookupByName(name, JSplitPane.class);
    verifyLookup(new JSplitPaneFixture(robot(), name));
  }

  public void shouldMoveDivider() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveDividerTo(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveDividerTo(8));
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JSplitPane target() { return target; }
  JSplitPaneFixture fixture() { return fixture; }
}
