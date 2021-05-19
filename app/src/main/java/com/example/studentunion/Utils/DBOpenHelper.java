package com.example.studentunion.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    final String CREATE_STUDENT_SQL = "create table tb_stu (_id integer primary key autoincrement,userName text not null,stuNum text,password text,gender text,phone text,className text,department text)";
    final String  CREATE_MEETING_SQL= "create table tb_meeting (_id integer primary key autoincrement,theme text not null,time text not null,type text,brief text)";
    final String  CREATE_MATERIAL_SQL= "create table tb_material (_id integer primary key autoincrement,materialName text not null,num text,position text)";
//    final String  CREATE_MATERIAL_OUTIN_SQL= "create table tb_material (_id integer primary key autoincrement,time_out)";
    final String  CREATE_FILE_SQL= "create table tb_file (_id integer primary key autoincrement,fileName text,groupchat text,department text)";
    final String  CREATE_BMAFFAIR_SQL= "create table tb_bmaffair (_id integer primary key autoincrement,bmaffairName text,process integer,department text)";
    final String  CREATE_COURSES_SQL= "create table tb_courses (userName text,stuNum text,d12_z1 text,d12_z2 text,d12_z3 text,d12_z4 text,d12_z5 text,d34_z1 text,d34_z2 text,d34_z3 text,d34_z4 text,d34_z5 text,d56_z1 text,d56_z2 text,d56_z3 text,d56_z4 text,d56_z5 text,d78_z1 text,d78_z2 text,d78_z3 text,d78_z4 text,d78_z5 text)";
    String tableName;

    public DBOpenHelper(Context context, String name, String tableName) {
        super(context, name, null, 2);
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if ("tb_stu".equals(tableName)){
            db.execSQL(CREATE_STUDENT_SQL);
            return;
        }
        if ("tb_meeting".equals(tableName)){
            db.execSQL(CREATE_MEETING_SQL);
            return;
        }
        if ("tb_material".equals(tableName)){
            db.execSQL(CREATE_MATERIAL_SQL);
            return;
        }
        if ("tb_file".equals(tableName)){
            db.execSQL(CREATE_FILE_SQL);
            return;
        }
        if ("tb_bmaffair".equals(tableName)){
            db.execSQL(CREATE_BMAFFAIR_SQL);
            return;
        }
        if ("tb_courses".equals(tableName)){
            db.execSQL(CREATE_COURSES_SQL);
            return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("", "版本更新了："+oldVersion+"-->"+newVersion);
    }
}
