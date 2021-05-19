package com.example.studentunion.department;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentunion.Beans.Bmaffair;
import com.example.studentunion.Dao.BmaffairDao;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department_03_bmaffair extends AppCompatActivity {
    private BmaffairDao bmaffairDao;
    String[] bmaffairLabel = new String[]{"bmaffairName", "process"};
    List<Bmaffair> bmaffairs;
    int selectedNum = 0;    //查询的标签
    String selectedDepartment;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_03_bmaffair);
        listView = findViewById(R.id.listView);


        /*********************listviewItem 点击事件 删除事务*************************/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(Department_03_bmaffair.this.getParent()).setTitle("您要对这条记录进行什么操作？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            Integer _id = bmaffairs.get(position).get_id();
                            BmaffairDao bmaffairDao = new BmaffairDao(Department_03_bmaffair.this);
                            Bmaffair bmaffair = (Bmaffair)bmaffairDao.queryById(_id);
                            Toast.makeText(Department_03_bmaffair.this, bmaffair.getBmaffairName(), Toast.LENGTH_SHORT).show();
//                            bmaffairDao.removeById(_id);
                        })
                        .setNeutralButton("取消", (dialog, which) -> { })
                        .show();
            }
        });
        /*************************************************/



        /****************spinner切换 显示选中部门的事务**************/
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedNum = position;
                Resources resources = getResources();
                String[] departmentListLabel = resources.getStringArray(R.array.departmentList);
                selectedDepartment = departmentListLabel[selectedNum];
//                Toast.makeText(getApplicationContext(), selectedDepartment, Toast.LENGTH_SHORT).show();
                if (selectedNum == departmentListLabel.length-1){
                    bmaffairDao = new BmaffairDao(Department_03_bmaffair.this);
                    bmaffairs = bmaffairDao.findAll();
                    setListView(bmaffairs);
                }else {
                    BmaffairDao bmaffairDao = new BmaffairDao(Department_03_bmaffair.this);
                    bmaffairs = bmaffairDao.queryByDepartment(selectedDepartment);
                    if (bmaffairs.size() == 0){
                        Toast.makeText(getApplicationContext(), "该部门的事务信息为空！", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        setListView(bmaffairs);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        /************************************/

        //点击事件 添加一个事务
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Department_03_bmaffair.this, Department_03_addBmaffair.class);
                startActivity(intent);
            }
        });
    }

    
    //替换listView中的数据内容
    public void setListView(List<Bmaffair> bmaffairs){
        if (bmaffairs != null){
            //给ListView添加数据
            List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
            for(int i = 0; i < bmaffairs.size(); i++){
                Map<String, Object> listItem = new HashMap<String, Object>();
                listItem.put("bmaffairName", bmaffairs.get(i).getBmaffairName());
                listItem.put("_id", bmaffairs.get(i).get_id());
                listItem.put("process", bmaffairs.get(i).getProcess());
                listItems.add(listItem);
            }
            Department_03_adapter simpleAdapter = new Department_03_adapter(this, listItems, R.layout.department_03_listview, new String[]{"_id", "bmaffairName"}, new int[]{R.id._id, R.id.bmaffairName}, bmaffairs, Department_03_bmaffair.this.getParent());
            bmaffairs = simpleAdapter.getBmaffairs();
            listView.setAdapter(simpleAdapter);
        }
    }
    
}