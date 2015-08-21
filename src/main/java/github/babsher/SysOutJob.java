package github.babsher;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public class SysOutJob extends Job {

  public SysOutJob(Integer id, Level logLevel) {
    super(id, logLevel);
  }

  @Override
  void logFatty(FatClass fatty) {
    if(check(Level.Debug)) {
      System.out.println(this.id + " has a fatty " + fatty);
    }
  }

  @Override
  void logPiTotal(double pi) {
    if(check(Level.Info)) {
      System.out.println(this.id + " found pi to be " + pi);
    }
  }

  @Override
  void logPiPart(double pi) {
    if(check(Level.Trace)) {
      System.out.println(this.id + " found a pi part " + pi);
    }
  }

  @Override
  void logHash(String hash) {
    if(check(Level.Info)) {
      System.out.println(this.id + " found hash " + hash);
    }
  }

  @Override
  void logElapsedTime(Stopwatch stopwatch) {
    if(check(Level.Info)) {
      System.out.println(this.id + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }
  }

  private boolean check(Level l) {
    return l.value >= logLevel.value;
  }
}
