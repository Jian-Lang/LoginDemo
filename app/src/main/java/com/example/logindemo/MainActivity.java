package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_log,btn_res;
    EditText edt_name,edt_pwd;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_name = findViewById(R.id.username);
        edt_pwd = findViewById(R.id.pwd);
        btn_log = findViewById(R.id.login);
        btn_res = findViewById(R.id.register);
        btn_log.setOnClickListener(this);
        btn_res.setOnClickListener(this);
        dataBaseHelper = new DataBaseHelper(this,"info.db",null,1);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        String uname = edt_name.getText().toString();
        String upwd = edt_pwd.getText().toString();

        switch (v.getId()){
            case R.id.login:
//                sql = "delete from user";
//                sqLiteDatabase.execSQL(sql);
                String args1[] = new String[]{uname};
                String p = "";
                sql = "select * from user where username=?";
                cursor = sqLiteDatabase.rawQuery(sql,args1);
                cursor.moveToFirst();
                if(!cursor.isAfterLast()) p = cursor.getString(cursor.getColumnIndex("userpwd"));
                if(!uname.equals("")&&!upwd.equals("")){
                    if(p.equals("")){
                        Toast.makeText(this,"用户名"+uname+"不存在",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(p.equals(upwd)){
                            Toast.makeText(this,"用户名"+uname+"登陆成功",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this,"用户名"+uname+"密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(uname.equals("")&&!upwd.equals("")){
                    Toast.makeText(this,"请填写用户名",Toast.LENGTH_SHORT).show();
                }
                else if(!uname.equals("")&&upwd.equals("")){
                    Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"请填写用户名和密码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                String args[] = new String[]{uname};
                sql = "select * from user where username=?";
                cursor = sqLiteDatabase.rawQuery(sql,args);
                cursor.moveToFirst();
                String tmp = "";
                if(!cursor.isAfterLast()) tmp = cursor.getString(cursor.getColumnIndex("userpwd"));
                if(!uname.equals("")&&!upwd.equals("")){
                    if(tmp.equals("")){
                        sql = "insert into user(username,userpwd) values('"+uname+"','"+upwd+"')";
                        sqLiteDatabase.execSQL(sql);
                        edt_pwd.setText("");
                        edt_name.setText("");
                        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(uname.equals("")&&!upwd.equals("")){
                    Toast.makeText(this,"请填写用户名",Toast.LENGTH_SHORT).show();
                }
                else if(!uname.equals("")&&upwd.equals("")){
                    Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"请填写用户名和密码",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}