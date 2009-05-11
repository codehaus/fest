/*
 * Created on Oct 31, 2008
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

import java.awt.Point;

import javax.swing.JList;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Objects.areEqual;

/**
 * Understands lookup of the first item in a <code>{@link JList}</code> whose value matches a given one.
 *
 * @author Alex Ruiz
 */
final class JListMatchingItemQuery {

  @RunsInEDT
  static Point centerOfMatchingItemCell(final JList list, final String value, final JListCellReader cellReader) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        int itemIndex = matchingItemIndex(list, value, cellReader);
        return centerOf(cellBounds(list, itemIndex));
      }
    });
  }
  
  @RunsInCurrentThread
  static int matchingItemIndex(JList list, String value, JListCellReader cellReader) {
    int size = list.getModel().getSize();
    for (int i = 0; i < size; i++)
      if (areEqual(value, cellReader.valueAt(list, i))) return i;
    return -1;
  }

  private JListMatchingItemQuery() {}
}
