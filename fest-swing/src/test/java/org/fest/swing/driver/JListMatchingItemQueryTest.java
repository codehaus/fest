/*
 * Created on Nov 3, 2008
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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;
import java.util.Collection;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.util.TextMatcher;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JListMatchingItemQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListMatchingItemQueryTest {

  private Robot robot;
  private JList list;
  private JListCellReader cellReader;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    robot.showWindow(window);
    cellReader = new BasicJListCellReader();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnIndexOfMatchingItem() {
    final TextMatcher matcher = mockTextMatcher();
    new EasyMockTemplate(matcher) {
      protected void expectations() {
        expect(matcher.isMatching("Yoda")).andReturn(false);
        expect(matcher.isMatching("Luke")).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(matchingItemIndex(matcher)).isEqualTo(1);
      }
    }.run();
  }

  public void shouldReturnNegativeOneIfMatchingItemNotFound() {
    final TextMatcher matcher = mockTextMatcher();
    new EasyMockTemplate(matcher) {
      protected void expectations() {
        expect(matcher.isMatching("Yoda")).andReturn(false);
        expect(matcher.isMatching("Luke")).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(matchingItemIndex(matcher)).isEqualTo(-1);
      }
    }.run();
  }

  @RunsInEDT
  private int matchingItemIndex(final TextMatcher matcher) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return JListMatchingItemQuery.matchingItemIndex(list, matcher, cellReader);
      }
    });
  }

  public void shouldReturnIndicesOfItemsMatchingPatternAsString() {
    assertThat(findMatchingItems(".*")).hasSize(2).contains(0, 1);
  }

  public void shouldReturnIndicesOfItemsMatchingPatternsAsString() {
    assertThat(findMatchingItems("Y.*", "L.*")).hasSize(2).contains(0, 1);
  }

  @RunsInEDT
  private Collection<Integer> findMatchingItems(final String...values) {
    return JListMatchingItemQuery.matchingItemIndices(list, values, cellReader);
  }

  public void shouldReturnIndicesOfMatchingItems() {
    final TextMatcher matcher = mockTextMatcher();
    new EasyMockTemplate(matcher) {
      protected void expectations() {
        expect(matcher.isMatching("Yoda")).andReturn(false);
        expect(matcher.isMatching("Luke")).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(matchingItemIndices(matcher)).hasSize(1).containsOnly(1);
      }
    }.run();
  }

  public void shouldReturnEmptyListIfMatchingItemsNotFound() {
    final TextMatcher matcher = mockTextMatcher();
    new EasyMockTemplate(matcher) {
      protected void expectations() {
        expect(matcher.isMatching("Yoda")).andReturn(false);
        expect(matcher.isMatching("Luke")).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(matchingItemIndices(matcher)).isEmpty();
      }
    }.run();
  }

  @RunsInEDT
  private List<Integer> matchingItemIndices(final TextMatcher matcher) {
    List<Integer> matchingIndices = execute(new GuiQuery<List<Integer>>() {
      protected List<Integer> executeInEDT() {
        return JListMatchingItemQuery.matchingItemIndices(list, matcher, cellReader);
      }
    });
    return matchingIndices;
  }

  private static TextMatcher mockTextMatcher() {
    return createMock(TextMatcher.class);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array(new Jedi("Yoda"), new Jedi("Luke")));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListMatchingItemQueryTest.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
