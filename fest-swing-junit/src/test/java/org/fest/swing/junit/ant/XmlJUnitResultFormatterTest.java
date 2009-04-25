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

import junit.framework.AssertionFailedError;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.util.DOMElementWriter;
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

  private static final String ERROR_OR_FAILURE_MESSAGE = "Thrown on purpose";
  private static final String CONSOLE_OUTPUT = "Hello World!";

  private ByteArrayOutputStream output;
  private BasicXmlJUnitResultFormatter formatter;
  private TestCollection tests;

  @BeforeMethod public void setUp() throws Exception {
    formatter = new BasicXmlJUnitResultFormatter();
    tests = formatter.tests();
    output = new ByteArrayOutputStream();
    formatter.setOutput(output);
  }

  public void shouldAddSystemOutputToXml() {
    formatter.startTestSuite(new JUnitTest("test"));
    formatter.setSystemOutput(CONSOLE_OUTPUT);
    XmlNode systemOutNode = root().child(1);
    assertThat(systemOutNode.name()).isEqualTo("system-out");
    assertThat(systemOutNode.text()).isEqualTo(CONSOLE_OUTPUT);
  }

  public void shouldAddSystemErrorToXml() {
    formatter.startTestSuite(new JUnitTest("test"));
    formatter.setSystemError(CONSOLE_OUTPUT);
    XmlNode systemErrNode = root().child(1);
    assertThat(systemErrNode.name()).isEqualTo("system-err");
    assertThat(systemErrNode.text()).isEqualTo(CONSOLE_OUTPUT);
  }

  public void shouldWriteSuiteAndEnvironmentInfoAndCallSubclassHookWhenStartingTestSuite() {
    JUnitTest suite = new JUnitTest("test");
    formatter.startTestSuite(suite);
    XmlNode root = root();
    assertThat(root.attributeCount()).isEqualTo(3);
    assertSuiteAndEnvironmentInfoAddedTo(root);
    assertNoPropertiesIn(root);
    assertThat(formatter.onStartTestSuiteMethod).wasCalledPassing(suite);
  }

  public void shouldWriteSuiteStatisticsAndWriteXmlToOutputStreamWhenEndingTestSuite() {
    JUnitTest suite = startTestSuiteWithStatistics();
    formatter.endTestSuite(suite);
    XmlNode root = assertStatisticsAddedToXml();
    assertNoPropertiesIn(root);
    assertThat(textIn(output)).isEqualTo(textOf(root));
  }

  public void shouldNotThrowErrorIfOutputIsNull() {
    formatter.setOutput(null);
    JUnitTest suite = startTestSuiteWithStatistics();
    formatter.endTestSuite(suite);
    XmlNode root = assertStatisticsAddedToXml();
    assertNoPropertiesIn(root);
    assertThat(output.toByteArray()).isEmpty();
  }

  private XmlNode assertStatisticsAddedToXml() {
    XmlNode root = root();
    assertThat(root.attributeCount()).isEqualTo(7);
    assertThat(root.valueOfAttribute("errors")).isEqualTo("6");
    assertThat(root.valueOfAttribute("failures")).isEqualTo("8");
    assertThat(root.valueOfAttribute("tests")).isEqualTo("18");
    double time = parseDouble(root.valueOfAttribute("time"));
    assertThat(time).isGreaterThanOrEqualTo(0);
    assertSuiteAndEnvironmentInfoAddedTo(root);
    return root;
  }

  private JUnitTest startTestSuiteWithStatistics() {
    JUnitTest suite = startSuite();
    suite.setCounts(18, 8, 6);
    return suite;
  }

  private static String textOf(XmlNode xml) {
    ByteArrayOutputStream o = new ByteArrayOutputStream();
    new XmlOutputWriter().write(xml, o, new DOMElementWriter());
    return textIn(o);
  }

  private static String textIn(ByteArrayOutputStream o) {
    return new String(o.toByteArray());
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
    assertTestCaseNodeWasAddedTo(root());
  }

  public void shouldWriteTestExecutionEndWhenTestFails() {
    startSuite();
    junit.framework.Test test = mockTest();
    AssertionFailedError error = errorOrFailure();
    formatter.addFailure(test, error);
    XmlNode root = root();
    assertTestCaseNodeWasAddedTo(root);
    XmlNode failureNode = firstTestCaseNodeIn(root).child(0);
    assertThat(failureNode.name()).isEqualTo("failure");
    assertErrorOrFailureWrittenTo(failureNode);
    assertThat(formatter.onFailureOrErrorMethod).wasCalledPassing(test, error, failureNode);
  }

  public void shouldWriteTestExecutionEndWhenErrorThrown() {
    startSuite();
    junit.framework.Test test = mockTest();
    AssertionFailedError error = errorOrFailure();
    formatter.addError(test, error);
    XmlNode root = root();
    assertTestCaseNodeWasAddedTo(root);
    XmlNode errorNode = firstTestCaseNodeIn(root).child(0);
    assertThat(errorNode.name()).isEqualTo("error");
    assertErrorOrFailureWrittenTo(errorNode);
    assertThat(formatter.onFailureOrErrorMethod).wasCalledPassing(test, error, errorNode);
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

  private AssertionFailedError errorOrFailure() {
    return new AssertionFailedError(ERROR_OR_FAILURE_MESSAGE);
  }

  private XmlNode root() {
    return formatter.xmlRootNode();
  }

  private void assertTestCaseNodeWasAddedTo(XmlNode root) {
    XmlNode testNode = firstTestCaseNodeIn(root);
    assertThat(testNode.name()).isEqualTo("testcase");
    assertThat(testNode.valueOfAttribute("classname")).startsWith("$Proxy");
    assertThat(testNode.valueOfAttribute("name")).isEqualTo("unknown");
    double executionTime = Double.parseDouble(testNode.valueOfAttribute("time"));
    assertThat(executionTime).isGreaterThanOrEqualTo(0);
  }

  private XmlNode firstTestCaseNodeIn(XmlNode root) {
    return root.child(1);
  }

  private static void assertErrorOrFailureWrittenTo(XmlNode errorNode) {
    assertThat(errorNode.valueOfAttribute("message")).isEqualTo(ERROR_OR_FAILURE_MESSAGE);
    assertThat(errorNode.valueOfAttribute("type")).isEqualTo("junit.framework.AssertionFailedError");
    assertThat(errorNode.text()).startsWith("junit.framework.AssertionFailedError: " + ERROR_OR_FAILURE_MESSAGE);
  }

  private static class BasicXmlJUnitResultFormatter extends XmlJUnitResultFormatter implements AssertExtension {
    final OnStartTestSuiteAssert onStartTestSuiteMethod = new OnStartTestSuiteAssert();
    final OnFailureOrErrorAssert onFailureOrErrorMethod = new OnFailureOrErrorAssert();

    @Override
    protected void onStartTestSuite(JUnitTest suite) {
      onStartTestSuiteMethod.calledWith(suite);
    }

    @Override
    protected void onFailureOrError(junit.framework.Test test, Throwable error, XmlNode errorXmlNode) {
      onFailureOrErrorMethod.calledWith(test, error, errorXmlNode);
    }
  }

  private static class OnStartTestSuiteAssert implements AssertExtension {
    private boolean called;
    private JUnitTest suite;

    void calledWith(JUnitTest suitePassed) {
      called = true;
      suite = suitePassed;
    }

    void wasCalledPassing(JUnitTest expectedSuite) {
      assertThat(called).isTrue();
      assertThat(suite).isSameAs(expectedSuite);
    }
  }

  private static class OnFailureOrErrorAssert implements AssertExtension {
    private boolean called;
    private junit.framework.Test test;
    private Throwable error;
    private XmlNode errorXmlNode;

    void calledWith(junit.framework.Test testPassed, Throwable errorPassed, XmlNode errorXmlNodePassed) {
      errorXmlNode = errorXmlNodePassed;
      called = true;
      test = testPassed;
      error = errorPassed;
    }

    void wasCalledPassing(junit.framework.Test expectedTest, Throwable expectedError, XmlNode expectedErrorXmlNode) {
      assertThat(called).isTrue();
      assertThat(test).isSameAs(expectedTest);
      assertThat(error).isSameAs(expectedError);
      assertThat(errorXmlNode).isEqualTo(expectedErrorXmlNode);
    }
  }
}
