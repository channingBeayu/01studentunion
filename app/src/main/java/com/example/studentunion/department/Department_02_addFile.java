package com.example.studentunion.department;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentunion.Beans.File;
import com.example.studentunion.Dao.FileDao;
import com.example.studentunion.R;
import com.example.studentunion.TabHost_index;
import com.example.studentunion.dailyWork.DailyWork_01_index;

public class Department_02_addFile extends AppCompatActivity {
    private FileDao fileDao;
    private Button addFileButton;
    private Button updateFileButton;
    private EditText fileNameEditText;
    private EditText groupchatEditText;
    private EditText departmentEditText;

    //修改时
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_02_addfile);
        fileNameEditText = findViewById(R.id.fileName);
        groupchatEditText = findViewById(R.id.groupchat);
        departmentEditText = findViewById(R.id.department);
        addFileButton = (Button) findViewById(R.id.addFileButton);
        updateFileButton = (Button) findViewById(R.id.updateFileButton);

        if (getIntent().getExtras() != null){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Integer _id = bundle.getInt("_id");
            fileDao = new FileDao(this);
            file = fileDao.queryById(_id);
            fileNameEditText.setText(file.getFileName());
            groupchatEditText.setText(file.getGroupchat());
            departmentEditText.setText(file.getDepartment());
            addFileButton.setVisibility(View.GONE);
            findViewById(R.id.update).setVisibility(View.VISIBLE);
        }

        //添加文件 点击事件
        addFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] fileInfo = check();
                if (fileInfo == null){
                    return;
                }

                //上述通过验证后，添加文件
                File file = new File();
                file.setFileName(fileInfo[0]);
                file.setGroupchat(fileInfo[1]);
                file.setDepartment(fileInfo[2]);

                FileDao fileDao = new FileDao(Department_02_addFile.this);
                fileDao.add(file);
                Toast.makeText(Department_02_addFile.this, "文件添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                startActivity(intent);
            }
        });


        //修改按钮的点击事件
        updateFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] fileInfo = check();
                if (check() == null){
                    return;
                }

                //上述通过验证后，添加文件
                file.setFileName(fileInfo[0]);
                file.setGroupchat(fileInfo[1]);
                file.setDepartment(fileInfo[2]);
                FileDao fileDao = new FileDao(Department_02_addFile.this);
                fileDao.update(file);
                Toast.makeText(Department_02_addFile.this, "文件修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                startActivity(intent);
            }
        });
    }

    //要提交时的检查
    public String[] check(){
        String fileName = fileNameEditText.getText().toString();
        String groupchat = groupchatEditText.getText().toString();
        String department = departmentEditText.getText().toString();
        if("".equals(fileName) || fileName == null){
            Toast.makeText(Department_02_addFile.this, "未输入文件名！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(groupchat) || groupchat == null){
            Toast.makeText(Department_02_addFile.this, "未输入文件所在群聊！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(department) || department == null){
            Toast.makeText(Department_02_addFile.this, "未输入文件所属部门！", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new String[]{fileName, groupchat, department};

    }

}