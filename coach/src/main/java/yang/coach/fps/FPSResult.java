package yang.coach.fps;

/**
 * author: Matthew Yang on 17/8/18
 * e-mail: yangtian@yy.com
 */

public class FPSResult {
    public enum Metric {GOOD, BAD, MEDIUM}

    public long value;
    public Metric metric;

    public FPSResult(long value, Metric metric) {
        this.value = value;
        this.metric = metric;
    }
}
