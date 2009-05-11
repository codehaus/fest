/*
 * Created on Jul 30, 2007
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
package org.fest.swing.finder;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands a <code>{@link JFrame}</code> that simulates a login window.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class LauncherWindow extends TestWindow {

  private static final long serialVersionUID = 1L;

  private static final int DEFAULT_DELAY = 10000;

  private int windowLaunchDelay = DEFAULT_DELAY;
  private int dialogLaunchDelay = DEFAULT_DELAY;

  @RunsInEDT
  public static LauncherWindow createNew(final Class<?> testClass) {
    return execute(new GuiQuery<LauncherWindow>() {
      protected LauncherWindow executeInEDT() {
        return new LauncherWindow(testClass);
      }
    });
  }
  
  private LauncherWindow(Class<?> testClass) {
    super(testClass);
    add(windowLaunchButton());
    add(dialogLaunchButton());
  }

  @RunsInCurrentThread
  private JButton windowLaunchButton() {
    JButton button = new JButton("Launch Frame");
    button.setName("launchFrame");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        setVisible(false);
        launchWindow();
      }
    });
    return button;
  }
  
  @RunsInCurrentThread
  void launchWindow() {
    start(new Timer(windowLaunchDelay, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showWindow(new WindowToLaunch());
      }
    }));
  }

  @RunsInCurrentThread
  private JButton dialogLaunchButton() {
    JButton button = new JButton("Launch Dialog");
    button.setName("launchDialog");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        setVisible(false);
        launchDialog();
      }
    });
    return button;
  }
  
  @RunsInCurrentThread
  private void launchDialog() {
    start(new Timer(dialogLaunchDelay, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showWindow(new DialogToLaunch());
      }
    }));
  }
  
  @RunsInCurrentThread
  private void start(Timer timer) {
    timer.setRepeats(false);
    timer.start();
  }

  @RunsInCurrentThread
  private void showWindow(Window window) {
    window.pack();
    window.setVisible(true);
  }
  
  public void windowLaunchDelay(int newWindowLaunchDelay) {
    this.windowLaunchDelay = newWindowLaunchDelay;
  }

  public void dialogLaunchDelay(int newDialogLaunchDelay) {
    this.dialogLaunchDelay = newDialogLaunchDelay;
  }
  
  public static class WindowToLaunch extends JFrame {
    private static final long serialVersionUID = 1L;

    public WindowToLaunch() {
      setName("frame");
    }
  }
  
  public static class DialogToLaunch extends JDialog {
    private static final long serialVersionUID = 1L;

    public DialogToLaunch() {
      setName("dialog");
    }
  }
}
