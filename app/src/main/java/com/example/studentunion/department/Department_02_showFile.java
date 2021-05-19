package com.example.studentunion.department;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentunion.Beans.File;
import com.example.studentunion.Beans.Student;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department_02_showFile extends AppCompatActivity {
    String[] departmentListLabel = new String[]{"fileName", "groupchat", "department"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailywork_01_2_showstudents);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<File> fileList = bundle.getParcelableArrayList("fileList");


        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < fileList.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();  //hashmap是无序集合，没有顺序
            listItem.put(departmentListLabel[0], fileList.get(i).getFileName());
            listItem.put(departmentListLabel[1], fileList.get(i).getGroupchat());
            listItem.put(departmentListLabel[2], fileList.get(i).getDepartment());
            listItems.add(listItem);
        }

        //创建SimpleAdapter并绑定
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.department_02_listview, departmentListLabel, new int[]{R.id.fileName, R.id.groupchat, R.id.department});
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);

    }
}