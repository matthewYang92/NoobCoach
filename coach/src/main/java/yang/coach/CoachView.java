package yang.coach;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import yang.coach.fps.FPSResult;


class CoachView implements CoachDataListener {

    private TextView fpsText, cpuText, pssText, privateDirtyText;
    private final WindowManager windowManager;

    CoachView(Context context) {
        View meterView = LayoutInflater.from(context).inflate(R.layout.view_meter, null);
        fpsText = (TextView) meterView.findViewById(R.id.fps);
        onShowFPS(new FPSResult(60, FPSResult.Metric.GOOD));
        cpuText = (TextView) meterView.findViewById(R.id.cpu);
        onShowCPU("0%");
        pssText = (TextView) meterView.findViewById(R.id.mem_pss);
        onShowPss(0);
        privateDirtyText = (TextView) meterView.findViewById(R.id.mem_private_dirty);
        onShowPrivateDirty(0);
        // grab window manager and add view to the window
        windowManager = (WindowManager) meterView.getContext().getSystemService(Service.WINDOW_SERVICE);
        addViewToWindow(meterView);
    }


    private void addViewToWindow(View view) {
        WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        // configure starting coordinates
        paramsF.x = 0;
        paramsF.y = 0;
        paramsF.gravity = Gravity.TOP | Gravity.START;
        // add view to the window
        windowManager.addView(view, paramsF);
        // attach touch listener
        view.setOnTouchListener(new CoachTouchListener(paramsF, windowManager));
        // disable haptic feedback
        view.setHapticFeedbackEnabled(false);
    }

    @Override
    public void onShowFPS(FPSResult fps) {
        switch (fps.metric) {
            case GOOD: fpsText.setTextColor(Color.GREEN); break;
            case MEDIUM: fpsText.setTextColor(Color.YELLOW); break;
            case BAD: fpsText.setTextColor(Color.RED); break;
            default: break;
        }
        fpsText.setText(String.format("FPS : %d", fps.value));
    }


    @Override
    public void onShowCPU(final String usage) {
        cpuText.post(new Runnable() {
            @Override
            public void run() {
                cpuText.setText(String.format("CPU : %s", usage));
            }
        });
    }

    @Override
    public void onShowPss(final long pss) {
        pssText.post(new Runnable() {
            @Override
            public void run() {
                pssText.setText(String.format("Pss : %dm", pss/1024));
            }
        });
    }

    @Override
    public void onShowPrivateDirty(final long privateDirty) {
        privateDirtyText.post(new Runnable() {
            @Override
            public void run() {
                privateDirtyText.setText(String.format("PrivateDirty : %dm", privateDirty/1024));
            }
        });
    }
}