package github.babsher;

import com.google.common.base.Stopwatch;
import com.google.common.io.Files;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import github.babsher.config.Config;
import github.babsher.job.JobCreator;

public class TestRun {

  public static final File data = new File("./data");
  private final JobCreator jobCreator;
  private final Config config;

  @Inject
  public TestRun(
      Config config,
      JobCreator jobCreator) {
    this.config = config;
    this.jobCreator = jobCreator;
  }

  public void run(String resultsFile) throws Exception {
    clear(data);
    data.mkdir();

    ExecutorService pool = Executors.newFixedThreadPool(2);

    List<Future<Stopwatch>> jobs = IntStream.rangeClosed(1, config.getNumJobs())
        .mapToObj(jobCreator::createJob)
        .map(pool::submit)
        .collect(Collectors.toList());

    SummaryStatistics summaryStatistics = new SummaryStatistics();
    try (BufferedWriter w = Files.newWriter(new File(resultsFile + ".txt"), Charset.defaultCharset())) {
      for(Future<Stopwatch> future: jobs) {
        Double value = future.get().elapsed(TimeUnit.MICROSECONDS) / 1000.0;
        w.write(value + "\n");
        summaryStatistics.addValue(value);
      }
    }
    pool.shutdown();

    String stats = summaryStatistics.toString();
    Files.write(stats.getBytes(), new File(resultsFile + ".stat"));
    System.out.println(stats);
    System.out.println("Done!\n\n");
    System.out.println("------------------------------");
  }

  public static void clear(File f) {
    if (f.isDirectory()) {
      Arrays.stream(f.listFiles())
          .forEach(TestRun::clear);
    }
    f.delete();
  }
}
