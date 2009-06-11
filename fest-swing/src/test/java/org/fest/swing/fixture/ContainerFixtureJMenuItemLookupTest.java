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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JMenuItem}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJMenuItemLookupTest {

  private ConcreteContainerFixture fixture;
  private Robot robot;
  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    fixture = new ConcreteContainerFixture(robot, window);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }


  public void shouldFindJMenuItemByPath() {
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File", "New");
    assertThat(menuItem.component()).isSameAs(window.menuNew);
  }

  public void shouldFailIfJMenuItemCannotBeFoundByPath() {
    try {
      fixture.menuItemWithPath("Edit");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("label='Edit'");
    }
  }

  public void shouldFindJMenuItemByName() {
    JMenuItemFixture menuItem = fixture.menuItem("menuNew");
    assertThat(menuItem.component()).isSameAs(window.menuNew);
  }

  public void shouldFailIfJMenuItemCannotBeFoundByName() {
    try {
      fixture.menuItem("myMenuNew");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='myMenuNew', type=javax.swing.JMenuItem, requireShowing=false");
    }
  }

  public void shouldFindJMenuItemWithCustomMatcher() {
    JMenuItemFixture menuItem = fixture.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
      protected boolean isMatching(JMenuItem m) {
        return "New".equals(m.getText());
      }
    });
    assertThat(menuItem.component()).isSameAs(window.menuNew);
  }

  public void shouldFailIfJMenuItemCannotBeFoundWithCustomMatcher() {
    try {
      fixture.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
        protected boolean isMatching(JMenuItem m) {
          return false;
        }
      });
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher");
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JMenu menuFile = new JMenu("File");
    final JMenuItem menuNew = new JMenuItem("New");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJMenuItemLookupTest.class);
      setJMenuBar(new JMenuBar());
      menuFile.add(menuNew);
      menuNew.setName("menuNew");
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(80, 60));
    }
  }
}
