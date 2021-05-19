package com.example.studentunion.dailyWork;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;


import androidx.annotation.RequiresApi;

import com.example.studentunion.Beans.Meeting;
import com.example.studentunion.Dao.MeetingDao;
import com.example.studentunion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DailyWork_01_Fragment3_MeetingItem extends Fragment {
    private MeetingDao meetingDao;
    private int[] images = new int[]{R.drawable.meetingitem01, R.drawable.meetingitem02, R.drawable.meetingitem03, R.drawable.meetingitem04, R.drawable.meetingitem05, R.drawable.meetingitem06 };
    private Animation[] animations = new Animation[2];
    ViewFlipper viewFlipper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailywork_01_fragment3_meetingitem, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.parentLinearLayout);


        //添加轮播图
        viewFlipper = linearLayout.findViewById(R.id.viewFlipper);
        for (int i = 0;i < images.length; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        animations[0] = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animations[1] = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        viewFlipper.setInAnimation(animations[0]);
        viewFlipper.setOutAnimation(animations[1]);



        //从数据库中返回所有会议的信息
        meetingDao = new MeetingDao(getContext());
        List<Meeting> meetings = meetingDao.findAll();

        //给ListView添加数据
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < meetings.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("theme", meetings.get(i).getTheme());
            listItem.put("type", meetings.get(i).getType());
            listItem.put("time", meetings.get(i).getTime());
            listItem.put("brief", meetings.get(i).getBreif());
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listItems, R.layout.dailywork_01_fragment3_listviewitem, new String[]{"theme", "type", "time", "brief"}, new int[]{R.id.theme, R.id.type, R.id.time, R.id.brief});
        ListView listView = linearLayout.findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);

        //跳转到 添加会议记录 页面
        Button addMeetingButton = view.findViewById(R.id.addMeetingButton);
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DailyWork_01_3_addMeeting.class);
                startActivity(intent);
            }
        });

        //删除/修改一条会议记录
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), ((TextView)((GridLayout)view).findViewById(R.id.theme)).getText().toString(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext()).setTitle("您要对这条记录进行什么操作？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            String theme = ((TextView)((GridLayout)view).findViewById(R.id.theme)).getText().toString();
                            meetingDao.removeByTheme(theme);
                        })
                        .setNeutralButton("取消", (dialog, which) -> { })
                        .setNegativeButton("修改", (dialog, which) -> {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String theme = ((TextView)((GridLayout)view).findViewById(R.id.theme)).getText().toString();
                                    Message message = new Message();
                                    message.obj = theme;
                                    handler.sendMessage(message);
                                }
                            });
                            thread.start();
                        }).show();
            }
        });
        return view;
    }

    final Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            String theme = (String) message.obj;
            Bundle bundle = new Bundle();
            bundle.putString("theme", theme);
            Intent intent = new Intent(getContext(), DailyWork_01_3_addMeeting.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };






}



