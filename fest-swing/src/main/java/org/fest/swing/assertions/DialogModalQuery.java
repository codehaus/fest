/*
 * Created on Aug 8, 2008
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
 * Copyright @2008-2010 the original author or authors.
 */
package org.fest.swing.assertions;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Dialog;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Indicates whether a <code>{@link Dialog}</code> is modal or not.
 * @see Dialog#isModal()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 *
 * @since 1.3
 */
final class DialogModalQuery {

  @RunsInEDT
  static boolean isModal(final Dialog dialog) {
    return execute(new GuiQuery<Boolean>() {
      @Override protected Boolean executeInEDT() {
        return dialog.isModal();
      }
    });
  }

  private DialogModalQuery() {}
}
