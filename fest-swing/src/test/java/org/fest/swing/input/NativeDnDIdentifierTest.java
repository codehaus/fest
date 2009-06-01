/*
 * Created on May 22, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.input;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLabels.label;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link NativeDnDIdentifier}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class NativeDnDIdentifierTest {

  private NativeDnDIdentifier dnd;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
    dnd = new NativeDnDIdentifier();
  }

  public void shouldReturnIsNativeDnDIfEventIsMouseEventAndClassNameIsSunDropTargetEvent() {
    assertThat(dnd.isNativeDragAndDrop(new SunDropTargetEvent())).isTrue();
  }

  public void shouldReturnIsNotNativeDnDIfEventIsNotMouseEvent() {
    KeyEvent e = new KeyEvent(label().createNew(), 0, 0, 0, 0, 'a');
    assertThat(dnd.isNativeDragAndDrop(e)).isFalse();
  }

  public void shouldReturnIsNotNativeDnDIfEventClassNameIsNotSunDropTargetEvent() {
    MouseEvent e = new MouseEvent(label().createNew(), 0, 0, 0, 0, 0, 0, false);
    assertThat(dnd.isNativeDragAndDrop(e)).isFalse();
  }

  static class SunDropTargetEvent extends MouseEvent {
    private static final long serialVersionUID = 1L;

    SunDropTargetEvent() {
      super(label().createNew(), 0, 0, 0, 0, 0, 0, false);
    }
  }
}
