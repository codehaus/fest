/*
 * Created on Nov 1, 2007
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

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JPanels.panel;

import javax.swing.JPanel;

import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JComponentDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JPanelFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class JPanelFixtureTest extends CommonComponentFixtureTestCase<JPanel> {

  private JComponentDriver driver;
  private JPanel target;
  private JPanelFixture fixture;

  void onSetUp() {
    driver = createMock(JComponentDriver.class);
    target = panel().createNew();
    fixture = new JPanelFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "panel";
    expectLookupByName(name, JPanel.class);
    verifyLookup(new JPanelFixture(robot(), name));
  }

  public void shouldBeContainerFixture() {
    assertThat(fixture).isInstanceOf(ContainerFixture.class);
  }

  ComponentDriver driver() { return driver; }
  JPanel target() { return target; }
  JPanelFixture fixture() { return fixture; }
}
