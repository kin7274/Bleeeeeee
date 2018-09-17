package com.example.elab_yang.bleee;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReadNeedleActivity extends AppCompatActivity {

    TextView needleTimeView;

    int needleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_needle);

        Intent intent = getIntent();
        needleId = intent.getIntExtra("id", 1);

        needleTimeView = (TextView) findViewById(R.id.read_needleTime);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select*from tb_needle where _id=" +needleId, null);
        db.close();
    }
}
