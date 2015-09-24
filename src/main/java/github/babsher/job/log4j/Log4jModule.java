package github.babsher.job.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

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
  public Logger logger(Config config, Appender appender) {
    BasicConfigurator.configure(appender);
    Logger l = Logger.getLogger(Log4jJob.class);
    switch (config.getLevel()) {
      case Debug: l.setLevel(Level.DEBUG); break;
      case Info: l.setLevel(Level.INFO); break;
      case Trace: l.setLevel(Level.TRACE); break;
      case Warn: l.setLevel(Level.WARN); break;
      case Error: l.setLevel(Level.ERROR); break;
    }
    return l;
  }

  @Provides
  @Singleton
  public Appender appender() {
    ConsoleAppender c = new ConsoleAppender();

    String PATTERN = "%d [%p|%c|%C{1}] %m%n";
    c.setLayout(new PatternLayout(PATTERN));
    c.activateOptions();
    //add appender to any Logger (here is root)
    Logger.getRootLogger().addAppender(c);

    return c;
  }
}
