package com.example.studentunion.dailyWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentunion.Beans.Meeting;
import com.example.studentunion.Dao.MeetingDao;
import com.example.studentunion.R;
import com.example.studentunion.TabHost_index;

public class DailyWork_01_3_addMeeting extends AppCompatActivity {
    private MeetingDao meetingDao;
    private Button addMeetingButton;
    private Button updateMeetingButton;
    private EditText themeEditText;
    private EditText timeEditText;
    private EditText typeEditText;
    private EditText briefEditText;

    //修改时
    Meeting meeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailywork_01_3_addmeeting);
        themeEditText = findViewById(R.id.theme);
        timeEditText = findViewById(R.id.time);
        typeEditText = findViewById(R.id.type);
        briefEditText = findViewById(R.id.brief);
        addMeetingButton = (Button) findViewById(R.id.addMeetingButton);
        updateMeetingButton = (Button) findViewById(R.id.updateMeetingButton);

        if (getIntent().getExtras() != null){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String theTheme = bundle.getString("theme");
            meetingDao = new MeetingDao(this);
            meeting = meetingDao.queryByTheme(theTheme);
            themeEditText.setText(meeting.getTheme());
            timeEditText.setText(meeting.getTime());
            typeEditText.setText(meeting.getType());
            briefEditText.setText(meeting.getBreif());
            addMeetingButton.setVisibility(View.GONE);
            findViewById(R.id.update).setVisibility(View.VISIBLE);
        }

        //添加按钮的点击事件
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] meetingInfo = check();
                if (check() == null){
                    return;
                }

                //上述通过验证后，添加会议
                Meeting meeting = new Meeting();
                meeting.setTheme(meetingInfo[0]);
                meeting.setTime(meetingInfo[1]);
                meeting.setType(meetingInfo[2]);
                meeting.setBreif(meetingInfo[3]);

                MeetingDao meetingDao = new MeetingDao(DailyWork_01_3_addMeeting.this);
                meetingDao.add(meeting);
                Toast.makeText(DailyWork_01_3_addMeeting.this, "会议添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                startActivity(intent);
            }
        });


        //修改按钮的点击事件
        updateMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] meetingInfo = check();
                if (check() == null){
                    return;
                }

                //上述通过验证后，添加会议
                meeting.setTheme(meetingInfo[0]);
                meeting.setTime(meetingInfo[1]);
                meeting.setType(meetingInfo[2]);
                meeting.setBreif(meetingInfo[3]);
                MeetingDao meetingDao = new MeetingDao(DailyWork_01_3_addMeeting.this);
                meetingDao.update(meeting);
                Toast.makeText(DailyWork_01_3_addMeeting.this, "会议修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TabHost_index.class);
                startActivity(intent);
            }
        });
    }

    //要提交时的检查
    public String[] check(){
        String theme = themeEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String type = typeEditText.getText().toString();
        String brief = briefEditText.getText().toString();
        if("".equals(theme) || theme == null){
            Toast.makeText(DailyWork_01_3_addMeeting.this, "未输入会议主题！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(time) || time == null){
            Toast.makeText(DailyWork_01_3_addMeeting.this, "未输入会议时间！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(type) || type == null){
            Toast.makeText(DailyWork_01_3_addMeeting.this, "未输入会议类型！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(brief) || brief == null){
            Toast.makeText(DailyWork_01_3_addMeeting.this, "未输入会议简介！", Toast.LENGTH_SHORT).show();
            return null;
        }
        return new String[]{theme, time, type, brief};
    }

}