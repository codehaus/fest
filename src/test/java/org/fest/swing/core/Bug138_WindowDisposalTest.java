/*
 * Created on Jun 1, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.lock.ScreenLock;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=138">Bug 138</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { BUG, GUI })
public class Bug138_WindowDisposalTest {

  private ComponentHierarchy hierarchy;
  private BasicRobot robot;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    hierarchy = createMock(ComponentHierarchy.class);
    robot = new TestRobotFixture(hierarchy);
  }

  @AfterMethod public void tearDown() {
    ScreenLock screenLock = ScreenLock.instance();
    if (!screenLock.acquiredBy(robot)) return;
    try {
      screenLock.release(robot);
    } catch (Exception e) {}
  }

  public void shouldDisposeWindows() {
    final List<Container> roots = new ArrayList<Container>();
    final JFrame frame = frame().withTitle("Hello").createNew();
    roots.add(frame);
    new EasyMockTemplate(hierarchy) {
      protected void expectations() {
        hierarchy.roots();
        expectLastCall().andReturn(roots);
        hierarchy.dispose(frame);
      }

      protected void codeToTest() {
        robot.cleanUp();
      }
    }.run();
  }

  public void shouldNotDisposeWindows() {
    new EasyMockTemplate(hierarchy) {
      protected void expectations() {}

      protected void codeToTest() {
        robot.cleanUpWithoutDisposingWindows();
      }
    }.run();
  }

}
