package com.yumo.paho;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.paho.mqtt.MqttAndroidService;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "mqtt";
    final String subscriptionTopic = "home/garden/fountain";
    private String clientId = "";

//    private final String mServerUri = "tcp://192.168.2.57:8964";
//    private final String userName = "yumo";
//    private final String userPassword = "123456";

    private final String mServerUri = "tcp://192.168.2.70:61613";
    private final String userName = "admin";
    private final String userPassword = "password";


    private final String MESSAGE = "message";
    private final int NOTIFICATION_ID =  255;
    private MqttAndroidClient mqttAndroidClient;

    private NotificationManager notificationManager ;
    private NotificationCompat.Builder notificationBuilder ;


    MqttAndroidService service = null;

    private EditText mUserNameView;
    private EditText mUserPasswordView;
    private EditText mMqttHostView;
    private EditText mMqttSubscribeView;
    private EditText mClientIdView;
    private EditText mMessageView;
    private TextView mMqttMessageView;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((MqttAndroidService.MyBinder)iBinder).getService();
            service.setCallbackListener(new MqttAndroidService.YmMqttCallbackListener() {
                @Override
                public void onMessage(final String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String messageOld = mMqttMessageView.getText().toString();
                            mMqttMessageView.setText(messageOld+"\n"+message);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initNotification();

        initView();

        bindService(new Intent(this,MqttAndroidService.class),mConnection,BIND_AUTO_CREATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
           changeConfigToXY();
        } else if (id == R.id.nav_gallery) {
            changeConfigToHP();
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView(){
        mUserNameView = (EditText)findViewById(R.id.mqtt_user_name);
        mUserPasswordView = (EditText)findViewById(R.id.mqtt_user_password);
        mMqttSubscribeView = (EditText)findViewById(R.id.mqtt_subscribe);
        mMqttHostView = (EditText)findViewById(R.id.mqtt_host);
        mClientIdView = (EditText)findViewById(R.id.mqtt_client_id);
        mMessageView = (EditText)findViewById(R.id.public_et);
        mMqttMessageView = (TextView)findViewById(R.id.mqttmessage);

        mUserNameView.setText(MqttManager.getInstance().getUserName());
        mUserPasswordView.setText(MqttManager.getInstance().getUserPassword());
        mMqttHostView.setText(MqttManager.getInstance().getMqttHost());
        mMqttSubscribeView.setText(MqttManager.getInstance().getMqttSubscribe());
        mClientIdView.setText(MqttManager.getInstance().getClientId(getApplicationContext()));

        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfig();
                service.connect();
            }
        });

        findViewById(R.id.disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.disconnect();
            }
        });

        findViewById(R.id.isconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnected = service.isConnect();
                if (isConnected){
                    Toast.makeText(getApplicationContext(),  "已连接", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),  "未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.config).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeConfigToXY();
            }
        });

        findViewById(R.id.subscribe).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isConnected = service.isConnect();
                if (!isConnected){
                    Toast.makeText(getApplicationContext(),  "尚未连接服务器", Toast.LENGTH_SHORT).show();
                    return;
                }

                service.subscribeTopic(MqttManager.getInstance().getMqttSubscribe(), new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Toast.makeText(getApplicationContext(),  "订阅成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Toast.makeText(getApplicationContext(),  "订阅失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.publicBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnected = service.isConnect();
                if (!isConnected){
                    Toast.makeText(getApplicationContext(),  "尚未连接服务器", Toast.LENGTH_SHORT).show();
                    return;
                }

                String message = mMessageView.getText().toString();
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(getApplicationContext(),  "消息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                service.publicMessage(MqttManager.getInstance().getMqttSubscribe(), message, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Toast.makeText(getApplicationContext(),  "发布成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Toast.makeText(getApplicationContext(),  "发布失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void changeConfigToXY(){
        mMqttHostView.setText("tcp://192.168.2.57:1884");
        mUserNameView.setText("yumo");
        mUserPasswordView.setText("123456");
        mMqttSubscribeView.setText("home/garden/fountain");
        mClientIdView.setText(MqttManager.getInstance().getClientId(getApplicationContext()));
    }

    private void changeConfigToHP(){
        mMqttHostView.setText("tcp://192.168.2.70:61613");
        mUserNameView.setText("admin");
        mUserPasswordView.setText("password");
        mMqttSubscribeView.setText("home/garden/fountain");
        mClientIdView.setText(MqttManager.getInstance().getClientId(getApplicationContext()));
    }

    private void saveConfig(){
        MqttManager.getInstance().setUserName(mUserNameView.getText().toString());
        MqttManager.getInstance().setUserPassword(mUserPasswordView.getText().toString());
        MqttManager.getInstance().setMqttHost(mMqttHostView.getText().toString());
        MqttManager.getInstance().setMqttSubscribe(mMqttSubscribeView.getText().toString());
        MqttManager.getInstance().setClientId(mClientIdView.getText().toString());
    }

    private void initMqtt(){
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), mServerUri, clientId);

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        // 配置MQTT连接
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(userPassword.toCharArray());
        mqttConnectOptions.setConnectionTimeout(10);  //超时时间
        mqttConnectOptions.setKeepAliveInterval(60); //心跳时间,单位秒
        mqttConnectOptions.setAutomaticReconnect(true);//自动重连
        mqttConnectOptions.setMaxInflight(10);//允许同时发送几条消息（未收到broker确认信息）
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);//选择MQTT版本


        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "onSuccess ---> " + asyncActionToken.isComplete() );

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "onFailure ---> " + asyncActionToken.isComplete() + "       exception--->" + exception.getMessage());

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Log.e(TAG, "       exception--->" +e.getMessage());

        }

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.e(TAG, "reconnect ---> " + reconnect + "       serverURI--->" + serverURI);
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "cause ---> " + cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e(TAG, "topic ---> " + topic + "       message--->" + message);
                startNotification(message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.e(TAG, "token ---> " + token);
            }
        });
    }


    private void initNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this);
    }

    private void startNotification(MqttMessage message) {
        // params
        Bitmap largeIcon = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher_round)).getBitmap();
        String info = message.toString();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(MESSAGE, info);

        notificationBuilder.setLargeIcon(largeIcon)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("推送消息啦")
                .setContentText(info)
                .setTicker(info)
                .setContentIntent(PendingIntent.getActivity(MainActivity.this, 0, intent, 0));

        Notification notification = notificationBuilder.getNotification();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

}
