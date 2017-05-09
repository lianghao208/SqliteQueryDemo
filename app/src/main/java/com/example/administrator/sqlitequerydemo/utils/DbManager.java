package com.example.administrator.sqlitequerydemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.sqlitequerydemo.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 *数据库工具类
 */

public class DbManager {
    private static MySqliteHelper helper;
    public static MySqliteHelper getInstace(Context context){
        if(helper==null){
            helper = new MySqliteHelper(context);
        }
        return helper;
    }

    /**
     * 根据sql语句查询获得cursor对象
     * @param db 数据库对象
     * @param sql sql的查询语句
     * @param selectionArgs 查询条件的占位符
     * @return 查询结果
     */
    public static Cursor selectDataBysql(SQLiteDatabase db,String sql,String[] selectionArgs ){
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(sql,selectionArgs);
        }
        return cursor;
    }

    /**
     * 将查询得到的cursor转换成list对象
     * @param cursor
     * @return
     */
    public static List<Person> cursorToList(Cursor cursor){
        List<Person> list = new ArrayList<Person>();
        while(cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex(Constant._ID));
            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person person = new Person(_id,name,age);
            list.add(person);
        }
        return list;
    }
}
