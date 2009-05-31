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

import javax.swing.Action;
import javax.swing.ActionMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.exception.ActionFailedException;

import static javax.swing.Action.NAME;
import static org.easymock.EasyMock.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

/**
 * Tests for <code>{@link Actions}</code>.
 *
 * @author Alex Ruiz
 */
public class ActionsTest {

  private ActionMap map;
  private Action action;
  
  @BeforeMethod public void setUp() {
    map = new ActionMap();
    action = createMock(Action.class);
    map.put("key", action);
  }
  
  @Test public void shouldReturnGivenNameIfEqualToKey() {
    Object found = Actions.findActionKey("key", map);
    assertThat(found).isEqualTo("key");
  }
  
  @Test public void shouldReturnKeyIfActionNameEqualsGivenName() {
    new EasyMockTemplate(action) {
      protected void expectations() {
        expect(action.getValue(NAME)).andReturn("name");
      }

      protected void codeToTest() {
        Object found = Actions.findActionKey("name", map);
        assertThat(found).isEqualTo("key");
      }
    }.run();
  }
  
  @Test public void shouldThrowErrorIfKeyNotFound() {
    try {
      new EasyMockTemplate(action) {
        protected void expectations() {
          expect(action.getValue(NAME)).andReturn("name");
        }

        protected void codeToTest() {
          Object found = Actions.findActionKey("someName", map);
          assertThat(found).isEqualTo("key");
        }
      }.run();
      failWhenExpectingException();
    } catch (ActionFailedException expected) {
      assertThat(expected.getMessage()).isEqualTo("The action 'someName' is not available, available actions:['key']");
    }
  }
}
