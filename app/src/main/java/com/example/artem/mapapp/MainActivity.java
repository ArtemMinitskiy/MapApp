package com.example.artem.mapapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainActivity extends AppCompatActivity  {

    EditText login, password;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        dbHelper = new DBHelper(this);

    }



    public void addInDB(View view){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cv.put("login", login.getText().toString());
        cv.put("password", password.getText().toString());
        db.insert("users", null, cv);
        Log.d("Log", login.getText().toString() + " " + password.getText().toString());
        dbHelper.close();

        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

}



class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Log", "--- onCreate database ---");
        db.execSQL("create table users ("
                + "id integer primary key autoincrement,"
                + "login text,"
                + "password text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
