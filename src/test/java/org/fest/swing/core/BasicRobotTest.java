/*
 * Created on Sep 5, 2007
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.recorder.KeyRecorder;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Condition;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.*;
import static org.fest.swing.core.BasicRobotTest.KeyAction.action;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.swing.test.task.ComponentSetVisibleTask.setVisible;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link org.fest.swing.core.BasicRobot}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class BasicRobotTest {

  private BasicRobot robot;
  private MyWindow window;
  private JTextField textFieldWithPopup;
  private JTextField textFieldWithoutPopup;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = (BasicRobot)BasicRobot.robotWithCurrentAwtHierarchy();
    window = MyWindow.createAndShow();
    textFieldWithPopup = window.textFieldWithPopup;
    textFieldWithoutPopup = window.textFieldWithoutPopup;
    robot.showWindow(window); // implicitly test 'showWindow(Window)'
    assertThat(isShowing(window)).isTrue();
    assertThat(locationOnScreen(window)).isEqualTo(new Point(100, 100));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorIfWindowNeverShown() {
    try {
      robot.showWindow(AlwaysInvisibleFrame.createNew());
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for Window to open");
    }
  }
  
  private static class AlwaysInvisibleFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static AlwaysInvisibleFrame createNew() {
      return execute(new GuiQuery<AlwaysInvisibleFrame>() {
        protected AlwaysInvisibleFrame executeInEDT() {
          return new AlwaysInvisibleFrame();
        }
      });
    }
    
    @Override public void setVisible(boolean b) {
      super.setVisible(false);
    }
  }

  public void shouldNotPackWindowAsSpecified() {
    class WindowToShow extends JWindow {
      private static final long serialVersionUID = 1L;
      private boolean packed;

      @Override public void pack() {
        packed = true;
        super.pack();
      }

      boolean packed() { return packed; }
    }
    Dimension size = new Dimension(100, 100);
    WindowToShow w = execute(new GuiQuery<WindowToShow>() {
      protected WindowToShow executeInEDT() {
        return new WindowToShow();
      }
    });
    robot.showWindow(w, size, false);
    assertThat(sizeOf(w)).isEqualTo(size);
    assertThat(w.packed()).isFalse();
    assertThat(locationOnScreen(w)).isEqualTo(new Point(0, 0));
  }

  public void shouldClickComponent() {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1);
  }

  public void shouldRightClickComponent() {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.rightClick(textFieldWithoutPopup);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(1);
  }

  public void shouldDoubleClickComponent() {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.doubleClick(textFieldWithoutPopup);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(2);
  }

  public void shouldClickComponentOnceWithLeftButtonAtGivenPoint() {
    Point p = new Point(10, 10);
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup, p);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1).clickedAt(p);
  }

  @Test(groups = GUI, dataProvider = "mouseButtons")
  public void shouldClickComponentOnceWithGivenButton(MouseButton button) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup, button);
    assertThat(recorder).clicked(button).timesClicked(1);
  }

  @DataProvider(name = "mouseButtons")
  public Object[][] mouseButtons() {
    return new Object[][] {
        { LEFT_BUTTON },
        { MIDDLE_BUTTON },
        { RIGHT_BUTTON }
    };
  }

  @Test(groups = GUI, dataProvider = "clickingData")
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimes(MouseButton button, int times) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    robot.click(textFieldWithoutPopup, button, times);
    assertThat(recorder).clicked(button).timesClicked(times).clickedAt(centerOf(textFieldWithoutPopup));
  }

  @Test(groups = GUI, dataProvider = "clickingData")
  public void shouldClickComponentWithGivenMouseButtonAndGivenNumberOfTimesAtGivenPoint(MouseButton button, int times) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    Point point = new Point(10, 10);
    robot.click(textFieldWithoutPopup, point, button, times);
    assertThat(recorder).clicked(button).timesClicked(times).clickedAt(point);
  }

  @Test(groups = GUI, dataProvider = "clickingData")
  public void shouldClickWithGivenMouseButtonAndGivenNumberOfTimesAtGivenPoint(MouseButton button, int times) {
    ClickRecorder recorder = attachTo(textFieldWithoutPopup);
    Point where = locationOnScreenOf(textFieldWithoutPopup);
    Point visibleCenter = visibleCenterOf(textFieldWithoutPopup);
    where.translate(visibleCenter.x, visibleCenter.y);
    robot.click(where, button, times);
    assertThat(recorder).clicked(button).timesClicked(times).clickedAt(visibleCenter);
  }
  
  @DataProvider(name = "clickingData")
  public Object[][] clickingData() {
    return new Object[][] {
        { LEFT_BUTTON, 1 },
        { LEFT_BUTTON, 2 },
        { MIDDLE_BUTTON, 1 },
        { MIDDLE_BUTTON, 2 },
        { RIGHT_BUTTON, 1 },
        { RIGHT_BUTTON, 2 },
    };
  }

  public void shouldPressAndReleaseGivenKeys() {
    textFieldWithPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(textFieldWithPopup);
    int[] keys = { VK_A, VK_B, VK_Z };
    robot.pressAndReleaseKeys(keys);
    robot.waitForIdle();
    assertThat(recorder).keysPressed(keys).keysReleased(keys);
  }

  public void shouldPressKeyAndModifiers() {
    robot.focusAndWaitForFocusGain(textFieldWithPopup);
    KeyPressRecorder recorder = KeyPressRecorder.attachTo(textFieldWithPopup);
    robot.pressAndReleaseKey(VK_C, new int[] { CTRL_MASK, SHIFT_MASK });
    robot.waitForIdle();
    List<KeyAction> actions = recorder.actions;
    assertThat(actions).containsOnly(
        action(KEY_PRESSED,  VK_SHIFT),
        action(KEY_PRESSED,  VK_CONTROL),
        action(KEY_PRESSED,  VK_C),
        action(KEY_RELEASED, VK_C),
        action(KEY_RELEASED, VK_CONTROL),
        action(KEY_RELEASED, VK_SHIFT)
    );
  }

  public void shouldPressGivenKeyWithoutReleasingIt() {
    textFieldWithPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(textFieldWithPopup);
    robot.pressKey(VK_A);
    assertThat(recorder).keysPressed(VK_A).noKeysReleased();
  }

  public void shouldReleaseGivenKey() {
    textFieldWithPopup.requestFocusInWindow();
    KeyRecorder recorder = KeyRecorder.attachTo(textFieldWithPopup);
    robot.pressKey(VK_A);
    robot.releaseKey(VK_A);
    assertThat(recorder).keysReleased(VK_A);
  }

  public void shouldShowPopupMenu() {
    JPopupMenu menu = robot.showPopupMenu(textFieldWithPopup);
    assertThat(menu).isSameAs(popupMenu());
    assertThat(menu.isVisible()).isTrue();
  }

  public void shouldThrowErrorIfPopupNotFound() {
    try {
      robot.showPopupMenu(textFieldWithoutPopup);
      failWhenExpectingException();
    } catch (ComponentLookupException expected) {
      assertThat(expected.getMessage()).contains("Unable to show popup")
                                    .contains("on javax.swing.JTextField")
                                    .contains("name='withoutPopup'");
    }
  }

  public void shouldReturnActivePopupMenu() {
    robot.showPopupMenu(textFieldWithPopup);
    JPopupMenu found = robot.findActivePopupMenu();
    assertThat(found).isSameAs(window.popupMenu);
  }

  public void shouldReturnNullIfActivePopupMenuNotFound() {
    JPopupMenu found = robot.findActivePopupMenu();
    assertThat(found).isNull();
  }

  public void shouldCloseWindow() {
    final TestWindow w = TestWindow.createNewWindow(getClass());
    w.display();
    robot.close(w);
    pause(new Condition("Window closed") {
      public boolean test() {
        return !isVisible(w);
      }
    });
    assertThat(isVisible(w)).isFalse();
  }

  public void shouldNotCloseWindowIfWindowNotShowing() {
    TestWindow w = TestWindow.createNewWindow(getClass());
    w.display();
    setVisible(w, false);
    robot.waitForIdle();
    robot.close(w);
    assertThat(isShowing(w)).isFalse();
  }

  public void shouldGiveFocus() {
    giveFocusAndVerifyThatHasFocus(textFieldWithPopup);
    giveFocusAndVerifyThatHasFocus(textFieldWithoutPopup);
  }

  private void giveFocusAndVerifyThatHasFocus(Component c) {
    robot.focus(c);
    pause(500);
    assertThat(c.isFocusOwner()).isTrue();
  }

  public void shouldGiveFocusAndWaitUntilComponentHasFocus() {
    robot.focusAndWaitForFocusGain(textFieldWithPopup);
    assertThat(textFieldWithPopup.isFocusOwner()).isTrue();
    robot.focusAndWaitForFocusGain(textFieldWithoutPopup);
    assertThat(textFieldWithoutPopup.isFocusOwner()).isTrue();
  }

  private JPopupMenu popupMenu() {
    return window.popupMenu;
  }

  public void shouldPassIfNoJOptionPaneIsShowing() {
    robot.requireNoJOptionPaneIsShowing();
  }

  public void shouldFailIfJOptionPaneIsShowingAndExpectingNotShowing() {
    robot.click(window.button);
    pause(500);
    try {
      robot.requireNoJOptionPaneIsShowing();
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Expecting no JOptionPane to be showing");
    }
  }
  
  public void shouldRotateMouseWheel() {
    JList list = window.list;
    assertThat(firstVisibleIndexOf(list)).isEqualTo(0);
    MouseWheelRecorder recorder = MouseWheelRecorder.attachTo(window.listScrollPane);
    int amount = 50;
    robot.rotateMouseWheel(list, amount);
    assertThat(recorder.wheelRotation()).isEqualTo(amount);
    assertThat(firstVisibleIndexOf(list)).isGreaterThan(0);
  }
  
  @RunsInEDT
  private static int firstVisibleIndexOf(final JList list) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return list.getFirstVisibleIndex();
      }
    });
  }
  
  private static class MouseWheelRecorder implements MouseWheelListener {
    private int wheelRotation;

    static MouseWheelRecorder attachTo(Component c) {
      MouseWheelRecorder recorder = new MouseWheelRecorder();
      c.addMouseWheelListener(recorder);
      return recorder;
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
      wheelRotation = e.getWheelRotation();
    }

    int wheelRotation() { return wheelRotation; }
  }

  static class KeyPressRecorder extends KeyAdapter {
    final List<KeyAction> actions = new ArrayList<KeyAction>();

    static KeyPressRecorder attachTo(Component c) {
      KeyPressRecorder recorder = new KeyPressRecorder();
      c.addKeyListener(recorder);
      return recorder;
    }

    @Override public void keyPressed(KeyEvent e) {
      actions.add(action(KEY_PRESSED, e.getKeyCode()));
    }

    @Override public void keyReleased(KeyEvent e) {
      actions.add(action(KEY_RELEASED, e.getKeyCode()));
    }
  }

  static class KeyAction {
    final int type;
    final int keyCode;

    static KeyAction action(int type, int keyCode) {
      return new KeyAction(type, keyCode);
    }

    private KeyAction(int type, int keyCode) {
      this.type = type;
      this.keyCode = keyCode;
    }

    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      final KeyAction other = (KeyAction) obj;
      if (type != other.type) return false;
      return keyCode == other.keyCode;
    }

    @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + type;
      result = prime * result + keyCode;
      return result;
    }

    @Override public String toString() {
      StringBuilder b = new StringBuilder();
      b.append("[type=").append(type).append(", ");
      b.append("keyCode=").append(keyCode).append("]");
      return b.toString();
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final int COLUMNS = 15;

    final JTextField textFieldWithPopup = new JTextField("With Pop-up Menu", COLUMNS);
    final JTextField textFieldWithoutPopup = new JTextField("Without Pop-up Menu", COLUMNS);
    final JList list = new JList(array("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"));
    final JScrollPane listScrollPane = new JScrollPane(list);
    final JButton button = new JButton("Click Me");
    final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");


    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return display(new MyWindow());
        }
      });
    }

    private MyWindow() {
      super(BasicRobotTest.class);
      listScrollPane.setPreferredSize(new Dimension(300, 100));
      addComponents(textFieldWithPopup, textFieldWithoutPopup, button, listScrollPane);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(MyWindow.this, "A Message");
        }
      });
      textFieldWithPopup.setComponentPopupMenu(popupMenu);
      textFieldWithoutPopup.setName("withoutPopup");
      popupMenu.add(new JMenuItem("Luke"));
      popupMenu.add(new JMenuItem("Leia"));
    }
  }
}
