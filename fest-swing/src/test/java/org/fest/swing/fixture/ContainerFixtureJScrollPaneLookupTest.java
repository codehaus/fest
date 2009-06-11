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
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JScrollPane}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJScrollPaneLookupTest {

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

  public void shouldFindJScrollPaneByType() {
    JScrollPaneFixture scrollPane = fixture.scrollPane();
    assertThatFixtureHasCorrectJScrollPane(scrollPane);
  }

  public void shouldFailIfJScrollPaneCannotBeFoundByType() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.remove(window.scrollPane);
      }
    });
    robot.waitForIdle();
    try {
      fixture.scrollPane();
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("type=javax.swing.JScrollPane, requireShowing=true");
    }
  }

  public void shouldFindJScrollPaneByName() {
    JScrollPaneFixture scrollPane = fixture.scrollPane("scrollMeScrollBar");
    assertThatFixtureHasCorrectJScrollPane(scrollPane);
  }

  public void shouldFailIfJScrollPaneCannotBeFoundByName() {
    try {
      fixture.scrollPane("myScrollPane");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='myScrollPane', type=javax.swing.JScrollPane, requireShowing=true");
    }
  }

  public void shouldFindJScrollPaneWithCustomMatcher() {
    JScrollPaneFixture scrollPane = fixture.scrollPane(new GenericTypeMatcher<JScrollPane>(JScrollPane.class) {
      protected boolean isMatching(JScrollPane s) {
        return s.getViewport().getView() instanceof JList;
      }
    });
    assertThatFixtureHasCorrectJScrollPane(scrollPane);
  }

  private void assertThatFixtureHasCorrectJScrollPane(JScrollPaneFixture scrollPaneFixture) {
    assertThat(scrollPaneFixture.component()).isSameAs(window.scrollPane);
  }

  public void shouldFailIfJScrollPaneCannotBeFoundWithCustomMatcher() {
    try {
      fixture.scrollPane(new GenericTypeMatcher<JScrollPane>(JScrollPane.class) {
        protected boolean isMatching(JScrollPane s) {
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

    final JScrollPane scrollPane = new JScrollPane(new JList(array("One", "Two")));

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJScrollPaneLookupTest.class);
      scrollPane.setName("scrollMeScrollBar");
      scrollPane.setPreferredSize(new Dimension(100, 50));
      addComponents(scrollPane);
    }
  }
}
