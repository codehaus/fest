/*
 * Created on Feb 10, 2007
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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.DialogShowTask.packAndShow;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.DialogDriver;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for <code>{@link DialogFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogFixture_Test extends CommonComponentFixture_TestCase<Dialog> {

  private DialogDriver driver;
  private Dialog target;
  private DialogFixture fixture;

  void onSetUp() {
    driver = createMock(DialogDriver.class);
    target = createDialog();
    fixture = new DialogFixture(robot, target);
    fixture.driver(driver);
  }

  @RunsInEDT
  private static Dialog createDialog() {
    return execute(new GuiQuery<Dialog>() {
      protected Dialog executeInEDT() throws Throwable {
        JDialog dialog = new JDialog();
        dialog.setName("dialog");
        dialog.setTitle(DialogFixture_Test.class.getSimpleName());
        return dialog;
      }
    });
  }

  @Test
  public void should_create_fixture_with_given_Component_name() {
    String name = "dialog";
    expectLookupByName(name, Dialog.class);
    verifyLookup(new DialogFixture(robot, name));
  }

  @Test
  public void should_create_fixture_with_new_Robot_and_given_Component() {
    fixture = new DialogFixture(target);
    try {
      assertThat(fixture.robot).isInstanceOf(BasicRobot.class);
    } finally {
      fixture.cleanUp();
    }
  }

  @Test
  public void should_create_fixture_with_new_Robot_and_given_Component_name() {
    packAndShow(target);
    fixture = new DialogFixture("dialog");
    try {
      assertThat(fixture.robot).isInstanceOf(BasicRobot.class);
      assertThat(fixture.component()).isSameAs(target);
    } finally {
      fixture.cleanUp();
    }
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_driver_is_null() {
    fixture.driver(null);
  }

  @Test
  public void should_move_to_front() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveToFront(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveToFront());
      }
    }.run();
  }

  @Test
  public void should_move_to_back() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveToBack(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveToBack());
      }
    }.run();
  }

  @Test
  public void should_require_modal() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireModal(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireModal());
      }
    }.run();
  }

  @Test
  public void should_require_size() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSize(target, size);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSize(size));
      }
    }.run();
  }

  @Test
  public void should_move_to_point() {
    final Point p = new Point(6, 8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveTo(target, p);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveTo(p));
      }
    }.run();
  }

  @Test
  public void should_resize_height() {
    final int height = 68;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeHeightTo(target, height);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeHeightTo(height));
      }
    }.run();
  }

  @Test
  public void should_resize_width() {
    final int width = 68;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeWidthTo(target, width);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeWidthTo(width));
      }
    }.run();
  }

  @Test
  public void should_resize_width_and_height() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeTo(target, size);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeTo(size));
      }
    }.run();
  }

  @Test
  public void should_show() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.show(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.show());
      }
    }.run();
  }

  @Test
  public void should_show_with_given_size() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.show(target, size);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.show(size));
      }
    }.run();
  }

  @Test
  public void should_show_popup_menu() {
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
  public void should_show_popup_menu_at_given_point() {
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
  public void should_close() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.close(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        fixture.close();
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  Dialog target() { return target; }
  DialogFixture fixture() { return fixture; }
}