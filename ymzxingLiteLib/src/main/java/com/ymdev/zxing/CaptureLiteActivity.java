package com.ymdev.zxing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.ymdev.zxing.camera.CameraManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by yumodev on 17/12/4.
 * 一个简单的获取二维码的页面
 */

public class CaptureLiteActivity extends CaptureBaseActivity  implements SurfaceHolder.Callback{
    private final String LOG_TAG = "CaptureLiteActivity";
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private InactivityTimer inactivityTimer;
    private boolean hasSurface;

    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_lite);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        initQr();
    }

    private void initQr(){
        cameraManager = new CameraManager(getApplication());

        viewfinderView = findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        SurfaceView surfaceView = findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(LOG_TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
            //decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(LOG_TAG, ioe);
            //displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(LOG_TAG, "Unexpected error initializing camera", e);
            //displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(LOG_TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }

        if (!hasSurface){
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }


    @Override
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }


    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
          inactivityTimer.onActivity();
          if (rawResult != null){
              Toast.makeText(getApplicationContext(), rawResult.getText(), Toast.LENGTH_SHORT).show();
          }
//        inactivityTimer.onActivity();
//        lastResult = rawResult;
//        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
//
//        boolean fromLiveScan = barcode != null;
//        if (fromLiveScan) {
//            historyManager.addHistoryItem(rawResult, resultHandler);
//            // Then not from history, so beep/vibrate and we have an image to draw on
//            beepManager.playBeepSoundAndVibrate();
//            drawResultPoints(barcode, scaleFactor, rawResult);
//        }
//
//        switch (source) {
//            case NATIVE_APP_INTENT:
//            case PRODUCT_SEARCH_LINK:
//                handleDecodeExternally(rawResult, resultHandler, barcode);
//                break;
//            case ZXING_LINK:
//                if (scanFromWebPageManager == null || !scanFromWebPageManager.isScanFromWebPage()) {
//                    handleDecodeInternally(rawResult, resultHandler, barcode);
//                } else {
//                    handleDecodeExternally(rawResult, resultHandler, barcode);
//                }
//                break;
//            case NONE:
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//                if (fromLiveScan && prefs.getBoolean(PreferencesActivity.KEY_BULK_MODE, false)) {
//                    Toast.makeText(getApplicationContext(),
//                            getResources().getString(R.string.msg_bulk_mode_scanned) + " (" + rawResult.getText() + ')',
//                            Toast.LENGTH_SHORT).show();
//                    maybeSetClipboard(resultHandler);
//                    // Wait a moment or else it will scan the same barcode continuously about 3 times
//                    restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
//                } else {
//                    handleDecodeInternally(rawResult, resultHandler, barcode);
//                }
//                break;
//        }
    }
}
