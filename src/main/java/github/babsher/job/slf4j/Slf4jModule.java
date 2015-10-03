package github.babsher.job.slf4j;

import dagger.Module;
import dagger.Provides;
import github.babsher.config.Config;
import github.babsher.job.JobCreator;

@Module
public class Slf4jModule {

  @Provides
  public JobCreator jobCreator(Config config) {
    return new Slf4jJobCreator(config);
  }
}
