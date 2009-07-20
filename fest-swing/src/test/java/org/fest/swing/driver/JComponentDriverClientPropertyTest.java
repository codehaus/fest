/*
 * Created on Jul 19, 2009
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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

import javax.swing.JComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JComponentDriver#clientProperty(JComponent, Object)}</code>
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JComponentDriverClientPropertyTest extends JComponentDriverTestCase {

  public void shouldReturnClientPropertyUnderGivenKey() {
    putClientProperty(button(), "name", "Leia");
    assertThat(driver().clientProperty(button(), "name")).isEqualTo("Leia");
  }

  public void shouldReturnNullIfClientPropertyNotFound() {
    assertThat(driver().clientProperty(button(), "name")).isNull();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfKeyIsNull() {
    driver().clientProperty(button(), null);
  }

  @RunsInEDT
  private static void putClientProperty(final JComponent c, final Object key, final Object value) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        c.putClientProperty(key, value);
      }
    });
  }
}
