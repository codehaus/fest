/*
 * Created on Jul 16, 2009
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

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.core.Robot;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#separator(String)}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JTreeDriverCellReaderTest {

  private JTreeDriver driver;

  @BeforeMethod public void setUp() {
    driver = new JTreeDriver(createMock(Robot.class));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  public void shouldSetCellReader() {
    JTreeCellReader cellReader = createMock(JTreeCellReader.class);
    driver.cellReader(cellReader);
    assertThat(driver.cellReader()).isSameAs(cellReader);
  }

}
