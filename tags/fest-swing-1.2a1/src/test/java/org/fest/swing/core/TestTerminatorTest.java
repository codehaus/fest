/*
 * Created on May 2, 2009
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
package org.fest.swing.core;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TestTerminator}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TestTerminatorTest {

  private ThreadsSource threadsSource;
  private FrameDisposer frameDisposer;
  private TestTerminator terminator;

  @BeforeMethod public void setUp() {
    threadsSource = createMock(ThreadsSource.class);
    frameDisposer = createMock(FrameDisposer.class);
    terminator = new TestTerminator(threadsSource, frameDisposer);
  }

  public void shouldTerminateGUITests() {
    final Thread mainThread = createMock(Thread.class);
    new EasyMockTemplate(threadsSource, frameDisposer, mainThread) {
      protected void expectations() {
        expect(threadsSource.mainThread()).andReturn(mainThread);
        mainThread.interrupt();
        expectLastCall().once();
        expectFramesDisposal();
      }

      protected void codeToTest() {
        terminateTestsAndExpectException();
      }
    }.run();
  }

  public void shouldNotThrowErrorIfMainThreadNotFound() {
    new EasyMockTemplate(threadsSource, frameDisposer) {
      protected void expectations() {
        expect(threadsSource.mainThread()).andReturn(null);
        expectFramesDisposal();
      }

      protected void codeToTest() {
        terminateTestsAndExpectException();
      }
    }.run();
  }

  private void expectFramesDisposal() {
    frameDisposer.disposeFrames();
    expectLastCall().once();
  }

  private void terminateTestsAndExpectException() {
    try {
      terminator.terminateTests();
      failWhenExpectingException();
    } catch (RuntimeException e) {
      assertThat(e.getMessage()).isEqualTo("User aborted FEST-Swing tests");
    }
  }
}
