/*
 * Created on Jul 23, 2009
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

import java.awt.Point;

import javax.swing.JTree;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Base class for tests for <code>{@link JTreeDriver}</code> that double-click a cell.
 *
 * @author Alex Ruiz
 */
public abstract class JTreeDriverDoubleClickCellTestCase extends JTreeDriverTestCase {

  @RunsInEDT
  final int rowAt(Point p) {
    return rowAtPoint(tree(), p);
  }
  
  @RunsInEDT
  private static int rowAtPoint(final JTree tree, final Point p) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tree.getRowForLocation(p.x, p.y);
      }
    });
  }
}