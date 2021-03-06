package com.example.newhackathonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newhackathonproject.Model.ActiveRequestModel;
import com.example.newhackathonproject.Model.UserActiveRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActiveRequestActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseUser firebaseUser;
    String name;

    UserActiveRequestAdapter userActiveRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_user_active_request);



        recyclerView=findViewById(R.id.active_user_recyclerview);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final List<UserActiveRequestModel> userActiveRequestModelList = new ArrayList<>();

        userActiveRequestAdapter = new UserActiveRequestAdapter(userActiveRequestModelList);

        recyclerView.setAdapter(userActiveRequestAdapter);

        userActiveRequestModelList.clear();
        userActiveRequestAdapter.notifyDataSetChanged();

        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("ActiveRequests");

        databaseReference1= FirebaseDatabase.getInstance().getReference("Accounts");
        firebaseUser= firebaseAuth.getCurrentUser();



        databaseReference1.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("Username").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userActiveRequestAdapter.notifyDataSetChanged();
                userActiveRequestModelList.clear();

                try {


                    if (dataSnapshot.exists()) {



                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (snapshot.child("username").getValue().toString().equals(name)) {

                                //requestModelList.clear();
                                userActiveRequestModelList.add(new UserActiveRequestModel(
                                        snapshot.child("Address").getValue().toString(),
                                        snapshot.child("ContactNo").getValue().toString(),
                                        snapshot.child("UserMail").getValue().toString(),
                                        snapshot.child("username").getValue().toString(),
                                        snapshot.child("PeopleCount").getValue().toString(),
                                        (long) snapshot.child("timestamp").getValue(),
                                        snapshot.child("AcceptedBy").getValue().toString()
                                ));

                                //requestAdapter.notifyDataSetChanged();
                            }


                        }

                        //  progressBar.setVisibility(View.INVISIBLE);


                    }

                    else
                    {
                        // progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(UserActiveRequestActivity.this, "Added", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                //progressBar.setVisibility(View.INVISIBLE);
            }
        });




    }
}
