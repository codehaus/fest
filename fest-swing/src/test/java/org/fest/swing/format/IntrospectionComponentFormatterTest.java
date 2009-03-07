/*
 * Created on Dec 22, 2007
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
package org.fest.swing.format;

import javax.swing.JButton;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link IntrospectionComponentFormatter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class IntrospectionComponentFormatterTest {

  private JButton button;
  private IntrospectionComponentFormatter formatter;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    button = button().withName("button")
                     .withText("Click Me")
                     .createNew();
    formatter = new IntrospectionComponentFormatter(JButton.class, "name", "text");
  }
  
  public void shouldFormatComponent() {
    String expected = concat(button.getClass().getName(), "[name='button', text='Click Me']");
    String formatted = formatter.format(button);
    assertThat(formatted).isEqualTo(expected);
  }
  
  public void shouldFormatEvenWithInvalidPropertyNames() {
    formatter = new IntrospectionComponentFormatter(JButton.class, "lastName", "text");
    String formatted = formatter.format(button);
    assertThat(formatted).contains("lastName=<Unable to read property")
                         .contains("text='Click Me'");
  }

  public void shouldFormatOneDimensionalArrayProperties() {
    MyButton myButton = MyButton.newButton(array("Luke", "Leia"));
    formatter = new IntrospectionComponentFormatter(MyButton.class, "names", "text");
    String formatted = formatter.format(myButton);
    assertThat(formatted).contains("names=['Luke', 'Leia']");
  }
  
  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    static MyButton newButton(final String[] names) {
      return GuiActionRunner.execute(new GuiQuery<MyButton>() {
        protected MyButton executeInEDT() {
          return new MyButton(names);
        }
      });
    }
    
    final String[] names;

    MyButton(String[] names) {
      this.names = names;
    }

    // to be called by introspector - do not remove
    public String[] getNames() {
      return names;
    }
  };

  public void shouldShowPropertyNamesInToString() {
    String s = formatter.toString();
    assertThat(s).contains("name").contains("text");
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTargetTypeIsNull() {
    formatter = new IntrospectionComponentFormatter(null, "name", "text");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentHasUnsupportedType() {
    formatter.format(comboBox().createNew());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfComponentIsNull() {
    formatter.format(null);
  }
  
  public void shouldFormatPropertyWithNameShowing() {
    formatter = new IntrospectionComponentFormatter(JButton.class, "showing");
    String formatted = formatter.format(button);
    String expected = concat(button.getClass().getName(), "[showing=false]");
    assertThat(formatted).isEqualTo(expected);
  }
}
