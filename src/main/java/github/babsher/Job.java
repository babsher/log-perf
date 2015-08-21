package github.babsher;

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

public abstract class Job implements Runnable {

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

  protected final Level logLevel;
  protected final Integer id;

  public Job(Integer id, Level logLevel) {
    this.logLevel = logLevel;
    this.id = id;
  }

  public void run() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    FatClass fatty = new FatClass();
    try {
      // compute PI
      double pi = computePi(App.SIZE);
      logPiTotal(pi);

      // Write PI to disk
      Path file = FileSystems.getDefault().getPath("data", id + ".txt");
      try (BufferedWriter w = Files.newBufferedWriter(file, Charset.defaultCharset())) {
        for (int i = 0; i < App.SIZE; i++) {
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
    App.results.offer(stopwatch);
    logElapsedTime(stopwatch);
  }


  private double computePi(int n) {
    double sequenceFormula = 0;
    for (int counter = 1; counter < n; counter += 2) {
      sequenceFormula = sequenceFormula + ((1.0 / (2.0 * counter - 1.0)) - (1.0 / (2.0 * counter + 1.0)));
      logPiPart(sequenceFormula);
    }
    double pi = 4 * sequenceFormula;
    return pi;
  }

  abstract void logFatty(FatClass fatty);

  abstract void logPiTotal(double pi);

  abstract void logPiPart(double pi);

  abstract void logHash(String hash);

  abstract void logElapsedTime(Stopwatch stopwatch);

  public static class FatClass {
    @Override
    public String toString() {
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      StringBuffer str = new StringBuffer("FatClass{}");
      for (int i = 0; i < App.STRING_SIZE; i++) {
        str.append("char");
      }
      return str.toString();
    }
  }

}
