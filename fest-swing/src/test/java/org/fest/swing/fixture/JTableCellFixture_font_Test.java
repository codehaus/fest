/*
 * Created on Mar 2, 2008
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
package org.fest.swing.fixture;

import static java.awt.Font.PLAIN;
import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.Font;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for <code>{@link JTableCellFixture#font()}</code>.
 *
 * @author Alex Ruiz
 */
public class JTableCellFixture_font_Test extends JTableCellFixture_withMockTable_TestCase {

  @Test
  public void should_return_font() {
    final FontFixture fontFixture = new FontFixture(new Font("SansSerif", PLAIN, 8));
    new EasyMockTemplate(table) {
      @Override protected void expectations() {
        expect(table.fontAt(cell)).andReturn(fontFixture);
      }

      @Override protected void codeToTest() {
        FontFixture result = fixture.font();
        assertThat(result).isSameAs(fontFixture);
      }
    }.run();
  }
}
