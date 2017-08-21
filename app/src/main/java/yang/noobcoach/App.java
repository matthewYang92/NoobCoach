package yang.noobcoach;

import android.app.Application;

import yang.coach.NoobCoach;

/**
 * author: Matthew Yang on 17/8/21
 * e-mail: yangtian@yy.com
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NoobCoach.startDefaultMeter(this);
    }
}
