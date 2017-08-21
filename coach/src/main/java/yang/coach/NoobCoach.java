package yang.coach;

import android.content.Context;

import yang.coach.cpu.CpuCoach;
import yang.coach.fps.FPSCoach;
import yang.coach.memory.MemCoach;


/**
 * author: Matthew Yang on 17/8/18
 * e-mail: yangtian@yy.com
 */

public class NoobCoach {

    public static void startDefaultMeter(Context context) {
        CoachView coachView = new CoachView(context);
        FPSCoach fpsCoach = new FPSCoach();
        CpuCoach cpuCoach = new CpuCoach();
        MemCoach memCoach = new MemCoach(context);
        fpsCoach.setCoachDataListener(coachView);
        cpuCoach.setCoachListener(coachView);
        memCoach.setCoachListener(coachView);
        fpsCoach.start();
        cpuCoach.start();
        memCoach.start();
    }

}
