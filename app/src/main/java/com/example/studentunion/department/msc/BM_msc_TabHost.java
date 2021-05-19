package com.example.studentunion.department.msc;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.studentunion.department.Department_01_list;

public class BM_msc_TabHost extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("Tab1")
                .setIndicator("物资表")
                .setContent(new Intent(this, BM_msc_01_material.class)));

        tabHost.addTab(tabHost.newTabSpec("Tab2")
                .setIndicator("值班表")
                .setContent(new Intent(this, BM_msc_02_duty.class)));


    }


}