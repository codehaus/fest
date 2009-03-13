/*
 * Created on Mar 13, 2009
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
package org.fest.swing.junit45.runner;

import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.annotation.GUITestFinder.isGUITest;
import static org.fest.util.Collections.list;
import static org.fest.util.TypeFilter.byType;
import static org.junit.runner.Description.createTestDescription;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;
import org.fest.swing.annotation.GUITest;
import org.fest.util.Collections;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Understands a JUnit 4.5 test runner that takes a screenshot of a failed GUI test.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestRunner extends BlockJUnit4ClassRunner {

  private static final List<RunListener> NO_LISTENERS = new ArrayList<RunListener>();

  private static final GUITest GUI_TEST_ANNOTATION_INSTANCE = new GUITest() {
    public Class<? extends Annotation> annotationType() {
      return GUITest.class;
    }
  };

  /**
   * Creates a new <code>{@link GUITestRunner}</code>.
   * @param testClass the class containing the tests to run.
   * @throws InitializationError if something goes wrong when creating this runner.
   */
  public GUITestRunner(Class<?> testClass) throws InitializationError  {
    super(testClass);
  }

  /**
   * Run the tests for this runner, taking screenshots of failing tests.
   * @param notifier will be notified of events while tests are being run, started, finishing, and failing.
   */
  @Override public void run(RunNotifier notifier) {
    addFailedGUITestListenerTo(notifier);
    super.run(notifier);
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

  private List<RunListener> listenersIn(RunNotifier notifier) {
    try {
      return field("fListeners").ofType(new TypeRef<List<RunListener>>() {}).in(notifier).get();
    } catch (ReflectionError e) {
      return NO_LISTENERS;
    }
  }

  /**
   * Creates a description for the given method. The created description includes the annotation
   * <code>{@link GUITest}</code> only if the method or its class are annotated with such annotation.
   * @param method the method to annotate.
   * @return the created description.
   */
  @Override protected Description describeChild(FrameworkMethod method) {
    Method testMethod = method.getMethod();
    Class<?> testClass = testMethod.getDeclaringClass();
    if (!isGUITest(testClass, testMethod)) return super.describeChild(method);
    Annotation[] annotations = addGuiAnnotationToAnnotationsIn(method);
    return createTestDescription(getTestClass().getJavaClass(), testName(method), annotations);
  }

  private Annotation[] addGuiAnnotationToAnnotationsIn(FrameworkMethod method) {
    List<Annotation> annotations = list(method.getAnnotations());
    boolean containsGUITestAnnotation = guiTestAnnotationIn(annotations);
    if (!containsGUITestAnnotation) annotations.add(GUI_TEST_ANNOTATION_INSTANCE);
    return annotations.toArray(new Annotation[annotations.size()]);
  }

  private boolean guiTestAnnotationIn(List<Annotation> annotations) {
    for (Annotation annotation : annotations)
      if (GUITest.class.isAssignableFrom(annotation.getClass())) return true;
    return false;
  }
}
