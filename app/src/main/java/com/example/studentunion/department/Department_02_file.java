package com.example.studentunion.department;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
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

import com.example.studentunion.Beans.File;
import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.FileDao;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.Log_02_Reg;
import com.example.studentunion.R;
import com.example.studentunion.dailyWork.DailyWork_01_2_showStudents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department_02_file extends AppCompatActivity {
    private FileDao fileDao;
    String[] fileLabel = new String[]{"fileName", "groupchat"};
    List<File> files;
    int selectedNum = 0;    //查询的标签
    String selectedDepartment;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_02_file);

        //从数据库中返回所有文件
        fileDao = new FileDao(this);
        files = fileDao.findAll();

        listView = findViewById(R.id.listView);
        if (files != null){
            //给ListView添加数据
            listviewAdd(files);
        }




        /*********************listviewItem 删除文件*************************/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(Department_02_file.this.getParent()).setTitle("您要对这条记录进行什么操作？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            Integer _id = files.get(position).get_id();
                            FileDao fileDao = new FileDao(Department_02_file.this);
                            File file = (File)fileDao.queryById(_id);
//                            Toast.makeText(Department_02_file.this, file.getFileName(), Toast.LENGTH_SHORT).show();
                            fileDao.removeById(_id);
                        })
                        .setNeutralButton("取消", (dialog, which) -> { })
                        .setNegativeButton("修改", (dialog, which) -> {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Integer _id = files.get(position).get_id();
                                    Message message = new Message();
                                    message.arg1 = _id;
                                    handler.sendMessage(message);
                                }
                            });
                            thread.start();
                        }).show();
            }
        });
        /*************************************************/



        /****************查询选中部门的文件**************/
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNum = position;
                Resources resources = getResources();
                String[] departmentListLabel = resources.getStringArray(R.array.departmentList);
                selectedDepartment = departmentListLabel[selectedNum];
//                Toast.makeText(getApplicationContext(), selectedDepartment, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //点击事件  查询
        Button queryButton = findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileDao fileDao = new FileDao(getApplicationContext());
                List<File> fileList;
                if ("所有".equals(selectedDepartment)){
                    fileList = fileDao.findAll();
                }else {
                    fileList = fileDao.queryByDepartment(selectedDepartment);
                }

                if (fileList.size() == 0){
                    Toast.makeText(getApplicationContext(), "该部门的文件信息为空！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList("fileList", (ArrayList<? extends Parcelable>) fileList);
//
//                    Intent intent = new Intent(getApplicationContext(), Department_02_showFile.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                    listviewAdd(fileList);
                }
            }
        });
        /************************************/

        //点击事件 添加一个文件
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Department_02_file.this, Department_02_addFile.class);
                startActivity(intent);
            }
        });





    }

    final Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            Integer _id = message.arg1;
            Bundle bundle = new Bundle();
            bundle.putInt("_id", _id);
            Intent intent = new Intent(Department_02_file.this, Department_02_addFile.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    public void listviewAdd(List<File> files){
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < files.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("fileName", files.get(i).getFileName());
            listItem.put("groupchat", files.get(i).getGroupchat());
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.department_02_listview, fileLabel, new int[]{R.id.fileName, R.id.groupchat});
        listView.setAdapter(simpleAdapter);
    }
}