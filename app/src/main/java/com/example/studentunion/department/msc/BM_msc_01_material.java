package com.example.studentunion.department.msc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.studentunion.Beans.Material;
import com.example.studentunion.Dao.MaterialDao;
import com.example.studentunion.R;

import java.util.List;

public class BM_msc_01_material extends AppCompatActivity {
    MaterialDao materialDao;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_msc_material);
        tableLayout = findViewById(R.id.tableLayout);

        //查找所有物资
        materialDao = new MaterialDao(this);
        List<Material> materialList = materialDao.findAll();
        for (int i=0; i<materialList.size(); i++){
            TableRow tableRow = new TableRow(this);
            TextView id = new TextView(this);
            TextView materialName = new TextView(this);
            TextView num = new TextView(this);
            TextView position = new TextView(this);
            id.setText(materialList.get(i).get_id().toString());
            materialName.setText(materialList.get(i).getMaterialName());
            num.setText(materialList.get(i).getNum());
            position.setText(materialList.get(i).getPosition());
            tableRow.addView(id);
            tableRow.addView(materialName);
            tableRow.addView(num);
            tableRow.addView(position);
            tableLayout.addView(tableRow);
        }

        //点击事件 添加物资 页面跳转
        Button addMaterialButton = findViewById(R.id.addMaterialButton);
        addMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BM_msc_01_material.this, BM_msc_01_addMaterial.class);
                startActivity(intent);
            }
        });


    }
}