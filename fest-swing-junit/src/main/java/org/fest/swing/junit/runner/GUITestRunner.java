/*
 * Created on Nov 17, 2007
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.*;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

import org.fest.reflect.exception.ReflectionError;
import org.fest.util.Collections;

import static org.fest.reflect.core.Reflection.field;
import static org.fest.util.TypeFilter.byType;

/**
 * Understands a JUnit 4 test runner that takes a screenshot of a failed GUI test.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestRunner extends Runner {

  private static final List<RunListener> NO_LISTENERS = new ArrayList<RunListener>();

  private final List<Method> testMethods;
  private final Class<?> testClass;

  /**
   * Creates a new <code>{@link GUITestRunner}</code>.
   * @param testClass the class containing the tests to run.
   * @throws InitializationError if something goes wrong when creating this runner.
   */
  public GUITestRunner(Class<?> testClass) throws InitializationError  {
    this.testClass = testClass;
    testMethods = new TestIntrospector(testClass).getTestMethods(Test.class);
    validate();
  }

  private void validate() throws InitializationError {
    MethodValidator methodValidator= new MethodValidator(testClass);
    methodValidator.validateMethodsForDefaultRunner();
    methodValidator.assertValid();
  }

  /**
   * Run the tests for this runner, taking screenshots of failing tests.
   * @param notifier will be notified of events while tests are being run, started, finishing, and failing.
   */
  @Override public void run(RunNotifier notifier) {
    addFailedGUITestListenerTo(notifier);
    new InnerRunner(notifier).runProtected();
    removeFailedGUITestListenersFrom(notifier);
  }

  private void addFailedGUITestListenerTo(RunNotifier notifier) {
    if (!failedGUITestListenersIn(notifier).isEmpty()) return;
    notifier.addListener(new FailedGUITestListener());
  }

  private void removeFailedGUITestListenersFrom(RunNotifier notifier) {
    for(RunListener listener : failedGUITestListenersIn(notifier))
      notifier.removeListener(listener);
  }

  private List<? extends RunListener> failedGUITestListenersIn(RunNotifier notifier) {
    List<RunListener> listeners = listenersIn(notifier);
    if (listeners.isEmpty()) return NO_LISTENERS;
    return Collections.filter(listeners, byType(FailedGUITestListener.class));
  }

  @SuppressWarnings("unchecked")
  private List<RunListener> listenersIn(RunNotifier notifier) {
    try {
      return field("fListeners").ofType(List.class).in(notifier).get();
    } catch (ReflectionError e) {
      return NO_LISTENERS;
    }
  }

  final void doRun(RunNotifier notifier) {
    if (testMethods.isEmpty()) notifier.testAborted(getDescription(), new Exception("No runnable methods"));
    for (Method method : testMethods)
      invokeTestMethod(method, notifier);
  }

  private void invokeTestMethod(Method method, RunNotifier notifier) {
    Object test;
    try {
      test = testClass.getConstructor().newInstance();
    } catch (InvocationTargetException e) {
      notifier.testAborted(methodDescription(method), e.getCause());
      return;
    } catch (Exception e) {
      notifier.testAborted(methodDescription(method), e);
      return;
    }
    createMethodRunner(test, method, notifier).run();
  }

  private TestMethodRunner createMethodRunner(Object test, Method method, RunNotifier notifier) {
    return new TestMethodRunner(test, method, notifier, methodDescription(method));
  }

  final Class<?> testClass() { return testClass; }
  
  /**
   * Returns a <code>{@link Description}</code> showing the tests to be run by the receiver.
   * @return a <code>Description</code> showing the tests to be run by the receiver.
   */
  @Override public Description getDescription() {
    Description spec= Description.createSuiteDescription(testClass.getName());
    for (Method method : testMethods)
        spec.addChild(methodDescription(method));
    return spec;
  }

  private Description methodDescription(Method method) {
    return new GUITestDescription(testClass, method);
  }

  private class InnerRunner extends BeforeAndAfterRunner {
    private final RunNotifier notifier;

    InnerRunner(RunNotifier notifier) {
      super(testClass(), BeforeClass.class, AfterClass.class, null);
      this.notifier = notifier;
    }

    @Override protected void runUnprotected() {
      doRun(notifier);
    }

    @Override protected void addFailure(Throwable targetException) {
      notifier.fireTestFailure(new Failure(getDescription(), targetException));
    }
  }
}
