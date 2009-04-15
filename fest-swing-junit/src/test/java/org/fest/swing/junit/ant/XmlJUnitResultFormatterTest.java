/*
 * Created on Apr 13, 2009
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
package org.fest.swing.junit.ant;

import static java.lang.Double.parseDouble;
import static org.apache.tools.ant.util.DateUtils.parseIso8601DateTimeOrDate;
import static org.easymock.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.assertions.AssertExtension;
import org.fest.swing.junit.xml.XmlNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link XmlJUnitResultFormatter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class XmlJUnitResultFormatterTest {

  private ByteArrayOutputStream output;
  private BasicXmlJUnitResultFormatter formatter;
  private TestCollection tests;

  @BeforeMethod public void setUp() throws Exception {
    formatter = new BasicXmlJUnitResultFormatter();
    tests = formatter.tests();
    output = new ByteArrayOutputStream();
    formatter.setOutput(output);
  }

  public void shouldWriteSuiteAndEnvironmentInfoAndCallSubclassHookWhenStartingTestSuite() {
    JUnitTest suite = new JUnitTest("test");
    formatter.startTestSuite(suite);
    XmlNode root = formatter.xmlRootNode();
    assertThat(root.attributeCount()).isEqualTo(3);
    assertSuiteAndEnvironmentInfoAddedTo(root);
    assertNoPropertiesIn(root);
    assertThat(formatter.onStartTestSuiteMethod).wasCalledPassing(suite);
  }

  public void shouldWriteSuiteStatisticsAndWriteXmlToOutputStreamWhenEndingTestSuite() {
    JUnitTest suite = startSuite();
    suite.setCounts(18, 8, 6);
    formatter.endTestSuite(suite);
    XmlNode root = formatter.xmlRootNode();
    assertThat(root.attributeCount()).isEqualTo(7);
    assertThat(root.valueOfAttribute("errors")).isEqualTo("6");
    assertThat(root.valueOfAttribute("failures")).isEqualTo("8");
    assertThat(root.valueOfAttribute("tests")).isEqualTo("18");
    double time = parseDouble(root.valueOfAttribute("time"));
    assertThat(time).isGreaterThanOrEqualTo(0);
    assertSuiteAndEnvironmentInfoAddedTo(root);
    assertNoPropertiesIn(root);
  }

  private static void assertNoPropertiesIn(XmlNode root) {
    XmlNode properties = root.child(0);
    assertThat(properties.attributeCount()).isEqualTo(0);
    assertThat(properties.size()).isEqualTo(0);
  }

  private static void assertSuiteAndEnvironmentInfoAddedTo(XmlNode root) {
    assertThat(root.valueOfAttribute("hostname")).isEqualTo(localHostName());
    assertThat(root.valueOfAttribute("name")).isEqualTo("test");
    Date timestamp = parseDate(root.valueOfAttribute("timestamp"));
    assertThat(timestamp.before(now()));
  }

  private static Date parseDate(String s) {
    try {
      return parseIso8601DateTimeOrDate(s);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private static Date now() { return new Date(); }

  private static String localHostName() {
    try {
      return new HostNameReader().localHostName();
    } catch (UnknownHostException e) {
      return "localhost";
    }
  }

  public void shouldAddTestToListWhenTestStarted() {
    junit.framework.Test test = mockTest();
    formatter.startTest(test);
    assertTestWasStarted(test);
  }

  public void shouldWriteExecutionTimeForSuccessfulAndNotStartedTestWhenTestFinished() {
    startSuite();
    junit.framework.Test test = mockTest();
    formatter.endTest(test);
    assertTestWasStarted(test);
  }

  private JUnitTest startSuite() {
    JUnitTest suite = new JUnitTest("test");
    formatter.startTestSuite(suite);
    return suite;
  }

  private static junit.framework.Test mockTest() {
    return createMock(junit.framework.Test.class);
  }

  private void assertTestWasStarted(junit.framework.Test test) {
    Map<junit.framework.Test, Long> startedTests = tests.started;
    assertThat(startedTests).hasSize(1);
    assertThat(startedTests.keySet()).containsOnly(test);
  }


//  private void toString(XmlNode node) {
//    ByteArrayOutputStream o = new ByteArrayOutputStream();
//    new XmlOutputWriter().write(node, o);
//    System.out.println(new String(o.toByteArray()));
//  }

  private static class BasicXmlJUnitResultFormatter extends XmlJUnitResultFormatter implements AssertExtension {

    final OnStartTestSuiteAssert onStartTestSuiteMethod = new OnStartTestSuiteAssert();

    @Override
    protected void onStartTestSuite(JUnitTest suite) {
      onStartTestSuiteMethod.calledWith(suite);
    }

    @Override
    protected void onFailureOrError(junit.framework.Test test, Throwable error, XmlNode errorXmlNode) {
    }
  }

  private static class OnStartTestSuiteAssert implements AssertExtension {
    private boolean called;
    private JUnitTest suite;

    void calledWith(JUnitTest suitePassed) {
      called = true;
      suite = suitePassed;
    }

    void wasCalledPassing(JUnitTest expected) {
      assertThat(called).isTrue();
      assertThat(suite).isSameAs(expected);
    }
  }
}
