/*
 * Created on Jun 7, 2009
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

import javax.swing.JFrame;

import org.fest.swing.core.Robot;

/**
 * Understands a concrete implementation of <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class ConcreteContainerFixture extends ContainerFixture<JFrame> {

  public ConcreteContainerFixture(Robot robot, Class<? extends JFrame> type) {
    super(robot, type);
  }

  public ConcreteContainerFixture(Robot robot, String name, Class<? extends JFrame> type) {
    super(robot, name, type);
  }

  public ConcreteContainerFixture(Robot robot, JFrame target) {
    super(robot, target);
  }
}
