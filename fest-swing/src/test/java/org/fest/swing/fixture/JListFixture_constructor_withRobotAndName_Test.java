/*
 * Created on Nov 18, 2009
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
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JList;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link JListFixture#JListFixture(org.fest.swing.core.Robot, String)}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {

  private MyWindow window;

  @Override protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  public void should_lookup_showing_JList_by_name() {
    robot.showWindow(window);
    JListFixture fixture = new JListFixture(robot, "list");
    assertThat(fixture.robot).isSameAs(robot);
    assertThat(fixture.component()).isSameAs(window.list);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_JList_with_matching_name_is_not_showing() {
    new JListFixture(robot, "list");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_a_JList_with_matching_name_is_not_found() {
    new JListFixture(robot, "other");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JList list = new JList(array("One"));

    private MyWindow() {
      super(JListFixture_constructor_withRobotAndName_Test.class);
      list.setName("list");
      list.setPreferredSize(new Dimension(100, 50));
      addComponents(list);
    }
  }
}
