package com.example.newhackathonproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class NgoMainActivity extends AppCompatActivity {

    ImageView showl,activerequest,ngoDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_ngo_main);


        showl=findViewById(R.id.showList);

        activerequest=findViewById(R.id.ngoActiveRequest);

        ngoDone=findViewById(R.id.ngo_done);


        ngoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(NgoMainActivity.this,NgoRequestDoneActivity.class);
                // intent.putExtra("username",name);
                startActivity(intent);
            }
        });


        showl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(NgoMainActivity.this,ShowListActivity.class));



            }
        });


        activerequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(NgoMainActivity.this,NgoActiveRequestActivity.class));



            }
        });


    }
}
