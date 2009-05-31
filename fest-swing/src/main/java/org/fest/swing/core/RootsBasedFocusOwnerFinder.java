/*
 * Created on May 31, 2009
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
package org.fest.swing.core;

import java.awt.*;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.hierarchy.ExistingHierarchy;

/**
 * Understands how to find the focus owner using the roots of a component hierarchy.
 *
 * @author Alex Ruiz
 */
class RootsBasedFocusOwnerFinder implements FocusOwnerFinderStrategy {

  @RunsInCurrentThread
  public Component focusOwner() {
    Component focus = null;
    for (Container c : new ExistingHierarchy().roots()) {
      if (!(c instanceof Window)) continue;
      Window w = (Window) c;
      if (w.isShowing() && (focus = focusOwner(w)) != null) break;
    }
    return focus;
  }

  @RunsInCurrentThread
  private Component focusOwner(Window w) {
    Component focus = w.getFocusOwner();
    if (focus != null) return focus;
    for (Window owndedWindow : w.getOwnedWindows())
      if ((focus = owndedWindow.getFocusOwner()) != null) return focus;
    return focus;
  }
}
