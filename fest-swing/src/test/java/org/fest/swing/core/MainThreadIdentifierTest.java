/*
 * Created on May 31, 2009
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
import static org.fest.util.Arrays.array;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link MainThreadIdentifier}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class MainThreadIdentifierTest {

  private MainThreadIdentifier identifier;

  @BeforeClass public void setUpOnce() {
    identifier = new MainThreadIdentifier();
  }

  public void shouldReturnThreadWithNameEqualToMain() {
    Thread mainThread = new Thread("main");
    Thread[] allThreads = array(new Thread(), mainThread);
    assertThat(identifier.mainThreadIn(allThreads)).isSameAs(mainThread);
  }

  public void shouldReturnNullIfThreadArrayIsEmpty() {
    assertThat(identifier.mainThreadIn(new Thread[0])).isNull();
  }

  public void shouldReturnNullIfThreadArrayDoesNotContainMainThread() {
    Thread[] allThreads = array(new Thread(), new Thread());
    assertThat(identifier.mainThreadIn(allThreads)).isNull();
  }
}
