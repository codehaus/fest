/*
 * Created on Jul 12, 2008
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
package org.fest.swing.applet;

import java.applet.AppletContext;
import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicAppletStub}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test
public class BasicAppletStubTest {

  private Window viewer;
  private AppletContext context;
  private BasicAppletStub stub;
  
  @BeforeClass public void setUp() {
    viewer = createMock(Window.class);
    context = createMock(AppletContext.class);
    stub = new BasicAppletStub(viewer, context);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfViewerIsNullInConstructorWithNoParameterMap() {
    new BasicAppletStub(null, context);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfContextIsNullInConstructorWithNoParameterMap() {
    new BasicAppletStub(viewer, null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfViewerIsNullInConstructorWithParameterMap() {
    new BasicAppletStub(null, context, new HashMap<String, String>());
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfContextIsNullInConstructorWithParameterMap() {
    new BasicAppletStub(viewer, null, new HashMap<String, String>());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfParameterMapIsNull() {
    new BasicAppletStub(viewer, context, null);
  }

  public void shouldBeActive() {
    assertThat(stub.isActive()).isTrue();
  }
  
  public void shouldResizeViewWhenAppletResized() {
    final int width = 800;
    final int height = 600;
    new EasyMockTemplate(viewer) {
      protected void expectations() {
        viewer.setSize(width, height);
      }

      protected void codeToTest() {
        stub.appletResize(width, height);
      }
    }.run();
  }
  
  public void shouldReturnContext() {
    assertThat(stub.getAppletContext()).isSameAs(context);
  }
  
  public void shouldReturnCodeBase() {
    assertThat(stub.getCodeBase()).isNotNull();
  }

  public void shouldReturnDocumentBase() {
    assertThat(stub.getDocumentBase()).isNotNull();
  }
  
  public void shouldReturnParameter() {
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("key1", "value1");
    stub = new BasicAppletStub(viewer, context, parameters);
    assertThat(stub.getParameter("key1")).isEqualTo("value1");
  }
}
