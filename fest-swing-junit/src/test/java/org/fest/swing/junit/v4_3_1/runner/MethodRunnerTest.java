/*
 * Created on Mar 16, 2009
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
package org.fest.swing.junit.v4_3_1.runner;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.util.Objects.areEqual;

import java.lang.reflect.Method;

import org.easymock.IArgumentMatcher;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.runner.*;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link MethodRunner}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class MethodRunnerTest {

  private Class<?> testClass;
  private RunNotifier notifier;
  private FailureScreenshotTaker screenshotTaker;

  @BeforeMethod public void setUp() {
    testClass = SomeGuiTest.class;
    notifier = createMock(RunNotifier.class);
    screenshotTaker = createMock(FailureScreenshotTaker.class);
  }

  public void shouldTakeScreenshotIfTestFailsAndIsGuiTest() throws Exception {
    final TestInfo testInfo = new TestInfo(new Object(), testClass, method("failedGUITest"));
    final MethodRunner runner = new MethodRunner(testInfo, notifier, screenshotTaker);
    final Exception e = new Exception();
    new EasyMockTemplate(notifier, screenshotTaker) {
      protected void expectations() {
        screenshotTaker.saveScreenshot(testInfo.screenshotFileName());
        expectLastCall().once();
        Failure expectedFailure = new Failure(testInfo.description(), e);
        reportMatcher(new FailureMatcher(expectedFailure));
        notifier.fireTestFailure(expectedFailure);
        expectLastCall().once();
      }

      protected void codeToTest() {
        runner.addFailure(e);
      }
    }.run();
  }

  public void shouldNotTakeScreenshotIfTestFailsAndIsNotGuiTest() throws Exception {
    final TestInfo testInfo = new TestInfo(new Object(), testClass, method("failedNonGUITest"));
    final MethodRunner runner = new MethodRunner(testInfo, notifier, screenshotTaker);
    final Exception e = new Exception();
    new EasyMockTemplate(notifier, screenshotTaker) {
      protected void expectations() {
        Failure expectedFailure = new Failure(testInfo.description(), e);
        reportMatcher(new FailureMatcher(expectedFailure));
        notifier.fireTestFailure(expectedFailure);
        expectLastCall().once();
      }

      protected void codeToTest() {
        runner.addFailure(e);
      }
    }.run();
  }

  private Method method(String name) throws Exception {
    return testClass.getDeclaredMethod(name);
  }

  private static class FailureMatcher implements IArgumentMatcher {
    private final Failure expected;

    FailureMatcher(Failure expected) {
      this.expected = expected;
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append(expected.getClass().getName()).append("[");
      buffer.append("description=").append(expected.getDescription()).append(",");
      buffer.append("exception=").append(expected.getException().getMessage()).append("]");
    }

    public boolean matches(Object argument) {
      if (!(argument instanceof Failure)) return false;
      Failure actual = (Failure) argument;
      if (!areEqual(expected.getDescription(), actual.getDescription())) return false;
      return areEqual(expected.getException(), actual.getException());
    }
  }
}
