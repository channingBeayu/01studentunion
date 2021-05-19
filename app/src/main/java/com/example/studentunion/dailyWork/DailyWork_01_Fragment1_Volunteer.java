package com.example.studentunion.dailyWork;


import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.studentunion.Beans.Meeting;
import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.BmaffairDao;
import com.example.studentunion.Dao.MeetingDao;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.R;
import com.example.studentunion.department.Department_03_bmaffair;
import com.example.studentunion.my.My_02_userInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DailyWork_01_Fragment1_Volunteer extends Fragment {
    private EditText countEditText;
    private Button randomSelectButton;
    private GridLayout gridLayout;
    private Spinner spinner;
    private Integer selectedNum;  //选中部门的序号
    List<Student> studentList;
    String[] departmentList;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailywork_01_fragment1_volunteer, container, false);
        countEditText = view.findViewById(R.id.count);
        randomSelectButton = view.findViewById(R.id.randomSelect);
        gridLayout = view.findViewById(R.id.gridLayout);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        departmentList = getResources().getStringArray(R.array.departmentList);


        //记录选中部门的序号
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //点击事件 随机选号
        randomSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getCount = countEditText.getText().toString();
                if(getCount == null || "".equals(getCount+"")){
                    return;
                }
                Integer count = Integer.parseInt(getCount.toString()); //志愿者人数


                List randomNumberList = getRandomNumber(count, departmentList[selectedNum]);
                if (randomNumberList == null){
                    return;
                }

                gridLayout.removeAllViews();
                for (int i=0; i<count; i++){
                    String userName = studentList.get((Integer) (randomNumberList.get(i))).getUserName();
                    TextView textView = new TextView(getContext());
                    textView.setText(userName);
                    textView.setTextSize(getResources().getDimension(R.dimen.textSize));
                    textView.setPadding(10, 10, 10, 10);
                    gridLayout.addView(textView);
                }
            }
        });


        return view;
    }

    //生成一组志愿者序号
    @RequiresApi(api = Build.VERSION_CODES.M)
    public List getRandomNumber(int count, String department){
        StudentDao studentDao = new StudentDao(getContext());
        String all = departmentList[departmentList.length-1];
        List<Integer> orders = new ArrayList<Integer>();
        if (department == all){
            studentList = studentDao.findAll();
        }else {
            studentList = studentDao.queryByXxx("department", department);
        }

        int allCount = studentList.size(); //学生总数量
        if (count > allCount){
            Toast.makeText(getContext(), "人数超过学生总数量!", Toast.LENGTH_SHORT).show();
            return null;
        }
        List orderList = new ArrayList();
        for (int i=0; i<count; i++){
            int order = new Random().nextInt(allCount);
            while (orders.contains(order)){
                order = new Random().nextInt(allCount);
            }
            orderList.add(order);
        }
        return orderList;
    }

}
