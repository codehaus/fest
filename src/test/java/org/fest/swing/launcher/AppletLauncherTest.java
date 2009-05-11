/*
 * Created on Jul 15, 2008
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
package org.fest.swing.launcher;

import java.applet.Applet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestApplet;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link AppletLauncher}</code>.
 *
 * @author Yvonne Wang
 */
@Test public class AppletLauncherTest {

  private TestApplet applet;
  private AppletViewer viewer;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @AfterMethod public void tearDown() {
    try {
      disposeViewer();
      disposeApplet();
    } finally {
      ScreenLock screenLock = ScreenLock.instance();
      if (screenLock.acquiredBy(this)) screenLock.release(this);
    }
  }

  private void disposeViewer() {
    if (viewer == null) return;
    viewer.setVisible(false);
    viewer.dispose();
  }

  private void disposeApplet() {
    if (applet == null) return;
    applet.stop();
    applet.destroy();
  }

  @Test(groups = GUI) public void shouldLaunchGivenApplet() {
    ScreenLock.instance().acquire(this);
    applet = TestApplet.createNew();
    viewer = AppletLauncher.applet(applet).start();
    assertAppletWasLaunched();
    assertThat(viewer.applet()).isSameAs(applet);
  }

  @Test(groups = GUI) public void shouldInstantiateAndLaunchApplet() {
    ScreenLock.instance().acquire(this);
    viewer = AppletLauncher.applet(TestApplet.class).start();
    assertAppletWasLaunched();
  }

  @Test(groups = GUI) public void shouldLoadAndLaunchApplet() {
    ScreenLock.instance().acquire(this);
    viewer = AppletLauncher.applet(TestApplet.class.getName()).start();
    assertAppletWasLaunched();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAppletIsNull() {
    AppletLauncher.applet((Applet)null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAppletTypeIsNull() {
    Class<? extends Applet> type = null;
    AppletLauncher.applet(type);
  }

  public void shouldThrowErrorIfAppletCannotBeInstantiated() {
    try {
      AppletLauncher.applet(AnApplet.class);
      failWhenExpectingException();
    } catch (UnexpectedException e) {
      assertThat(e.getMessage()).contains("Unable to create a new instance");
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAppletTypeNameIsNull() {
    String type = null;
    AppletLauncher.applet(type);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfAppletTypeNameIsEmpty() {
    AppletLauncher.applet("");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfAppletTypeNameIsNotApplet() {
    AppletLauncher.applet(JButton.class.getName());
  }

  public void shouldThrowErrorIfAppletTypeIsNotType() {
    try {
      AppletLauncher.applet("Hello");
      failWhenExpectingException();
    } catch (UnexpectedException e) {
      assertThat(e.getMessage()).contains("Unable to load class Hello");
    }
  }

  public void shouldThrowErrorIfAppletCannotBeInstantiatedFromTypeName() {
    try {
      AppletLauncher.applet(AnApplet.class.getName());
      failWhenExpectingException();
    } catch (UnexpectedException e) {
      assertThat(e.getMessage()).contains("Unable to create a new instance");
    }
  }

  private static class AnApplet extends Applet {
    private static final long serialVersionUID = 1L;

    AnApplet(String name) {}
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfParameterMapIsNull() {
    Map<String, String> parameters = null;
    AppletLauncher.applet(TestApplet.createNew()).withParameters(parameters);
  }

  @Test(groups = GUI) public void shouldSetParametersInMap() {
    ScreenLock.instance().acquire(this);
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("bgcolor", "blue");
    parameters.put("color", "red");
    applet = TestApplet.createNew();
    viewer = AppletLauncher.applet(applet).withParameters(parameters).start();
    assertAppletWasLaunched();
    assertThat(applet.getParameter("bgcolor")).isEqualTo("blue");
    assertThat(applet.getParameter("color")).isEqualTo("red");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfParameterArrayIsNull() {
    AppletParameter[] parameters = null;
    AppletLauncher.applet(TestApplet.createNew()).withParameters(parameters);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfParameterInArrayIsNull() {
    AppletParameter[] parameters = new AppletParameter[2];
    parameters[0] = AppletParameter.name("bgcolor").value("blue");
    parameters[1] = null;
    AppletLauncher.applet(TestApplet.createNew()).withParameters(parameters);
  }

  @Test(groups = GUI) public void shouldSetParametersInArray() {
    ScreenLock.instance().acquire(this);
    applet = TestApplet.createNew();
    viewer = AppletLauncher.applet(applet).withParameters(
        AppletParameter.name("bgcolor").value("blue"),
        AppletParameter.name("color").value("red")
    ).start();
    assertAppletWasLaunched();
    assertThat(applet.getParameter("bgcolor")).isEqualTo("blue");
    assertThat(applet.getParameter("color")).isEqualTo("red");
  }

  private void assertAppletWasLaunched() {
    assertThat(isShowing(viewer)).isTrue();
    assertThat(viewer.applet()).isInstanceOf(TestApplet.class);
  }
}
