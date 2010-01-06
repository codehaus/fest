/*
 * Created on Dec 4, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JProgressBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;

/**
 * Understands an action, executed in the event dispatch thread, that returns the minimum and maximum values of a
 * <code>{@link JProgressBar}</code>.
 *
 * @author Alex Ruiz
 */
final class JProgressBarMinimumAndMaximumQuery {

  @RunsInEDT
  static Pair<Integer, Integer> minimumAndMaximumOf(final JProgressBar progressBar) {
    return execute(new GuiQuery<Pair<Integer, Integer>>() {
      protected Pair<Integer, Integer> executeInEDT() {
        return new Pair<Integer, Integer>(progressBar.getMinimum(), progressBar.getMaximum());
      }
    });
  }

  private JProgressBarMinimumAndMaximumQuery() {}
}
