/*
 * Created on May 21, 2007
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
import static org.fest.swing.test.builder.JTrees.tree;

import javax.swing.JTree;

import org.fest.swing.driver.JTreeDriver;

/**
 * Base test case for <code>{@link JTreeFixture}</code>.
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFixture_TestCase extends CommonComponentFixtureTestCase<JTree> {

  private JTreeDriver driver;
  private JTree target;
  private JTreeFixture fixture;

  final void onSetUp() {
    driver = createMock(JTreeDriver.class);
    target = tree().createNew();
    fixture = new JTreeFixture(robot, target);
    fixture.driver(driver);
  }

  JTreeDriver driver() { return driver; }
  JTree target() { return target; }
  JTreeFixture fixture() { return fixture; }
}