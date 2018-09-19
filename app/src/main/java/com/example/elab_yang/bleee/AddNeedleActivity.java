package com.example.elab_yang.bleee;

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

public class AddNeedleActivity extends AppCompatActivity {

    private final static String TAG = AddNeedleActivity.class.getSimpleName();

    myDB my;
    SQLiteDatabase sql;

    public TextView user_name;
    public Button button1, button2, button3;

    BluetoothLeService mBluetoothLeService = new BluetoothLeService();
    //
    //
    //
//
    public ArrayList<String> mUserNameArrayList = new ArrayList<String>();
    String user_name2;
    //
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "온리시브");
            final String message = intent.getStringExtra("message");
            Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "겟 메세지" + message);
            String[] MSG = message.split("");
//            MSG[1] + MSG[2] + MSG[3] + MSG[4] = 2018년도
//            MSG[5] + MSG[6] = 09월
//            MSG[7] + MSG[8] = 19일
//            MSG[9] + MSG[10] = 15시
//            MSG[11] + MSG[12] = 39분
            
//            Log.d(TAG, "MSG[0] = " + MSG[0]);
//            Log.d(TAG, "MSG[1] = " + MSG[1]);
//            Log.d(TAG, "MSG[2] = " + MSG[2]);
//            Log.d(TAG, "MSG[3] = " + MSG[3]);
//            Log.d(TAG, "MSG[4] = " + MSG[4]);
//            Log.d(TAG, "MSG[5] = " + MSG[5]);
//            Log.d(TAG, "MSG[6] = " + MSG[6]);
//            Log.d(TAG, "MSG[7] = " + MSG[7]);
            String REALREALREAL = MSG[1] + MSG[2] + MSG[3] + MSG[4] + "년도 " + MSG[5] + MSG[6] + "월 " + MSG[7] + MSG[8] + "일 " + MSG[9] + MSG[10] + "시 " + MSG[11] + MSG[12] + "분입니다.";
            setDB(REALREALREAL);
        }
    };

    //
    public void setDB(String item) {
        sql = my.getWritableDatabase();
        sql.execSQL("INSERT INTO tb_needle VALUES(null, '" + item + "')");
        sql.close();
        Toast.makeText(getApplicationContext(), "저장", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_needle);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("namsik"));

        my = new myDB(this);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        user_name = (TextView) findViewById(R.id.textView5);

        // 초기화
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "초기화", Toast.LENGTH_LONG).show();
                sql = my.getWritableDatabase();
                my.onUpgrade(sql, 1, 2);
                sql.close();
            }
        });

        // DB 조회
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sql = my.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("select*from tb_NEEDLE order by needleTime", null);
                while (cursor.moveToNext()) {
                    user_name2 += cursor.getString(0) + " : "
                    + cursor.getString(1) + " / " + "\n";
                }
                user_name.setText(user_name2);
                cursor.close();
                sql.close();
                Toast.makeText(getApplicationContext(), "조회하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // sd카드의 데이터를 가져온다!
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // a : sd카드 다 리드
                // b : "1" 수신
                // c : text 삭제
                mBluetoothLeService.writeCharacteristic("a");
            }
        });
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    // 설정 후 돌아오면!
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("namsik"));
    }
}