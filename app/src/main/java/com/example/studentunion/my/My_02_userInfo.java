package com.example.studentunion.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.Log_01_Log;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_02_userInfo extends AppCompatActivity {
    ListView userInfo;
    String stuNum;
    Button updateStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_02_userinfo);

        //获取传过来的学号信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        stuNum = bundle.getString("stuNum");

        //从数据库中返回该学生信息
        StudentDao studentDao = new StudentDao(My_02_userInfo.this);
        Student student = studentDao.queryByStuNum(stuNum);

        //给ListView添加数据
        String[] labels = new String[] { "姓名", "学号", "密码", "性别", "手机", "班级", "部门"};
        String[] info = new String[] { student.getUserName(), student.getStuNum(), student.getPassword(), student.getGender(), student.getPhone(), student.getClassName(), student.getDepartment()};
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < labels.length; i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("labels", labels[i]);
            listItem.put("info", info[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.my_02_userinfo_listview, new String[]{"labels", "info"}, new int[]{R.id.label, R.id.info});
        userInfo = (ListView) findViewById(R.id.UserInfo);
        userInfo.setAdapter(simpleAdapter);

//        updateStudentButton = findViewById(R.id.updateStudentButton);
//        updateStudentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                return;
//            }
//        });
    }
}