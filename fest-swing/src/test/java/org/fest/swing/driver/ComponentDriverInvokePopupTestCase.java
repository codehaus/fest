/*
 * Created on Jul 20, 2009
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Base class for tests for <code>{@link ComponentDriver}</code> that verify that a <code>{@link JPopupMenu}</code>
 * is invoked by a <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class ComponentDriverInvokePopupTestCase extends ComponentDriverTestCase {

  private JPopupMenu popupMenu;
  
  @RunsInEDT
  @Override void beforeShowingWindow() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("Hello"));
        textField().setComponentPopupMenu(popupMenu);
      }
    });
  }
  
  final JPopupMenu popupMenu() { return popupMenu; }
}