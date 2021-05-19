package com.example.studentunion.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentunion.Beans.Material;
import com.example.studentunion.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MaterialDao {

    DBOpenHelper dbOpenHelper = null;
    SQLiteDatabase db = null;
    String tableName;
    public MaterialDao(Context context){
        dbOpenHelper = new DBOpenHelper(context, "db_bm", "tb_material");
        db = dbOpenHelper.getWritableDatabase();
        this.tableName = "tb_material";
    }

    /**
     * 添加一个物资
     * @param material
     */
    public void add(Material material) {
        ContentValues value = new ContentValues();
        value.put("materialName",material.getMaterialName());
        value.put("num",material.getNum());
        value.put("position",material.getPosition());
        db.insert(tableName,null,value);
        db.close();
    }

    /**
     * 根据物资名materialName删除一个物资
     * @param materialName
     */
    public void removeByMaterialName(String materialName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(tableName,"materialName=?",new String[]{materialName+""});
        db.close();
    }
    /**
     * 根据物资id更新一个物资
     * @param material
     */
    public void update(Material material) {
        ContentValues value = new ContentValues();
        value.put("materialName",material.getMaterialName());
        value.put("num",material.getNum());
        value.put("position",material.getPosition());
        db.update(tableName,value,"_id=?",new String[]{material.get_id()+""});
        db.close();
    }

    /**
     * 根据物资名materialName查找一个物资
     * @param materialName
     */
    public Material queryByMaterialName(String materialName) {
        Material Material = null;
        Cursor cursor = db.query(tableName,null,"materialName=?",new String[]{materialName+""},null,null,null);
        Material material = new Material();
        if(cursor.moveToNext()){
            material.set_id(cursor.getInt(0));
            material.setMaterialName(cursor.getString(1));
            material.setNum(cursor.getString(2));
            material.setPosition(cursor.getString(3));
        }
        db.close();
        return material;
    }

    /**
     * 查找所有物资
     */
    public List<Material> findAll() {
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        List<Material> list = new ArrayList<Material>();
        while(cursor.moveToNext()){
            Material material = new Material();
            material.set_id(cursor.getInt(0));
            material.setMaterialName(cursor.getString(1));
            material.setNum(cursor.getString(2));
            material.setPosition(cursor.getString(3));
            list.add(material);
        }
        db.close();
        return list;
    }

    /**
     * 返回物资数量
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
