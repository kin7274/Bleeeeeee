package com.example.elab_yang.bleee;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

public class DBinsertActivity extends AppCompatActivity {

    private Button btnCreateDatabase, btnInsertDatabase, btnSelectAllData;
    private DBHelper dbHelper;
    private ListView lvPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbinsert);
        btnCreateDatabase = (Button) findViewById(R.id.btnCreateButton);
        btnInsertDatabase = (Button) findViewById(R.id.btnInsertButton);
        btnSelectAllData = (Button) findViewById(R.id.btnSelectAllData);
        lvPeople = (ListView) findViewById(R.id.lvPeople);

        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etDBName = new EditText(DBinsertActivity.this);
                etDBName.setHint("DB명을 입력하세요.");

                // 다이얼로그로 데이터베이스의 이름을 입력받는다.
                AlertDialog.Builder dialog = new AlertDialog.Builder(DBinsertActivity.this);
                dialog.setTitle("데이터베이스 이름을 입력하세요.")
                        .setMessage("데이터베이스 이름을 입력하세요.")
                        .setView(etDBName)
                        .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (etDBName.getText().toString().length() > 0) {
                                    dbHelper = new DBHelper(
                                            DBinsertActivity.this,
                                            etDBName.getText().toString(),
                                            null, 1);
                                    dbHelper.testDB();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .create()
                        .show();
            }
        });


        btnInsertDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(DBinsertActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etName = new EditText(DBinsertActivity.this);
                etName.setHint("이름을 입력하세요.");

                final EditText etAge = new EditText(DBinsertActivity.this);
                etAge.setHint("나이를 입력하세요.");

                final EditText etPhone = new EditText(DBinsertActivity.this);
                etPhone.setHint("전화번호를 입력하세요.");

                layout.addView(etName);
                layout.addView(etAge);
                layout.addView(etPhone);

                AlertDialog.Builder dialog = new AlertDialog.Builder(DBinsertActivity.this);
                dialog.setTitle("정보를 입력하세요.")
                        .setView(layout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etName.getText().toString();
                                String age = etAge.getText().toString();
                                String phone = etPhone.getText().toString();

                                if(dbHelper == null){
                                    dbHelper = new DBHelper(DBinsertActivity.this, "TEST", null, 1);
                                }

                                Person person = new Person();
                                person.setName(name);
                                person.setAge(age);
                                person.setPhone(phone);

                                dbHelper.addPerson(person);
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .create()
                        .show();
            }
        });

        btnSelectAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPeople.setVisibility(View.VISIBLE);
                if(dbHelper == null){
                    dbHelper = new DBHelper(DBinsertActivity.this, "TEST", null, 1);
                }

                List people = dbHelper.getAllPersonData();

                lvPeople.setAdapter(new PersonListAdapter(people, DBinsertActivity.this));
            }
        });
    }
}
