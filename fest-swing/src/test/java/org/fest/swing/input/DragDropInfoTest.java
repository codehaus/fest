/*
 * Created on Mar 28, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.input;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.awt.event.MouseEvent.MOUSE_DRAGGED;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import static java.awt.event.MouseEvent.MOUSE_EXITED;
import static java.awt.event.MouseEvent.MOUSE_MOVED;
import static java.awt.event.MouseEvent.MOUSE_PRESSED;
import static java.awt.event.MouseEvent.MOUSE_RELEASED;
import static java.awt.event.MouseEvent.MOUSE_WHEEL;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link DragDropInfo}</code>.
 *
 * @author Yvonne Wang
 */
public class DragDropInfoTest extends EDTSafeTestCase {

  private DragDropInfo info;
  private Component source;
  private Point origin;
  private long when;

  @Before public void setUp() {
    info = new DragDropInfo();
    source = textField().createNew();
    origin = new Point(6, 8);
    when = System.currentTimeMillis();
  }

  @Test public void shouldUpdateOnMousePressed() {
    MouseEvent event = new MouseEvent(source, MOUSE_PRESSED, when, 0, origin.x, origin.y, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isSameAs(source);
    assertThat(info.origin()).isEqualTo(origin);
  }

  @Test
  public void shouldNotUpdateForUnrecognizedEvents() {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, MOUSE_CLICKED, when, 0, 0, 0, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isSameAs(source);
    assertThat(info.origin()).isEqualTo(origin);
  }

  // TODO send them as parameters
  public Object[][] notRecognizedEvents() {
    return new Object[][] { { MOUSE_CLICKED }, { MOUSE_DRAGGED }, { MOUSE_ENTERED }, { MOUSE_EXITED }, { MOUSE_WHEEL } };
  }

  @Test
  public void shouldClearOnMouseReleased() {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, MOUSE_RELEASED, when, 0, 7, 9, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isNull();
  }

  @Test
  public void shouldClearOnMouseMoved() {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, MOUSE_MOVED, when, 0, 7, 9, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isNull();
  }
}
