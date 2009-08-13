/*
 * Created on Jun 11, 2008
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
package org.fest.swing.keystroke;

import static java.lang.String.valueOf;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.driver.JTextComponentDriver;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.recorder.KeyRecorder;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Condition;

/**
 * Test case for implementations of <code>{@link KeyStrokeMappingProvider}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class KeyStrokeMappingProviderTestCase extends RobotBasedTestCase {

  static final char BACKSPACE = 8;
  static final char CR = 10;
  static final char DELETE = 127;
  static final char ESCAPE = 27;
  static final char LF = 13;

  private JTextComponent textArea;
  private JTextComponentDriver driver;
  
  final char character;
  final KeyStroke keyStroke;

  public KeyStrokeMappingProviderTestCase(char character, KeyStroke keyStroke) {
    this.character = character;
    this.keyStroke = keyStroke;
  }
  
  @Override protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    textArea = window.textArea;
    robot.showWindow(window);
    driver = new JTextComponentDriver(robot);
  }

  static class KeyStrokeMappingIterator implements Iterator<Object[]> {
    private final Iterator<KeyStrokeMapping> realIterator;

    KeyStrokeMappingIterator(Collection<KeyStrokeMapping> keyStrokeMappings) {
      realIterator = keyStrokeMappings.iterator();
    }

    public boolean hasNext() {
      return realIterator.hasNext();
    }

    public Object[] next() {
      KeyStrokeMapping next = realIterator.next();
      return new Object[] { next.character(), next.keyStroke() };
    }

    public void remove() {}
  }

  final void pressKeyStrokeAndVerify(char expectedChar) {
    pressInTextArea();
    final String expectedText = valueOf(expectedChar);
    pause(new Condition(concat("text in JTextArea is ", quote(expectedText))) {
      public boolean test() {
        return expectedText.equals(textArea.getText());
      }
    }, 500);
  }

  final void pressKeyStrokeAndVerify(final int expectedKey) {
    assertThat(keyStroke.getModifiers()).as("no modifiers should be specified").isEqualTo(0);
    final KeyRecorder recorder = KeyRecorder.attachTo(textArea);
    pressInTextArea();
    pause(new Condition(concat("key with code ", expectedKey, " is pressed")) {
      public boolean test() {
        return recorder.keysWerePressed(expectedKey);
      }
    }, 500);
  }

  static Collection<Object[]> keyStrokesFrom(Collection<KeyStrokeMapping> mappings) {
    List<Object[]> keyStrokes = new ArrayList<Object[]>();
    for (KeyStrokeMapping mapping : mappings)
      keyStrokes.add(new Object[] { mapping.character(), mapping.keyStroke() });
    return keyStrokes;
  }
  
  private void pressInTextArea() {
    driver.pressAndReleaseKey(textArea, keyStroke.getKeyCode(), new int[] { keyStroke.getModifiers() });
  }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JTextArea textArea = new JTextArea(3, 8);

    private MyWindow(Class<?> testClass) {
      super(testClass);
      add(textArea);
      setPreferredSize(new Dimension(200, 200));
    }
  }

}
