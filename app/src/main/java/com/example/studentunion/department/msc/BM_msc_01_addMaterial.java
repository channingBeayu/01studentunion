package com.example.studentunion.department.msc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentunion.Beans.Material;
import com.example.studentunion.Dao.MaterialDao;
import com.example.studentunion.R;
import com.example.studentunion.dailyWork.DailyWork_01_index;

public class BM_msc_01_addMaterial extends AppCompatActivity {
    private MaterialDao materialDao;
    private Button addMaterialButton;
    private Button updateMaterialButton;
    private EditText materialNameEditText;
    private EditText numEditText;
    private EditText positionEditText;
    private EditText briefEditText;

    //修改时
    Material material;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_msc_addmaterial);
        materialNameEditText = findViewById(R.id.materialName);
        numEditText = findViewById(R.id.num);
        positionEditText = findViewById(R.id.position);
        addMaterialButton = (Button) findViewById(R.id.addMaterialButton);
        updateMaterialButton = (Button) findViewById(R.id.updateMaterialButton);

        if (getIntent().getExtras() != null){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String theMaterialName = bundle.getString("materialName");
            materialDao = new MaterialDao(this);
            material = materialDao.queryByMaterialName(theMaterialName);
            materialNameEditText.setText(material.getMaterialName());
            numEditText.setText(material.getNum());
            positionEditText.setText(material.getPosition());
            addMaterialButton.setVisibility(View.GONE);
            findViewById(R.id.update).setVisibility(View.VISIBLE);
        }

        //添加物资 点击事件
        addMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] materialInfo = check();
                if (materialInfo == null){
                    return;
                }

                //上述通过验证后，添加物资
                Material material = new Material();
                material.setMaterialName(materialInfo[0]);
                material.setNum(materialInfo[1]);
                material.setPosition(materialInfo[2]);

                MaterialDao materialDao = new MaterialDao(BM_msc_01_addMaterial.this);
                materialDao.add(material);
                Toast.makeText(BM_msc_01_addMaterial.this, "物资添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BM_msc_01_material.class);
                startActivity(intent);
            }
        });


        //修改按钮的点击事件
        updateMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] materialInfo = check();
                if (check() == null){
                    return;
                }

                //上述通过验证后，添加物资
                material.setMaterialName(materialInfo[0]);
                material.setNum(materialInfo[1]);
                material.setPosition(materialInfo[2]);
                MaterialDao materialDao = new MaterialDao(BM_msc_01_addMaterial.this);
                materialDao.update(material);
                Toast.makeText(BM_msc_01_addMaterial.this, "物资修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DailyWork_01_index.class);
                startActivity(intent);
            }
        });
    }

    //要提交时的检查
    public String[] check(){
        String materialName = materialNameEditText.getText().toString();
        String num = numEditText.getText().toString();
        String position = positionEditText.getText().toString();
        if("".equals(materialName) || materialName == null){
            Toast.makeText(BM_msc_01_addMaterial.this, "未输入物资名！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(num) || num == null){
            Toast.makeText(BM_msc_01_addMaterial.this, "未输入物资现有数量！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if("".equals(position) || position == null){
            Toast.makeText(BM_msc_01_addMaterial.this, "未输入物资存放位置！", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new String[]{materialName, num, position};

    }

}