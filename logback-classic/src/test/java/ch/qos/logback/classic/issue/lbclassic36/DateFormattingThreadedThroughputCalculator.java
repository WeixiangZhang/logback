package ch.qos.logback.classic.issue.lbclassic36;

import ch.qos.logback.classic.issue.lbclassic36.SelectiveDateFormattingRunnable.FormattingModel;
import ch.qos.logback.core.issue.ThreadedThroughputCalculator;

/**
 * Measure the threaded throughtput of date formatting operations
 * 
 * @author Joern Huxhorn
 * @author Ceki Gulcu
 */
public class DateFormattingThreadedThroughputCalculator {

  static int THREAD_COUNT = 16;
  static long OVERALL_DURATION_IN_MILLIS = 3000;

  public static void main(String args[]) throws InterruptedException {

    ThreadedThroughputCalculator tp = new ThreadedThroughputCalculator(
        OVERALL_DURATION_IN_MILLIS);
    tp.printEnvironmentInfo("DateFormatting");

    for (int i = 0; i < 2; i++) {
      tp.execute(buildArray(FormattingModel.SDF));
      tp.execute(buildArray(FormattingModel.JODA));
    }

    tp.execute(buildArray(FormattingModel.JODA));
    tp.printThroughput("JODA: ");
    
    tp.execute(buildArray(FormattingModel.SDF));
    tp.printThroughput("SDF:  ");


  }

  static SelectiveDateFormattingRunnable[] buildArray(FormattingModel model) {
    SelectiveDateFormattingRunnable[] array = new SelectiveDateFormattingRunnable[THREAD_COUNT];
    for (int i = 0; i < THREAD_COUNT; i++) {
      array[i] = new SelectiveDateFormattingRunnable(model);
    }
    return array;
  }

}