/*
 * Created on Dec 22, 2009
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
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.easymock.EasyMock.expectLastCall;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for <code>{@link JTableFixture#requireSelectedRows(int...)}</code>.
 *
 * @author Alex Ruiz
 */
public class JTableFixture_requireSelectedRows_Test extends JTableFixture_TestCase {

  @Test
  public void should_require_selected_rows() {
    final int[] rows = { 6, 8 };
    new EasyMockTemplate(driver()) {
      @Override protected void expectations() {
        driver().requireSelectedRows(target(), rows);
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThatReturnsSelf(fixture().requireSelectedRows(rows));
      }
    }.run();
  }
}
