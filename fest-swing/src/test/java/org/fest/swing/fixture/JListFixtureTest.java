/*
 * Created on Jun 12, 2007
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
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.test.builder.JLists.list;
import static org.fest.swing.test.builder.JPopupMenus.popupMenu;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.util.Range.from;
import static org.fest.swing.util.Range.to;
import static org.fest.util.Arrays.array;

import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JListDriver;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JListFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JListFixtureTest extends CommonComponentFixtureTestCase<JList> {

  private JListDriver driver;
  private JList target;
  private JListFixture fixture;

  void onSetUp() {
    driver = createMock(JListDriver.class);
    target = list().createNew();
    fixture = new JListFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "list";
    expectLookupByName(name, JList.class);
    verifyLookup(new JListFixture(robot(), name));
  }

  public void shouldReturnContents() {
    final String[] contents = array("Luke", "Leia");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.contentsOf(target)).andReturn(contents);
      }

      protected void codeToTest() {
        Object[] result = fixture.contents();
        assertThat(result).isSameAs(contents);
      }
    }.run();
  }

  public void shouldReturnSelection() {
    final String[] selection = array("Luke", "Leia");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.selectionOf(target)).andReturn(selection);
      }

      protected void codeToTest() {
        Object[] result = fixture.selection();
        assertThat(result).isSameAs(selection);
      }
    }.run();
  }

  public void shouldClearSelection() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clearSelection(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.clearSelection());
      }
    }.run();
  }

  public void shouldSelectItemsInRange() {
    final From from = from(6);
    final To to = to(8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, from, to);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(from, to));
      }
    }.run();
  }

  public void shouldSelectItemsUnderIndices() {
    final int[] indices = new int[] { 6, 8 };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, indices);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(indices));
      }
    }.run();
  }

  public void shouldSelectItemUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem(6));
      }
    }.run();
  }

  public void shouldSelectItemsWithValues() {
    final String[] values = array("Frodo", "Sam");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, values);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(values));
      }
    }.run();
  }

  public void shouldSelectItemsMatchingPatterns() {
    final Pattern[] patterns = array(regex("Frodo"), regex("Sam"));
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, patterns);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(patterns));
      }
    }.run();
  }

  public void shouldSelectItemWithValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, "Frodo");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem("Frodo"));
      }
    }.run();
  }

  public void shouldSelectItemMatchingPattern() {
    final Pattern pattern = regex("Hello");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem(pattern));
      }
    }.run();
  }

  public void shouldDoubleClickItemUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickItem(target, 6, LEFT_BUTTON, 2);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickItem(6));
      }
    }.run();
  }

  public void shouldDoubleClickItemWithValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickItem(target, "Frodo", LEFT_BUTTON, 2);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickItem("Frodo"));
      }
    }.run();
  }

  public void shouldDoubleClickItemMatchingPattern() {
    final Pattern p = regex("hello");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickItem(target, p, LEFT_BUTTON, 2);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickItem(p));
      }
    }.run();
  }

  public void shouldRequireSelectionValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, "Frodo");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection("Frodo"));
      }
    }.run();
  }

  public void shouldRequireSelectionIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(6));
      }
    }.run();
  }

  public void shouldRequireSelectedItems() {
    final String[] items = array("Frodo", "Sam");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelectedItems(target, items);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelectedItems(items));
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

  public void shouldReturnValueAtIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.value(target, 6)).andReturn("Frodo");
      }

      protected void codeToTest() {
        assertThat(fixture.valueAt(6)).isEqualTo("Frodo");
      }
    }.run();
  }

  public void shouldReturnJListItemFixture() {
    JListItemFixture item = fixture.item(6);
    assertThat(item.index).isEqualTo(6);
    assertThat(item.list).isSameAs(fixture);
  }

  public void shouldReturnJListItemFixtureUsingValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.indexOf(target, "Frodo")).andReturn(8);
      }

      protected void codeToTest() {
        JListItemFixture item = fixture.item("Frodo");
        assertThat(item.index).isEqualTo(8);
        assertThat(item.list).isSameAs(fixture);
      }
    }.run();
  }

  public void shouldReturnJListItemFixtureWithItemMatchingPatternAsString() {
    final Pattern pattern = regex("Frodo");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.indexOf(target, pattern)).andReturn(8);
      }

      protected void codeToTest() {
        JListItemFixture item = fixture.item(pattern);
        assertThat(item.index).isEqualTo(8);
        assertThat(item.list).isSameAs(fixture);
      }
    }.run();
  }

  public void shouldDragElementWithElement() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, "Frodo");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag("Frodo"));
      }
    }.run();
  }

  public void shouldDropElementWithElement() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, "Frodo");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop("Frodo"));
      }
    }.run();
  }

  public void shouldDragElementUnderIndex() {
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

  public void shouldDrop() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop());
      }
    }.run();
  }

  public void shouldDropElementUnderIndex() {
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

  public void shouldShowJPopupMenuAtItemUnderIndex() {
    final JPopupMenu popup = popupMenu().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, 6)).andReturn(popup);
      }

      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(6);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  public void shouldShowJPopupMenuAtItemWithValue() {
    final JPopupMenu popup = popupMenu().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, "Frodo")).andReturn(popup);
      }

      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt("Frodo");
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  public void shouldSetCellReaderInDriver() {
    final JListCellReader reader = createMock(JListCellReader.class);
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

  ComponentDriver driver() { return driver; }
  JList target() { return target; }
  JListFixture fixture() { return fixture; }
}
