package com.yumodev.process.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class TestSensor extends YmTestFragment implements SensorEventListener{

    private final String LOG_TAG = "Sensor";
    SensorManager mSensorManager = null;
    private TextView mMessageView = null;
    private List<String> mMessageList = null;

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

    /**
     * 打印本机支持的所有传感器
     */
    public void testAllSensor(){
        List<Sensor> dataList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorSb = new StringBuilder();
        for (Sensor sensor : dataList){
            StringBuilder sb = new StringBuilder();
            sb.append("\n name:"+sensor.getName());//名称
            sb.append(" type；"+sensor.getType());//传感器类型
            sb.append(" vender:"+sensor.getVendor());// 设备商名称
            sb.append(" version:"+sensor.getVersion());//设备版本
            sb.append(" type:"+sensor.getType());//设备版本
            sb.append(" resolution:"+sensor.getResolution());//分辨率
            sb.append(" maximumRange:"+sensor.getMaximumRange());//最大量程
            sb.append(" minDelay:"+sensor.getMinDelay());//最大量程
            sb.append(" power:"+sensor.getPower());//功耗



            sensorSb.append(sensor.getName()+"\n");

            Log.i(LOG_TAG, sb.toString());
        }

        Toast.makeText(getContext(), sensorSb.toString(), Toast.LENGTH_LONG).show();
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
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        if (sensor != null){
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    Log.i(LOG_TAG, "Sensor change:"+event.accuracy+" "+ Arrays.toString(event.values));
                    float[] values = event.values;
                    StringBuilder
                    sb = new StringBuilder();
                    sb.append(" X轴：");
                    sb.append(values[0]);
                    sb.append(" Y轴：");
                    sb.append(values[1]);
                    sb.append(" Z轴：");
                    sb.append(values[2]);
                    sb.append("\n");

                    mMessageList.add(sb.toString());

                    mMessageView.setText(Arrays.toString(mMessageList.toArray()));

                    Log.i(LOG_TAG, sb.toString());
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    Log.i(LOG_TAG, "onAccuracyChanged:"+accuracy);

                }
            }, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }


    /**
     * 方向传感器
     */
    public void testOrientation(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 陀螺仪传感器
     */
    public void testGYROSCOPE(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 磁力场传感器
     */
    public void testMagneticField(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 重力传感器
     */
    public void testGravity(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 线性加速度传感器
     */
    public void testAcceleration(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 温度传感器
     */
    public void testTemperature(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 光传感器
     */
    public void testLight(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 压力传感器
     */
    public void testPressure(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 是否存在加速度感应器
     */
    public void testHasLinearAcceleration(){
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
            showToastMessage("存在传感器："+Sensor.TYPE_LINEAR_ACCELERATION);
        }else{
            showToastMessage("不存在该传感器"+Sensor.TYPE_LINEAR_ACCELERATION);
        }
    }


    /**
     * 是否存在重力传感器
     */
    public void testHasLinearGravity(){
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            showToastMessage("存在传感器："+Sensor.TYPE_GRAVITY);
        }else{
            showToastMessage("不存在该传感器"+Sensor.TYPE_GRAVITY);
        }
    }


    /**
     * 是否存在加速器传感器
     */
    public void testHasAcceleration(){
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            showToastMessage("存在传感器："+Sensor.TYPE_ACCELEROMETER);
        }else{
            showToastMessage("不存在该传感器"+Sensor.TYPE_ACCELEROMETER);
        }
    }


    /**
     * 是否存在磁力场传感器
     */
    public void testHasMagneticField(){
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            showToastMessage("存在传感器："+Sensor.TYPE_MAGNETIC_FIELD);
        }else{
            showToastMessage("不存在该传感器"+Sensor.TYPE_MAGNETIC_FIELD);
        }
    }


}
