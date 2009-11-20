/*
 * Created on Nov 19, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLabels.label;
import javax.swing.JLabel;
import org.fest.swing.driver.JLabelDriver;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that <code>{@link JLabelFixture}</code> is a <code>{@link ContainerFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JPanelFixture_container_Test extends ComponentFixture_TestCase<JLabel> {

  private static JLabel target;

  private JLabelDriver driver;
  private JLabelFixture fixture;

  @BeforeClass
  public static void setUpTarget() {
    target = label().createNew();
  }

  void onSetUp() {
    driver = createMock(JLabelDriver.class);
    fixture = new JLabelFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test
  public void should_be_ContainerFixture() {
    assertThat(fixture).isInstanceOf(ContainerFixture.class);
  }

  JLabelDriver driver() {  return driver; }
  JLabel target() { return target; }
  JLabelFixture fixture() { return fixture; }
}
