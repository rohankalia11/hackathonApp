package com.example.newhackathonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddrequestActivity extends AppCompatActivity {

    EditText peoplecount,contactno,address;
    Button addrequest;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_addrequest);

        peoplecount=findViewById(R.id.no_of_people);
        contactno=findViewById(R.id.contact_no);
        address =findViewById(R.id.address);
        addrequest=findViewById(R.id.add_request_btn);


        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Requests");
        firebaseUser= firebaseAuth.getCurrentUser();








        addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String count = peoplecount.getText().toString();

                String number = contactno.getText().toString();
                String addr = address.getText().toString();
                String usermail = firebaseUser.getEmail();
                String userName = getIntent().getStringExtra("username");


                //String time = ServerValue.TIMESTAMP.toString();

                //Date date = new Date(time);
                //String datepattern = "MMM dd, yyyy EEE h:mm a";
                //String datetext = new SimpleDateFormat(datepattern).format(date);


                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("PeopleCount", count);
                hashMap.put("ContactNo", number);
                hashMap.put("Address", addr);
                hashMap.put("UserMail", usermail);
                hashMap.put("username", userName);
              //  hashMap.put("timestamp", ServerValue.TIMESTAMP.toString());


                databaseReference.child(firebaseUser.getUid()).setValue(hashMap);

                databaseReference.child(firebaseUser.getUid()).child("timestamp").setValue(ServerValue.TIMESTAMP);

                Toast.makeText(AddrequestActivity.this, "Added", Toast.LENGTH_SHORT).show();


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {



                        startActivity(new Intent(AddrequestActivity.this,MainActivity.class));

                        finish();


                    }
                },0);




            }
        });



    }
}
