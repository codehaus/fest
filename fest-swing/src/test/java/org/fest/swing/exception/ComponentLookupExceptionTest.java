/*
 * Created on May 1, 2009
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
package org.fest.swing.exception;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLabels.label;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test for <code>{@link ComponentLookupException}</code>
 *
 * @author Alex Ruiz
 */
public class ComponentLookupExceptionTest {

  @Test
  public void shouldReturnCopyOfFoundComponents() {
    List<Component> found = new ArrayList<Component>();
    found.add(label().createNew());
    found.add(textField().createNew());
    ComponentLookupException e = new ComponentLookupException("Hello", found);
    assertThat(e.found()).isNotSameAs(found).containsOnly(found.toArray());
  }

}
