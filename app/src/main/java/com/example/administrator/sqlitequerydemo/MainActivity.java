package com.example.administrator.sqlitequerydemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.sqlitequerydemo.bean.Person;
import com.example.administrator.sqlitequerydemo.utils.Constant;
import com.example.administrator.sqlitequerydemo.utils.DbManager;
import com.example.administrator.sqlitequerydemo.utils.MySqliteHelper;

import java.util.List;

/**
 * sqlite中数据查询
 */
public class MainActivity extends AppCompatActivity {

    private MySqliteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = DbManager.getInstace(this);
    }

    /**
     * 点击按钮创建数据库
     *
     */
    public void createDb(){
        SQLiteDatabase db = helper.getWritableDatabase();
        for(int i=0;i<30;i++){
            String sql = "insert into " + Constant.TABLE_NAME + " values("+ i +",'张三" + i + "',20)";
            db.execSQL(sql);
        }
        db.close();
    }

    /**
     * 点击按钮查询表中数据
     * @param view
     */
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_create:
                createDb();
                break;
            case R.id.btn_query:
                SQLiteDatabase db = helper.getWritableDatabase();
                String selectSql = "select * from "+Constant.TABLE_NAME;
                Cursor cursor = DbManager.selectDataBysql(db,selectSql,null);
                List<Person> list = DbManager.cursorToList(cursor);
                for(Person p:list){
                    Log.i("tag",p.toString());
                }
                db.close();
                break;
            case R.id.btn_queryApi:
                db = helper.getWritableDatabase();
                //select _id,name,age from person where _id>10 group by x having x order by x
                /*
                query(String table, String[] colums, String selection,
                String[] selectionArgs, String groupBy, String having,
                String orderBy)
                       String table 表名
                       String[] colums 查询表中的字段名称 null 查询所有
                       String selection 查询条件 where字句
                       String[] selectionArgs 查询条件占位符的取值
                       String having 筛选条件 having子句
                       String orserBy 排序条件 order by子句
                 */
                cursor=db.query(Constant.TABLE_NAME,null,Constant._ID+">?"
                ,new String[]{"10"},null,null,Constant._ID+" desc");
                list=DbManager.cursorToList(cursor);
                for(Person p:list){
                    Log.i("tag", p.toString());
                }
                db.close();
                break;

        }
    }
}
