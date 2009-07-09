/*
 * Created on Aug 27, 2007
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

import static java.awt.Event.CTRL_MASK;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.OSIdentifierStub.*;

import java.awt.Toolkit;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.lock.ScreenLock;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link Platform}</code>
 *
 * @author Alex Ruiz
 */
@Test public class PlatformTest {

  private ToolkitProviderStub toolkitProvider;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    toolkitProvider = new ToolkitProviderStub();
  }

  @AfterMethod public void tearDown() {
    try {
      Platform.reload();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnTrueIfOSIsWindows() {
    Platform.initialize(windows9x(), toolkitProvider);
    assertThat(Platform.isWindows()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotWindows() {
    Platform.initialize(macintosh(), toolkitProvider);
    assertThat(Platform.isWindows()).isFalse();
  }

  public void shouldReturnTrueIfOSIsWindows9x() {
    Platform.initialize(windows9x(), toolkitProvider);
    assertThat(Platform.isWindows9x()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotWindows9x() {
    Platform.initialize(macintosh(), toolkitProvider);
    assertThat(Platform.isWindows9x()).isFalse();
  }

  public void shouldReturnTrueIfOSIsWindowsXP() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isWindowsXP()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotWindowsXP() {
    Platform.initialize(macintosh(), toolkitProvider);
    assertThat(Platform.isWindowsXP()).isFalse();
  }

  public void shouldReturnTrueIfOSIsMacintosh() {
    Platform.initialize(macintosh(), toolkitProvider);
    assertThat(Platform.isMacintosh()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotMacintosh() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isMacintosh()).isFalse();
  }

  public void shouldReturnTrueIfOSIsOSX() {
    Platform.initialize(osX(), toolkitProvider);
    assertThat(Platform.isOSX()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotOSX() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isOSX()).isFalse();
  }

  public void shouldReturnTrueIfOSIsX11() {
    Platform.initialize(x11(), toolkitProvider);
    assertThat(Platform.isX11()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotX11() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isX11()).isFalse();
  }

  public void shouldReturnTrueIfOSIsSoloaris() {
    Platform.initialize(solaris(), toolkitProvider);
    assertThat(Platform.isSolaris()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotSolaris() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isSolaris()).isFalse();
  }

  public void shouldReturnTrueIfOSIsHPUX() {
    Platform.initialize(hpUX(), toolkitProvider);
    assertThat(Platform.isHPUX()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotHPUX() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isHPUX()).isFalse();
  }

  public void shouldReturnTrueIfOSIsLinux() {
    Platform.initialize(linux(), toolkitProvider);
    assertThat(Platform.isLinux()).isTrue();
  }

  public void shouldReturnFalseIfOSIsNotLinux() {
    Platform.initialize(windowsXP(), toolkitProvider);
    assertThat(Platform.isLinux()).isFalse();
  }


  public void shouldReturnControlOrCommandMaskFromToolkit() {
    final Toolkit toolkit = wireMockToolkit();
    new EasyMockTemplate(toolkit) {
      protected void expectations() {
        expect(toolkit.getMenuShortcutKeyMask()).andReturn(CTRL_MASK);
      }

      protected void codeToTest() {
        assertThat(Platform.controlOrCommandMask()).isEqualTo(CTRL_MASK);
      }
    }.run();
  }

  public void shouldReturnControlOrCommandKeyFromToolkit() {
    final Toolkit toolkit = wireMockToolkit();
    new EasyMockTemplate(toolkit) {
      protected void expectations() {
        expect(toolkit.getMenuShortcutKeyMask()).andReturn(CTRL_MASK);
      }

      protected void codeToTest() {
        assertThat(Platform.controlOrCommandKey()).isEqualTo(VK_CONTROL);
      }
    }.run();
  }

  private Toolkit wireMockToolkit() {
    Platform.initialize(windowsXP(), toolkitProvider);
    return toolkitProvider.toolkit(createMock(Toolkit.class));
  }

  public void shouldReturnCanResizeWindowsIfOSIsNotWindowsOrOSX() {
    Platform.initialize(linux(), toolkitProvider);
    assertThat(Platform.canResizeWindows()).isEqualTo(true);
  }

  public void shouldReturnCannotResizeWindowsIfOSIsWindows() {
    Platform.initialize(windows9x(), toolkitProvider);
    assertThat(Platform.canResizeWindows()).isEqualTo(false);
  }

  public void shouldReturnCannotResizeWindowsIfOSIsOSX() {
    Platform.initialize(osX(), toolkitProvider);
    assertThat(Platform.canResizeWindows()).isEqualTo(false);
  }

  public void shouldReturnCanMoveWindowsIfOSIsNotWindowsOrOSX() {
    Platform.initialize(linux(), toolkitProvider);
    assertThat(Platform.canMoveWindows()).isEqualTo(true);
  }

  public void shouldReturnCannotMoveWindowsIfOSIsWindows() {
    Platform.initialize(windows9x(), toolkitProvider);
    assertThat(Platform.canMoveWindows()).isEqualTo(false);
  }

  public void shouldReturnCannotMoveWindowsIfOSIsOSX() {
    Platform.initialize(osX(), toolkitProvider);
    assertThat(Platform.canMoveWindows()).isEqualTo(false);
  }

  private static class ToolkitProviderStub extends ToolkitProvider {
    private Toolkit toolkit;

    @Override Toolkit toolkit() {
      return toolkit;
    }

    Toolkit toolkit(Toolkit newToolkit) {
      toolkit = newToolkit;
      return toolkit;
    }
  }
}
