/*
 * Created on Nov 4, 2008
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
import java.awt.Rectangle;

import javax.swing.JList;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;

import static org.fest.swing.awt.AWT.*;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.fest.swing.driver.JListItemIndexValidator.validateIndex;
import static org.fest.swing.driver.JListMatchingItemQuery.matchingItemIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands actions, executed in the event dispatch thread, that perform scrolling to an element in a
 * <code>{@link JList}</code>.
 * 
 * @author Alex Ruiz
 */
final class JListScrollToItemTask {

  static final Pair<Integer, Point> ITEM_NOT_FOUND = new Pair<Integer, Point>(-1, null);
  
  @RunsInEDT
  // returns the point that the JList was scrolled to.
  static Point scrollToItem(final JList list, final int index) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(list);
        validateIndex(list, index);
        return scrollToItemWithIndex(list, index);
      }
    });
  }

  @RunsInEDT
  // returns the index of first matching element and the point that the JList was scrolled to.
  static Pair<Integer, Point> scrollToItem(final JList list, final String value, final JListCellReader cellReader) {
    return execute(new GuiQuery<Pair<Integer, Point>>() {
      protected Pair<Integer, Point> executeInEDT() {
        validateIsEnabledAndShowing(list);
        int index = matchingItemIndex(list, value, cellReader);
        if (index < 0) return ITEM_NOT_FOUND;
        return new Pair<Integer, Point>(index, scrollToItemWithIndex(list, index));
      }
    });
  }
  
  @RunsInEDT
  // returns the index of first matching element and the point that the JList was scrolled to.
  static Pair<Integer, Point> scrollToItemIfNotSelectedYet(final JList list, final String value,
      final JListCellReader cellReader) {
    return execute(new GuiQuery<Pair<Integer, Point>>() {
      protected Pair<Integer, Point> executeInEDT() {
        validateIsEnabledAndShowing(list);
        int index = matchingItemIndex(list, value, cellReader);
        if (index < 0) return ITEM_NOT_FOUND;
        return new Pair<Integer, Point>(index, scrollToItemWithIndexIfNotSelectedYet(list, index));
      }
    });
  }

  @RunsInEDT
  // returns the point that the JList was scrolled to.
  static Point scrollToItemIfNotSelectedYet(final JList list, final int index) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabledAndShowing(list);
        validateIndex(list, index);
        return scrollToItemWithIndexIfNotSelectedYet(list, index);
      }
    });
  }
  
  @RunsInCurrentThread
  // returns the point that the JList was scrolled to.
  private static Point scrollToItemWithIndexIfNotSelectedYet(final JList list, final int index) {
    if (list.getSelectedIndex() == index) return null;
    return scrollToItemWithIndex(list, index);
  }
  
  @RunsInCurrentThread
  private static Point scrollToItemWithIndex(JList list, int index) {
    Rectangle cellBounds = cellBounds(list, index);
    list.scrollRectToVisible(cellBounds);
    return cellCenter(list, cellBounds);
  }

  /*
   * Sometimes the cell can be a lot longer than the JList (e.g. when a list item has long text and the JList is in 
   * a JScrollPane.) In this case, we return the center of visible rectangle of the JList (issue FEST-65.)
   */
  private static Point cellCenter(JList list, Rectangle cellBounds) {
    Point cellCenter = centerOf(cellBounds);
    Point translatedCellCenter = translate(list, cellCenter.x, cellCenter.y);
    int listVisibleWidth = list.getVisibleRect().width;
    if (translatedCellCenter.x < listVisibleWidth) return cellCenter;
    Point listCenter = centerOfVisibleRect(list);
    return new Point(listCenter.x, cellCenter.y);
  }
  
  private JListScrollToItemTask() {}
}
