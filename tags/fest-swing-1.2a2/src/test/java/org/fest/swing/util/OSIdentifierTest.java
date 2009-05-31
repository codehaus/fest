/*
 * Created on May 16, 2009
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
package org.fest.swing.util;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link OSIdentifier}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class OSIdentifierTest {

  private SystemPropertyReader propertyReader;

  @BeforeMethod public void setUp() {
    propertyReader = createMock(SystemPropertyReader.class);
  }

  @Test(dataProvider = "windows")
  public void shouldReturnWindowsIfOSNameStartsWithWindows(final String windows) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(windows);
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isWindows()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
        assertThat(osIdentifier.isX11()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "windows") public Object[][] windows() {
    return new Object[][] { { "windows" }, { "Windows" }, { "WINDOWS" } };
  }

  @Test(dataProvider = "windows9x")
  public void shouldReturnWindows9xIfOSNameStartsWithWindowsAndContainsAny9xVersion(final String windows) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(windows);
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isWindows()).isTrue();
        assertThat(osIdentifier.isWindows9x()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
        assertThat(osIdentifier.isX11()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "windows9x") public Object[][] windows9x() {
    return new Object[][] { { "windows95" }, { "Windows98" }, { "WINDOWSME" } };
  }

  @Test(dataProvider = "windowsXP")
  public void shouldReturnWindowsXPIfOSNameStartsWithWindowsAndContainsXP(final String windows) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(windows);
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isWindows()).isTrue();
        assertThat(osIdentifier.isWindowsXP()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isX11()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "windowsXP") public Object[][] windowsXP() {
    return new Object[][] { { "windowsxp" }, { "WindowsXP" }, { "WINDOWSXP" } };
  }

  public void shouldReturnMacintoshIfMRJVersionIsNotNull() {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn("");
        expect(propertyReader.systemProperty("mrj.version")).andReturn("6");
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isMacintosh()).isTrue();
        assertThat(osIdentifier.isX11()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
      }
    }.run();
  }

  @Test(dataProvider = "osX")
  public void shouldReturnOSXIfMRJVersionNotNullAndOSNameContainsOSX(final String osX) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(osX);
        expect(propertyReader.systemProperty("mrj.version")).andReturn("6");
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isMacintosh()).isTrue();
        assertThat(osIdentifier.isOSX()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
        assertThat(osIdentifier.isX11()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "osX") public Object[][] osX() {
    return new Object[][] { { "os x" }, { "OS X" } };
  }

  public void shouldReturnX11IfOSNotOSXOrWindows() {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn("");
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isX11()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
      }
    }.run();
  }

  @Test(dataProvider = "solaris")
  public void shouldReturnSolarisIfOSNameStartsWithSunOSOrSolaris(final String solaris) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(solaris);
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isSolaris()).isTrue();
        assertThat(osIdentifier.isX11()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isWindows()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "solaris") public Object[][] solaris() {
    return new Object[][] { { "sunos" }, { "SunOS" }, { "SUNOS" }, { "solaris" }, { "Solaris" }, { "SOLARIS" } };
  }

  @Test(dataProvider = "hpUX")
  public void shouldReturnHPUXIfOSNameIsEqualToHPUX(final String hpUX) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(hpUX);
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isHPUX()).isTrue();
        assertThat(osIdentifier.isX11()).isTrue();
        assertThat(osIdentifier.isLinux()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "hpUX") public Object[][] hpUX() {
    return new Object[][] { { "hp-ux" }, { "HP-UX" }, { "Hp-Ux" } };
  }

  @Test(dataProvider = "linux")
  public void shouldReturnLinuxIfOSNameIsEqualToLinux(final String linux) {
    new EasyMockTemplate(propertyReader) {
      protected void expectations() {
        expect(propertyReader.systemProperty("os.name")).andReturn(linux);
        expect(propertyReader.systemProperty("mrj.version")).andReturn(null);
      }

      protected void codeToTest() {
        OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
        assertThat(osIdentifier.isLinux()).isTrue();
        assertThat(osIdentifier.isX11()).isTrue();
        assertThat(osIdentifier.isHPUX()).isFalse();
        assertThat(osIdentifier.isMacintosh()).isFalse();
        assertThat(osIdentifier.isOSX()).isFalse();
        assertThat(osIdentifier.isSolaris()).isFalse();
        assertThat(osIdentifier.isWindows()).isFalse();
        assertThat(osIdentifier.isWindows9x()).isFalse();
        assertThat(osIdentifier.isWindowsXP()).isFalse();
      }
    }.run();
  }

  @DataProvider(name = "linux") public Object[][] linux() {
    return new Object[][] { { "linux" }, { "Linux" }, { "LINUX" } };
  }
}
