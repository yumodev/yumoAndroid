package com.yumodev.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yumo.demo.config.Config;
import com.yumo.demo.view.YmTestClassFragment;
import com.yumodev.test.service.TestService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        showTestPackageHomePage();

        //startService(new Intent(getApplicationContext(), TestService.class));
    }

    private void showTestPackageHomePage(){
        YmTestClassFragment fragment = new YmTestClassFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, View.GONE);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.test_fragment_id, fragment, "package").commit();
    }
}
