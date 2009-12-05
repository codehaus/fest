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

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ThreadsSource}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ThreadsSourceTest {

  private ThreadsSource source;

  @BeforeMethod public void setUp() {
    source = new ThreadsSource();
  }

  public void shouldReturnMainThread() {
    Thread mainThread = source.mainThread();
    assertThat(mainThread).isNotNull();
    assertThat(mainThread.getName().toLowerCase()).isEqualTo("main");
  }
}