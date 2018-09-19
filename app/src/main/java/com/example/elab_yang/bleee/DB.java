package com.example.elab_yang.bleee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class DB extends AppCompatActivity {
    private final static String TAG = DB.class.getSimpleName();

    DBHelper dbHelper;
    SQLiteDatabase sql;
    TextView result;
    BluetoothLeService mBluetoothLeService = new BluetoothLeService();

    String MESSAGE;

    // 브로드캐스트
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String message = intent.getStringExtra("message");
            Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
            MESSAGE = message;
            // 삽입
            insert(message);
        }
    };

    public void insert(String item) {
        sql = dbHelper.getWritableDatabase();
        sql.execSQL("INSERT INTO NEEDLE VALUES (null, '" + item + "');");
        result.setText(dbHelper.getResult());
        sql.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        dbHelper = new DBHelper(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("namsik"));

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "NEEDLE.db", null, 1);

        result = (TextView) findViewById(R.id.result);
        EditText etItem = (EditText) findViewById(R.id.item);

        // 동기화
        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'b'값을 치면
                mBluetoothLeService.writeCharacteristic("b");
            }
        });

        // 삭제
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "삭제", Toast.LENGTH_SHORT).show();
//                String item = etItem.getText().toString();
//                dbHelper.delete(item);
//                result.setText(dbHelper.getResult());
            }
        });

        // 조회
        Button select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "조회", Toast.LENGTH_SHORT).show();
//                result.setText(dbHelper.getResult());
            }
        });

        // 초기화
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear
                Toast.makeText(getApplicationContext(), "초기화", Toast.LENGTH_SHORT).show();
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