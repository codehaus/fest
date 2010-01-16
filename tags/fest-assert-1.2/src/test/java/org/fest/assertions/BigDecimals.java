/*
 * Created on Sep 5, 2009
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
package org.fest.assertions;

import java.math.BigDecimal;

/**
 * Understands some <code>{@link BigDecimal}</code> values used for testing.
 *
 * @author Alex Ruiz
 */
final class BigDecimals {

  private static final BigDecimal EIGHT = new BigDecimal("8.0");
  private static final BigDecimal NINE = new BigDecimal("9.0");
  private static final BigDecimal NEGATIVE_EIGHT = new BigDecimal(-8);

  static BigDecimal eight() {
    return EIGHT;
  }

  static BigDecimal nine() {
    return NINE;
  }

  static BigDecimal negativeEight() {
    return NEGATIVE_EIGHT;
  }

  private BigDecimals() {}
}