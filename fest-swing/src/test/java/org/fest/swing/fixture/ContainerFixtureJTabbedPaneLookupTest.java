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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JTabbedPane}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJTabbedPaneLookupTest {

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

  public void shouldFindJTabbedPaneByType() {
    JTabbedPaneFixture tabbedPane = fixture.tabbedPane();
    assertThatFixtureHasCorrectJTabbedPane(tabbedPane);
  }

  public void shouldFailIfJTabbedPaneCannotBeFoundByType() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.remove(window.tabbedPane);
      }
    });
    robot.waitForIdle();
    try {
      fixture.tabbedPane();
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("type=javax.swing.JTabbedPane, requireShowing=true");
    }
  }

  public void shouldFindJTabbedPaneByName() {
    JTabbedPaneFixture tabbedPane = fixture.tabbedPane("selectMeTabbedPane");
    assertThatFixtureHasCorrectJTabbedPane(tabbedPane);
  }

  public void shouldFailIfJTabbedPaneCannotBeFoundByName() {
    try {
      fixture.tabbedPane("myTabbedPane");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='myTabbedPane', type=javax.swing.JTabbedPane, requireShowing=true");
    }
  }

  public void shouldFindJTabbedPaneWithCustomMatcher() {
    JTabbedPaneFixture tabbedPane = fixture.tabbedPane(new GenericTypeMatcher<JTabbedPane>(JTabbedPane.class) {
      protected boolean isMatching(JTabbedPane t) {
        return t.getTabCount() == 1;
      }
    });
    assertThatFixtureHasCorrectJTabbedPane(tabbedPane);
  }

  private void assertThatFixtureHasCorrectJTabbedPane(JTabbedPaneFixture tabbedPaneFixture) {
    assertThat(tabbedPaneFixture.component()).isSameAs(window.tabbedPane);
  }

  public void shouldFailIfJTabbedPaneCannotBeFoundWithCustomMatcher() {
    try {
      fixture.tabbedPane(new GenericTypeMatcher<JTabbedPane>(JTabbedPane.class) {
        protected boolean isMatching(JTabbedPane t) {
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

    final JTabbedPane tabbedPane = new JTabbedPane();

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJTabbedPaneLookupTest.class);
      tabbedPane.setName("selectMeTabbedPane");
      tabbedPane.addTab("Tab 0", panel());
      addComponents(tabbedPane);
    }

    private JPanel panel() {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(100, 50));
      return panel;
    }
  }
}
