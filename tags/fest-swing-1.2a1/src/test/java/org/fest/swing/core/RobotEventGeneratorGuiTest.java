/*
 * Created on Apr 3, 2008
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
package org.fest.swing.core;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link RobotEventGenerator}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class RobotEventGeneratorGuiTest extends InputEventGeneratorTestCase {

  private Settings settings;
  private RobotEventGenerator eventGenerator;
  
  @Override void onSetUp() {
    settings = new Settings();
    eventGenerator = new RobotEventGenerator(settings);
  }
  
  InputEventGenerator eventGenerator() {
    return eventGenerator;
  }

  public void shouldAttachRobotToSettings() {
    assertThat(eventGenerator.robot()).isSameAs(settings.robot());
  }
}
