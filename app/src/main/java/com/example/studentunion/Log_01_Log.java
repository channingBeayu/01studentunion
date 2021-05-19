package com.example.studentunion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.admin.Admin_index;

public class Log_01_Log extends AppCompatActivity {
    TextView regButton;
    Button logButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_01_log);

        final SharedPreferences sharedPreferences =
                getSharedPreferences("mrsoft", MODE_PRIVATE);

        EditText stuNumEditText = findViewById(R.id.stuNum);
        EditText passwordEditText = findViewById(R.id.password);

        //注册按钮的点击事件
        regButton =  findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Log_02_Reg.class);
                startActivity(intent);
            }
        });

        //登录按钮的点击事件
        logButton = (Button) findViewById(R.id.logButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stuNum_in = stuNumEditText.getText().toString();
                String password_in = passwordEditText.getText().toString();

                if("admin".equals(stuNum_in) && "111".equals(password_in)){
                    //管理员 跳转到管理页面
                    startActivity(new Intent(Log_01_Log.this, Admin_index.class));
                    return;
                }

                if ((stuNum_in == null || password_in == null) || ("".equals(stuNum_in) || "".equals(password_in))){
                    Toast.makeText(Log_01_Log.this, "您输入的学号或密码为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //从数据库中根据学号查找该学生
                StudentDao studentDao = new StudentDao(Log_01_Log.this);
                Student student = studentDao.queryByStuNum(stuNum_in);

                if(student == null){
                    Toast.makeText(Log_01_Log.this, "该学号未被录入！", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!password_in.equals(student.getPassword())){
                    Toast.makeText(Log_01_Log.this, "您输入的学号或密码不正确！", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("stuNum", stuNum_in);
                editor.commit();

                Bundle bundle = new Bundle();
                bundle.putCharSequence("stuNum", stuNum_in);
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}