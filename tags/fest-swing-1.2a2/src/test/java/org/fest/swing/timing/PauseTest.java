/*
 * Created on Dec 19, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.timing;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.util.StopWatch;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;
import static org.fest.swing.timing.Timeout.timeout;

/**
 * Tests for <code>{@link Pause}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class PauseTest {

  private static final int TIMEOUT = 1000;

  public void shouldSleepForTheGivenTime() {
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    Pause.pause(delay);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
  }
  
  public void shouldSleepForTheGivenTimeInUnits() {
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    Pause.pause(2, TimeUnit.SECONDS);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfUnitIsNull() {
    Pause.pause(2, null);
  }

  public void shouldWaitTillConditionIsTrue() {
    int timeToWaitTillSatisfied = TIMEOUT;
    SatisfiedCondition condition = new SatisfiedCondition(timeToWaitTillSatisfied);
    StopWatch watch = startNewStopWatch();
    Pause.pause(condition);
    watch.stop();
    assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
    assertThat(condition.satisfied).isTrue();
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeoutIfConditionIsNeverTrue() {
    Pause.pause(new NeverSatisfiedCondition());
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeoutWithGivenTimeoutObjectIfConditionIsNeverTrue() {
    Pause.pause(new NeverSatisfiedCondition(), timeout(TIMEOUT));
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeoutWithGivenTimeoutIfConditionIsNeverTrue() {
    Pause.pause(new NeverSatisfiedCondition(), TIMEOUT);
  }

  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionIfConditionIsNull() {
    Pause.pause(nullCondition());
  }
 
  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionWithGivenTimeoutObjectIfConditionIsNull() {
    Pause.pause(nullCondition(), timeout(TIMEOUT));
  }
  
  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionIfGivenTimeoutObjectIsNullAndConditionIsNot() {
    Pause.pause(new NeverSatisfiedCondition(), null);
  }

  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionWithGivenTimeoutIfConditionIsNull() {
    Pause.pause(nullCondition(), TIMEOUT);
  }

  private Condition nullCondition() {
    return null;
  }

  public void shouldWaitTillConditionsAreTrue() {
    int timeToWaitTillSatisfied = 1000;
    SatisfiedCondition one = new SatisfiedCondition(timeToWaitTillSatisfied);
    SatisfiedCondition two = new SatisfiedCondition(timeToWaitTillSatisfied);
    StopWatch watch = startNewStopWatch();
    Pause.pause(new Condition[] { one, two });
    watch.stop();
    assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
    assertThat(one.satisfied).isTrue();
    assertThat(two.satisfied).isTrue();
  }

  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeoutIfConditionsAreNeverTrue() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), new NeverSatisfiedCondition() });
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeoutWithGivenTimeoutObjectIfConditionsAreNeverTrue() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), new NeverSatisfiedCondition() }, timeout(TIMEOUT));
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeoutWithGivenTimeoutIfConditionsAreNeverTrue() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), new NeverSatisfiedCondition() }, TIMEOUT);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeoutWithGivenTimeoutIfOneOfConditionsIsNeverTrue() {
    Pause.pause(new Condition[] { new SatisfiedCondition(10), new NeverSatisfiedCondition() }, TIMEOUT);
  }

  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionIfConditionArrayIsNull() {
    Pause.pause(nullConditionArray());
  }

  @Test(expectedExceptions = IllegalArgumentException.class) 
  public void shouldThrowExceptionIfConditionArrayIsEmpty() {
    Pause.pause(emptyConditionArray());
  }
  
  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionIfAnyConditionInArrayIsNull() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), null });
  }

  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionWithGivenTimeoutObjectIfConditionArrayIsNull() {
    Pause.pause(nullConditionArray(), timeout(TIMEOUT));
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class) 
  public void shouldThrowExceptionWithGivenTimeoutObjectIfConditionArrayIsEmpty() {
    Pause.pause(emptyConditionArray(), timeout(TIMEOUT));
  }
  
  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionWithGivenTimeoutObjectIfAnyConditionInArrayIsNull() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), null }, timeout(TIMEOUT));
  }

  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionIfGivenTimeoutObjectIsNullAndConditionArrayIsNot() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition() }, null);
  }

  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionWithGivenTimeoutIfConditionArrayIsNull() {
    Pause.pause(nullConditionArray(), TIMEOUT);
  }

  @Test(expectedExceptions = IllegalArgumentException.class) 
  public void shouldThrowExceptionWithGivenTimeoutIfConditionArrayIsEmpty() {
    Pause.pause(emptyConditionArray(), TIMEOUT);
  }
  
  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowExceptionWithGivenTimeoutIfAnyConditionInArrayIsNull() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), null }, TIMEOUT);
  }

  private Condition[] nullConditionArray() {
    return null;
  }

  private Condition[] emptyConditionArray() {
    return new Condition[0];
  }

  private static class SatisfiedCondition extends Condition {
    boolean satisfied;
    private final int timeToWaitTillSatisfied;
    
    public SatisfiedCondition(int timeToWaitTillSatisfied) {
      super("Satisfied condition");
      this.timeToWaitTillSatisfied = timeToWaitTillSatisfied;
    }

    public boolean test() {
      try {
        Thread.sleep(timeToWaitTillSatisfied);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      satisfied = true;
      return satisfied;
    }
  };
  
  private static class NeverSatisfiedCondition extends Condition {
    public NeverSatisfiedCondition() {
      super("Never satisfied");
    }

    public boolean test() {
      return false;
    }
  };    
}
