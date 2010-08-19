/*
 * Created on May 31, 2009
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
package org.fest.swing.core;

import java.awt.Container;
import java.util.Collection;

import org.fest.swing.hierarchy.ExistingHierarchy;

/**
 * Obtains all the root containers of an existing component hierarchy.
 *
 * @author Alex Ruiz
 */
class HierarchyRootsSource {

  Container[] existingHierarchyRoots() {
    Collection<? extends Container> roots = new ExistingHierarchy().roots();
    return roots.toArray(new Container[roots.size()]);
  }
}
