/*
 * Created on Oct 29, 2007
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
package org.fest.swing.util;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link TimeoutWatch}</code>.
 *
 * @author Alex Ruiz
 */
public class TimeoutWatchTest {

  @Test public void shouldNotTimeout() {
    TimeoutWatch watch = TimeoutWatch.startWatchWithTimeoutOf(5000);
    assertThat(watch.isTimeOut()).isFalse();
  }
  
  @Test public void shouldTimeout() throws Exception {
    TimeoutWatch watch = TimeoutWatch.startWatchWithTimeoutOf(10);
    Thread.sleep(100);
    assertThat(watch.isTimeOut()).isTrue();
  }
  
}
