/*
 * Created on Jul 12, 2008
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
package org.fest.swing.applet;

import java.applet.Applet;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicAppletContext}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test
public class BasicAppletContextTest {

  private BasicAppletContext context;
  private StatusDisplay statusDisplay;
  
  @BeforeClass public void setUp() {
    statusDisplay = createMock(StatusDisplay.class);
    context = new BasicAppletContext(statusDisplay);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfStatusDisplayIsNull() {
    new BasicAppletContext(null);
  }
  
  public void shouldReturnNullAppletForAnyName() {
    assertThat(context.getApplet("aName")).isNull();
  }
  
  public void shouldReturnEmptyEnumerationOfApplets() {
    Enumeration<Applet> applets = context.getApplets();
    assertThat(applets.hasMoreElements()).isFalse();
    assertThat(applets.nextElement()).isNull();
  }
  
  public void shouldReturnNullAudioClip() {
    assertThat(context.getAudioClip(null)).isNull();
  }
  
  public void shouldReturnNullImage() {
    assertThat(context.getImage(null)).isNull();
  }
  
  public void shouldSetAndGetStreams() {
    InputStream inputStream = createMock(InputStream.class);
    context.setStream("key1", inputStream);
    assertThat(context.getStream("key1")).isSameAs(inputStream);
    assertThat(streamKeys()).hasSize(1)
                            .containsOnly("key1");
  }
  
  private List<String> streamKeys() {
    Iterator<String> streamKeyIterator = context.getStreamKeys();
    List<String> streamKeys = new ArrayList<String>();
    while (streamKeyIterator.hasNext()) streamKeys.add(streamKeyIterator.next());
    return streamKeys;
  }
  
  public void shouldShowStatus() {
    final String status = "Hi";
    new EasyMockTemplate(statusDisplay) {
      protected void expectations() {
        statusDisplay.showStatus(status);
        expectLastCall().once();
      }

      protected void codeToTest() {
        context.showStatus(status);
      }
    }.run();
  }
}
