package com.example.elab_yang.bleee;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.elab_yang.bleee.BluetoothLeService.*;

public class DB extends AppCompatActivity {
    private final static String TAG = DB.class.getSimpleName();

    MyDB my;
    public EditText editText1, editText2;
    public TextView user_name, setting_time;
    public Button button1, button2, button3;
    SQLiteDatabase sql;


    BluetoothLeService mBluetoothLeService = new BluetoothLeService();
    //
    //
    //

    public ArrayList<String> mUserNameArrayList = new ArrayList<String>();
    String user_name2;
    String setting_time2;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "온리시브");
            String message = intent.getStringExtra("message");
            Log.d(TAG, "겟 메세지" + message);
            editText1.setText(message);
            editText2.setText(" ");
            Log.d(TAG, "셋텍스트");
            setDB();
        }
    };

    public void setDB() {
        Log.d(TAG, "셋디비 스타트");
        sql = my.getWritableDatabase();
        sql.execSQL("INSERT INTO member VALUES('" + editText1.getText().toString() + "','" + editText2.getText().toString() + "');"
        );
        sql.close();
        Toast.makeText(getApplicationContext(), "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "셋디비 탈출");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("namsik"));

        my = new MyDB(this);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        user_name = (TextView) findViewById(R.id.textView5);
        setting_time = (TextView) findViewById(R.id.textView6);

        // 초기화
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = my.getWritableDatabase();
                my.onUpgrade(sql, 1, 2);
                sql.close();
                Toast.makeText(getApplicationContext(), "초기화했어유", Toast.LENGTH_LONG).show();
            }
        });

        // DB 조회
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sql = my.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM MEMBER;", null);
                user_name2 = "이름" + "\r\n";
                setting_time2 = "시간" + "\r\n";

                while (cursor.moveToNext()) {
                    user_name2 += cursor.getString(0) + "\r\n";
                    setting_time2 += cursor.getString(1) + "\r\n";
                }

                user_name.setText(user_name2);
                setting_time.setText(setting_time2);
                cursor.close();
                sql.close();
            }
        });

        // sd카드의 데이터를 가져온다!
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "띠용");
                mBluetoothLeService.writeCharacteristic("b");
            }
        });
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }
}

// 설정 후 돌아오면!
//    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("namsik"));
//    }