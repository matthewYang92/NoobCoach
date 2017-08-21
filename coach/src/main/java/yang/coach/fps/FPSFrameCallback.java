package yang.coach.fps;

import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;

import yang.coach.CoachDataListener;


/**
 * author: Matthew Yang on 17/8/18
 * e-mail: yangtian@yy.com
 */

class FPSFrameCallback implements Choreographer.FrameCallback {
    public static final String TAG = "FPSFrameCallback";

    private FPSConfig fpsConfig;
    private List<Long> dataSet = new ArrayList<>(); //保存 一个样例时间的 frame times
    private boolean enabled = true;
    private long startSampleTimeInNs = 0;
    private CoachDataListener coachDataListener;

    FPSFrameCallback(FPSConfig fpsConfig) {
        this.fpsConfig = fpsConfig;
    }

    void setCoachDataListener(CoachDataListener coachListener) {
        this.coachDataListener = coachListener;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if (!enabled) {
            destroy();
            return;
        }

        if (startSampleTimeInNs == 0) {
            startSampleTimeInNs = frameTimeNanos;
        }
        //frame time in new list
        if (isFinishedWithSample(frameTimeNanos)) {
            collectSampleAndSend(frameTimeNanos);
        }

        // add current frame time to our list
        dataSet.add(frameTimeNanos);

        //we need to register for the next frame callback
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void collectSampleAndSend(long frameTimeNanos) {
        //this occurs only when we have gathered over the sample time ~700ms
        List<Long> dataSetCopy = new ArrayList<>();
        dataSetCopy.addAll(dataSet);

        List<Integer> droppedSet = Calculation.getDroppedSet(fpsConfig, dataSetCopy);

        if(coachDataListener != null) {
            coachDataListener.onShowFPS(Calculation.calculateMetric(fpsConfig, dataSetCopy, droppedSet));
        }

        // clear data
        dataSet.clear();

        //reset sample timer to last frame
        startSampleTimeInNs = frameTimeNanos;
    }

    /**
     * 完成一个样例收集 时长 > 样例设置时长
     * @param frameTimeNanos
     * @return
     */
    private boolean isFinishedWithSample(long frameTimeNanos) {
        return frameTimeNanos - startSampleTimeInNs > fpsConfig.getSampleTimeInNs();
    }

    void destroy() {
        dataSet.clear();
        startSampleTimeInNs = 0;
    }
}
