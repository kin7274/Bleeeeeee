package com.example.elab_yang.bleee;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBMainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    FloatingActionButton addBtn;
    ListView listView;
    ArrayList<NeedleVO> datas;


    double initTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_main);
        addBtn = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.main_list);

        addBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == addBtn){
            Intent intent = new Intent(this, AddNeedleActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Intent intent = new Intent(this, ReadNeedleActivity.class);
        intent.putExtra("id", datas.get(position).id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_needle order by needleTime", null);

        datas = new ArrayList<>();
        while(cursor.moveToNext()){
            NeedleVO vo= new NeedleVO();
            vo.id = cursor.getInt(0);
            vo.needleTime = cursor.getString(1);
            datas.add(vo);
        }

        db.close();

        MainListAdapter adapter = new MainListAdapter(this, R.layout.main_list_item, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(getApplicationContext(), "버튼따닥", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            initTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
