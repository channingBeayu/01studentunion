package com.example.studentunion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentunion.Beans.Student;
import com.example.studentunion.Dao.StudentDao;
import com.example.studentunion.admin.Admin_index;

public class Log_02_Reg extends AppCompatActivity {
    Button regButton;
    EditText userNameEditText;
    EditText stuNumEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    EditText genderEditText;
    EditText phoneEditText;
    EditText classNameEditText;
    EditText departmentEditText;

    private StudentDao studentDao;
    private Button updateStudentButton;
    //修改时
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_02_reg);
        regButton = findViewById(R.id.regButton);
        updateStudentButton = findViewById(R.id.updateStudentButton);

        userNameEditText = findViewById(R.id.userName);
        stuNumEditText = findViewById(R.id.stuNum);
        passwordEditText = findViewById(R.id.password);
        passwordAgainEditText = findViewById(R.id.passwordAgain);
        genderEditText = findViewById(R.id.gender);
        phoneEditText = findViewById(R.id.phone);
        classNameEditText = findViewById(R.id.className);
        departmentEditText = findViewById(R.id.department);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            String stuNum = bundle.getString("stuNum");
            if ("admin".equals(stuNum)){  //true表示是添加学生

            }else {
                studentDao = new StudentDao(this);
                student = studentDao.queryByStuNum(stuNum);
                userNameEditText.setText(student.getUserName());
                stuNumEditText.setText(student.getStuNum());
                passwordEditText.setText(student.getPassword());
                genderEditText.setText(student.getGender());
                phoneEditText.setText(student.getPhone());
                classNameEditText.setText(student.getClassName());
                departmentEditText.setText(student.getDepartment());
                regButton.setVisibility(View.GONE);
                findViewById(R.id.passwordAgainRow).setVisibility(View.GONE);
                findViewById(R.id.updateStudentButton).setVisibility(View.VISIBLE);
            }

        }

        //注册按钮的点击事件
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] studentInfo = check();


                if (studentInfo == null){
                    return;
                }
                StudentDao studentDao2 = new StudentDao(Log_02_Reg.this);
                Student student2 = studentDao2.queryByStuNum(studentInfo[1]);
                if (student2 == null){
                    Toast.makeText(Log_02_Reg.this, "该生所属学号已注册！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!passwordEditText.getText().toString().equals(passwordAgainEditText.getText().toString()) || passwordEditText.getText().toString() == null){
                    Toast.makeText(Log_02_Reg.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                }else{
                    Student student = new Student();
                    student.setUserName(userNameEditText.getText().toString());
                    student.setStuNum(stuNumEditText.getText().toString());
                    student.setPassword(passwordEditText.getText().toString());
                    student.setGender(genderEditText.getText().toString());
                    student.setPhone(phoneEditText.getText().toString());
                    student.setClassName(classNameEditText.getText().toString());
                    student.setDepartment(departmentEditText.getText().toString());
                    StudentDao studentDao = new StudentDao(Log_02_Reg.this);
                    studentDao.add(student);
                    Toast.makeText(Log_02_Reg.this, "注册成功", Toast.LENGTH_SHORT).show();

                    String stuNum = bundle.getString("stuNum");
                    if ("admin".equals(stuNum)){
                        Intent intent_admin = new Intent(getApplicationContext(), Admin_index.class);
                        startActivity(intent_admin);
                    }else {
                        Intent intent_log = new Intent(getApplicationContext(), Log_01_Log.class);
                        startActivity(intent_log);
                    }
                }
            }
        });

        //修改按钮的点击事件
        updateStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] studentInfo = check();
                if (check() == null){
                    return;
                }

                //上述通过验证后，修改学生信息
                student.setUserName(userNameEditText.getText().toString());
                student.setStuNum(stuNumEditText.getText().toString());
                student.setPassword(passwordEditText.getText().toString());
                student.setGender(genderEditText.getText().toString());
                student.setPhone(phoneEditText.getText().toString());
                student.setClassName(classNameEditText.getText().toString());
                student.setDepartment(departmentEditText.getText().toString());
                StudentDao studentDao = new StudentDao(Log_02_Reg.this);
                studentDao.updateById(student);
                Toast.makeText(Log_02_Reg.this, "学生修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Admin_index.class);
                startActivity(intent);
            }
        });
    }

    //要提交时的检查
    public String[] check(){
        String userName = userNameEditText.getText().toString();
        String stuNum = stuNumEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String gender = genderEditText .getText().toString();
        String phone = phoneEditText .getText().toString();
        String className = classNameEditText .getText().toString();
        String department = departmentEditText .getText().toString();
        if("".equals(userName) || userName == null){
            Toast.makeText(Log_02_Reg.this, "未输入学生姓名！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(stuNum) || stuNum == null){
            Toast.makeText(Log_02_Reg.this, "未输入学生学号！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(password) || password == null){
            Toast.makeText(Log_02_Reg.this, "未输入密码！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (password.length()<6){
            Toast.makeText(Log_02_Reg.this, "密码长度不够，请重新设置！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(gender) || gender == null){
            Toast.makeText(Log_02_Reg.this, "未输入性别！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(phone) || phone == null){
            Toast.makeText(Log_02_Reg.this, "未输入手机号！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(className) || className == null){
            Toast.makeText(Log_02_Reg.this, "未输入班级！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(department) || department == null){
            Toast.makeText(Log_02_Reg.this, "未输入所在部门！", Toast.LENGTH_SHORT).show();
            return null;
        }



        return new String[]{userName, stuNum, password, gender, phone, className, department};

    }
}