/*
 * Created on Jul 13, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentLookupScope.DEFAULT;

/**
 * Understands test methods for implementations of <code>{@link ComponentFixture}</code>.
 * @param <T> the type of component tested by this test class. 
 *
 * @author Alex Ruiz
 */
public abstract class ComponentFixtureTestCase<T extends Component> {

  private Robot robot;
  private ComponentFinder finder;
  private Settings settings;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public final void setUp() {
    robot = createMock(Robot.class);
    finder = createMock(ComponentFinder.class);
    settings = new Settings();
    settings.componentLookupScope(DEFAULT);
    onSetUp();
  }

  abstract void onSetUp();

  Robot robot() { return robot; }

  ComponentFinder finder() { return finder; }
  
  abstract T target();

  abstract ComponentDriver driver();

  final void expectLookupByName(String name, Class<T> targetType) {
    expectLookupByName(name, targetType, DEFAULT.requireShowing());
  }
  
  final void expectLookupByName(String name, Class<T> targetType, boolean requireShowing) {
    expect(robot.finder()).andReturn(finder);
    expect(robot.settings()).andReturn(settings);
    expect(finder.findByName(name, targetType, requireShowing)).andReturn(target());
    replay(robot, finder);
  }

  final void verifyLookup(ComponentFixture<? extends Component> fixture) {
    verify(robot, finder);
    assertThat(fixture.target).isSameAs(target());
  }
  
  final void expectLookupByType(Class<T> targetType) {
    expectLookupByType(targetType, DEFAULT.requireShowing());
  }

  final void expectLookupByType(Class<T> targetType, boolean requireShowing) {
    expect(robot.finder()).andReturn(finder);
    expect(robot.settings()).andReturn(settings);
    expect(finder.findByType(targetType, requireShowing)).andReturn(target());
    replay(robot, finder);
  }
}