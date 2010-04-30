/*
 * Created on 2010-4-22
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
 * Copyright @2007-2010 the original author or authors.
 */

package org.fest.assertions;

/**
 * Test ensuring that {@link ByteAssert} obeys the {@link GenericAssert#isNot(Condition)} contract for {@link Byte}.
 *
 * @author Ansgar Konermann
 */
public class ByteAssert_isNot_Test extends GenericAssert_isNot_TestBase<Byte> {

  @Override
  protected NotNull<Byte> createNotNullCondition() {
    return NotNull.instance();
  }

  @Override
  protected ByteAssert createInstanceFromNullReference() {
    return new ByteAssert(null);
  }

  @Override
  protected ByteAssert createInstanceRepresentingZero() {
    return new ByteAssert((byte) 1);
  }

  @Override
  protected String createStringRepresentationOfZero() {
    return "1";
  }
}