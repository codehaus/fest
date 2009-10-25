/*
 * Created on Feb 24, 2008
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

import static org.easymock.classextension.EasyMock.createMock;

import javax.swing.JList;

import org.fest.swing.core.Robot;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link JListDriver#selectItems(JList, int[])}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_selectItemsByIndex_withInvalidInput_Test extends EDTSafeTestCase {

  private JListDriver driver;
  private JList list;

  @Before public void setUp() {
    driver = new JListDriver(createMock(Robot.class));
    list = createMock(JList.class);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_of_indices_is_null() {
    int[] indices = null;
    driver.selectItems(list, indices);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_array_of_indices_is_empty() {
    int[] indices = new int[0];
    driver.selectItems(list, indices);
  }


}