package com.example.studentunion.department;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentunion.Beans.Bmaffair;
import com.example.studentunion.Dao.BmaffairDao;
import com.example.studentunion.R;
import com.example.studentunion.TabHost_index;

public class Department_03_addBmaffair extends AppCompatActivity {
    private BmaffairDao bmaffairDao;
    private Button addBmaffairButton;
    private Button updateBmaffairButton;
    private EditText bmaffairNameEditText;
    private EditText processEditText;
    private EditText departmentEditText;

    //修改时
    Bmaffair bmaffair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_03_addbmaffair);
        bmaffairNameEditText = findViewById(R.id.bmaffairName);
        processEditText = findViewById(R.id.process);
        departmentEditText = findViewById(R.id.department);
        addBmaffairButton = (Button) findViewById(R.id.addBmaffairButton);
        updateBmaffairButton = (Button) findViewById(R.id.updateBmaffairButton);

        if (getIntent().getExtras() != null){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Integer _id = bundle.getInt("_id");
            bmaffairDao = new BmaffairDao(this);
            bmaffair = bmaffairDao.queryById(_id);
            bmaffairNameEditText.setText(bmaffair.getBmaffairName());
            processEditText.setText(bmaffair.getProcess());
            departmentEditText.setText(bmaffair.getDepartment());
            addBmaffairButton.setVisibility(View.GONE);
            findViewById(R.id.update).setVisibility(View.VISIBLE);
        }

        //添加部门事务 点击事件
        addBmaffairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] bmaffairInfo = check();
                if (bmaffairInfo == null){
                    return;
                }

                //上述通过验证后，添加部门事务
                Bmaffair bmaffair = new Bmaffair();
                bmaffair.setBmaffairName(bmaffairInfo[0]);
                bmaffair.setProcess(Integer.parseInt(bmaffairInfo[1]));
                bmaffair.setDepartment(bmaffairInfo[2]);

                BmaffairDao bmaffairDao = new BmaffairDao(Department_03_addBmaffair.this);
                bmaffairDao.add(bmaffair);
                Toast.makeText(Department_03_addBmaffair.this, "部门事务添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                startActivity(intent);
            }
        });


        //修改按钮的点击事件
        updateBmaffairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] bmaffairInfo = check();
                if (check() == null){
                    return;
                }

                //上述通过验证后，添加部门事务
                bmaffair.setBmaffairName(bmaffairInfo[0]);
                bmaffair.setProcess(Integer.parseInt(bmaffairInfo[1]));
                bmaffair.setDepartment(bmaffairInfo[2]);
                BmaffairDao bmaffairDao = new BmaffairDao(Department_03_addBmaffair.this);
                bmaffairDao.update(bmaffair);
                Toast.makeText(Department_03_addBmaffair.this, "部门事务修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                startActivity(intent);
            }
        });
    }

    //要提交时的检查
    public String[] check(){
        String bmaffairName = bmaffairNameEditText.getText().toString();
//        Integer process = Integer.parseInt(processEditText.getText().toString());
        String process = processEditText.getText().toString();
        String department = departmentEditText.getText().toString();
        if("".equals(bmaffairName) || bmaffairName == null){
            Toast.makeText(Department_03_addBmaffair.this, "未输入事务名！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(process) || process == null){
            Toast.makeText(Department_03_addBmaffair.this, "未输入事务所在群聊！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(department) || department == null){
            Toast.makeText(Department_03_addBmaffair.this, "未输入事务所属部门！", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new String[]{bmaffairName, process, department};

    }

}