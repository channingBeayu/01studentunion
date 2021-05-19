package com.example.studentunion;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.studentunion.dailyWork.DailyWork_01_index;
import com.example.studentunion.department.Department_TabHost;
import com.example.studentunion.my.My_01_index;

public class TabHost_index extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("Tab1")
                .setIndicator("日常办公")
                .setContent(new Intent(this, DailyWork_01_index.class)));

        tabHost.addTab(tabHost.newTabSpec("Tab2")
                .setIndicator("部门事务")
                .setContent(new Intent(this, Department_TabHost.class)));

        tabHost.addTab(tabHost.newTabSpec("Tab3")
                .setIndicator("我的")
                .setContent(new Intent(this, My_01_index.class)));

    }


}