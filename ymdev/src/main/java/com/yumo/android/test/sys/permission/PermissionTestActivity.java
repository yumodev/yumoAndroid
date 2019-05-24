package com.yumo.android.test.sys.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class PermissionTestActivity extends AppCompatActivity {

    final int REQUEST_CODE = 99;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,
                permissions,
                REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE: {
                // grantResults是一个数组，和申请的数组一一对应。
                // 如果请求被取消，则结果数组为空。
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限同意了，做相应处理
                    Toast.makeText(this, "获取权限", Toast.LENGTH_SHORT).show();
                } else {
                    // 权限被拒绝了
                    Toast.makeText(this, "用户拒绝权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


}
