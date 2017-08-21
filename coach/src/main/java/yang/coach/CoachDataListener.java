package yang.coach;


import yang.coach.fps.FPSResult;

/**
 * author: Matthew Yang on 17/8/16
 * e-mail: yangtian@yy.com
 */

public interface CoachDataListener {
    void onShowFPS(FPSResult fps);
    void onShowCPU(String usage);
    void onShowPss(long pss);
    void onShowPrivateDirty(long privateDirty);
}
