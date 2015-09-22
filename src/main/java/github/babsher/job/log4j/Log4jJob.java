package github.babsher.job.log4j;

import com.google.common.base.Stopwatch;

import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

import github.babsher.config.Config;
import github.babsher.job.Job;

public class Log4jJob extends Job {

  private final Logger logger;

  public Log4jJob(Config config, Integer id, Logger logger) {
    super(config, id);
    this.logger = logger;
  }

  @Override
  public void logFatty(FatClass fatty) {
    logger.debug(this.id + " has a fatty " + fatty);
  }

  @Override
  public void logPiTotal(double pi) {
    logger.info(this.id + " found pi to be " + pi);
  }

  @Override
  public void logPiPart(double pi) {
    logger.trace(this.id + " found a pi part " + pi);
  }

  @Override
  public void logHash(String hash) {
    logger.info(this.id + " found hash " + hash);
  }

  @Override
  public void logElapsedTime(Stopwatch stopwatch) {
    logger.warn(this.id + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
  }
}
