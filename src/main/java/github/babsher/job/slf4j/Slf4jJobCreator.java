package github.babsher.job.slf4j;

import github.babsher.config.Config;
import github.babsher.job.Job;
import github.babsher.job.JobCreator;

import javax.inject.Inject;

public class Slf4jJobCreator extends JobCreator {

  @Inject
  public Slf4jJobCreator(Config config) {
    super(config);
  }

  @Override
  public Job createJob(Integer id) {
    return new Slf4jJob(config, id);
  }
}
