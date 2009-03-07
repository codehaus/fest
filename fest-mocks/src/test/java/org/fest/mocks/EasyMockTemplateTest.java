/*
 * Created on Apr 22, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.mocks;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.easymock.EasyMock.createMock;
import static org.testng.Assert.*;

/**
 * Unit tests for <code>{@link EasyMockTemplate}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class EasyMockTemplateTest {

  private static interface Server {
    void process(String arg);
  }
  
  private static class Client {
    final Server server;
    
    Client(Server server) {
      this.server = server;
    }
    
    void delegateProcessToServer(String arg) {
      server.process(arg);
    }
  }
  
  private Client client;
  private Server mockServer;
  
  @BeforeMethod public void setUp() {
    mockServer = createMock(Server.class);
    client = new Client(mockServer);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksIsNull() {
    new BasicEasyMockTemplate(new Object[0]);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksContainsNull() {
    Object[] mocks = new Object[] { mockServer, null };
    new BasicEasyMockTemplate(mocks);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksIsEmpty() {
    new BasicEasyMockTemplate((Object[])null);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "notMocksProvider")
  public void shouldThrowExceptionIfGivenObjectIsNotMock(Object[] mocks) {
    new BasicEasyMockTemplate(mocks);
  }

  @DataProvider(name = "notMocksProvider")
  public Object[][] notMocksProvider() {
   return new Object[][] { 
       { new Object[] { mockServer, "Not a mock" } }, 
       { new Object[] { "Not a mock" } }, 
       { new Object[] { "Not a mock", "Not a mock either" } }, 
       { new Object[] { "Not a mock", mockServer } } 
   };
  }

  public void shouldReturnMocksInTemplate() {
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer);
    List<Object> actualMocks = template.mocks();
    assertEquals(actualMocks, asList(mockServer));
  }
  
  public void shouldWrapCatchedExceptionThrownBySetup() {
    final Throwable expected = new Throwable();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void setUp() throws Throwable {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (UnexpectedError e) {
      assertSame(e.getCause(), expected);
    }
  }

  public void shouldRethrowRuntimeExceptionThrownBySetup() {
    final RuntimeException expected = new RuntimeException();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void setUp() {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (RuntimeException e) {
      assertSame(e, expected);
    }
  }

  public void shouldWrapCatchedExceptionThrownByExpectations() {
    final Throwable expected = new Throwable();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void expectations() throws Throwable {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (UnexpectedError e) {
      assertSame(e.getCause(), expected);
    }
  }

  public void shouldRethrowRuntimeExceptionThrownByExpectations() {
    final RuntimeException expected = new RuntimeException();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void expectations() {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (RuntimeException e) {
      assertSame(e, expected);
    }
  }

  public void shouldWrapCatchedExceptionThrownByCodeToTest() {
    final Throwable expected = new Throwable();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void codeToTest() throws Throwable {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (UnexpectedError e) {
      assertSame(e.getCause(), expected);
    }
  }

  public void shouldRethrowRuntimeExceptionThrownByCodeToTest() {
    final RuntimeException expected = new RuntimeException();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void codeToTest() {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (RuntimeException e) {
      assertSame(e, expected);
    }
  }

  public void shouldWrapCatchedExceptionThrownByCleanUp() {
    final Throwable expected = new Throwable();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void cleanUp() throws Throwable {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (UnexpectedError e) {
      assertSame(e.getCause(), expected);
    }
  }
  
  public void shouldRethrowRuntimeExceptionThrownByCleanUp() {
    final RuntimeException expected = new RuntimeException();
    BasicEasyMockTemplate template = new BasicEasyMockTemplate(mockServer) {
      @Override protected void cleanUp() {
        throw expected;
      }
    };
    try {
      template.run();
      failWhenExpectingException();
    } catch (RuntimeException e) {
      assertSame(e, expected);
    }
  }

  private static class BasicEasyMockTemplate extends EasyMockTemplate {
    BasicEasyMockTemplate(Object... mocks) {
      super(mocks);
    }

    protected void expectations() throws Throwable {}
    protected void codeToTest() throws Throwable {}
  }

  private void failWhenExpectingException() {
    fail("Expecting exception");
  }
  
  public void shouldCompleteMockUsageCycle() {
    final String arg = "name";
    MethodOrderCheckerEasyMockTemplate template = new MethodOrderCheckerEasyMockTemplate(mockServer) {
      
      @Override protected void expectations() {
        super.expectations();
        mockServer.process(arg);  
      }
      
      @Override protected void codeToTest() {
        super.codeToTest();
        client.delegateProcessToServer(arg);
      }
    };
    template.run();
    assertMethodCallOrder(template.setUp, template.expectations, template.codeToTest, template.cleanUp);
  }
  
  private void assertMethodCallOrder(int... callOrders) {
    int expected = 0;
    for (int callOrder : callOrders) assertEquals(callOrder, ++expected);
  }
  
  /** <code>{@link EasyMockTemplate}</code> that tracks the order in which its methods are called. */
  private static class MethodOrderCheckerEasyMockTemplate extends EasyMockTemplate {
    int setUp;
    int expectations;
    int codeToTest;
    int cleanUp;

    int methodCallOrder;
    
    public MethodOrderCheckerEasyMockTemplate(Object... mocks) {
      super(mocks);
    }

    @Override protected void setUp() {
      setUp = ++methodCallOrder;
    }

    protected void expectations() {
      expectations = ++methodCallOrder;
    }
    
    protected void codeToTest() {
      codeToTest = ++methodCallOrder;     
    }

    @Override protected void cleanUp() throws Throwable {
      cleanUp = ++methodCallOrder;
    }
  }
}
