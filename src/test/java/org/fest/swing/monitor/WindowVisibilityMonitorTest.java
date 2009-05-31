/*
 * Created on Oct 10, 2007
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
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.MethodInvocations.Args;
import org.fest.swing.test.swing.TestWindow;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.MethodInvocations.Args.args;

/**
 * Tests for <code>{@link WindowVisibilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowVisibilityMonitorTest {

  private WindowVisibilityMonitor monitor;

  private MyWindow window;
  private Windows windows;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    window = MyWindow.createNew();
    windows = createMock(Windows.class);
    createAndAttachMonitor();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  private void createAndAttachMonitor() {
    monitor = new WindowVisibilityMonitor(windows);
  }

  @Test public void shouldMarkWindowAsShowingIfWindowShown() {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsShowing(window);
      }

      protected void codeToTest() {
        monitor.componentShown(componentEventWithWindowAsSource());
      }
    }.run();
  }

  @Test public void shouldNotMarkWindowAsShowingIfComponentShownIsNotWindow() {
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.componentShown(componentEventWithTextFieldAsSource());
      }
    }.run();
  }

  @Test public void shouldMarkWindowAsHiddenIfWindowHidden() {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsHidden(window);
      }

      protected void codeToTest() {
        monitor.componentHidden(componentEventWithWindowAsSource());
      }
    }.run();
  }

  @Test public void shouldNotMarkWindowAsHiddenIfComponentHiddenIsNotWindow() {
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.componentHidden(componentEventWithTextFieldAsSource());
      }
    }.run();
  }

  @Test public void shouldRemoveItselfWhenWindowClosed() {
    window.startRecording();
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.windowClosed(new WindowEvent(window, 8));
        assertThat(window.requireInvoked("removeWindowListener", args(monitor)));
        assertThat(window.requireInvoked("removeComponentListener", args(monitor)));
      }
    }.run();
  }

  private ComponentEvent componentEventWithWindowAsSource() {
    return componentEvent(window);
  }

  @RunsInEDT
  private ComponentEvent componentEventWithTextFieldAsSource() {
    return componentEvent(textField().createNew());
  }

  private ComponentEvent componentEvent(Component source) {
    return new ComponentEvent(source, 8);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(WindowVisibilityMonitorTest.class);
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public synchronized void removeWindowListener(WindowListener l) {
      if (recording) methodInvocations.invoked("removeWindowListener", args(l));
      super.removeWindowListener(l);
    }

    @Override
    public synchronized void removeComponentListener(ComponentListener l) {
      if (recording) methodInvocations.invoked("removeComponentListener", args(l));
      super.removeComponentListener(l);
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
