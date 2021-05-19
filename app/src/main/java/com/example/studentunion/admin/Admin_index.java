package com.example.studentunion.admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.Log_02_Reg;
import com.example.studentunion.R;
import com.example.studentunion.dailyWork.DailyWork_01_2_showStudents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_index extends AppCompatActivity {
    private StudentDao studentDao;
    String[] stuLabel = new String[]{"userName", "stuNum", "phone", "gender", "department", "className"};
    String[] stuQueryLabel = new String[]{"userName", "phone", "gender", "department", "className"};
    int selectedNum = 0;    //查询的标签

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_01_showstudent);

        //从数据库中返回所有学生的phone信息
        studentDao = new StudentDao(this);
        List<Student> students = studentDao.findAll();

        //给ListView添加数据
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < students.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("phone", students.get(i).getPhone());
            listItem.put("stuNum", students.get(i).getStuNum());
            listItem.put("userName", students.get(i).getUserName());
            listItem.put("gender", students.get(i).getGender());
            listItem.put("department", students.get(i).getDepartment());
            listItem.put("className", students.get(i).getClassName());
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.dailywork_01_fragment2_listviewitem, stuLabel, new int[]{R.id.userName, R.id.stuNum, R.id.phone, R.id.gender, R.id.department, R.id.className});
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);


        /****************查询目标学生***************/
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Resources resources = getResources();
//                String[] stuLabel = resources.getStringArray(R.array.stuQuery);
                selectedNum = position;
//                Toast.makeText(this, selected, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //点击事件 查找学生
        Button queryButton = findViewById(R.id.queryButton);
        EditText queryEditText = findViewById(R.id.queryEditText);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xxx = queryEditText.getText().toString();
                if (xxx == null || "".equals(xxx)){
                    return;
                }

                StudentDao studentDao = new StudentDao(Admin_index.this);
                List<Student> studentList = studentDao.queryByXxx(stuQueryLabel[selectedNum], xxx);
                if (studentList.size() == 0){
                    Toast.makeText(Admin_index.this, "未查找到该生信息！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("stu", (ArrayList<? extends Parcelable>) studentList);

                    Intent intent = new Intent(Admin_index.this, DailyWork_01_2_showStudents.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        /*************************************************/


        /*********************删除/修改学生信息*************************/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), ((TextView)((GridLayout)view).findViewById(R.id.theme)).getText().toString(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(Admin_index.this).setTitle("您要对这条记录进行什么操作？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            String userName = ((TextView)((GridLayout)view).findViewById(R.id.userName)).getText().toString();
//                            Toast.makeText(Admin_01_removeStudent.this, userName, Toast.LENGTH_SHORT).show();
                            StudentDao studentDao = new StudentDao(Admin_index.this);
                            List<Student> studentList = studentDao.queryByXxx("userName", userName);
                            studentDao.removeById(studentList.get(0).get_id());
                        })
                        .setNeutralButton("取消", (dialog, which) -> { })
                        .setNegativeButton("修改", (dialog, which) -> {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String stuNum = ((TextView)((GridLayout)view).findViewById(R.id.stuNum)).getText().toString();
                                    Message message = new Message();
                                    message.obj = stuNum;
                                    handler.sendMessage(message);
                                }
                            });
                            thread.start();
                        }).show();
            }
        });
        /*************************************************/


        //点击事件 添加一个学生
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("stuNum", "admin");
                Intent intent = new Intent(Admin_index.this, Log_02_Reg.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    final Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            String stuNum = (String) message.obj;
            Bundle bundle = new Bundle();
            bundle.putString("stuNum", stuNum);
            Intent intent = new Intent(Admin_index.this, Log_02_Reg.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}