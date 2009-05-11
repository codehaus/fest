/*
 * Created on Jul 14, 2008
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

import java.applet.Applet;
import java.applet.AppletStub;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestApplet;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Tests for <code>{@link AppletViewer}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class AppletViewerTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAppletIsNull() {
    newAppletViewer(null);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAppletStubIsNull() {
    newAppletViewer(TestApplet.createNew(), (AppletStub)null);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfParameterMapIsNull() {
    Map<String, String> parameters = null;
    newAppletViewer(TestApplet.createNew(), parameters);
  }

  @RunsInEDT
  private static AppletViewer newAppletViewer(final Applet applet) {
    return execute(new GuiQuery<AppletViewer>() {
      protected AppletViewer executeInEDT() {
        return new AppletViewer(applet);
      }
    });
  }

  @RunsInEDT
  private static AppletViewer newAppletViewer(final Applet applet, final Map<String, String> parameters) {
    return execute(new GuiQuery<AppletViewer>() {
      protected AppletViewer executeInEDT() {
        return new AppletViewer(applet, parameters);
      }
    });
  }

  @RunsInEDT
  private static AppletViewer newAppletViewer(final Applet applet, final AppletStub appletStub) {
    return execute(new GuiQuery<AppletViewer>() {
      protected AppletViewer executeInEDT() {
        return new AppletViewer(applet, appletStub);
      }
    });
  }
}
