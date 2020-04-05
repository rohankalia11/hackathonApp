package com.example.newhackathonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newhackathonproject.Model.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_show_list);

        recyclerView=findViewById(R.id.show_list_recyclerView);


        progressBar=findViewById(R.id.progressBar3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final List<RequestModel> requestModelList = new ArrayList<>();

          requestAdapter = new RequestAdapter(requestModelList);

        recyclerView.setAdapter(requestAdapter);

        requestModelList.clear();
        requestAdapter.notifyDataSetChanged();

        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Requests");
        firebaseUser= firebaseAuth.getCurrentUser();


        progressBar.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



            }
        },5000);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                requestAdapter.notifyDataSetChanged();
                requestModelList.clear();

                try {


                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            //requestModelList.clear();
                            requestModelList.add(new RequestModel(
                                    snapshot.child("Address").getValue().toString(),
                                    snapshot.child("ContactNo").getValue().toString(),
                                    snapshot.child("UserMail").getValue().toString(),
                                    snapshot.child("username").getValue().toString(),
                                    snapshot.child("PeopleCount").getValue().toString(),
                                    (long) snapshot.child("timestamp").getValue()
                            ));

                            //requestAdapter.notifyDataSetChanged();



                        }

                        progressBar.setVisibility(View.INVISIBLE);


                    }

                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(ShowListActivity.this, "Added", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
            }
        });




    }
}
