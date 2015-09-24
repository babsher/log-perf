package github.babsher;

import javax.inject.Singleton;

import dagger.Component;
import github.babsher.config.ConfigProvider;
import github.babsher.job.log4j.Log4jModule;
import github.babsher.job.sysout.SysOutModule;

@Singleton
@Component(modules = {ConfigProvider.class, Log4jModule.class})
public interface LogPerformance {
  TestRun testRun();
  App app();
}