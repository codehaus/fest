/*
 * Created on Jul 3, 2007
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
package org.fest.swing.fixture;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;
import static org.easymock.EasyMock.expectLastCall;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseClickInfo.middleButton;
import static org.fest.swing.timing.Timeout.timeout;

import java.awt.Component;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.timing.Timeout;


/**
 * Understands test methods for implementations of <code>{@link CommonComponentFixture}</code>.
 * @param <T> the type of component supported by the fixture to test. 
 *
 * @author Alex Ruiz
 */
public abstract class CommonComponentFixtureTestCase<T extends Component> extends ComponentFixtureTestCase<T> 
    implements KeyboardInputSimulationFixtureTestCase, StateVerificationFixtureTestCase {

  public void shouldClick() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().click(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().click());
      }
    }.run();
  }
  
  public void shouldClickUsingGivenMouseButton() {
    final MouseButton button = MIDDLE_BUTTON;
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().click(target(), button);
        expectLastCall().once();
      } 

      protected void codeToTest() {
        assertThatReturnsThis(fixture().click(button));
      }
    }.run();
  }

  public void shouldClickUsingGivenMouseClickInfo() {
    final MouseClickInfo mouseClickInfo = middleButton().times(2);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().click(target(), mouseClickInfo);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().click(mouseClickInfo));
      }
    }.run();
  }

  public void shouldDoubleClick() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().doubleClick(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().doubleClick());
      }
    }.run();
  }

  public void shouldRightClick() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().rightClick(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().rightClick());
      }
    }.run();
  }
  
  public void shouldGiveFocus() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().focus(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().focus());
      }
    }.run();
  }

  public void should_press_and_release_key() {
    final KeyPressInfo keyPressInfo = keyCode(VK_A).modifiers(SHIFT_MASK);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().pressAndReleaseKey(target(), keyPressInfo);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().pressAndReleaseKey(keyPressInfo));
      }
    }.run();
  }

  public void should_press_and_release_keys() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().pressAndReleaseKeys(target(), VK_A, VK_B, VK_C);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().pressAndReleaseKeys(VK_A, VK_B, VK_C));
      }
    }.run();
  }

  public void should_press_key() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().pressKey(target(), VK_A);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().pressKey(VK_A));
      }
    }.run();
  }

  public void should_release_key() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().releaseKey(target(), VK_A);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().releaseKey(VK_A));
      }
    }.run();
  }

  public void should_require_disabled() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireDisabled(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().requireDisabled());
      }
    }.run();
  }
  
  public void should_require_enabled() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireEnabled(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().requireEnabled());
      }
    }.run();
  }

  public void should_require_enabled_using_timeout() {
    final Timeout timeout = timeout(2000);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireEnabled(target(), timeout);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().requireEnabled(timeout));
      }
    }.run();
  }

  public void should_require_not_visible() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireNotVisible(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().requireNotVisible());
      }
    }.run();
  }

  public void should_require_visible() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireVisible(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().requireVisible());
      }
    }.run();
  }
  
  public void should_require_focused() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireFocused(target());
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture().requireFocused());
      }
    }.run();
  }

  final void assertThatReturnsThis(Object result) {
    assertThat(result).isSameAs(fixture());
  }

  abstract CommonComponentFixture fixture(); 
}
