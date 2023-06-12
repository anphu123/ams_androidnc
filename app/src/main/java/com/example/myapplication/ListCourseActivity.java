package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.example.myapplication.adapter.GetAllCourseAdapter;
import com.example.myapplication.database.DbHelper;
import com.example.myapplication.model.KhoaHoc;
import com.example.myapplication.services.GetAllCourseServices;

import java.util.ArrayList;

public class ListCourseActivity extends AppCompatActivity {

    ArrayList<KhoaHoc> arrKhoaHoc = new ArrayList<>();
    DbHelper dbHelper;
    ListView listViewAllCourse;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course);

        listViewAllCourse = findViewById(R.id.listViewAllCourse);
        ivBack = findViewById(R.id.ivBack);
        dbHelper = new DbHelper(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit);
            }
        });

        //đăng ký IntentFilter cho 2 cái service
        IntentFilter filterGetAllCourse = new IntentFilter("GetAllCourseServices");
        LocalBroadcastManager.getInstance(this).registerReceiver(getAllCourseReceiver, filterGetAllCourse);

        IntentFilter filterRegisterCourse = new IntentFilter("unRegisterAndRegisterCourseServices");
        LocalBroadcastManager.getInstance(this).registerReceiver(getAllCourseReceiver, filterRegisterCourse);

        Intent intent = new Intent(this, GetAllCourseServices.class);
        //thay đổi mssv phù hợp với chức năng đăng nhập/đăng ký
        intent.putExtra("maSV",LoginActivity.USER);
        intent.putExtra("isMine",false);
        intent.setAction("GetAllCourseServices");
        startService(intent);
    }

    private final BroadcastReceiver getAllCourseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra("action");
                switch (action){
                    case "GetAllCourseServices":
                    case "unRegisterAndRegisterCourseServices":
                        arrKhoaHoc.clear();
                        ArrayList<KhoaHoc> alCourse = (ArrayList<KhoaHoc>) intent.getSerializableExtra("allCourse");
                        ArrayList<KhoaHoc> alCourseRegister = (ArrayList<KhoaHoc>) intent.getSerializableExtra("allCourseRegister");
                        for(KhoaHoc kh : alCourse){
                            for(KhoaHoc khdk : alCourseRegister){
                                if(kh.getMaKH() == khdk.getMaKH()){
                                    kh.setCheck(true);
                                    break;
                                }
                            }
                            arrKhoaHoc.add(kh);
                        }

                        GetAllCourseAdapter adapter = new GetAllCourseAdapter(arrKhoaHoc, ListCourseActivity.this, dbHelper);
                        listViewAllCourse.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getAllCourseReceiver);
    }
}