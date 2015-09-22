package github.babsher.job.log4j;

import org.apache.log4j.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.babsher.config.Config;
import github.babsher.job.JobCreator;

@Module
public class Log4jModule {

  @Provides
  public JobCreator jobCreator(Config config, Logger logger) {
    return new Log4jJobCreator(config, logger);
  }

  @Provides
  @Singleton
  public Logger logger() {

    return Logger.getLogger(Log4jJob.class);
  }
}
