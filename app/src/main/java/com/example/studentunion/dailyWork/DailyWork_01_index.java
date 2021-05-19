package com.example.studentunion.dailyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.studentunion.R;

public class DailyWork_01_index extends AppCompatActivity {
    TextView announcementLabel;
    TextView contactsLabel;
    TextView meetingItemsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailywork_01);
        announcementLabel = (TextView)findViewById(R.id.announcementLabel);
        contactsLabel = (TextView)findViewById(R.id.contactsLabel);
        meetingItemsLabel = (TextView)findViewById(R.id.meetingItemsLabel);

        //设置点击事件
        announcementLabel.setOnClickListener(listener);
        contactsLabel.setOnClickListener(listener);
        meetingItemsLabel.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Fragment f = null;
            switch (v.getId()){
                case R.id.announcementLabel:
                    f = new DailyWork_01_Fragment1_Volunteer();
                    break;
                case R.id.contactsLabel:
                    f = new DailyWork_01_Fragment2_Contacts();
                    break;
                case R.id.meetingItemsLabel:
                    f = new DailyWork_01_Fragment3_MeetingItem();
                    break;
                default:
                    break;
            }
//            Bundle bundle = new Bundle();
//            bundle.putString("context", ".this");
//            f.setArguments(bundle);
            fragmentTransaction.replace(R.id.childLinearLayout, f);
            fragmentTransaction.commit();
        }
    };


}