package github.babsher.job;

import github.babsher.config.Config;

public abstract class JobCreator {

  protected final Config config;

  public JobCreator(Config config) {
    this.config = config;
  }

  public abstract Job createJob(Integer id);
}
