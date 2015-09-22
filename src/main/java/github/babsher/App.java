package github.babsher;

import javax.inject.Inject;

import github.babsher.config.Config;
import github.babsher.config.ConfigProvider;
import github.babsher.job.Job;

public class App {

  private final TestRun runner;
  private final Config config;

  public static void main(String[] args) throws Exception {
    Job.Level level = Job.Level.Info;
    DaggerLogPerformance.builder()
        .configProvider(new ConfigProvider(args, level))
        .build()
        .app()
        .run();
  }

  @Inject
  public App(Config config, TestRun runner) {
    this.config = config;
    this.runner = runner;
  }

  public void run() throws Exception {
    for (int i = 0; i < config.getNumRuns(); i++) {
      System.out.print("Starting run " + i);
      runner.run(config.getOutputFilename() + "-" + i);
    }
  }
}
