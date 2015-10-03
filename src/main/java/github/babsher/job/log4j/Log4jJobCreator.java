package github.babsher.job.log4j;

import org.apache.log4j.Logger;

import github.babsher.config.Config;
import github.babsher.job.Job;
import github.babsher.job.JobCreator;

import javax.inject.Inject;

public class Log4jJobCreator extends JobCreator {

  private final Logger logger;

  @Inject
  public Log4jJobCreator(Config config, Logger logger) {
    super(config);
    this.logger = logger;
  }

  @Override
  public Job createJob(Integer id) {
    return new Log4jJob(config, id, logger);
  }
}
