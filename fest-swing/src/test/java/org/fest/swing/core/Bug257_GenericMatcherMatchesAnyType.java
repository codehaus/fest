/*
 * Created on Dec 19, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;

import javax.swing.JDialog;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=257">Bug 257</a>.
 * 
 * @author Juhos Csaba-Zsolt
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug257_GenericMatcherMatchesAnyType {

  private ExtendedGenericTypeMatcher<JDialog> matcher;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    matcher = new ExtendedGenericTypeMatcher<JDialog>(JDialog.class);
  }
  
  public void shouldNotMatchComponentsOtherThanDialog() {
    assertThat(matcher.matches(textField().createNew())).isFalse();
    assertThat(matcher.matches(dialog().createNew())).isTrue();
  }
  
  private static class ExtendedGenericTypeMatcher<T extends Component> extends GenericTypeMatcher<T> {
    ExtendedGenericTypeMatcher(Class<T> supportedType) {
      super(supportedType);
    }

    protected boolean isMatching(final T component) {
      return true;
    }
  }
}
