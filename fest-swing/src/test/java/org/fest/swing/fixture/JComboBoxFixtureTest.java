/*
 * Created on Apr 9, 2007
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

import static java.awt.event.KeyEvent.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.swing.test.builder.JLists.list;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.util.Arrays.array;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.easymock.classextension.EasyMock;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.driver.JComboBoxDriver;
import org.fest.swing.driver.JComponentDriver;
import org.junit.Test;

/**
 * Tests for <code>{@link JComboBoxFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxFixtureTest extends CommonComponentFixtureTestCase<JComboBox> {

  private JComboBoxDriver driver;
  private JComboBox target;
  private JComboBoxFixture fixture;

  void onSetUp() {
    driver = EasyMock.createMock(JComboBoxDriver.class);
    target = comboBox().createNew();
    fixture = new JComboBoxFixture(robot, target);
    fixture.driver(driver);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  @Test
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "comboBox";
    expectLookupByName(name, JComboBox.class);
    verifyLookup(new JComboBoxFixture(robot, name));
  }

  @Test
  public void shouldReturnContents() {
    final String[] contents = array("Frodo", "Sam");
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

  @Test
  public void shouldReplaceText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.replaceText(target, "Hello");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.replaceText("Hello"));
      }
    }.run();
  }

  @Test
  public void shouldSelectAllText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectAllText(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectAllText());
      }
    }.run();
  }

  @Test
  public void shouldEnterText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.enterText(target, "Hello");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.enterText("Hello"));
      }
    }.run();
  }

  @Override public void should_press_and_release_keys() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.pressAndReleaseKeys(target, VK_A, VK_B, VK_C);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().pressAndReleaseKeys(VK_A, VK_B, VK_C));
      }
    }.run();
  }

  @Test
  public void shouldReturnList() {
    final JList list = list().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.dropDownList()).andReturn(list);
      }

      protected void codeToTest() {
        JList result = fixture.list();
        assertThat(result).isSameAs(list);
      }
    }.run();
  }

  @Test
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

  @Test
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

  @Test
  public void shouldSelectItemWithText() {
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

  @Test
  public void shouldSelectItemWithTextMatchingPattern() {
    final Pattern p = regex(".");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, p);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem(p));
      }
    }.run();
  }

  @Test
  public void shouldReturnValueAtIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.value(target, 8)).andReturn("Sam");
      }

      protected void codeToTest() {
        Object result = fixture.valueAt(8);
        assertThat(result).isEqualTo("Sam");
      }
    }.run();
  }

  @Test
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

  @Test
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

  @Test
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

  @Test
  public void shouldSetCellReaderInDriver() {
    final JComboBoxCellReader reader = createMock(JComboBoxCellReader.class);
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

  @Test
  public void shouldRequireSelectionValue() {
    final String value = "Hello";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(value));
      }
    }.run();
  }

  @Test
  public void shouldRequireSelectionByPatternMatching() {
    final Pattern p = regex("Hello");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, p);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(p));
      }
    }.run();
  }

  @Test
  public void shouldRequireSelectionIndex() {
    final int index = 6;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, index);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection(index));
      }
    }.run();
  }

  @Test
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

  @Test
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

  @Test
  public void shouldShowPopupMenu() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target())).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
        assertThat(popupMenuFixture.robot).isSameAs(robot);
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  @Test
  public void shouldShowPopupMenuAtPoint() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final Point p = new Point();
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target(), p)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
        assertThat(popupMenuFixture.robot).isSameAs(robot);
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  @Test
  public void shouldReturnClientProperty() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.clientProperty(target, "name")).andReturn("Luke");
      }

      protected void codeToTest() {
        assertThat(fixture.clientProperty("name")).isEqualTo("Luke");
      }
    }.run();
  }
  
  JComponentDriver driver() { return driver; }
  JComboBox target() { return target; }
  JComboBoxFixture fixture() { return fixture; }
}
