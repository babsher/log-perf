package github.babsher;

import dagger.Component;
import github.babsher.config.ConfigProvider;
import github.babsher.job.log4j.Log4jModule;
import github.babsher.job.sysout.SysOutModule;

@Component(modules = {ConfigProvider.class, SysOutModule.class})
public interface LogPerformance {
  TestRun testRun();
  App app();
}