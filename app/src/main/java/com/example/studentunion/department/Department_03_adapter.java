package com.example.studentunion.department;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentunion.Beans.Bmaffair;
import com.example.studentunion.Dao.BmaffairDao;
import com.example.studentunion.R;

import java.util.List;
import java.util.Map;

public class Department_03_adapter extends SimpleAdapter {
    List<? extends Map<String, ?>> data;
    List<Bmaffair> bmaffairs;
    Context context;
    Context pContext;
    public Department_03_adapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, List<Bmaffair> bmaffairs, Context pContext) {
        super(context, data, resource, from, to);
        this.data = data;
        this.bmaffairs = bmaffairs;
        this.context = context;
        this.pContext = pContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Integer process = (Integer) data.get(position).get("process");
        SeekBar seekBar = (SeekBar) ((LinearLayout) view).findViewById(R.id.process);
        seekBar.setProgress(process);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Integer _id = Integer.parseInt(((TextView) ((LinearLayout) view).findViewById(R.id._id)).getText().toString());


                new AlertDialog.Builder(pContext).setTitle("您要对这条记录进行什么操作？")
                        .setPositiveButton("确定修改进度", (dialog, which) -> {
                            bmaffairs.get(position).setProcess(progress);
                            BmaffairDao bmaffairDao = new BmaffairDao(context);
                            Bmaffair bmaffair = bmaffairDao.queryById(_id);
                            bmaffair.setProcess(progress);
                            BmaffairDao bmaffairDao2 = new BmaffairDao(context);
                            bmaffairDao2.update(bmaffair);
                        })
                        .setNegativeButton("删除该事务", (dialog, which) -> {
                            BmaffairDao bmaffairDao = new BmaffairDao(context);
                            Bmaffair bmaffair = (Bmaffair)bmaffairDao.queryById(_id);
                            bmaffairDao.removeById(_id);
                            Toast.makeText(context, "已删除，请刷新查看", Toast.LENGTH_SHORT).show();
                        })
                        .setNeutralButton("取消", (dialog, which) -> { })
                        .show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    public List<Bmaffair> getBmaffairs(){return bmaffairs;}
}
