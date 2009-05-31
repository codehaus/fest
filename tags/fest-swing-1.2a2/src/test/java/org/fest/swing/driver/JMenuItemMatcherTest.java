/*
 * Created on Apr 17, 2008
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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link JMenuItemMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI) 
public class JMenuItemMatcherTest {

  private ComponentFinder finder;
  private MyWindow window;
  private JMenuItemMatcher matcher;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    finder = finderWithNewAwtHierarchy();
    window = MyWindow.createNew();
    window.display();
    matcher = new JMenuItemMatcher("Logout", "Logout");
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  public void shouldFindSecondLogoutMenu() {
    Component found = finder.find(window, matcher);
    assertThat(found).isSameAs(window.logoutMenuItem);
  }
  
  public void shouldShowPathInToString() {
    assertThat(matcher.toString()).contains("label='Logout|Logout'");
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JMenuItem logoutMenuItem = new JMenuItem("Logout"); 
    
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
    
    private MyWindow() {
      super(JMenuItemMatcherTest.class);
      JMenuBar menuBar = new JMenuBar();
      JMenu logoutMenu = new JMenu("Logout");
      logoutMenu.add(logoutMenuItem);
      logoutMenu.add(new JMenuItem("Exit"));
      menuBar.add(logoutMenu);
      setJMenuBar(menuBar);
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
