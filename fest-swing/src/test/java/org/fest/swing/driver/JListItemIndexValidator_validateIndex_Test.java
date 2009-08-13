/*
 * Created on Nov 3, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.assertions.Assertions;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <code>{@link JListItemIndexValidator#validateIndex(JList, int)}</code>.
 *
 * @author Alex Ruiz
 */
public class JListItemIndexValidator_validateIndex_Test extends RobotBasedTestCase {

  private JList list;

  @Override protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    list = window.list;
  }
  
  @Test
  public void should_pass_if_index_is_valid() {
    JListItemIndexValidator.validateIndex(list, 0);
  }
  
  @Test
  public void should_throw_error_if_index_is_negative() {
    try {
      JListItemIndexValidator.validateIndex(list, -1);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("Item index (-1) should be between [0] and [2] (inclusive)");
    }
  }

  @Test
  public void should_throw_error_if_index_is_greater_than_index_of_last_item() {
    try {
      JListItemIndexValidator.validateIndex(list, 3);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("Item index (3) should be between [0] and [2] (inclusive)");
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array("One", "Two", "Three"));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListItemIndexValidator.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
