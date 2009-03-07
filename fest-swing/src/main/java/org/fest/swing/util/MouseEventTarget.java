/*
 * Created on Mar 29, 2008
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
package org.fest.swing.util;

import java.awt.Component;
import java.awt.Point;

/**
 * Understands the target of a <code>{@link java.awt.event.MouseEvent}</code>.
 *
 * @author Alex Ruiz
 */
public class MouseEventTarget {

  /** The source <code>{@link Component}</code> */
  public final Component source;
  
  /** The x,y position of the event relative to the source component. */
  public final Point position;

  public MouseEventTarget(Component source, Point position) {
    this.source = source;
    this.position = position;
  }
}
