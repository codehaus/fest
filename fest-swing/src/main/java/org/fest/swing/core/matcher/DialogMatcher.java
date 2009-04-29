/*
 * Created on Jul 16, 2008
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

import static java.lang.String.valueOf;
import static org.fest.util.Strings.concat;

import java.awt.Dialog;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Understands matching a <code>{@link Dialog}</code> whose title is equal to the provided one.
 *
 * @author Alex Ruiz
 */
public final class DialogMatcher extends NamedComponentMatcherTemplate<Dialog> {

  private Object title;

  /**
   * Creates a new <code>{@link DialogMatcher}</code> that matches a <code>{@link Dialog}</code> that:
   * <ol>
   * <li>has a matching name</li>
   * <li>(optionally) has matching title</li>
   * <li>(optionally) is showing on the screen</li>
   * <p>
   * The following code listing shows how to match a <code>{@link Dialog}</code> by name and title:
   * <pre>
   * DialogMatcher m = {@link #withName(String) withName}("ok").{@link #andTitle(String) andTitle}("OK");
   * </pre>
   * </p>
   * <p>
   * The following code listing shows how to match a <code>{@link Dialog}</code>, that should be showing on the screen,
   * by name and title:
   * <pre>
   * DialogMatcher m = {@link #withName(String) withName}("ok").{@link #andTitle(String) andTitle}("OK").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * @param name the id to match.
   * @return the created matcher.
   */
  public static DialogMatcher withName(String name) {
    return new DialogMatcher(name, ANY);
  }

  /**
   * Creates a new <code>{@link DialogMatcher}</code> that matches a <code>{@link Dialog}</code> by its title.
   * @param title the title to match.
   * @return the created matcher.
   */
  public static DialogMatcher withTitle(String title) {
    return new DialogMatcher(ANY, title);
  }

  /**
   * Creates a new <code>{@link DialogMatcher}</code> that matches any <code>{@link Dialog}</code>.
   * @return the created matcher.
   */
  public static DialogMatcher any() {
    return new DialogMatcher(ANY, ANY);
  }

  private DialogMatcher(Object name, Object title) {
    super(Dialog.class, name);
    this.title = title;
  }

  /**
   * Specifies the title to match. If this matcher was created using <code>{@link #withTitle(String)}</code>, this method
   * will simply update the title to match.
   * @param newTitle the new title to match.
   * @return this matcher.
   */
  public DialogMatcher andTitle(String newTitle) {
    title = newTitle;
    return this;
  }

  /**
   * Indicates that the <code>{@link Dialog}</code> to match should be showing on the screen.
   * @return this matcher.
   */
  public DialogMatcher andShowing() {
    requireShowing(true);
    return this;
  }

  /**
   * Indicates whether the title of the given <code>{@link Dialog}</code> is equal to the title in this matcher.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for
   * invoking this method in the EDT.
   * </p>
   * @param dialog the <code>Dialog</code> to match.
   * @return <code>true</code> if the title in the <code>Dialog</code> is equal to the title in this matcher,
   * <code>false</code> otherwise.
   */
  @RunsInCurrentThread
  protected boolean isMatching(Dialog dialog) {
    if (!isNameMatching(dialog.getName())) return false;
    return arePropertyValuesMatching(title, dialog.getTitle());
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "name=", quotedName(), ", ",
        "title=", quoted(title), ", ",
        "requireShowing=", valueOf(requireShowing()),
        "]"
    );
  }
}
