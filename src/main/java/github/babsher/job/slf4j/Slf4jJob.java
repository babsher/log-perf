package github.babsher.job.slf4j;

import com.google.common.base.Stopwatch;
import github.babsher.config.Config;
import github.babsher.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Slf4jJob extends Job {

  /**
   * http://www.slf4j.org/faq.html#declared_static
   */
  private final Logger logger = LoggerFactory.getLogger(Slf4jJob.class);

  public Slf4jJob(Config config, Integer id) {
    super(config, id);
  }

  @Override
  public void logFatty(FatClass fatty) {
    logger.debug("{} has a fatty {}", this.id, fatty);
  }

  @Override
  public void logPiTotal(double pi) {
    logger.info("{} found pi to be {}", this.id, pi);
  }

  @Override
  public void logPiPart(double pi) {
    logger.trace("{} found a pi part {}", this.id, pi);
  }

  @Override
  public void logHash(String hash) {
    logger.info("{} found hash {}", this.id, hash);
  }

  @Override
  public void logElapsedTime(Stopwatch stopwatch) {
    logger.warn("{} completed in {}ms", this.id, stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }
}
