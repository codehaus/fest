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

import java.awt.Toolkit;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.data.BooleanProvider;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link Platform}</code>
 *
 * @author Alex Ruiz
 */
@Test public class PlatformTest {

  private OSIdentifier osIdentifier;
  private ToolkitProvider toolkitProvider;

  @BeforeMethod public void setUp() {
    osIdentifier = createMock(OSIdentifier.class);
    toolkitProvider = createMock(ToolkitProvider.class);
    Platform.initialize(osIdentifier, toolkitProvider);
  }

  @AfterMethod public void tearDown() {
    Platform.reload();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsWindowsFromOSIdentifier(final boolean isWindows) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(isWindows);
      }

      protected void codeToTest() {
        assertThat(Platform.isWindows()).isEqualTo(isWindows);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsWindows9xFromOSIdentifier(final boolean isWindows9x) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows9x()).andReturn(isWindows9x);
      }

      protected void codeToTest() {
        assertThat(Platform.isWindows9x()).isEqualTo(isWindows9x);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsWindowsXPFromOSIdentifier(final boolean isWindowsXP) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindowsXP()).andReturn(isWindowsXP);
      }

      protected void codeToTest() {
        assertThat(Platform.isWindowsXP()).isEqualTo(isWindowsXP);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsMacintoshFromOSIdentifier(final boolean isMacintosh) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isMacintosh()).andReturn(isMacintosh);
      }

      protected void codeToTest() {
        assertThat(Platform.isMacintosh()).isEqualTo(isMacintosh);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsOSXFromOSIdentifier(final boolean isOSX) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isOSX()).andReturn(isOSX);
      }

      protected void codeToTest() {
        assertThat(Platform.isOSX()).isEqualTo(isOSX);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsX11FromOSIdentifier(final boolean isX11) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isX11()).andReturn(isX11);
      }

      protected void codeToTest() {
        assertThat(Platform.isX11()).isEqualTo(isX11);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsSolarisFromOSIdentifier(final boolean isSolaris) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isSolaris()).andReturn(isSolaris);
      }

      protected void codeToTest() {
        assertThat(Platform.isSolaris()).isEqualTo(isSolaris);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsHPUXFromOSIdentifier(final boolean isHPUX) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isHPUX()).andReturn(isHPUX);
      }

      protected void codeToTest() {
        assertThat(Platform.isHPUX()).isEqualTo(isHPUX);
      }
    }.run();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsLinuxFromOSIdentifier(final boolean isLinux) {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isLinux()).andReturn(isLinux);
      }

      protected void codeToTest() {
        assertThat(Platform.isLinux()).isEqualTo(isLinux);
      }
    }.run();
  }

  public void shouldReturnControlOrCommandMaskFromToolkit() {
    final Toolkit toolkit = createMock(Toolkit.class);
    final int ctrlMask = CTRL_MASK;
    new EasyMockTemplate(toolkitProvider, toolkit) {
      protected void expectations() {
        expect(toolkitProvider.toolkit()).andReturn(toolkit);
        expect(toolkit.getMenuShortcutKeyMask()).andReturn(ctrlMask);
      }

      protected void codeToTest() {
        assertThat(Platform.controlOrCommandMask()).isEqualTo(ctrlMask);
      }
    }.run();
  }

  public void shouldReturnControlOrCommandKeyFromToolkit() {
    final Toolkit toolkit = createMock(Toolkit.class);
    new EasyMockTemplate(toolkitProvider, toolkit) {
      protected void expectations() {
        expect(toolkitProvider.toolkit()).andReturn(toolkit);
        expect(toolkit.getMenuShortcutKeyMask()).andReturn(CTRL_MASK);
      }

      protected void codeToTest() {
        assertThat(Platform.controlOrCommandKey()).isEqualTo(VK_CONTROL);
      }
    }.run();
  }

  public void shouldReturnCanResizeWindowsIfOSIsNotWindowsOrOSX() {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(false);
        expect(osIdentifier.isMacintosh()).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(Platform.canResizeWindows()).isEqualTo(true);
      }
    }.run();
  }

  public void shouldReturnCannotResizeWindowsIfOSIsWindows() {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(Platform.canResizeWindows()).isEqualTo(false);
      }
    }.run();
  }

  public void shouldReturnCannotResizeWindowsIfOSIsOSX() {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(false);
        expect(osIdentifier.isMacintosh()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(Platform.canResizeWindows()).isEqualTo(false);
      }
    }.run();
  }

  public void shouldReturnCanMoveWindowsIfOSIsNotWindowsOrOSX() {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(false);
        expect(osIdentifier.isMacintosh()).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(Platform.canMoveWindows()).isEqualTo(true);
      }
    }.run();
  }

  public void shouldReturnCannotMoveWindowsIfOSIsWindows() {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(Platform.canMoveWindows()).isEqualTo(false);
      }
    }.run();
  }

  public void shouldReturnCannotMoveWindowsIfOSIsOSX() {
    new EasyMockTemplate(osIdentifier) {
      protected void expectations() {
        expect(osIdentifier.isWindows()).andReturn(false);
        expect(osIdentifier.isMacintosh()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(Platform.canMoveWindows()).isEqualTo(false);
      }
    }.run();
  }
}
