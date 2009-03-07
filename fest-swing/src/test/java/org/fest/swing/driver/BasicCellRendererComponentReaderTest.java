/*
 * Created on Oct 22, 2008
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

import javax.swing.JButton;
import javax.swing.JLabel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JLabels.label;

/**
 * Tests for <code>{@link BasicCellRendererReader}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class BasicCellRendererComponentReaderTest {

  private BasicCellRendererReader reader;

  @BeforeClass public void setUp() {
    reader = new BasicCellRendererReader();
  }

  public void shouldReturnValueFromComponentIfItIsJLabel() {
    JLabel label = label().withText("Hello").createNew();
    assertThat(reader.valueFrom(label)).isEqualTo("Hello");
  }

  public void shouldReturnNullValueFromComponentIfItIsNotJLabel() {
    JButton button = button().withText("Hello").createNew();
    assertThat(reader.valueFrom(button)).isNull();
  }
}
