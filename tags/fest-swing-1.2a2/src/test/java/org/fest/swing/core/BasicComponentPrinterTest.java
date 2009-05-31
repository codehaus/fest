/*
 * Created on Dec 22, 2007
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

import javax.swing.JButton;
import javax.swing.JComboBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.io.PrintStreamStub;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicComponentPrinter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicComponentPrinterTest {

  private BasicComponentPrinter printer;
  private MyWindow windowOne;
  private MyWindow windowTwo;
  private PrintStreamStub out;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    printer = (BasicComponentPrinter)BasicComponentPrinter.printerWithNewAwtHierarchy();
    windowOne = MyWindow.createAndShow();
    windowOne.buttonName("button1");
    windowTwo = MyWindow.createAndShow();
    windowTwo.buttonName("button2");
    out = new PrintStreamStub();
  }

  @AfterMethod public void tearDown() {
    try {
      windowOne.destroy();
      windowTwo.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldCreatePrinterWithExistingHierarchy() {
    printer = (BasicComponentPrinter)BasicComponentPrinter.printerWithCurrentAwtHierarchy();
    assertThat(printer.hierarchy()).isInstanceOf(ExistingHierarchy.class);
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfOutputStreamIsNull() {
    printer.printComponents(null);
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfOutputStreamIsNullWhenPrintingFromRoot() {
    printer.printComponents(null, windowOne);
  }

  public void shouldPrintAllComponents() {
    printer.printComponents(out);
    assertThat(out.printed()).contains(format(windowOne),
                                       format(windowOne.button),
                                       format(windowTwo),
                                       format(windowTwo.button));
  }

  public void shouldPrintComponentsUnderGivenRootOnly() {
    printer.printComponents(out, windowOne);
    assertThat(out.printed()).contains(format(windowOne),
                                       format(windowOne.button))
                             .excludes(format(windowTwo),
                                       format(windowTwo.button));
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfOutputStreamIsNullWhenMatchingByType() {
    printer.printComponents(null, JButton.class);
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfOutputStreamIsNullWhenMatchingByTypeAndPrintingFromRoot() {
    printer.printComponents(null, JButton.class, windowOne);
  }
  
  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfTypeToMatchIsNull() {
    Class<? extends Component> type = null;
    printer.printComponents(out, type);
  }
  
  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfTypeToMatchIsNullWhenPrintingFromRoot() {
    Class<? extends Component> type = null;
    printer.printComponents(out, type, windowOne);
  }

  public void shouldPrintAllComponentsOfGivenType() {
    printer.printComponents(out, JButton.class);
    assertThat(out.printed()).containsOnly(format(windowOne.button),
                                           format(windowTwo.button));
  }

  public void shouldNotPrintComponentsIfTypeDoesNotMatch() {
    printer.printComponents(out, JComboBox.class);
    assertThat(out.printed()).isEmpty();
  }

  public void shouldPrintAllComponentsOfGivenTypeUnderGivenRootOnly() {
    printer.printComponents(out, JButton.class, windowOne);
    assertThat(out.printed()).containsOnly(format(windowOne.button));
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfOutputStreamIsNullWhenUsingMatcher() {
    printer.printComponents(null, new NameMatcher("button1"));
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfOutputStreamIsNullWhenUsingMatcherAndPrintingFromRoot() {
    printer.printComponents(null, new NameMatcher("button1"), windowOne);
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMatcherIsNull() {
    ComponentMatcher matcher = null;
    printer.printComponents(out, matcher);
  }
  
  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMatcherIsNullWhenPrintingFromRoot() {
    ComponentMatcher matcher = null;
    printer.printComponents(out, matcher, windowOne);
  }

  public void shouldPrintAllComponentsThatMatchMatcher() {
    printer.printComponents(out, new NameMatcher("button1"));
    assertThat(out.printed()).containsOnly(format(windowOne.button));
  }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A button");

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return display(new MyWindow());
        }
      });
    }

    private MyWindow() {
      super(BasicComponentPrinterTest.class);
      addComponents(button);
    }

    void buttonName(final String buttonName) {
      execute(new GuiTask() {
        protected void executeInEDT() {
          button.setName(buttonName);
        }
      });
    }
  }
}
