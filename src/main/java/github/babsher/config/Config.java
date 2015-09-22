package github.babsher.config;

import github.babsher.job.Job;

public class Config {
  private final int numRuns;
  private final String outputFilename;
  private final Integer stringSize;
  private final Integer numJobs;
  private final Integer size;
  private final Job.Level level;

  public Config(String[] args, Integer numJobs, Integer size, Integer stringSize, Job.Level level) {
    this.numRuns = Integer.parseInt(args[1]);
    this.outputFilename = args[0];
    this.numJobs = numJobs;
    this.size = size;
    this.stringSize = stringSize;
    this.level = level;
  }

  public int getNumRuns() {
    return numRuns;
  }

  public String getOutputFilename() {
    return outputFilename;
  }

  public Integer getStringSize() {
    return stringSize;
  }

  public Integer getNumJobs() {
    return numJobs;
  }

  public Integer getSize() {
    return size;
  }

  public Job.Level getLevel() {
    return level;
  }
}