/*
 * Created on May 21, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JPopupMenus.popupMenu;
import static org.fest.swing.test.builder.JTrees.tree;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.util.Arrays.array;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JTreeDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeFixture}</code>.
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test
public class JTreeFixtureTest extends CommonComponentFixtureTestCase<JTree> {

  private JTreeDriver driver;
  private JTree target;
  private JTreeFixture fixture;

  void onSetUp() {
    driver = createMock(JTreeDriver.class);
    target = tree().createNew();
    fixture = new JTreeFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "tree";
    expectLookupByName(name, JTree.class);
    verifyLookup(new JTreeFixture(robot(), name));
  }
  
  public void shouldDoubleClickRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.doubleClickRow(target, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickRow(0));
      }
    }.run();
  }

  public void shouldDoubleClickPath() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.doubleClickPath(target, "root");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickPath("root"));
      }
    }.run();
  }

  public void shouldExpandRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.expandRow(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.expandRow(8));
      }
    }.run();
  }

  public void shouldCollapseRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.collapseRow(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.collapseRow(8));
      }
    }.run();
  }

  public void shouldExpandPath() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.expandPath(target, "root");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.expandPath("root"));
      }
    }.run();
  }

  public void shouldCollapsePath() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.collapsePath(target, "root");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.collapsePath("root"));
      }
    }.run();
  }

  public void shouldDragAtRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(8));
      }
    }.run();
  }

  public void shouldDragAtTreePath() {
    final String path = "root/node1";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, path);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(path));
      }
    }.run();
  }

  public void shouldDropAtRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(8));
      }
    }.run();
  }

  public void shouldDropAtTreePath() {
    final String path = "root/node1";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, path);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(path));
      }
    }.run();
  }

  public void shouldSelectRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectRow(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectRow(8));
      }
    }.run();
  }

  public void shouldSelectRows() {
    final int[] rows = { 6, 8 };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectRows(target, rows);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectRows(rows));
      }
    }.run();
  }

  public void shouldSelectTreePath() {
    final String path = "root/node1";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectPath(target, path);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectPath(path));
      }
    }.run();
  }

  public void shouldSelectTreePaths() {
    final String[] paths = array("root/node1", "root/node2");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectPaths(target, paths);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectPaths(paths));
      }
    }.run();
  }

  public void shouldRequireSelectedTreePath() {
    final String[] paths = { "root/node1" };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, paths);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(paths));
      }
    }.run();
  }

  public void shouldRequireSelectedRow() {
    final int[] rows = { 0 };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, rows);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(rows));
      }
    }.run();
  }

  public void shouldRequireNoSelection() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNoSelection(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNoSelection());
      }
    }.run();
  }

  public void shouldToggleRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.toggleRow(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.toggleRow(8));
      }
    }.run();
  }

  public void shouldRequireEditable() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEditable(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEditable());
      }
    }.run();
  }

  public void shouldRequireNotEditable() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotEditable(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotEditable());
      }
    }.run();
  }

  public void shouldRequireSelectedPath() {
    final String[] paths = { "root/node1" };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, paths);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(paths));
      }
    }.run();

  }

  public void shouldSetCellReaderInDriver() {
    final JTreeCellReader reader = createMock(JTreeCellReader.class);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.cellReader(reader);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.cellReader(reader));
      }
    }.run();
  }

  public void shouldReturnSeparatorFromDriver() {
    final String separator = "\\";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.separator()).andReturn(separator);
      }

      protected void codeToTest() {
        String result = fixture.separator();
        assertThat(result).isSameAs(separator);
      }
    }.run();
  }

  public void shouldSetSeparatorInDriver() {
    final String separator = "\\";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.separator(separator);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.separator(separator));
      }
    }.run();
  }

  public void shouldShowPopupMenuAtRow() {
    final int row = 0;
    final JPopupMenu popupMenu = popupMenu().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, row)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture showPopupMenuFixture = fixture.showPopupMenuAt(row);
        assertThat(showPopupMenuFixture.target).isSameAs(popupMenu);
      }
    }.run();
  }

  public void shouldShowPopupMenuAtPath() {
    final String path = "root";
    final JPopupMenu popupMenu = popupMenu().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, path)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture showPopupMenuFixture = fixture.showPopupMenuAt(path);
        assertThat(showPopupMenuFixture.target).isSameAs(popupMenu);
      }
    }.run();
  }

  public void shouldRequireToolTip() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.requireToolTip(target(), "A ToolTip");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireToolTip("A ToolTip"));
      }
    }.run();
  }

  public void shouldRequireToolTipToMatchPattern() {
    final Pattern pattern = regex(".");
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.requireToolTip(target(), pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireToolTip(pattern));
      }
    }.run();
  }

  public void shouldShowPopupMenu() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target())).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
        assertThat(popupMenuFixture.robot).isSameAs(robot());
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  public void shouldShowPopupMenuAtPoint() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final Point p = new Point();
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target(), p)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
        assertThat(popupMenuFixture.robot).isSameAs(robot());
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  JComponentDriver driver() { return driver; }
  JTree target() { return target; }
  JTreeFixture fixture() { return fixture; }
}