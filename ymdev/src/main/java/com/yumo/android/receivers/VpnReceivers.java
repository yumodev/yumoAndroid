package com.yumo.android.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.yumo.common.android.YmToastUtil;
import com.yumo.common.log.Log;

public class VpnReceivers extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    String state = intent.getSerializableExtra("connection_state").toString();
    YmToastUtil.showMessage("vpn state:"+state);
    Log.d("**************", state.toString());

    if (state.equals("CONNECTING")) {
      // Do what needs to be done
    }
    else if (state.equals("CONNECTED")) {
      // Do what needs to be done
    }
    else if (state.equals("IDLE")) {
      int errorCode = intent.getIntExtra("err", 0);
      if (errorCode != 0) {
        // Do what needs to be done to report a failure
      }
      else {
        // Normal disconnect
      }
    }
    else if (state.equals("DISCONNECTING")) {
      // Usually not very interesting
    }
  }
}
