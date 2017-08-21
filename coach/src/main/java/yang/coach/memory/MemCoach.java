package yang.coach.memory;

import android.content.Context;
import android.os.Process;

import yang.coach.CoachDataListener;
import yang.coach.ICoach;


/**
 * author: Matthew Yang on 17/8/18
 * e-mail: yangtian@yy.com
 */

public class MemCoach implements ICoach {

    public static final String TAG = "MemCoach";

    private boolean enabled = false;

    private CoachDataListener coachDataListener;
    private int currentPid;
    private Context context;

    Runnable memProcessTask = new Runnable() {
        @Override
        public void run() {
            while (enabled) {
                long totalPss = getTotalPss();
                long totalPrivateDirty = getTotalPrivateDirty();
                if (coachDataListener != null) {
                    coachDataListener.onShowPss(totalPss);
                }
                if (coachDataListener != null) {
                    coachDataListener.onShowPrivateDirty(totalPrivateDirty);
                }
                try {
                    Thread.sleep(ICoach.sampleTimeInMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void start() {
        if (!enabled) {
            currentPid = Process.myPid();
            new Thread(memProcessTask).start();
            enabled = true;
        }
    }

    public MemCoach(Context context) {
        this.context = context;
    }

    public void setCoachListener(CoachDataListener coachListener) {
        this.coachDataListener = coachListener;
    }

    private long getTotalPss() {
        return MemUtils.getPSS(context, currentPid)[2];
    }

    private long getTotalPrivateDirty() {
       return MemUtils.getPrivDirty(context, currentPid)[2];
    }
}
