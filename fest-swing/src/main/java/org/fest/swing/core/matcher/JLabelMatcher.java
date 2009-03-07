/*
 * Created on Jul 17, 2008
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
package org.fest.swing.core.matcher;

import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInCurrentThread;

import static java.lang.String.valueOf;

import static org.fest.util.Strings.concat;

/**
 * Understands matching a <code>{@link JLabel}</code> by type, name or text.
 *
 * @author Alex Ruiz 
 */
public class JLabelMatcher extends NamedComponentMatcherTemplate<JLabel> {

  private Object text;
  
  /**
   * Creates a new <code>{@link JLabelMatcher}</code> that matches a <code>{@link JLabel}</code> that:
   * <ol>
   * <li>has a matching name</li>
   * <li>(optionally) has matching text</li>
   * <li>(optionally) is showing on the screen</li>
   * <p>
   * The following code listing shows how to match a <code>{@link JLabel}</code> by name and text:
   * <pre>
   * JLabelMatcher m = {@link #withName(String) withName}("ok").{@link #andText(String) andText}("OK");
   * </pre> 
   * </p>
   * <p>
   * The following code listing shows how to match a <code>{@link JLabel}</code>, that should be showing on the screen,
   * by name and text:
   * <pre>
   * JLabelMatcher m = {@link #withName(String) withName}("ok").{@link #andText(String) andText}("OK").{@link #andShowing() andShowing}();
   * </pre> 
   * </p>
   * @param name the id to match.
   * @return the created matcher.
   */
  public static JLabelMatcher withName(String name) {
    return new JLabelMatcher(name, ANY);
  }
  
  /**
   * Creates a new <code>{@link JLabelMatcher}</code> that matches a <code>{@link JLabel}</code> by its text. 
   * @param text the text to match.
   * @return the created matcher.
   */
  public static JLabelMatcher withText(String text) {
    return new JLabelMatcher(ANY, text);
  }
  
  /**
   * Creates a new <code>{@link JLabelMatcher}</code> that matches any <code>{@link JLabel}</code>. 
   * @return the created matcher.
   */
  public static JLabelMatcher any() {
    return new JLabelMatcher(ANY, ANY);
  }
  
  private JLabelMatcher(Object name, Object text) {
    super(JLabel.class, name);
    this.text = text;
  }

  /**
   * Specifies the text to match. If this matcher was created using <code>{@link #withText(String)}</code>, this method
   * will simply update the text to match.
   * @param newText the new text to match.
   * @return this matcher.
   */
  public JLabelMatcher andText(String newText) {
    text = newText;
    return this;
  }
  
  /**
   * Indicates that the <code>{@link JLabel}</code> to match should be showing on the screen.
   * @return this matcher.
   */
  public JLabelMatcher andShowing() {
    requireShowing(true);
    return this;
  }

  /**
   * Indicates whether the text of the given <code>{@link JLabel}</code> is equal to the text in this matcher.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param button the <code>JLabel</code> to match.
   * @return <code>true</code> if the text in the <code>JLabel</code> is equal to the text in this matcher, 
   * <code>false</code> otherwise.
   */
  @RunsInCurrentThread
  protected boolean isMatching(JLabel button) {
    if (!isNameMatching(button.getName())) return false;
    return arePropertyValuesMatching(text, button.getText());
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "name=", quotedName(), ", ",
        "text=", quoted(text), ", ",
        "requireShowing=", valueOf(requireShowing()), 
        "]"
    );
  }
}
