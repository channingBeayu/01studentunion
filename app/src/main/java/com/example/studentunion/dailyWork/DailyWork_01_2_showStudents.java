package com.example.studentunion.dailyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyWork_01_2_showStudents extends AppCompatActivity {
    String[] stuLabel = new String[]{"userName", "stuNum", "phone", "gender", "department", "className"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailywork_01_2_showstudents);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Student> stu = bundle.getParcelableArrayList("stu");


        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < stu.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();  //hashmap是无序集合，没有顺序
            listItem.put(stuLabel[0], stu.get(i).getUserName());
            listItem.put(stuLabel[1], stu.get(i).getStuNum());
            listItem.put(stuLabel[2], stu.get(i).getPhone());
            listItem.put(stuLabel[3], stu.get(i).getGender());
            listItem.put(stuLabel[4], stu.get(i).getDepartment());
            listItem.put(stuLabel[5], stu.get(i).getClassName());
            listItems.add(listItem);
        }

        //创建SimpleAdapter并绑定
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.dailywork_01_fragment2_listviewitem, stuLabel, new int[]{R.id.userName, R.id.stuNum, R.id.phone, R.id.gender, R.id.department, R.id.className});
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);

    }
}