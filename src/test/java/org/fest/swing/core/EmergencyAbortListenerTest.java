/*
 * Created on Jul 20, 2008
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
package org.fest.swing.core;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.InputModifiers.*;
import static org.fest.swing.test.builder.JButtons.button;

/**
 * Tests for <code>{@link EmergencyAbortListener}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class EmergencyAbortListenerTest {

  private Toolkit toolkit;
  private TestTerminator terminator;
  private EmergencyAbortListener listener;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    toolkit = createMock(Toolkit.class);
    terminator = createMock(TestTerminator.class);
    listener = new EmergencyAbortListener(toolkit, terminator);
  }
  
  public void shouldHaveDefaultKeyCombination() {
    assertThat(listener.keyCode()).isEqualTo(VK_A);
    int modifiers = listener.modifiers();
    assertThat(isControlDown(modifiers)).isTrue();
    assertThat(isShiftDown(modifiers)).isTrue();
    assertThat(isAltDown(modifiers)).isFalse();
    assertThat(isAltGraphDown(modifiers)).isFalse();
    assertThat(isMetaDown(modifiers)).isFalse();
  }
  
  public void shouldRegisterItselfInToolkit() {
    final EmergencyAbortListener previous = createMock(EmergencyAbortListener.class);
    final AWTEventListener[] allPrevious = { previous, createMock(AWTEventListener.class) };
    new EasyMockTemplate(toolkit) {
      protected void expectations() {
        expect(toolkit.getAWTEventListeners(KEY_EVENT_MASK)).andReturn(allPrevious);
        toolkit.removeAWTEventListener(previous);
        expectLastCall().once();
        toolkit.addAWTEventListener(listener, KEY_EVENT_MASK);
        expectLastCall().once();
      }
    
      protected void codeToTest() {
        listener.register();
      }
    }.run();
  }

  public void shouldUnregisterFromToolkit() {
    new EasyMockTemplate(toolkit) {
      protected void expectations() {
        toolkit.removeAWTEventListener(listener);
        expectLastCall().once();
      }
    
      protected void codeToTest() {
        listener.unregister();
      }
    }.run();
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfKeyPressInfoIsNull() {
    listener.keyCombination(null);
  }
  
  public void shouldChangeKeyCombinationToGivenOne() {
    listener.keyCombination(KeyPressInfo.keyCode(VK_C).modifiers(ALT_MASK, META_MASK));
    assertThat(listener.keyCode()).isEqualTo(VK_C);
    int modifiers = listener.modifiers();
    assertThat(isAltDown(modifiers)).isTrue();
    assertThat(isMetaDown(modifiers)).isTrue();
    assertThat(isAltGraphDown(modifiers)).isFalse();
    assertThat(isControlDown(modifiers)).isFalse();
    assertThat(isShiftDown(modifiers)).isFalse();
  }
  
  public void shouldNotTerminateTestsIfEventIdIsNotKeyPressed() {
    final AWTEvent event = createMock(AWTEvent.class);
    final int eventId = KEY_PRESSED + 1; 
    new EasyMockTemplate(event, terminator) {
      protected void expectations() {
        expect(event.getID()).andReturn(eventId);
      }

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();
  }
  
  public void shouldNotTerminateTestsIfEventIsNotKeyEvent() {
    final AWTEvent event = createMock(AWTEvent.class);
    new EasyMockTemplate(event, terminator) {
      protected void expectations() {
        expect(event.getID()).andReturn(KEY_PRESSED);
      }

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();    
  }

  public void shouldNotTerminateTestsIfKeyCodeNotMatching() {
    final KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, 0, VK_Z, 'Z');
    new EasyMockTemplate(terminator) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();    
  }

  public void shouldNotTerminateTestsIfModifiersNotMatching() {
    final KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, 0, VK_A, 'A');
    new EasyMockTemplate(terminator) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();    
  }

  public void shouldTerminateTestsIfKeyCombinationMatches() {
    final KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, CTRL_MASK | SHIFT_MASK, VK_A, 'A');
    new EasyMockTemplate(terminator) {
      protected void expectations() {
        terminator.terminateTests();
        expectLastCall().once();
      }

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();    
  }
}
