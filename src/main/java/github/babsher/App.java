package github.babsher;

import com.google.common.base.Stopwatch;
import com.google.common.io.Files;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import github.babsher.Job.Level;

public class App {

  public static final Integer NUM_JOBS = 5000;
  public static final Integer SIZE = 1000;
  public static final Integer STRING_SIZE = 100;
  public static final File data = new File("./data");
  public static final Queue<Stopwatch> results = new ConcurrentLinkedQueue<>();

  public static void main(String[] args) throws Exception {

    int numRuns = Integer.parseInt(args[1]);
    for(int i = 0; i < numRuns; i++) {
      System.out.print("Starting run " + i);
      run(args[0] + "-" + i);
    }

  }

  public static void run(String resultsFile) throws Exception {
    clear(data);
    data.mkdir();

    ExecutorService pool = Executors.newFixedThreadPool(2);

    List<Future<?>> jobs = IntStream.rangeClosed(1, NUM_JOBS)
        .mapToObj(i -> new SysOutJob(i, Level.Error))
        .map(pool::submit)
        .collect(Collectors.toList());

    SummaryStatistics summaryStatistics = new SummaryStatistics();
    boolean done = false;
    try(BufferedWriter w = Files.newWriter(new File(resultsFile + ".txt"), Charset.defaultCharset())) {
      while (!done) {
        // System.out.println("Jobs left: " + jobs.stream().filter(f -> !f.isDone()).collect(Collectors.counting()));
        done = jobs.stream().allMatch(Future::isDone);
        Thread.sleep(10);
        while(!results.isEmpty()) {
          Double value = results.poll().elapsed(TimeUnit.MICROSECONDS) / 1000.0;
          w.write(value + "\n");
          summaryStatistics.addValue(value);
        }
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
    if(f.isDirectory()) {
      Arrays.stream(f.listFiles())
          .forEach(App::clear);
    }
    f.delete();
  }
}
