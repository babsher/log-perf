package github.babsher.job.sysout;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

import github.babsher.config.Config;
import github.babsher.job.Job;

public class SysOutJob extends Job {

  public SysOutJob(Config config, Integer id) {
    super(config, id);
  }

  @Override
  public void logFatty(FatClass fatty) {
    if(check(Level.Debug)) {
      System.out.println(this.id + " has a fatty " + fatty);
    }
  }

  @Override
  public void logPiTotal(double pi) {
    if(check(Level.Info)) {
      System.out.println(this.id + " found pi to be " + pi);
    }
  }

  @Override
  public void logPiPart(double pi) {
    if(check(Level.Trace)) {
      System.out.println(this.id + " found a pi part " + pi);
    }
  }

  @Override
  public void logHash(String hash) {
    if(check(Level.Info)) {
      System.out.println(this.id + " found hash " + hash);
    }
  }

  @Override
  public void logElapsedTime(Stopwatch stopwatch) {
    if(check(Level.Warn)) {
      System.out.println(this.id + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }
  }

  private boolean check(Level l) {
    return l.value >= this.config.getLevel().value;
  }
}
