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

import static java.util.Collections.sort;
import static org.fest.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.fest.swing.driver.JListCellCenterQuery.cellCenter;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.util.Strings.areEqualOrMatch;
import static org.fest.swing.util.Strings.match;

import java.awt.Point;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.JList;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.edt.GuiQuery;

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
        return cellCenter(list, cellBounds(list, itemIndex));
      }
    });
  }

  @RunsInCurrentThread
  static int matchingItemIndex(JList list, String value, JListCellReader cellReader) {
    int size = list.getModel().getSize();
    for (int i = 0; i < size; i++)
      if (areEqualOrMatch(value, cellReader.valueAt(list, i))) return i;
    return -1;
  }

  @RunsInCurrentThread
  static int matchingItemIndex(JList list, Pattern pattern, JListCellReader cellReader) {
    int size = list.getModel().getSize();
    for (int i = 0; i < size; i++)
      if (match(pattern, cellReader.valueAt(list, i))) return i;
    return -1;
  }

  @RunsInEDT
  static List<Integer> matchingItemIndices(final JList list, final String[] values, final JListCellReader cellReader) {
    return execute(new GuiQuery<List<Integer>>() {
      protected List<Integer> executeInEDT() {
        Set<Integer> indices = new HashSet<Integer>();
        int size = list.getModel().getSize();
        for (int i = 0; i < size; i++)
          if (matches(cellReader.valueAt(list, i), values)) indices.add(i);
        List<Integer> indexList = new ArrayList<Integer>(indices);
        sort(indexList);
        return indexList;
      }
    });
  }

  private static boolean matches(String value, String[] values) {
    for (String val : values)
      if (areEqualOrMatch(val, value)) return true;
    return false;
  }

  @RunsInEDT
  static List<Integer> matchingItemIndices(final JList list, final Pattern[] patterns, final JListCellReader cellReader) {
    return execute(new GuiQuery<List<Integer>>() {
      protected List<Integer> executeInEDT() {
        Set<Integer> indices = new HashSet<Integer>();
        int size = list.getModel().getSize();
        for (int i = 0; i < size; i++)
          if (matches(cellReader.valueAt(list, i), patterns)) indices.add(i);
        List<Integer> indexList = new ArrayList<Integer>(indices);
        sort(indexList);
        return indexList;
      }
    });
  }

  private static boolean matches(String value, Pattern[] patterns) {
    for (Pattern p : patterns)
      if (match(p, value)) return true;
    return false;
  }

  private JListMatchingItemQuery() {}
}
