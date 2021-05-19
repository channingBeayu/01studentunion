package com.example.studentunion.department;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Department_TabHost extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("Tab1")
                .setIndicator("各部门")
                .setContent(new Intent(this, Department_01_list.class)));

        tabHost.addTab(tabHost.newTabSpec("Tab2")
                .setIndicator("关键文件整理")
                .setContent(new Intent(this, Department_02_file.class)));

        tabHost.addTab(tabHost.newTabSpec("Tab3")
                .setIndicator("事务进度")
                .setContent(new Intent(this, Department_03_bmaffair.class)));
//
//        tabHost.addTab(tabHost.newTabSpec("Tab4")
//                .setIndicator("志愿者随机选择")
//                .setContent(new Intent(this, xxx.class)));





    }
}