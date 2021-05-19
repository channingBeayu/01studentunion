package com.example.studentunion.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentunion.Beans.Meeting;
import com.example.studentunion.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MeetingDao {

    DBOpenHelper dbOpenHelper = null;
    SQLiteDatabase db = null;
    String tableName;
    public MeetingDao(Context context){
        dbOpenHelper = new DBOpenHelper(context, "db_dailyWork", "tb_meeting");
        db = dbOpenHelper.getWritableDatabase();
        this.tableName = "tb_meeting";
    }

    /**
     * 添加一个会议
     * @param meeting
     */
    public void add(Meeting meeting) {
        ContentValues value = new ContentValues();
        value.put("theme",meeting.getTheme());
        value.put("time",meeting.getTime());
        value.put("type",meeting.getType());
        value.put("brief",meeting.getBreif());
        db.insert(tableName,null,value);
        db.close();
    }

    /**
     * 根据会议名theme删除一个会议
     * @param theme
     */
    public void removeByTheme(String theme) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(tableName,"theme=?",new String[]{theme+""});
        db.close();
    }
    /**
     * 根据会议id更新一个会议
     * @param meeting
     */
    public void update(Meeting meeting) {
        ContentValues value = new ContentValues();
        value.put("theme",meeting.getTheme());
        value.put("time",meeting.getTime());
        value.put("type",meeting.getType());
        value.put("brief",meeting.getBreif());
        db.update(tableName,value,"_id=?",new String[]{meeting.get_id()+""});
        db.close();
    }

    /**
     * 根据主题theme查找一个会议
     * @param theme
     */
    public Meeting queryByTheme(String theme) {
        Meeting Meeting = null;
        Cursor cursor = db.query(tableName,null,"theme=?",new String[]{theme+""},null,null,null);
        Meeting meeting = new Meeting();
        if(cursor.moveToNext()){
            meeting.set_id(cursor.getInt(0));
            meeting.setTheme(cursor.getString(1));
            meeting.setTime(cursor.getString(2));
            meeting.setType(cursor.getString(3));
            meeting.setBreif(cursor.getString(4));
        }
        db.close();
        return meeting;
    }

    /**
     * 查找所有会议
     */
    public List<Meeting> findAll() {
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        List<Meeting> list = new ArrayList<Meeting>();
        while(cursor.moveToNext()){
            Meeting meeting = new Meeting();
            meeting.set_id(cursor.getInt(0));
            meeting.setTheme(cursor.getString(1));
            meeting.setTime(cursor.getString(2));
            meeting.setType(cursor.getString(3));
            meeting.setBreif(cursor.getString(4));
            list.add(meeting);
        }
        db.close();
        return list;
    }

    /**
     * 返回会议数量
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
