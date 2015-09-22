package github.babsher.job.sysout;

import dagger.Module;
import dagger.Provides;
import github.babsher.config.Config;
import github.babsher.job.JobCreator;

@Module
public class SysOutModule {

  @Provides
  public JobCreator jobCreator(Config config) {
    return new SysOutJobCreator(config);
  }
}