/*
 * Created on Nov 6, 2008
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
package org.fest.swing.driver;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.builder.JInternalFrames.internalFrame;


/**
 * Tests for <code>{@link ComponentMovableQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ComponentMovableQueryTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  public void shouldReturnIsMovableIfComponentIsFrame() {
    assertThat(ComponentMovableQuery.isUserMovable(frame().createNew())).isTrue();
  }
  
  public void shouldReturnIsMovableIfComponentIsDialog() {
    assertThat(ComponentMovableQuery.isUserMovable(dialog().createNew())).isTrue();
  }
 
  public void shouldReturnIsMovableIfComponentIsJInternalFrame() {
    assertThat(ComponentMovableQuery.isUserMovable(internalFrame().createNew())).isTrue();
  }
  
  public void shouldReturnIsNotMovableIfComponentIsNotWindow() {
    assertThat(ComponentMovableQuery.isUserMovable(button().createNew())).isFalse();
  }
}
