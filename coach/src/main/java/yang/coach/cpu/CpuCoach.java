package yang.coach.cpu;

import android.os.Process;

import yang.coach.CoachDataListener;
import yang.coach.ICoach;
import yang.coach.utils.DoubleUtils;


/**
 * author: Matthew Yang on 17/8/18
 * e-mail: yangtian@yy.com
 */

public class CpuCoach implements ICoach {

    public static final String TAG = "CpuCoach";

    private double pCpu = 0.0;
    private double aCpu = 0.0;
    private double o_pCpu = 0.0;
    private double o_aCpu = 0.0;
    private boolean enabled = false;
    private int currentPid;
    private CoachDataListener coachDataListener;

    Runnable cpuProcessTask = new Runnable() {
        @Override
        public void run() {
            while (enabled) {
                String usage = getProcessCpuUsage(currentPid);
                if (coachDataListener != null) {
                    coachDataListener.onShowCPU(usage);
                }
                try {
                    Thread.sleep(ICoach.sampleTimeInMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void setCoachListener(CoachDataListener coachListener) {
        this.coachDataListener = coachListener;
    }

    @Override
    public void start() {
        if (!enabled) {
            currentPid = Process.myPid();
            new Thread(cpuProcessTask).start();
            enabled = true;
        }
    }


    public String getProcessCpuUsage(int pid) {
        String result = "";
        String[] result1 = null;
        String[] result2 = null;
        if (pid >= 0) {

            result1 = CpuUtils.getProcessCpuAction(pid);
            if (null != result1) {
                pCpu = Double.parseDouble(result1[1])
                        + Double.parseDouble(result1[2]);
            }
            result2 = CpuUtils.getCpuAction();
            if (null != result2) {
                aCpu = 0.0;
                for (int i = 2; i < result2.length; i++) {

                    aCpu += Double.parseDouble(result2[i]);
                }
            }
            double usage = 0.0;
            if ((aCpu - o_aCpu) != 0) {
                usage = DoubleUtils.div(((pCpu - o_pCpu) * 100.00),
                        (aCpu - o_aCpu), 2);
                if (usage < 0) {
                    usage = 0;
                } else if (usage > 100) {
                    usage = 100;
                }

            }
            o_pCpu = pCpu;
            o_aCpu = aCpu;
            result = String.valueOf(usage) + "%";
        }
        return result;
    }
}
