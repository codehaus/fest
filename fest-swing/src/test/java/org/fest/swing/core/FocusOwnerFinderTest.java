/*
 * Created on Apr 1, 2008
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
package org.fest.swing.core;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.Component;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.builder.JLabels;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link FocusOwnerFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class FocusOwnerFinderTest {

  private FocusOwnerFinderStrategy strategy1;
  private FocusOwnerFinderStrategy strategy2;

  @BeforeMethod public void setUp() {
    strategy1 = createMock(FocusOwnerFinderStrategy.class);
    strategy2 = createMock(FocusOwnerFinderStrategy.class);
    FocusOwnerFinder.replaceStrategiesWith(strategy1, strategy2);
  }

  @AfterMethod public void tearDown() {
    FocusOwnerFinder.initializeStrategies();
  }

  public void shouldUseStrategiesUntilFocusOwnerFound() {
    final Component focusOwner = JLabels.label().createNew();
    new EasyMockTemplate(strategy1, strategy2) {
      protected void expectations() {
        expect(strategy1.focusOwner()).andThrow(new RuntimeException());
        expect(strategy2.focusOwner()).andReturn(focusOwner);
      }

      protected void codeToTest() {
        assertThat(FocusOwnerFinder.focusOwner()).isSameAs(focusOwner);
      }
    }.run();
  }

  public void shouldReturnNullIfStrategiesFailToFindFocusOwner() {
    new EasyMockTemplate(strategy1, strategy2) {
      protected void expectations() {
        expect(strategy1.focusOwner()).andThrow(new RuntimeException());
        expect(strategy2.focusOwner()).andThrow(new RuntimeException());
      }

      protected void codeToTest() {
        assertThat(FocusOwnerFinder.focusOwner()).isNull();
      }
    }.run();
  }
}
