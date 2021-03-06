/*
 * Created on May 29, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.javafx.core;

import static javafx.scene.input.KeyCode.VK_A;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.javafx.core.SceneFromStageQuery.sceneIn;
import static org.fest.javafx.core.SingleSceneNodeHierarchy.hierarchyFor;
import static org.fest.javafx.core.Visibility.REQUIRE_VISIBLE;
import static org.fest.javafx.launcher.GuiLauncher.launch;
import static org.fest.javafx.threading.GuiActionRunner.execute;
import static org.fest.javafx.util.Scenes.closeInUIThread;
import javafx.scene.Scene;
import javafx.scene.control.TextBox;
import javafx.scene.input.KeyCode;

import org.fest.javafx.annotations.RunsInUIThread;
import org.fest.javafx.scripts.TextBoxDemo;
import org.fest.javafx.test.core.SequentialTestCase;
import org.fest.javafx.threading.GuiQuery;
import org.fest.javafx.threading.GuiTask;
import org.junit.Test;

/**
 * Tests for implementations of <code>{@link InputGenerator#pressKey(KeyCode)}</code> and
 * <code>{@link InputGenerator#releaseKey(KeyCode)}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class InputGenerator_pressKey_and_releaseKey_TestCase extends SequentialTestCase {

  private NodeFinder finder;
  private Scene scene;
  private InputGenerator inputGenerator;

  @Override protected void onSetUp() {
    scene = sceneIn(launch(TextBoxDemo.class));
    finder = new BasicNodeFinder(hierarchyFor(scene));
    inputGenerator = createInputGenerator();
  }

  abstract InputGenerator createInputGenerator();

  @Override protected void onTearDown() {
    closeInUIThread(scene);
  }

  @Test
  public void should_press_and_release_key() {
    TextBox textBox = finder.findById("textBox", TextBox.class, REQUIRE_VISIBLE);
    setFocusOn(textBox);
    inputGenerator.waitForIdle();
    inputGenerator.pressKey(VK_A).releaseKey(VK_A);
    assertThat(textOf(textBox)).isEqualTo("a");
  }

  @RunsInUIThread
  private static void setFocusOn(final TextBox textBox) {
    execute(new GuiTask() {
      @Override protected void executeInUIThread() {
        textBox.set$focused(true);
      }
    });
  }

  @RunsInUIThread
  private static String textOf(final TextBox textBox) {
    return execute(new GuiQuery<String>() {
      @Override protected String executeInUIThread() {
        return textBox.get$rawText();
      }
    });
  }

  final Scene scene() { return scene; }
}
