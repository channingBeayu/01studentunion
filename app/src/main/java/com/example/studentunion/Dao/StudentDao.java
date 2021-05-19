package com.example.studentunion.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    DBOpenHelper dbOpenHelper = null;
    SQLiteDatabase db = null;
    String tableName;
    public StudentDao(Context context){
        dbOpenHelper = new DBOpenHelper(context, "db_stu", "tb_stu");
        db = dbOpenHelper.getWritableDatabase();
        this.tableName = "tb_stu";
    }

    /**
     * 添加一个学生
     * @param student
     */
    public void add(Student student) {
        ContentValues value = new ContentValues();
        value.put("userName",student.getUserName());
        value.put("stuNum",student.getStuNum());
        value.put("password",student.getPassword());
        value.put("gender",student.getGender());
        value.put("phone",student.getPhone());
        value.put("className",student.getClassName());
        value.put("department",student.getDepartment());
        db.insert(tableName,null,value);
        db.close();
    }

    /**
     * 根据id删除一个学生
     * @param id
     */
    public void removeById(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(tableName,"_id=?",new String[]{id+""});
        db.close();
    }
    /**
     * 根据id更新一个学生
     * @param student
     */
    public void updateById(Student student) {
        ContentValues values = new ContentValues();
        values.put("userName",student.getUserName());
        values.put("stuNum",student.getStuNum());
        values.put("password",student.getPassword());
        values.put("gender",student.getGender());
        values.put("phone",student.getPhone());
        values.put("className",student.getClassName());
        values.put("department",student.getDepartment());
        db.update(tableName,values,"_id=?",new String[]{student.get_id()+""});
        db.close();
    }

    /**
     * 根据学号查找一个学生
     * @param stuNum
     */
    public Student queryByStuNum(String stuNum) {
        Student Student = null;
        Cursor cursor = db.query(tableName,null,"stuNum=?",new String[]{stuNum+""},null,null,null);
        Student student = new Student();
        if(cursor.moveToNext()){
            student.set_id(cursor.getInt(0));
            student.setUserName(cursor.getString(1));
            student.setStuNum(cursor.getString(2));
            student.setPassword(cursor.getString(3));
            student.setGender(cursor.getString(4));
            student.setPhone(cursor.getString(5));
            student.setClassName(cursor.getString(6));
            student.setDepartment(cursor.getString(7));
        }
        db.close();
        return student;
    }

    /**
     * 根据xxx查找符合条件的学生
     * @param xxx
     */
    public List<Student> queryByXxx(String label, String xxx) {
        Cursor cursor = db.rawQuery("select * from tb_stu where "+label+"='"+xxx+"'", null);
        List<Student> studentList = new ArrayList<Student>();
        while(cursor.moveToNext()){
            Student student = new Student();
            student.set_id(cursor.getInt(0));
            student.setUserName(cursor.getString(1));
            student.setStuNum(cursor.getString(2));
            student.setPassword(cursor.getString(3));
            student.setGender(cursor.getString(4));
            student.setPhone(cursor.getString(5));
            student.setClassName(cursor.getString(6));
            student.setDepartment(cursor.getString(7));
            studentList.add(student);
        }
        db.close();
        return studentList;
    }

    /**
     * 查找所有学生
     */
    public List<Student> findAll() {
        Cursor cursor = db.rawQuery("select * from tb_stu",null);
        List<Student> list = new ArrayList<Student>();
        while(cursor.moveToNext()){
            Student student = new Student();
            student.set_id(cursor.getInt(0));
            student.setUserName(cursor.getString(1));
            student.setStuNum(cursor.getString(2));
            student.setPassword(cursor.getString(3));
            student.setGender(cursor.getString(4));
            student.setPhone(cursor.getString(5));
            student.setClassName(cursor.getString(6));
            student.setDepartment(cursor.getString(7));
            list.add(student);
        }
        db.close();
        return list;
    }

    /**
     * 返回学生数量
     */
    public int getCount() {
        Cursor cursor = db.rawQuery("select count(*) from "+tableName, null);
        int count = -1;
        if (cursor.moveToNext()) {
            count++;
        }
        return count;
    }
}
