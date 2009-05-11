/*
 * Created on Nov 1, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JDialogs.dialog;

/**
 * Tests for <code>{@link WindowFilter}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowFilterTest {

  private Map<Component, Boolean> ignored;
  private Map<Component, Boolean> implictlyIgnored;

  private WindowFilter filter;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    filter = new WindowFilter(new ParentFinder(), new ChildrenFinder());
    ignored = filter.ignored;
    ignored.clear();
    implictlyIgnored = filter.implicitlyIgnored;
    implictlyIgnored.clear();
  }

  @Test public void shouldFilterComponent() {
    Component c = button().createNew();
    implictlyIgnored.put(c, true);
    ignore(filter, c);
    assertThat(ignored.keySet()).containsOnly(c);
    assertThat(implictlyIgnored.size()).isZero();
  }

  @Test public void shouldFilterOwnedWindows() {
    TestWindow window = TestWindow.createNewWindow(getClass());
    TestDialog dialog = TestDialog.createNewDialog(window);
    implictlyIgnored.put(window, true);
    implictlyIgnored.put(dialog, true);
    ignore(filter, window);
    assertThat(ignored.keySet()).containsOnly(window, dialog);
    assertThat(implictlyIgnored.size()).isZero();
  }

  @Test public void shouldFilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = dialog().createNew();
    implictlyIgnored.put(dialog, true);
    ignore(filter, dialog.getOwner());
    assertThat(ignored.keySet()).containsOnly(dialog);
    assertThat(implictlyIgnored.size()).isZero();
  }

  private static void ignore(final WindowFilter filter, final Component c) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        filter.ignore(c);
      }
    });
  }

  @Test public void shouldUnfilter() {
    Component c = button().createNew();
    ignored.put(c, true);
    implictlyIgnored.put(c, true);
    recognize(filter, c);
    assertThat(ignored.size()).isZero();
    assertThat(implictlyIgnored.size()).isZero();
  }

  @Test(dependsOnMethods = "shouldFilterChildrenOfSharedInvisibleFrame")
  public void shouldUnfilterChildrenOfSharedInvisibleFrame() {
    JDialog dialog = dialog().createNew();
    ignored.put(dialog, true);
    implictlyIgnored.put(dialog, true);
    recognize(filter, dialog.getOwner());
    assertThat(ignored.size()).isZero();
    assertThat(implictlyIgnored.size()).isZero();
  }

  private static void recognize(final WindowFilter filter, final Component c) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        filter.recognize(c);
      }
    });
  }

  @Test public void shouldReturnTrueIfObjectIsFiltered() {
    Component c = button().createNew();
    ignored.put(c, true);
    assertThat(isComponentIgnored(filter, c)).isTrue();
  }

  @Test public void shouldReturnTrueIfWindowParentIsFiltered() {
    MyWindow window = MyWindow.createNew();
    Component c = window.button;
    ignored.put(window, true);
    assertThat(isComponentIgnored(filter, c)).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() throws Throwable {
          return new MyWindow();
        }
      });
    }

    final JButton button = new JButton("Press Me");

    private MyWindow() {
      super(WindowFilterTest.class);
      addComponents(button);
    }
  }

  @Test public void shouldReturnTrueIfParentOfWindowIsFiltered() {
    TestWindow window = TestWindow.createNewWindow(getClass());
    TestDialog dialog = TestDialog.createNewDialog(window);
    ignored.put(window, true);
    assertThat(isComponentIgnored(filter, dialog)).isTrue();
  }

  @Test public void shouldReturnNotFilteredIfGivenComponentIsNull() {
    assertThat(isComponentIgnored(filter, null)).isFalse();
  }

  private static boolean isComponentIgnored(final WindowFilter filter, final Component c) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return filter.isIgnored(c);
      }
    });
  }

  @Test public void shouldImplicitFilter() {
    Component c = button().createNew();
    filter.implicitlyIgnore(c);
    assertThat(implictlyIgnored.keySet()).containsOnly(c);
  }

  @Test public void shouldReturnTrueIfImplicitFiltered() {
    Component c = button().createNew();
    implictlyIgnored.put(c, true);
    assertThat(filter.isImplicitlyIgnored(c)).isTrue();
  }

  @Test public void shouldReturnFalseIfNotImplicitFiltered() {
    Component c = button().createNew();
    assertThat(filter.isImplicitlyIgnored(c)).isFalse();
  }
}
