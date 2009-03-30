/*
 * Created on Mar 30, 2009
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
package org.fest.assertions;

/**
 * Understands a finite increment in a variable.
 *
 * @author Alex Ruiz
 */
public final class Delta {

  /**
   * Creates a new <code>{@link Delta}</code>.
   * @param value the value of the delta.
   * @return the created <code>Delta</code>.
   */
  public static Delta delta(double value) {
    return new Delta(value);
  }

  private final double value;

  private Delta(double value) {
    this.value = value;
  }

  /**
   * Returns the value of this delta.
   * @return the value of this delta.
   */
  public double value() { return value; }
}
