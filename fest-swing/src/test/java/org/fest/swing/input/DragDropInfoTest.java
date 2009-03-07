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

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static java.awt.event.MouseEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link DragDropInfo}</code>.
 *
 * @author Yvonne Wang
 */
public class DragDropInfoTest {

  private DragDropInfo info;
  private Component source;
  private Point origin;
  private long when;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
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

  @Test(dataProvider = "notRecognizedEvents" )
  public void shouldNotUpdateForUnrecognizedEvents(int eventId) {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, eventId, when, 0, 0, 0, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isSameAs(source);
    assertThat(info.origin()).isEqualTo(origin);
  }

  @DataProvider(name = "notRecognizedEvents")
  public Object[][] notRecognizedEvents() {
    return new Object[][] { { MOUSE_CLICKED }, { MOUSE_DRAGGED }, { MOUSE_ENTERED }, { MOUSE_EXITED }, { MOUSE_WHEEL } };
  }

  @Test(dataProvider = "mouseReleasedOrMovedEvents")
  public void shouldClearOnMouseReleasedOrMoved(int eventId) {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, eventId, when, 0, 7, 9, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isNull();
  }

  @DataProvider(name = "mouseReleasedOrMovedEvents")
  public Object[][] mouseReleasedOrMovedEvents() {
    return new Object[][] { { MOUSE_RELEASED }, { MOUSE_MOVED } };
  }
}
