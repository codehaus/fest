/*
 * Created on Nov 19, 2007
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
package org.fest.swing.junit.runner;

import java.lang.reflect.Method;

import org.fest.swing.annotation.GUITestFinder;
import org.junit.runner.Description;

/**
 * Understands the description of a test class.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestDescription extends Description {
  
  private final Class<?> testClass;
  private final Method testMethod;
  private final boolean isGUITest;
  
  /**
   * Creates a new </code>{@link GUITestDescription}</code>.
   * @param testClass the test class.
   * @param testMethod the test method.
   */
  @SuppressWarnings("deprecation") 
  public GUITestDescription(Class<?> testClass, Method testMethod) {
    super(String.format("%s(%s)", testMethod.getName(), testClass.getName()));
    this.testClass = testClass;
    this.testMethod = testMethod;
    isGUITest = GUITestFinder.isGUITest(testClass, testMethod);
  }

  public final Class<?> testClass() { return testClass; }
  public final Method testMethod() { return testMethod; }
  public final boolean isGUITest() { return isGUITest; }

}
