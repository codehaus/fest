/*
 * Created on Jan 12, 2009
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
package org.fest.swing.core.matcher;

import java.awt.Component;

import org.fest.swing.core.GenericTypeMatcher;

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.quote;

/**
 * Understands a template for matching components by name. Subclasses are free to add other properties to use as search
 * criteria.
 * @param <T> the type of <code>Component</code> supported by this matcher. 
 *
 * @author Alex Ruiz
 */
public abstract class NamedComponentMatcherTemplate<T extends Component> extends GenericTypeMatcher<T> {

  /**
   * Indicates that a property value to use as search criteria has not been set.
   */
  protected static final Object ANY = new Object() {
    @Override public String toString() { return "<Any>"; }
  };

  /** The component name to match. **/
  protected final Object name;

  /**
   * Creates a new </code>{@link NamedComponentMatcherTemplate}</code>.
   * @param supportedType the type supported by this matcher.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  protected NamedComponentMatcherTemplate(Class<T> supportedType) {
    super(supportedType);
    this.name = ANY;
  }

  /**
   * Creates a new </code>{@link NamedComponentMatcherTemplate}</code>.
   * @param supportedType the type supported by this matcher.
   * @param name the component name to match.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  protected NamedComponentMatcherTemplate(Class<T> supportedType, Object name) {
    super(supportedType);
    this.name = name;
  }

  /**
   * Returns the component name to match surrounded by double quotes. If the component name has not been set, it will
   * return <code>{@link #ANY}</code>. This method is commonly used in implementations of <code>toString</code>.
   * @return the component name to match surrounded by double quotes, or <code>{@link #ANY}</code> if the component name
   * has not been set.
   */
  protected final Object quotedName() {
    return quoted(name);
  }
  
  /**
   * Returns the given property value to match surrounded by double quotes. If the property has not been set, it will
   * return <code>{@link #ANY}</code>. This method is commonly used in implementations of <code>toString</code>.
   * @param propertyValue the given property value.
   * @return the given property value to match surrounded by double quotes, or <code>{@link #ANY}</code> if the property
   * value has not been set.
   */
  protected final Object quoted(Object propertyValue) {
    if (ANY.equals(propertyValue)) return ANY;
    return quote(propertyValue);
  }

  /**
   * Indicates whether the given value matches the name in this matcher. It always returns <code>true</code> if this
   * matcher's name is <code>{@link #ANY}</code>.
   * @param actual the actual component name.
   * @return <code>true</code> if this matcher's name is <code>ANY</code> or if both the actual name is equal to the one
   * in this matcher. Otherwise <code>false</code>.
   */
  protected final boolean isNameMatching(String actual) {
    return arePropertyValuesMatching(name, actual);
  }
  
  /**
   * Indicates whether the given value matches the expected value in this matcher. It always returns <code>true</code> 
   * if this matcher's expected value is <code>{@link #ANY}</code>.
   * @param expected the expected value in this matcher.
   * @param actual the actual property value.
   * @return <code>true</code> if this matcher's expected value is <code>ANY</code> or if both the actual value is equal 
   * to the one in this matcher. Otherwise <code>false</code>.
   */
  protected final boolean arePropertyValuesMatching(Object expected, Object actual) {
    if (ANY.equals(expected)) return true;
    return areEqual(expected, actual);
  }
  
}
