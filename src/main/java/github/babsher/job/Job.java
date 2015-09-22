package github.babsher.job;

import com.google.common.base.Stopwatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.Callable;

import github.babsher.config.Config;

public abstract class Job implements Callable<Stopwatch> {

  protected final Config config;

  public enum Level {
    Trace(0),
    Debug(1),
    Info(2),
    Warn(3),
    Error(4);

    public final Integer value;
    Level(int value) {
      this.value = value;
    }
  }

  protected final Integer id;

  public Job(Config config, Integer id) {
    this.config = config;
    this.id = id;
  }

  @Override
  public Stopwatch call() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    FatClass fatty = new FatClass();
    try {
      // compute PI
      double pi = computePi(config.getSize());
      logPiTotal(pi);

      // Write PI to disk
      Path file = FileSystems.getDefault().getPath("data", id + ".txt");
      try (BufferedWriter w = Files.newBufferedWriter(file, Charset.defaultCharset())) {
        for (int i = 0; i < config.getSize(); i++) {
          w.write(id + "-" + i + " " + pi + "\n");
          logFatty(fatty);
        }
        w.flush();
      }

      // Read PI and hash
      ByteBuffer buf = ByteBuffer.allocate(1024);
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      try (BufferedReader r = Files.newBufferedReader(file, Charset.defaultCharset())) {
        logFatty(fatty);
        r.read(buf.asCharBuffer());
        sha.update(buf);
        buf.clear();
      }

      byte[] hash = Base64.getUrlEncoder().encode(sha.digest());
      logHash(new String(hash));
    } catch (Exception e) {
      e.printStackTrace();
    }
    stopwatch.stop();
    logElapsedTime(stopwatch);
    return stopwatch;
  }


  private double computePi(int n) {
    double sequenceFormula = 0;
    for (int counter = 1; counter < n; counter += 2) {
      sequenceFormula = sequenceFormula + ((1.0 / (2.0 * counter - 1.0)) - (1.0 / (2.0 * counter + 1.0)));
      logPiPart(sequenceFormula);
    }
    return 4 * sequenceFormula;
  }

  public abstract void logFatty(FatClass fatty);

  public abstract void logPiTotal(double pi);

  public abstract void logPiPart(double pi);

  public abstract void logHash(String hash);

  public abstract void logElapsedTime(Stopwatch stopwatch);

  public class FatClass {
    @Override
    public String toString() {
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      StringBuilder str = new StringBuilder("FatClass{}");
      for (int i = 0; i < config.getStringSize(); i++) {
        str.append("char");
      }
      return str.toString();
    }
  }

}
