package github.babsher.job.sysout;

import javax.inject.Inject;

import github.babsher.config.Config;
import github.babsher.job.Job;
import github.babsher.job.JobCreator;

public class SysOutJobCreator extends JobCreator {

  @Inject
  public SysOutJobCreator(Config config) {
    super(config);
  }

  @Override
  public Job createJob(Integer id) {
    return new SysOutJob(config, id);
  }
}
