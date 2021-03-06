/*
 * Created on Jan 23, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.javafx.maven;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.javafx.maven.CommonAssertions.failWhenExpectingMojoExecutionException;
import static org.fest.javafx.maven.JavaFxHomeDirectory.createJavaFxHomeDirectory;
import static org.fest.javafx.maven.JavaFxJarsInclusion.*;
import static org.fest.reflect.core.Reflection.field;
import static org.fest.util.Files.temporaryFolder;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;
import org.junit.*;

/**
 * Tests for <code>{@link JavaFxcFactory#createJavaFxc(java.io.File)}</code>.
 *
 * @author Alex Ruiz
 * @author Johannes Schneider
 */
public class JavaFxcFactory_createJavaFxc_Test {

  private JavaFxcFactory javaFxcFactory;

  @Before
  public void setUp() {
    javaFxcFactory = new JavaFxcFactory();
  }

  @Test
  public void should_create_JavaFxc() throws MojoExecutionException {
    Javac javaFxc = javaFxcFactory.createJavaFxc(createJavaFxHomeDirectory(), AUTOMATIC);
    assertThat(javaFxc).isNotNull();
    assertThat(javaFxc.getClass().getName()).isEqualTo("com.sun.tools.javafx.ant.JavaFxAntTask");
    assertThat(javaFxc.getProject()).isNotNull();
    Path compilerClassPath = field("compilerClassPath").ofType(Path.class).in(javaFxc).get();
    assertThat(compilerClassPath).isNotNull();
    assertThat(compilerClassPath.iterator().hasNext()).isTrue();
    assertThat(compilerClassPath.iterator().next()).toString().endsWith("javafxc.jar");
  }

  @Test
  public void should_create_JavaFxc_no_cp() {
    try {
      javaFxcFactory.createJavaFxc(createJavaFxHomeDirectory(), MANUAL);
    } catch (MojoExecutionException e) {
      assertThat(e.getMessage()).isEqualTo(
          "Unable to load JavaFX compiler Ant task. Please make sure javafxc.jar is in the classpath");
    }
  }

  @Test
  public void should_throw_error_if_JavaFX_compiler_Ant_task_cannot_be_instantiated() {
    try {
      javaFxcFactory.createJavaFxc(temporaryFolder(), AUTOMATIC);
      failWhenExpectingMojoExecutionException();
    } catch (MojoExecutionException e) {
      assertThat(e.getMessage()).contains("Unable to load JavaFX compiler Ant task.");
    }
  }
}
