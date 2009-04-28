/*
 * Created on Apr 28, 2009
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
package org.fest.swing.fixture;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Container;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiQuery;

/**
 * Understands utility methods related to <code>{@link Container}</code>s.
 * @since 1.2
 *
 * @author Alex Ruiz
 */
public final class Containers {

  /**
   * Creates a new <code>{@link JFrame}</code> and uses the given <code>{@link Container}</code> as its content pane.
   * The created <code>JFrame</code> is wrapped and displayed by a <code>{@link FrameFixture}</code>.
   * @param contentPane the <code>Container</code> to use as content pane for the <code>JFrame</code> to create.
   * @return the created <code>FrameFixture</code>.
   */
  public static FrameFixture showInFrame(Container contentPane) {
    FrameFixture frameFixture = createFrameFor(contentPane);
    frameFixture.show();
    return frameFixture;
  }

  /**
   * Creates a new <code>{@link JFrame}</code> and uses the given <code>{@link Container}</code> as its content pane.
   * The created <code>JFrame</code> is wrapped by a <code>{@link FrameFixture}</code>. Unlike
   * <code>{@link #showInFrame(Container)}</code>, this method does <strong>not</strong> display the created
   * <code>JFrame</code>.
   * @param contentPane the <code>Container</code> to use as content pane for the <code>JFrame</code> to create.
   * @return the created <code>FrameFixture</code>.
   */
  public static FrameFixture createFrameFor(Container contentPane) {
    return new FrameFixture(frameFor(contentPane));
  }

  private static JFrame frameFor(final Container contentPane) {
    return execute(new GuiQuery<JFrame>() {
      protected JFrame executeInEDT() throws Throwable {
        JFrame frame = new JFrame("Created by FEST");
        frame.setName("org.fest.swing.FrameFixtureForContainer");
        frame.setContentPane(contentPane);
        return frame;
      }
    });
  }

  private Containers() {}
}
