package com.example.elab_yang.bleee;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    public static Context mContext;

    Button btn1, btn2, btn3;
    TextView textview1, textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
        mContext = this;
    }

    private void setUp() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(myListener);
        btn2.setOnClickListener(myListener);
        btn3.setOnClickListener(myListener);

        textview1 = (TextView) findViewById(R.id.textview1);
        textview2 = (TextView) findViewById(R.id.textview2);
        //
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 블루투스
                case R.id.btn1:
                    Intent intent1 = new Intent(MainActivity.this, DeviceScanActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btn2:
                    final Intent intent2 = new Intent(MainActivity.this, DeviceControlActivity.class);
                    intent2.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, textview1.getText());
                    intent2.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, textview2.getText());
                    startActivity(intent2);
                    break;
                case R.id.btn3:
                    Intent intent3 = new Intent(MainActivity.this, AddNeedleActivity.class);
                    intent3.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, textview1.getText());
                    intent3.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, textview2.getText());
                    startActivity(intent3);
                    break;
            }
        }
    };

    // 설정 후 돌아오면!
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "11111 설정 후 내가 돌아왔어");
        textview1.setText(BluetoothLog.getName());
        textview2.setText(BluetoothLog.getAddress());
    }
}
