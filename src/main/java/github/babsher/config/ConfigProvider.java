package github.babsher.config;

import dagger.Module;
import dagger.Provides;
import github.babsher.job.Job;

@Module
public class ConfigProvider {

  static final Integer NUM_JOBS = 5000;
  static final Integer SIZE = 1000;
  static final Integer STRING_SIZE = 100;

  private final Config config;

  public ConfigProvider(String[] args, Job.Level level) {
    this.config = new Config(args, NUM_JOBS, SIZE, STRING_SIZE, level);
  }

  @Provides
  public Config config() {
    return config;
  }
}
