package com.example.studentunion.department;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studentunion.R;
import com.example.studentunion.department.msc.BM_msc_TabHost;

public class Department_01_list extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_01_list);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent1 = new Intent(Department_01_list.this, BM_msc_TabHost.class);
                        startActivity(intent1);
                        break;
//            case "wyb":
//                Intent intent2 = new Intent(DailyWork_01_index.this, xxx.class);
//                startActivity(intent2);
//                break;
//            case "jjb":
//                Intent intent3 = new Intent(DailyWork_01_index.this, xxx.class);
//                startActivity(intent3);
//                break;
//            case "kcb":
//                Intent intent4 = new Intent(DailyWork_01_index.this, xxx.class);
//                startActivity(intent4);
//                break;
//            case "tyb":
//                Intent intent5 = new Intent(DailyWork_01_index.this, xxx.class);
//                startActivity(intent5);
//                break;
                }
            }

        });
    }
}