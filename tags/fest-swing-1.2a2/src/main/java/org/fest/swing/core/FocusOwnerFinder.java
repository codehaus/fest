/*
 * Created on Mar 30, 2008
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

import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.Window;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.hierarchy.ExistingHierarchy;

import static org.fest.reflect.core.Reflection.staticField;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands lookup of a <code>{@link Component}</code> owning the input focus.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FocusOwnerFinder {

  /**
   * Returns the focus owner. This method is executed in the event dispatch thread.
   * @return the focus owner.
   */
  @RunsInEDT
  public static Component inEdtFocusOwner() {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return focusOwner();
      }
    });
  }

  /**
   * Returns the focus owner. <b>Note:</b> this method is <b>not</b> executed in the event dispatch thread. Callers are
   * responsible for calling this method in the event dispatch thread.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @return the focus owner.
   */
  @RunsInCurrentThread
  public static Component focusOwner() {
    try {
      return staticField("focusOwner").ofType(Component.class).in(KeyboardFocusManager.class).get();
    } catch (Exception e) {
      return focusOwnerInHierarchy();
    }
  }

  @RunsInCurrentThread
  static Component focusOwnerInHierarchy() {
    Component focus = null;
    for (Container c : new ExistingHierarchy().roots()) {
      if (!(c instanceof Window)) continue;
      Window w = (Window) c;
      if (w.isShowing() && (focus = focusOwner(w)) != null) break;
    }
    return focus;
  }

  @RunsInCurrentThread
  private static Component focusOwner(Window w) {
    Component focus = w.getFocusOwner();
    if (focus != null) return focus;
    for (Window owndedWindow : w.getOwnedWindows())
      if ((focus = owndedWindow.getFocusOwner()) != null) return focus;
    return focus;
  }

  private FocusOwnerFinder() {}
}
