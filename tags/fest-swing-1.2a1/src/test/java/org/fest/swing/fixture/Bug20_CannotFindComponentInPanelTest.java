/*
 * Created on Aug 28, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=20&can=2&q=" target="_blank">issue 20</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups =  { GUI, BUG })
public class Bug20_CannotFindComponentInPanelTest {
  
  private FrameFixture frame;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeClass public void setUp() {
    frame = new FrameFixture(MyWindow.createNew());
    frame.show(new Dimension(400, 200));
  }
  
  @AfterClass public void tearDown() {
    frame.cleanUp();
  }
  
  public void shouldFindList() {
    frame.list("list").requireVisible();
  }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private final JPanel panel = new JPanel();
    private final JList list = new JList();
    
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
     
    private MyWindow() {
      super(Bug20_CannotFindComponentInPanelTest.class);
      add(panel);
      panel.add(new JScrollPane(list));
      list.setName("list");
    }
  }
}
