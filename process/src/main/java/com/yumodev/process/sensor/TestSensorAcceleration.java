package com.yumodev.process.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.common.android.YmContext;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.io.YmSdUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yumo on 2018/3/30.
 *
 * * Motion Sensors
 *   运动传感器,加速度，重力感应器，陀螺仪，旋转矢量传感器
 *
 *   * Environmental sensors
 *
 *   环境空气温度和压力，照明和适度，气压计、光度计、温度计
 *
 *   * Position sensors
 *
 *   位置传感器，方向传感器和磁力计。
 *
 *   https://developer.android.com/guide/topics/sensors/sensors_overview
 *
 */

public class TestSensorAcceleration extends YmTestFragment implements SensorEventListener{

    private final String LOG_TAG = "Sensor";
    SensorManager mSensorManager = null;
    private TextView mMessageView = null;
    private List<String> mMessageList = null;

    private boolean mSaveToFile = false;
    private String mFileName = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager =  (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
        mMessageView = new TextView(getContext());
        mMessageList = new ArrayList<>();
    }

    @Override
    protected View getHeaderView() {
        return mMessageView;
    }

    public void testShowInfo(){
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        StringBuilder sb = new StringBuilder();
        sb.append("\n name:"+sensor.getName());//名称
        sb.append("\n type："+sensor.getType());//传感器类型
        sb.append("\n vender:"+sensor.getVendor());// 设备商名称
        sb.append("\n version:"+sensor.getVersion());//设备版本
        sb.append("\n resolution:"+sensor.getResolution());//分辨率
        sb.append("\n maximumRange:"+sensor.getMaximumRange());//最大量程
        sb.append("\n minDelay:"+sensor.getMinDelay());//最大量程
        //sb.append("\n maxDelay:"+sensor.getMaxDelay());//功耗
        sb.append("\n power:"+sensor.getPower());//功耗



        Log.i(LOG_TAG, sb.toString());

        Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_LONG).show();
    }


    public void testSensors(){
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的陀螺仪传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的磁场传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的重力传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的线性加速度传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的温度传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的光传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);
        // 为系统的压力传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 获取触发event的传感器类型
        int sensorType = event.sensor.getType();
        StringBuilder sb = null;
        // 判断是哪个传感器发生改变
        switch (sensorType)
        {
            // 方向传感器
            case Sensor.TYPE_ORIENTATION:
                sb = new StringBuilder();
                sb.append("Z轴角度：");
                sb.append(values[0]);
                sb.append(" 绕X轴角度：");
                sb.append(values[1]);
                sb.append(" 绕Y轴角度：");
                sb.append(values[2]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 陀螺仪传感器
            case Sensor.TYPE_GYROSCOPE:
                sb = new StringBuilder();
                sb.append("绕X轴角速度：");
                sb.append(values[0]);
                sb.append("\n绕Y轴角速度：");
                sb.append(values[1]);
                sb.append("\n绕Z轴角速度：");
                sb.append(values[2]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 磁场传感器
            case Sensor.TYPE_MAGNETIC_FIELD:
                sb = new StringBuilder();
                sb.append("X轴磁场强度：");
                sb.append(values[0]);
                sb.append("\nY轴磁场强度：");
                sb.append(values[1]);
                sb.append("\nZ轴磁场强度：");
                sb.append(values[2]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 重力传感器
            case Sensor.TYPE_GRAVITY:
                sb = new StringBuilder();
                sb.append("X轴重力：");
                sb.append(values[0]);
                sb.append("\nY轴重力：");
                sb.append(values[1]);
                sb.append("\nZ轴重力：");
                sb.append(values[2]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 线性加速度传感器
            case Sensor.TYPE_LINEAR_ACCELERATION:
                sb = new StringBuilder();
                sb.append("X轴线性加速度：");
                sb.append(values[0]);
                sb.append("\nY轴线性加速度：");
                sb.append(values[1]);
                sb.append("\nZ轴线性加速度：");
                sb.append(values[2]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 温度传感器
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                sb = new StringBuilder();
                sb.append("当前温度为：");
                sb.append(values[0]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 光传感器
            case Sensor.TYPE_LIGHT:
                sb = new StringBuilder();
                sb.append("当前光的强度为：");
                sb.append(values[0]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
            // 压力传感器
            case Sensor.TYPE_PRESSURE:
                sb = new StringBuilder();
                sb.append("当前压力为：");
                sb.append(values[0]);
                sb.append("\n");

                mMessageList.add(sb.toString());
                mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 测试加速度传感器
     */
    public void testAccelerationSensor(){
        mMessageList.clear();
        mMessageView.setText("");
        //获取服务
        SensorManager sensorManager = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
        //注册线性加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensor != null){
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    Log.i(LOG_TAG, "Sensor change:"+event.accuracy+" "+ Arrays.toString(event.values));
                    float[] values = event.values;
                    StringBuilder
                    sb = new StringBuilder();
                    sb.append(values[0]);
                    sb.append(",");
                    sb.append(values[1]);
                    sb.append(",");
                    sb.append(values[2]);
                    sb.append("\n");

                    if (mSaveToFile){
                        YmFileUtil.saveFile(mFileName, sb.toString().getBytes(), true);
                       // mMessageList.add(sb.toString());

                        //mMessageView.setText(Arrays.toString(mMessageList.toArray()));
                    }


                    Log.i(LOG_TAG, sb.toString());
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    Log.i(LOG_TAG, "onAccuracyChanged:"+accuracy);

                }
            }, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }


    public void testSaveToFile(){
        createFile();
        mSaveToFile = true;
    }

    public void testStopToFile(){
        mSaveToFile = false;
    }

    private void createFile(){
        String path = YmSdUtil.getSDPath();
        if (TextUtils.isEmpty(path)){
            path = YmAdFileUtil.getFileCachePath(YmContext.getAppContext());
        }

        mFileName =  path + File.separator + YmContext.getAppContext().getPackageName()+File.separator+"sensor"+File.separator+getStrTime()+".txt";

        try {
            YmFileUtil.createFile(mFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStrTime() {
        return new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss", Locale.US).format(new Date());
    }


}
