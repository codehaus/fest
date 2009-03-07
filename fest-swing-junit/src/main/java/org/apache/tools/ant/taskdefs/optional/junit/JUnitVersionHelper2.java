/*
 * Created on Jun 5, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.apache.tools.ant.taskdefs.optional.junit;

import junit.framework.Test;

/**
 * Understands a hack to make public the package-protected method <code>getTestCaseClassName</code> from
 * <code>{@link JUnitVersionHelper}</code>.
 * 
 * @author Alex Ruiz
 */
public final class JUnitVersionHelper2 {

  /**
   * Obtains the name of the test method from the given test.
   * @param test the give test.
   * @return the name of the test method obtained from the given test.
   */
  public static String testMethodName(Test test) {
    return JUnitVersionHelper.getTestCaseName(test);
  }

  /**
   * Obtains the name of the test class from the given test.
   * @param test the give test.
   * @return the name of the test class obtained from the given test.
   */
  public static String testClassName(Test test) {
    return JUnitVersionHelper.getTestCaseClassName(test);
  }
  
  private JUnitVersionHelper2() {}
}
