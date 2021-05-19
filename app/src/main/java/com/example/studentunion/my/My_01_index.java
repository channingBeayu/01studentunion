package com.example.studentunion.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.studentunion.Beans.Courses;
import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.CoursesDao;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.Log_01_Log;
import com.example.studentunion.R;
import com.example.studentunion.TabHost_index;

public class My_01_index extends AppCompatActivity {
    GridLayout userInfoEntrance;
    TableLayout tableLayout;
    TextView userNameTextView;
    TextView stuNumTextView;
    Button confirmButton;
    String stuNum;
    boolean[][] courseArray;
    Courses courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_01_index);

        final SharedPreferences sharedPreferences =
                getSharedPreferences("mrsoft", MODE_PRIVATE);

        tableLayout = findViewById(R.id.tableLayout);
        confirmButton = findViewById(R.id.confirmButton);
        userNameTextView = findViewById(R.id.userName);
        stuNumTextView = findViewById(R.id.stuNum);
        courseArray = new boolean[4][5];  //四行五列，第一维是四节课，第二维是周几

        //获取传过来的学号信息
        stuNum = sharedPreferences.getString("stuNum", null);
        StudentDao studentDao = new StudentDao(My_01_index.this);
        Student student = studentDao.queryByStuNum(stuNum);

        //根据性别修改头像
        String gender = student.getGender();
        if ("女".equals(gender)){
            ImageView headImageView = findViewById(R.id.headImageView);
            headImageView.setImageResource(R.drawable.head_female);
        }

        courses = new Courses();
        courses.setUserName(student.getUserName());
        courses.setStuNum(student.getStuNum());
        userNameTextView.setText(student.getUserName());

        CoursesDao coursesDao = new CoursesDao(My_01_index.this);
        Courses courses = coursesDao.queryByStuNum(stuNum);
        if (courses.getUserName() != null){
            //设置无课表信息
            TableRow[] tableRows = new TableRow[tableLayout.getChildCount()-1];
            for (int i = 0; i < 4; i++) {
                tableRows[i] = (TableRow) tableLayout.getChildAt(i+1);
                for (int j = 0; j < 5; j++) {
                    CheckBox checkBox = (CheckBox)tableRows[i].getChildAt(j);
                    Boolean b = (courses.getcourseArray())[i][j];
                    checkBox.setChecked(b);

                    ((CheckBox) tableRows[i].getChildAt(j)).setChecked((courses.getcourseArray())[i][j]);
                }
            }
        }



        //点击进入个人资料
        userInfoEntrance = (GridLayout) findViewById(R.id.UserInfo);
        userInfoEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence("stuNum", stuNum);
                Intent intent = new Intent(getApplicationContext(), My_02_userInfo.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        /***************获取课表信息*****************/
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存无课表信息到courses类中
                TableRow[] tableRows = new TableRow[tableLayout.getChildCount()-1];
                for (int i = 0; i < 4; i++) {
                    tableRows[i] = (TableRow) tableLayout.getChildAt(i+1);
                    courseArray[i][0] = ((CheckBox) tableRows[i].getChildAt(0)).isChecked();   //周一
                    courseArray[i][1] = ((CheckBox) tableRows[i].getChildAt(1)).isChecked();   //周二
                    courseArray[i][2] = ((CheckBox) tableRows[i].getChildAt(2)).isChecked();   //周三
                    courseArray[i][3] = ((CheckBox) tableRows[i].getChildAt(3)).isChecked();   //周四
                    courseArray[i][4] = ((CheckBox) tableRows[i].getChildAt(4)).isChecked();   //周五
                }
                My_01_index.this.courses.setcourseArray(courseArray);


                //判断数据库中是否已经存在该学生的无课表信息
                CoursesDao coursesDao = new CoursesDao(My_01_index.this);
                Courses courses = coursesDao.queryByStuNum(stuNum);
                if (courses.getUserName()==null){   //添加
                    CoursesDao coursesDao1 = new CoursesDao(My_01_index.this);
                    coursesDao1.add(My_01_index.this.courses);
                }else {  //更新
                    CoursesDao coursesDao1 = new CoursesDao(My_01_index.this);
                    coursesDao1.update(My_01_index.this.courses);
                }
            }
        });




    }
}