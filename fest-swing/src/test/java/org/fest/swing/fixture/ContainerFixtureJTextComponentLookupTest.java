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

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests lookup of <code>{@link JTextComponent}</code>s in <code>{@link ContainerFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFixtureJTextComponentLookupTest {

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

  public void shouldFindJTextComponentByType() {
    JTextComponentFixture textBox = fixture.textBox();
    assertThatFixtureHasCorrectJTextComponent(textBox);
  }

  public void shouldFailIfJTextComponentCannotBeFoundByType() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.remove(window.textBox);
      }
    });
    robot.waitForIdle();
    try {
      fixture.textBox();
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("type=javax.swing.text.JTextComponent, requireShowing=true");
    }
  }

  public void shouldFindJTextComponentByName() {
    JTextComponentFixture textBox = fixture.textBox("typeMeTextField");
    assertThatFixtureHasCorrectJTextComponent(textBox);
  }

  public void shouldFailIfJTextComponentCannotBeFoundByName() {
    try {
      fixture.textBox("myTextField");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Unable to find component using matcher")
                                .contains("name='myTextField', type=javax.swing.text.JTextComponent, requireShowing=true");
    }
  }

  public void shouldFindJTextComponentWithCustomMatcher() {
    JTextComponentFixture textBox = fixture.textBox(new GenericTypeMatcher<JTextComponent>(JTextComponent.class) {
      protected boolean isMatching(JTextComponent b) {
        return "".equals(b.getText());
      }
    });
    assertThatFixtureHasCorrectJTextComponent(textBox);
  }

  private void assertThatFixtureHasCorrectJTextComponent(JTextComponentFixture textBoxFixture) {
    assertThat(textBoxFixture.component()).isSameAs(window.textBox);
  }

  public void shouldFailIfJTextComponentCannotBeFoundWithCustomMatcher() {
    try {
      fixture.textBox(new GenericTypeMatcher<JTextComponent>(JTextComponent.class) {
        protected boolean isMatching(JTextComponent b) {
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

    final JTextComponent textBox = new JTextField(10);

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFixtureJTextComponentLookupTest.class);
      textBox.setName("typeMeTextField");
      addComponents(textBox);
    }
  }
}
