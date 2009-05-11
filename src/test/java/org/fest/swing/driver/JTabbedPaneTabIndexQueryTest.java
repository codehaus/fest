/*
 * Created on Aug 22, 2008
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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTabbedPaneTabIndexQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTabbedPaneTabIndexQueryTest {

  private Robot robot;
  private JTabbedPane tabbedPane;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "tabTitlesAndIndices", groups = { GUI, ACTION })
  public void shouldReturnIndexForTab(String tabTitle, int expectedIndex) {
    int index = indexOfTab(tabbedPane, tabTitle);
    assertThat(index).isEqualTo(expectedIndex);
  }

  @DataProvider(name = "tabTitlesAndIndices") public Object[][] tabTitlesAndIndices() {
    return new Object[][] {
        { "First" , 0 },
        { "Second", 1 },
        { "Third" , 2 },
    };
  }

  public void shouldNotFindIndexIfTitleIsNotMatching() {
    int index = indexOfTab(tabbedPane, "Hello");
    assertThat(index).isEqualTo(-1);
  }

  public void shouldNotFindIndexIfTabbedPaneHasNoTabs() {
    removeAllTabsIn(tabbedPane);
    robot.waitForIdle();
    int index = indexOfTab(tabbedPane, "First");
    assertThat(index).isEqualTo(-1);
  }

  @RunsInEDT
  private static int indexOfTab(final JTabbedPane tabbedPane, final String title) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return JTabbedPaneTabIndexQuery.indexOfTab(tabbedPane, title);
      }
    });
  }

  @RunsInEDT
  private static void removeAllTabsIn(final JTabbedPane tabbedPane) {
    execute(new GuiTask() {
      public void executeInEDT() {
        tabbedPane.removeAll();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTabbedPane tabbedPane = new JTabbedPane();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTabbedPaneTabIndexQueryTest.class);
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
      tabbedPane.addTab("Third", new JPanel());
      addComponents(tabbedPane);
    }
  }
}
