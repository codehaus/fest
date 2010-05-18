/*
 * Created on May 17, 2010
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
package org.fest.javafx.maven;

import java.lang.reflect.Method;

import org.junit.*;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JavaFxcMojo#execute()}</code>.
 *
 * @author Alex Ruiz
 */
public class JavaFxcMojo_execute_Test {

  private JavaFxcMojo mojo;

  @Before
  public void setUp() throws Exception {
    Method compileMethod = AbstractJavaFxcMojo.class.getDeclaredMethod("compile");
    mojo = createMock(JavaFxcMojo.class, compileMethod);
  }

  @Test
  public void should_compile_sources() {
    new EasyMockTemplate(mojo) {
      @Override protected void expectations() throws Exception {
        mojo.compile();
        expectLastCall();
      }

      @Override protected void codeToTest() throws Exception {
        mojo.execute();
      }
    }.run();
  }
}
