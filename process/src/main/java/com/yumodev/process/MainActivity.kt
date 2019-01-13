package com.yumodev.process

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.umeng.analytics.MobclickAgent
import com.yumo.common.log.Log
import com.yumo.common.util.YmDateUtil
import com.yumo.demo.config.Config
import com.yumo.demo.view.YmTestActivity
import com.yumo.demo.view.YmTestClassFragment
import com.yumodev.process.background.LocalService
import com.yumodev.process.background.BackgroundNotificationListerService
import com.yumodev.process.remote.RemoteService
import com.yumodev.process.test.TestEvent
import com.yumodev.processlib.ProcessLib
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    final val REQUEST_CODE_LOC_LINE = 1

    final val REQUEST_CODE_LOC_COARSE = 2

    final val REQUEST_CODE_WIFI_STATE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        startService(Intent(this, RemoteService::class.java))
        //startService(Intent(this, ForegroundService::class.java))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O+10){
            startService(Intent(this, LocalService::class.java))
        }else{
            startForegroundService(Intent(this, LocalService::class.java))
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            val intent = Intent(this, BackgroundNotificationListerService::class.java)
            startService(intent)
        }

        showTestPackageHomePage()

        checkAndRequestPermission()

        EventBus.getDefault().register(this)

        //startActivity(Intent(this@MainActivity, YmTestActivity::class.java))

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action

                 var i = 2 / 0
            }
            R.id.nav_gallery -> {
                ProcessLib.getInstance().createBug();
            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,"未获取权限", Toast.LENGTH_SHORT).show();

            return
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1f, object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i(Define.mDebugLog, "onLocationChanged" + location.time + " " + YmDateUtil.getStrFromTime(location.time))
                Log.i(Define.mDebugLog, "onLocationChanged" + location.toString())
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                Log.i(Define.mDebugLog, "onStateChanged:" + provider + " status" + status + " " + extras.toString())
            }

            override fun onProviderEnabled(provider: String) {
                Log.i(Define.mDebugLog, "onProviderEnable:" + provider)
            }

            override fun onProviderDisabled(provider: String) {
                Log.i(Define.mDebugLog, "onProviderDisabled:" + provider)
            }
        })

       // TestAppUtil.ignoreBatteryOptimization(this)

        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    private fun showTestPackageHomePage() {
        //        YmTestPackageFragment fragment = new YmTestPackageFragment();
        //        getSupportFragmentManager().beginTransaction().replace(R.id.test_fragment_id, fragment, "package").commit();

        val bundle = Bundle()
        bundle.putString("packageName", packageName)
        bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, View.GONE)
        val classFragment = YmTestClassFragment()
        classFragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        ft.replace(R.id.test_fragment_id, classFragment).commit()
    }

    private fun checkAndRequestPermission(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(" android.permission.WAKE_LOCK"), REQUEST_CODE_LOC_LINE)
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.ACCESS_FINE_LOCATION"), REQUEST_CODE_LOC_LINE)
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.ACCESS_COARSE_LOCATION"), REQUEST_CODE_LOC_COARSE)
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.ACCESS_WIFI_STATE"), REQUEST_CODE_WIFI_STATE)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOC_LINE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "loc line granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "loc line deny", Toast.LENGTH_SHORT).show();

            }

        }else if (requestCode == REQUEST_CODE_LOC_COARSE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "loc coarse granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "loc coarse deny", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == REQUEST_CODE_WIFI_STATE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "wifi state granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "wifi state deny", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     * 使用onEvent来接收事件，那么接收事件和分发事件在一个线程中执行
     *
     * @param event
     */

    fun onEvent(event: TestEvent) {
        Toast.makeText(this, event.tag, Toast.LENGTH_SHORT).show()
    }
}
