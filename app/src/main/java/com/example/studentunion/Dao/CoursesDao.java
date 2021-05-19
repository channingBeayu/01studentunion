package com.example.studentunion.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentunion.Beans.Courses;
import com.example.studentunion.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CoursesDao {

    DBOpenHelper dbOpenHelper = null;
    SQLiteDatabase db = null;
    String tableName;
    public CoursesDao(Context context){
        dbOpenHelper = new DBOpenHelper(context, "db_stu", "tb_courses");
        db = dbOpenHelper.getWritableDatabase();
        this.tableName = "tb_courses";
    }

    /**
     * 添加一个无课表
     * @param courses
     */
    public void add(Courses courses) {
        ContentValues value = new ContentValues();
        value.put("userName",courses.getUserName());
        value.put("stuNum",courses.getStuNum());
        boolean[][] courseArray = courses.getcourseArray();
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                String key = "";
                if (i==0){key="d12_z"+(j+1);}
                else if (i==1){key="d34_z"+(j+1);}
                else if (i==2){key="d56_z"+(j+1);}
                else if (i==3){key="d78_z"+(j+1);}
                value.put(key, courseArray[i][j]);
            }
        }
        db.insert(tableName,null,value);
        db.close();
    }

    /**
     * 根据无课表的学号stuNum删除一个无课表
     * @param stuNum
     */
    public void removeByStuNum(String stuNum) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(tableName,"stuNum=?",new String[]{stuNum+""});
        db.close();
    }
    /**
     * 根据无课表的学号stuNum更新一个无课表
     * @param courses
     */
    public void update(Courses courses) {
        ContentValues value = new ContentValues();
        value.put("userName",courses.getUserName());
        value.put("stuNum",courses.getStuNum());
        boolean[][] courseArray = courses.getcourseArray();
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                String key = "";
                if (i==0){key="d12_z"+(j+1);}
                else if (i==1){key="d34_z"+(j+1);}
                else if (i==2){key="d56_z"+(j+1);}
                else if (i==3){key="d78_z"+(j+1);}
                value.put(key, courseArray[i][j]);
            }
        }
        db.update(tableName,value,"stuNum=?",new String[]{courses.getStuNum()+""});
        db.close();
    }

    /**
     * 根据无课表stuNum查找一个无课表
     * @param stuNum
     */
    public Courses queryByStuNum(String stuNum) {
        Courses Courses = null;
        Cursor cursor = db.query(tableName,null,"stuNum=?",new String[]{stuNum+""},null,null,null);
        Courses courses = new Courses();
        if(cursor.moveToNext()){
            courses.setUserName(cursor.getString(0));
            courses.setStuNum(cursor.getString(1));
            boolean[][] courseArray = new boolean[4][5];
            int column = 2;
            for (int i=0; i<4; i++){
                for (int j=0; j<5; j++){
                    String flag = cursor.getString(column++);
                    if ("1".equals(flag)){
                        courseArray[i][j] = true;
                    }else {
                        courseArray[i][j] = false;
                    }
                }
            }
            courses.setcourseArray(courseArray);
        }
        db.close();
        return courses;
    }

    /**
     * 查找所有无课表
     */
    public List<Courses> findAll() {
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        List<Courses> list = new ArrayList<Courses>();
        while(cursor.moveToNext()){
            Courses courses = new Courses();
            courses.setUserName(cursor.getString(0));
            courses.setStuNum(cursor.getString(1));
            boolean[][] courseArray = new boolean[4][5];
            int column = 2;
            for (int i=0; i<4; i++){
                for (int j=0; j<5; j++){
                    String flag = cursor.getString(column++);
                    if ("1".equals(flag)){
                        courseArray[i][j] = true;
                    }else {
                        courseArray[i][j] = false;
                    }
                }
            }
            courses.setcourseArray(courseArray);
            list.add(courses);
        }
        db.close();
        return list;
    }

}
