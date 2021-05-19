package com.example.studentunion.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentunion.Beans.File;
import com.example.studentunion.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FileDao {

    DBOpenHelper dbOpenHelper = null;
    SQLiteDatabase db = null;
    String tableName;
    public FileDao(Context context){
        dbOpenHelper = new DBOpenHelper(context, "db_bm", "tb_file");
        db = dbOpenHelper.getWritableDatabase();
        this.tableName = "tb_file";
    }

    /**
     * 添加一个文件
     * @param file
     */
    public void add(File file) {
        ContentValues value = new ContentValues();
        value.put("fileName",file.getFileName());
        value.put("groupchat",file.getGroupchat());
        value.put("department",file.getDepartment());
        db.insert(tableName,null,value);
        db.close();
    }

    /**
     * 根据文件名fileName删除一个文件
     * @param _id
     */
    public void removeById(Integer _id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(tableName,"_id=?",new String[]{_id+""});
        db.close();
    }
    /**
     * 根据文件id更新一个文件
     * @param file
     */
    public void update(File file) {
        ContentValues value = new ContentValues();
        value.put("fileName",file.getFileName());
        value.put("groupchat",file.getGroupchat());
        value.put("department",file.getDepartment());
        db.update(tableName,value,"_id=?",new String[]{file.get_id()+""});
        db.close();
    }

    /**
     * 根据文件id查找一个文件
     * @param _id
     */
    public File queryById(Integer _id) {
        File File = null;
        Cursor cursor = db.query(tableName,null,"_id=?",new String[]{_id+""},null,null,null);
        File file = new File();
        if(cursor.moveToNext()){
            file.set_id(cursor.getInt(0));
            file.setFileName(cursor.getString(1));
            file.setGroupchat(cursor.getString(2));
            file.setDepartment(cursor.getString(3));
        }
        db.close();
        return file;
    }

    /**
     * 根据文件所在部门查找文件
     * @param department
     */
    public List<File> queryByDepartment(String department) {
        Cursor cursor = db.rawQuery("select * from "+tableName+" where department='"+department+"'",null);
        List<File> list = new ArrayList<File>();
        while(cursor.moveToNext()){
            File file = new File();
            file.set_id(cursor.getInt(0));
            file.setFileName(cursor.getString(1));
            file.setGroupchat(cursor.getString(2));
            file.setDepartment(cursor.getString(3));
            list.add(file);
        }
        db.close();
        return list;
    }



    /**
     * 查找所有文件
     */
    public List<File> findAll() {
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        List<File> list = new ArrayList<File>();
        while(cursor.moveToNext()){
            File file = new File();
            file.set_id(cursor.getInt(0));
            file.setFileName(cursor.getString(1));
            file.setGroupchat(cursor.getString(2));
            file.setDepartment(cursor.getString(3));
            list.add(file);
        }
        db.close();
        return list;
    }

    /**
     * 返回文件数量
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
