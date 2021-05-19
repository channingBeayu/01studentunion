package com.example.studentunion.department.msc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.studentunion.Beans.Courses;
import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.CoursesDao;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BM_msc_02_duty extends AppCompatActivity {
    private Button createButton;
    private String[][] dutyStudent = new String[4][5];
    CoursesDao coursesDao;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_msc_02_duty);
        tableLayout = findViewById(R.id.tableLayout);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dutyStudent = dutyStudent();

                TableRow[] tableRows = new TableRow[tableLayout.getChildCount()-1];
                for (int i = 0; i < 4; i++) {
                    tableRows[i] = (TableRow) tableLayout.getChildAt(i+1);
                    for (int j=0; j<5; j++){
                        ((TextView)tableRows[i].getChildAt(j)).setText(dutyStudent[i][j]);
                    }
                }

            }
        });

    }

    //生成值班人员
    public String[][] dutyStudent(){
        coursesDao = new CoursesDao(BM_msc_02_duty.this);
        List<Courses> studentList = coursesDao.findAll();
        List<Integer> orders = new ArrayList<Integer>();
        int allCount = studentList.size(); //学生总数量

        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                int order = new Random().nextInt(allCount);
                while (orders.contains(order)){
                    order = new Random().nextInt(allCount);
                }
                orders.add(order);
                Courses courses = studentList.get(order);
                boolean flag = (courses.getcourseArray())[i][j];
                while (!flag){
                    order = new Random().nextInt(allCount);
                    courses = studentList.get(order);
                    flag = (courses.getcourseArray())[i][j];
                }
                dutyStudent[i][j] = courses.getUserName();
            }
        }

        return dutyStudent;
    }

}