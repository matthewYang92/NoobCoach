package yang.coach.fps;


import android.view.Choreographer;

import yang.coach.CoachDataListener;
import yang.coach.ICoach;


public class FPSCoach implements ICoach {

    public static final String TAG = "FPSCoach";

    private FPSConfig fpsConfig;
    private FPSFrameCallback frameCallback;

    public FPSCoach() {
        fpsConfig = new FPSConfig();
        frameCallback = new FPSFrameCallback(fpsConfig);
    }

    public void setCoachDataListener(CoachDataListener coachListener) {
        frameCallback.setCoachDataListener(coachListener);
    }

    @Override
    public void start() {
        Choreographer.getInstance().postFrameCallback(frameCallback);
    }
}