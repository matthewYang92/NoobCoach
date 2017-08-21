package yang.coach;

/**
 * author: Matthew Yang on 17/8/18
 * e-mail: yangtian@yy.com
 */

public interface ICoach {
    long sampleTimeInMs = 1000; //取样间隔 单位ms
    void start();
}
