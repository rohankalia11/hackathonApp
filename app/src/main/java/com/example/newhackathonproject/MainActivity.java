package com.example.newhackathonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView add,userActiverequestlist,userDone;

    String name;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

        add=findViewById(R.id.addI);
        userActiverequestlist=findViewById(R.id.user_Active_request);

        userDone = findViewById(R.id.user_done);
        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Requests");
        firebaseUser= firebaseAuth.getCurrentUser();
         name = getIntent().getStringExtra("username");

         userDone.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent = new Intent(MainActivity.this,UserRequestDoneActivity.class);
                 // intent.putExtra("username",name);
                 startActivity(intent);
             }
         });

         userActiverequestlist.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this,UserActiveRequestActivity.class);
                // intent.putExtra("username",name);
                 startActivity(intent);

             }
         });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(MainActivity.this,AddrequestActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);

                finish();
                //startActivity(new Intent(MainActivity.this,AddrequestActivity.class));
            }
        });

        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists())
                {
                    add.setEnabled(false);


                }
                else
                {
                    add.setEnabled(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
