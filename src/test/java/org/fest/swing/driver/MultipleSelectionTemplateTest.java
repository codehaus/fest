/*
 * Created on May 8, 2008
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
package org.fest.swing.driver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.Platform.controlOrCommandKey;

/**
 * Tests for <code>{@link MultipleSelectionTemplate}</code>.
 *
 * @author Yvonne Wang
 */
public class MultipleSelectionTemplateTest {

  private Robot robot;
  private MultipleSelection template;

  @BeforeMethod public void setUp() {
    robot = createMock(Robot.class);
  }

  @Test public void shouldSelectOnceIfElementCountIsOne() {
    template = new MultipleSelection(robot, 1);
    new EasyMockTemplate(robot) {
      protected void expectations() {}

      protected void codeToTest() {
        template.multiSelect();
        assertThat(template.timesSelected).isEqualTo(1);
      }
    }.run();
  }
  
  @Test public void shouldSelectMultipleItems() {
    template = new MultipleSelection(robot, 2);
    final int key = controlOrCommandKey();
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.pressKey(key);
        expectLastCall().once();
        robot.releaseKey(key);
        expectLastCall().once();
      }

      protected void codeToTest() {
        template.multiSelect();
        assertThat(template.timesSelected).isEqualTo(2);
      }
    }.run();
  }

  private static class MultipleSelection extends MultipleSelectionTemplate {
    private final int elementCount;
    
    int timesSelected;

    
    MultipleSelection(Robot robot, int elementCount) {
      super(robot);
      this.elementCount = elementCount;
    }

    int elementCount() {
      return elementCount;
    }

    void selectElement(int index) {
      timesSelected++;
    }
  }
}
