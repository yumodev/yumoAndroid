package com.yumodev.google.util;

import android.content.Context;

/**
 * Created by yumo on 2018/5/9.
 */

public interface LocationModule {
    void LocationInit(Context context);
    void startLocationUpdates();
    void stopLocationUpdates();
    void googleClientConnect();
    void googleClientDisconnect();
    void setLocationChangedListener(GoogleLocationModule.LocationChangedListener locationChangedListener);
}
