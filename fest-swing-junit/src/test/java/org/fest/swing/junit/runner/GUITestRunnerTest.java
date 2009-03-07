/*
 * Created on Nov 18, 2007
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.fest.util.Collections.filter;
import static org.fest.util.TypeFilter.byType;

import java.util.List;

import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link GUITestRunner}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestRunnerTest {

  private GUITestRunner runner;
  private TestRunNotifier runNotifier;

  @BeforeMethod public void setUp() throws Exception {
    runner = new GUITestRunner(FakeGUITest.class);
    runNotifier = new TestRunNotifier();
  }

  @Test public void shouldAddListenerToNotifierWhenRunningAndRemoveItAfterwards() {
    runner.run(runNotifier);
    assertThat(runNotifier.addedListener).isInstanceOf(FailedGUITestListener.class);
    assertNoFailedGUITestListeners();
  }

  @Test public void shouldNotAddListenerToNotifierWhenRunningIfAlreadyPresentAndRemoveAllAfterwards() {
    runNotifier.listeners().add(new FailedGUITestListener());
    runner.run(runNotifier);
    assertThat(runNotifier.addedListener).isNull();
    assertNoFailedGUITestListeners();
  }

  private void assertNoFailedGUITestListeners() {
    List<FailedGUITestListener> listeners = filter(runNotifier.listeners(), byType(FailedGUITestListener.class));
    assertThat(listeners).isEmpty();
  }

  private static class TestRunNotifier extends RunNotifier {
    public RunListener addedListener;

    @SuppressWarnings("unchecked")
    List<RunListener> listeners() {
      return field("fListeners").ofType(List.class).in(this).get();
    }

    @Override public void addListener(RunListener listener) {
      addedListener = listener;
      super.addListener(listener);
    }
  }
}
