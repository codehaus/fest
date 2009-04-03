/*
 * Created on Jun 6, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;
import static org.fest.swing.junit.ant.Tests.testClassNameFrom;
import static org.fest.swing.junit.ant.Tests.testMethodNameFrom;
import static org.fest.util.Strings.isEmpty;

import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.*;
import org.w3c.dom.*;

/**
 * Understands a copy of the original <code>XMLJUnitResultFormatter</code>, with flexibility for extension.
 *
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter implements JUnitResultFormatter {

  /** Constant for unnamed test suites/cases */
  private static final String UNKNOWN = "unknown";

  private static DocumentBuilder documentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (Exception exc) {
      throw new ExceptionInInitializerError(exc);
    }
  }

  /** The XML document. */
  private Document document;

  /** The wrapper for the whole test suite. */
  private Element rootElement;

  /** Where to write the log to. */
  private OutputStream out;

  private final TestCollection tests;
  private final XmlElementWriter startSuiteXmlWriter;
  private final XmlElementWriter endSuiteXmlWriter;
  private final XmlOutputWriter xmlOutputWriter;

  /**
   * Creates a new </code>{@link XmlJUnitResultFormatter}</code>.
   */
  public XmlJUnitResultFormatter() {
    tests = new TestCollection();
    startSuiteXmlWriter = new SuiteNameWriter().then(
                            new TimestampWriter().then(
                                new HostNameWriter().then(
                                    new SuitePropertiesWriter())));
    endSuiteXmlWriter = new SuiteStatisticsWriter();
    xmlOutputWriter = new XmlOutputWriter();
  }

  /**
   * Sets the stream the formatter is supposed to write its results to.
   * @param out the output stream to use.
   */
  public final void setOutput(OutputStream out) {
    this.out = out;
  }

  /**
   * This is what the test has written to <code>System.out</code>,
   * @param out the <code>String</code> to write.
   */
  public final void setSystemOutput(String out) {
     formatOutput(SYSTEM_OUT, out);
  }

  /**
   * This is what the test has written to <code>System.err</code>.
   * @param out the <code>String</code> to write.
   */
  public final void setSystemError(String out) {
    formatOutput(SYSTEM_ERR, out);
  }

  private void formatOutput(String type, String output) {
    Element nested = document.createElement(type);
    rootElement.appendChild(nested);
    nested.appendChild(document.createCDATASection(output));
  }

  protected final Document document() { return document; }
  protected final Element rootElement() { return rootElement; }

  /**
   * The whole test suite started. This method starts creation of the XML report.
   * @param suite the test suite.
   */
  public final void startTestSuite(JUnitTest suite) {
    document = documentBuilder().newDocument();
    rootElement = document.createElement(TESTSUITE);
    startSuiteXmlWriter.write(document, rootElement, suite);
    onStartTestSuite(suite);
  }

  /**
   * Hook for subclasses to add extra functionality after the whole test suite started.
   * @param suite the test suite.
   */
  protected void onStartTestSuite(JUnitTest suite) {}

  /**
   * The whole test suite ended. This method finishes writing the XML report and writes its contents to this
   * formatter's <code>{@link OutputStream}</code>.
   * @param suite the test suite.
   * @throws BuildException on error.
   */
  public final void endTestSuite(JUnitTest suite) {
    endSuiteXmlWriter.write(document, rootElement, suite);
    if (out == null) return;
    xmlOutputWriter.write(rootElement, out);
  }

  /**
   * A new test is started.
   * @param test the test.
   */
  public final void startTest(Test test) {
    tests.started(test);
  }

  /**
   * A test is finished.
   * @param test the test.
   */
  public final void endTest(Test test) {
    if (!tests.wasStarted(test)) startTest(test);
    writeExecutionTime(test, xmlForFinished(test));
  }

  private Element xmlForFinished(Test test) {
    if (!tests.wasFailed(test)) return createAndAddCurrentTest(test);
    return tests.xmlFor(test);
  }

  private Element createAndAddCurrentTest(Test test) {
    Element e = document.createElement(TESTCASE);
    String methodName = testMethodNameFrom(test);
    e.setAttribute(ATTR_NAME, methodName == null ? UNKNOWN : methodName);
    e.setAttribute(ATTR_CLASSNAME, testClassNameFrom(test));
    rootElement.appendChild(e);
    return tests.addXml(test, e);
  }

  private void writeExecutionTime(Test test, Element currentTest) {
    long s = tests.startTimeOf(test);
    double executionTime = (currentTimeMillis() - s) / 1000.0;
    currentTest.setAttribute(ATTR_TIME, valueOf(executionTime));
  }

  /**
   * A test failed.
   * @param test the test.
   * @param error the exception.
   */
  public final void addFailure(Test test, Throwable error) {
    Element errorElement = formatError(FAILURE, test, error);
    onFailureOrError(test, error, errorElement);
  }

  /**
   * A test failed.
   * @param test the test.
   * @param failedAssertion the failed assertion.
   */
  public final void addFailure(Test test, AssertionFailedError failedAssertion) {
    addFailure(test, (Throwable)failedAssertion);
  }

  /**
   * An error occurred while running the test.
   * @param test the test.
   * @param error the error.
   */
  public final void addError(Test test, Throwable error) {
    Element errorElement = formatError(ERROR, test, error);
    onFailureOrError(test, error, errorElement);
  }

  private Element formatError(String type, Test test, Throwable error) {
    if (test != null) {
      endTest(test);
      tests.failed(test);
    }
    Element errorElement = document.createElement(type);
    xmlForFailed(test).appendChild(errorElement);
    writeErrorAndStackTrace(error, errorElement);
    return errorElement;
  }

  private Element xmlForFailed(Test test) {
    if (test != null) return tests.xmlFor(test);
    return rootElement;
  }

  protected final void writeErrorAndStackTrace(Throwable error, Element errorElement) {
    writeError(error, errorElement);
    writeStackTrace(error, errorElement);
  }

  private void writeError(Throwable error, Element destination) {
    String message = error.getMessage();
    if (!isEmpty(message)) destination.setAttribute(ATTR_MESSAGE, error.getMessage());
    destination.setAttribute(ATTR_TYPE, error.getClass().getName());
  }

  private final void writeStackTrace(Throwable error, Element destination) {
    String stackTrace = JUnitTestRunner.getFilteredTrace(error);
    writeText(stackTrace, destination);
  }

  protected final void writeText(String text, Element destination) {
    Text textNode = document.createTextNode(text);
    destination.appendChild(textNode);
  }

  protected void onFailureOrError(Test test, Throwable error, Element errorElement) {}
}
