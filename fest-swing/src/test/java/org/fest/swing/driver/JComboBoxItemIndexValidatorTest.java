/*
 * Created on May 22, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JComboBox;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JComboBoxItemIndexValidator}</code>
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JComboBoxItemIndexValidatorTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Test(dataProvider = "inBoundIndices")
  public void shouldNotThrowErrorIfIndexIsInBounds(int index) {
    JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    JComboBoxItemIndexValidator.validateIndex(comboBox, index);
  }

  @DataProvider(name = "inBoundIndices") public Object[][] inBoundIndices() {
    return new Object[][] { { 0 }, { 1 }, { 2 } };
  }

  public void shouldThrowErrorIfIndexIsNegative() {
    try {
      JComboBoxItemIndexValidator.validateIndex(comboBox().createNew(), -1);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("Item index (-1) should not be less than zero");
    }
  }

  public void shouldThrowErrorIfJComboBoxIsEmpty() {
    try {
      JComboBoxItemIndexValidator.validateIndex(comboBox().createNew(), 0);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("JComboBox is empty");
    }
  }

  public void shouldThrowErrorIfIndexOutOfBounds() {
    try {
      JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
      JComboBoxItemIndexValidator.validateIndex(comboBox, 6);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("Item index (6) should be between [0] and [2] (inclusive)");
    }
  }
}
