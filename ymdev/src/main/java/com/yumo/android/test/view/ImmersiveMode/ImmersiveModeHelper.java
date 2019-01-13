package com.yumo.android.test.view.ImmersiveMode;

import android.os.Build;
import android.view.View;

/**
 * Created by guo on 15/9/29.
 */
public class ImmersiveModeHelper {

    private static ImmersiveModeHelper mInstance = null;

    public static ImmersiveModeHelper getInstance(){
        if (mInstance == null){
            return  new ImmersiveModeHelper();
        }

        return  mInstance;
    }

    public boolean isImmersiveModeHelper(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            return  true;
        }

        return  false;
    }

    // This snippet hides the system bars.
    public void hideSystemUI(View view) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI(View view) {
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
