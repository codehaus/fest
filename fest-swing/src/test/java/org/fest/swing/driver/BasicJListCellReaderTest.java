/*
 * Created on Jun 3, 2009
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
package org.fest.swing.driver;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Collections.list;

import java.awt.Component;
import java.util.List;

import javax.swing.*;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BasicJListCellReader}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class BasicJListCellReaderTest {

  public void shouldThrowErrorIfCellRendererReaderIsNull() {
    try {
      new BasicJComboBoxCellReader(null);
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).isEqualTo("CellRendererReader should not be null");
    }
  }

  public void shouldReturnTextFromCellRendererComponent() {
    final JLabel editor = createMock(JLabel.class);
    final Mocks mocks = Mocks.mocksWithCellEditor(editor);
    final CellRendererReader cellRendererReader = mockCellRendererReader();
    final BasicJListCellReader reader = new BasicJListCellReader(cellRendererReader);
    new EasyMockTemplate(mocks.allMocksPlus(cellRendererReader)) {
      protected void expectations()  {
        mocks.expectToObtainEditorAtIndex(6);
        expect(cellRendererReader.valueFrom(editor)).andReturn("HELLO WORLD");
      }

      protected void codeToTest() {
        assertThat(reader.valueAt(mocks.list, 6)).isEqualTo("HELLO WORLD");
      }
    }.run();
  }

  public void shouldReturnTextFromElementIfCellRendererComponentIsNull() {
    final Mocks mocks = Mocks.mocksWithoutCellEditor();
    final CellRendererReader cellRendererReader = mockCellRendererReader();
    final BasicJListCellReader reader = new BasicJListCellReader(cellRendererReader);
    new EasyMockTemplate(mocks.allMocksPlus(cellRendererReader)) {
      protected void expectations()  {
        mocks.expectToObtainEditorAtIndex(6);
      }

      protected void codeToTest() {
        assertThat(reader.valueAt(mocks.list, 6)).isEqualTo("Hello World");
      }
    }.run();
  }

  private CellRendererReader mockCellRendererReader() {
    return createMock(CellRendererReader.class);
  }

  private static class Mocks {
    final JList list = createMock(JList.class);
    final ListModel model = createMock(ListModel.class);
    final ListCellRenderer cellRenderer = createMock(ListCellRenderer.class);
    final Component editor;

    static Mocks mocksWithCellEditor(Component editor) {
      return new Mocks(editor);
    }

    static Mocks mocksWithoutCellEditor() {
      return new Mocks(null);
    }

    Mocks(Component editor) {
      this.editor = editor;
    }

    void expectToObtainEditorAtIndex(int index) {
      Object element = new Element("Hello World");
      expect(list.getModel()).andReturn(model);
      expect(model.getElementAt(index)).andReturn(element);
      expect(list.getCellRenderer()).andReturn(cellRenderer);
      expect(cellRenderer.getListCellRendererComponent(list, element, index, true, true)).andReturn(editor);
    }

    Object[] allMocksPlus(Object... additionalMocks) {
      List<Object> allMocks = list(list, model, cellRenderer);
      allMocks.addAll(list(additionalMocks));
      return allMocks.toArray();
    }
  }

  private static class Element {
    private final String value;

    Element(String value) {
      this.value = value;
    }

    @Override public String toString() {
      return value;
    }
  }
}
