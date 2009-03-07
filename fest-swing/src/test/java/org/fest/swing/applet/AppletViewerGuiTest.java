/*
 * Created on Jul 10, 2008
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
package org.fest.swing.applet;

import java.awt.Container;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.test.swing.TestApplet;
import org.fest.swing.timing.Condition;
import org.fest.swing.util.Pair;

import static javax.swing.SwingUtilities.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.timing.Pause.pause;

/**
 * Tests for <code>{@link AppletViewer}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class AppletViewerGuiTest {

  private TestApplet applet;
  private FrameFixture fixture;
  private AppletViewer viewer;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    Pair<TestApplet, AppletViewer> appletAndViewer = appletAndViewer();
    applet = appletAndViewer.i;
    viewer = appletAndViewer.ii;
    fixture = new FrameFixture(viewer);
    fixture.show();
    assertThatIsInitializedAndStarted(applet);
  }

  @RunsInEDT
  private static Pair<TestApplet, AppletViewer> appletAndViewer() {
    return execute(new GuiQuery<Pair<TestApplet, AppletViewer>>() {
      protected Pair<TestApplet, AppletViewer> executeInEDT() {
        TestApplet applet = new TestApplet();
        return new Pair<TestApplet, AppletViewer>(applet, new AppletViewer(applet));
      }
    });
  }

  @RunsInEDT
  private static void assertThatIsInitializedAndStarted(final TestApplet applet) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        assertThat(applet.initialized()).isTrue();
        assertThat(applet.started()).isTrue();
      }
    });
  }

  @AfterMethod public void tearDown() {
    unloadAppletIn(viewer);
    assertThatIsStoppedAndDestroyed(applet);
    fixture.cleanUp();
  }

  @RunsInEDT
  private static void unloadAppletIn(final AppletViewer viewer) {
    invokeLater(new Runnable() {
      public void run() {
        viewer.unloadApplet();
      }
    });
    pause(new Condition("applet is unloaded") {
      public boolean test() {
        return !viewer.appletLoaded();
      }
    });
  }

  private static void assertThatIsStoppedAndDestroyed(final TestApplet applet) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        assertThat(applet.stopped()).isTrue();
        assertThat(applet.destroyed()).isTrue();
      }
    });
  }

  public void shouldLoadApplet() {
    assertThatIsShowingAndLoaded(applet, viewer);
    Container ancestor = getAncestorOfClass(AppletViewer.class, applet);
    assertThat(ancestor).isSameAs(viewer);
    fixture.label("status").requireText("Applet loaded");
    assertThat(viewer.applet()).isSameAs(applet);
    assertThat(viewer.stub()).isInstanceOf(BasicAppletStub.class);
  }

  public void shouldReloadApplet() {
    viewer.reloadApplet();
    assertThatIsShowingAndLoaded(applet, viewer);
  }

  private static void assertThatIsShowingAndLoaded(final TestApplet applet, final AppletViewer viewer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        assertThat(applet.isShowing()).isTrue();
        assertThat(viewer.appletLoaded()).isTrue();
      }
    });
  }
}
