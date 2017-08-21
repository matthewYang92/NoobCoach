package yang.coach.fps;

import java.util.concurrent.TimeUnit;

import yang.coach.ICoach;


/**
 * author: Matthew Yang on 17/8/16
 * e-mail: yangtian@yy.com
 */

class FPSConfig {

    float refreshRate = 60; //60fps
    float deviceRefreshRateInMs = 16.6f; //设备刷新速率 单位ms


    FPSConfig() {

    }

    long getSampleTimeInNs() {
        return TimeUnit.NANOSECONDS.convert(ICoach.sampleTimeInMs, TimeUnit.MILLISECONDS);
    }

    long getDeviceRefreshRateInNs() {
        float value = deviceRefreshRateInMs * 1000000f;
        return (long) value;
    }
}
