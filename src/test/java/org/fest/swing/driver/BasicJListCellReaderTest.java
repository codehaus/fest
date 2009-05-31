/*
 * Created on Apr 12, 2008
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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.fest.swing.test.swing.TestListModel;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJListCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJListCellReaderTest {

  private Robot robot;
  private MyList list;
  private BasicJListCellReader reader;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    reader = new BasicJListCellReader();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnModelValueToString() {
    list.setElements(new Jedi("Yoda"));
    Object value = firstItemValue(reader, list);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    list.setElements(new Object[] { null });
    setNotRecognizedRendererComponent(list);
    robot.waitForIdle();
    String value = firstItemValue(reader, list);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    list.setElements(new Jedi(null));
    setJLabelAsRendererComponent(list, "First");
    robot.waitForIdle();
    String value = firstItemValue(reader, list);
    assertThat(value).isEqualTo("First");
  }

  @RunsInEDT
  private static void setJLabelAsRendererComponent(final JList list, final String labelText) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setCellRenderer(new CustomCellRenderer(new JLabel(labelText)));
      }
    });
  }

  @RunsInEDT
  private static void setNotRecognizedRendererComponent(final JList list) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setCellRenderer(new CustomCellRenderer(new JToolBar()));
      }
    });
  }

  @RunsInEDT
  private static String firstItemValue(final BasicJListCellReader reader, final JList list) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return reader.valueAt(list, 0);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyList list = new MyList("One", "Two");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(BasicJListCellReaderTest.class);
      addComponents(list);
    }
  }

  private static class MyList extends JList {
    private static final long serialVersionUID = 1L;

    final TestListModel model;

    MyList(Object... elements) {
      model = new TestListModel(elements);
      setModel(model);
    }

    void setElements(final Object...elements) {
      execute(new GuiTask() {
        protected void executeInEDT() {
          model.setElements(elements);
        }
      });
    }

    @Override public TestListModel getModel() {
      return model;
    }
  }
}
