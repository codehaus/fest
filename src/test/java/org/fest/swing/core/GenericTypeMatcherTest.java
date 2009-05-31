/*
 * Created on Aug 6, 2007
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
package org.fest.swing.core;

import javax.swing.JButton;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JLabels.label;

/**
 * Tests for <code>{@link GenericTypeMatcher}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class GenericTypeMatcherTest {
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfSupportedTypeIsNull() {
    new GenericTypeMatcher<JButton>(null) {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
  }
  
  public void shouldReturnTrueIfTypeAndSearchCriteriaMatch() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(button().createNew())).isTrue();
  }
  
  public void shouldReturnFalseIfTypeMatchesButNotSearchCriteria() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(button().createNew())).isFalse();
  }

  public void shouldReturnFalseIfSearchCriteriaMatchesButNotType() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(label().createNew())).isFalse();
  }

  public void shouldReturnFalseIfSearchCriteriaAndTypeNotMatching() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(label().createNew())).isFalse();
  }

  public void shouldReturnFalseIfComponentIsNull() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(null)).isFalse();
  }

  public void shouldReturnFalseIfComponentIsNotShowing() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class, true) {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(button().createNew())).isFalse();
  }
}
