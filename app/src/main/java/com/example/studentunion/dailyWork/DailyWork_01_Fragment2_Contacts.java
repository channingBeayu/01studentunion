package com.example.studentunion.dailyWork;


import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyWork_01_Fragment2_Contacts extends Fragment {
    private StudentDao studentDao;
    String[] stuLabel = new String[]{"userName", "stuNum", "phone", "gender", "department", "className"};
    int selectedNum = 0;    //查询的标签
    LinearLayout linearLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailywork_01_fragment2_contacts, container, false);
        linearLayout = view.findViewById(R.id.parentLinearLayout);


        //从数据库中返回所有学生的phone信息
        studentDao = new StudentDao(getContext());
        List<Student> students = studentDao.findAll();

        //给ListView添加数据
        listviewAdd(students);



        /****************查询目标学生的联系方式***************/
        Spinner spinner = (Spinner) linearLayout.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resources resources = getResources();
                String[] stuLabel = resources.getStringArray(R.array.stuQuery);
                selectedNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //点击事件  查询目标学生
        Button queryButton = linearLayout.findViewById(R.id.queryButton);
        EditText queryEditText = linearLayout.findViewById(R.id.queryEditText);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xxx = queryEditText.getText().toString();
                if (xxx == null || "".equals(xxx)){
                    return;
                }

                String[] stuLabel1 = new String[]{"userName", "phone", "gender", "department", "className"};
                StudentDao studentDao = new StudentDao(getContext());
                List<Student> studentList = studentDao.queryByXxx(stuLabel1[selectedNum], xxx);
                if (studentList.size() == 0){
                    Toast.makeText(getContext(), "未查找到该生信息！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    listviewAdd(studentList);
                }
            }
        });
        /*************************************************/

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void listviewAdd(List<Student> students){
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < students.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("userName", students.get(i).getUserName());
            listItem.put("stuNum", students.get(i).getStuNum());
            listItem.put("phone", students.get(i).getPhone());
            listItem.put("gender", students.get(i).getGender());
            listItem.put("department", students.get(i).getDepartment());
            listItem.put("className", students.get(i).getClassName());
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listItems, R.layout.dailywork_01_fragment2_listviewitem, stuLabel, new int[]{R.id.userName, R.id.stuNum, R.id.phone, R.id.gender, R.id.department, R.id.className});
        ListView listView = linearLayout.findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);
    }


}
