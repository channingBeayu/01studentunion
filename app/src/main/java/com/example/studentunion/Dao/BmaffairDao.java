package com.example.studentunion.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentunion.Beans.Bmaffair;
import com.example.studentunion.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BmaffairDao {

    DBOpenHelper dbOpenHelper = null;
    SQLiteDatabase db = null;
    String tableName;
    public BmaffairDao(Context context){
        dbOpenHelper = new DBOpenHelper(context, "db_bm", "tb_bmaffair");
        db = dbOpenHelper.getWritableDatabase();
        this.tableName = "tb_bmaffair";
    }

    /**
     * 添加一个部门事务
     * @param bmaffair
     */
    public void add(Bmaffair bmaffair) {
        ContentValues value = new ContentValues();
        value.put("bmaffairName",bmaffair.getBmaffairName());
        value.put("process",bmaffair.getProcess());
        value.put("department",bmaffair.getDepartment());
        db.insert(tableName,null,value);
        db.close();
    }

    /**
     * 根据部门事务_id删除一个部门事务
     * @param _id
     */
    public void removeById(Integer _id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(tableName,"_id=?",new String[]{_id+""});
        db.close();
    }
    /**
     * 根据部门事务id更新一个部门事务
     * @param bmaffair
     */
    public void update(Bmaffair bmaffair) {
        ContentValues value = new ContentValues();
        value.put("bmaffairName",bmaffair.getBmaffairName());
        value.put("process",bmaffair.getProcess());
        value.put("department",bmaffair.getDepartment());
        db.update(tableName,value,"_id=?",new String[]{bmaffair.get_id()+""});
        db.close();
    }

    /**
     * 根据部门事务id查找一个部门事务
     * @param _id
     */
    public Bmaffair queryById(Integer _id) {
        Bmaffair Bmaffair = null;
        Cursor cursor = db.query(tableName,null,"_id=?",new String[]{_id+""},null,null,null);
        Bmaffair bmaffair = new Bmaffair();
        if(cursor.moveToNext()){
            bmaffair.set_id(cursor.getInt(0));
            bmaffair.setBmaffairName(cursor.getString(1));
            bmaffair.setProcess(cursor.getInt(2));
            bmaffair.setDepartment(cursor.getString(3));
        }
        db.close();
        return bmaffair;
    }

    /**
     * 根据部门事务所在部门查找部门事务
     * @param department
     */
    public List<Bmaffair> queryByDepartment(String department) {
        Cursor cursor = db.rawQuery("select * from "+tableName+" where department='"+department+"'",null);
        List<Bmaffair> list = new ArrayList<Bmaffair>();
        while(cursor.moveToNext()){
            Bmaffair bmaffair = new Bmaffair();
            bmaffair.set_id(cursor.getInt(0));
            bmaffair.setBmaffairName(cursor.getString(1));
            bmaffair.setProcess(cursor.getInt(2));
            bmaffair.setDepartment(cursor.getString(3));
            list.add(bmaffair);
        }
        db.close();
        return list;
    }



    /**
     * 查找所有部门事务
     */
    public List<Bmaffair> findAll() {
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        List<Bmaffair> list = new ArrayList<Bmaffair>();
        while(cursor.moveToNext()){
            Bmaffair bmaffair = new Bmaffair();
            bmaffair.set_id(cursor.getInt(0));
            bmaffair.setBmaffairName(cursor.getString(1));
            bmaffair.setProcess(cursor.getInt(2));
            bmaffair.setDepartment(cursor.getString(3));
            list.add(bmaffair);
        }
        db.close();
        return list;
    }

    /**
     * 返回部门事务数量
     */
    public int getCount() {
        Cursor cursor = db.rawQuery("select count(*) from "+tableName, null);
        int count = -1;
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }
}
