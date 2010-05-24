/*
 * Created on May 24, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.javafx.format;

import static org.fest.util.Strings.quote;

import java.util.Collection;
import javafx.scene.Node;

/**
 * Understands a builder of formatted properties.
 *
 * @author Alex Ruiz
 */
class PropertyBuilder {

  private final StringBuilder buffer = new StringBuilder();

  PropertyBuilder(Node n) {
    buffer.append(n.getClass().getName()).append("[");
  }

  void add(String propertyName, Object propertyValue) {
    buffer.append(propertyName).append("=").append(quote(propertyValue)).append(", ");
  }

  void add(Collection<String> properties) {
    for(String property : properties) {
      buffer.append(property).append(", ");
    }
  }

  String value() {
    buffer.append("]");
    return buffer.toString();
  }
}
