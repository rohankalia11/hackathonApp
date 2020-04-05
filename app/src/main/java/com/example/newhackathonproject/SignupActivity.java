package com.example.newhackathonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    TextView alreadyhaveaccount;

    EditText emailEdt,useNameEdt,passEdt;
    ImageView signupbtn;
    Spinner s;

    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_signup);

        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Accounts");

        emailEdt= findViewById(R.id.signup_email);
        passEdt=findViewById(R.id.signup_pass);
        useNameEdt=findViewById(R.id.signup_username);
        signupbtn=findViewById(R.id.signup_btn);

        progressBar=findViewById(R.id.progressBar);


        String[] arraySpinner = new String[] {"User", "NGO"};
         s = (Spinner) findViewById(R.id.spinner);
        s.setPrompt("Type");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);



        alreadyhaveaccount = findViewById(R.id.alreadyhaveaccount);


        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();

            }
        });




        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                final String email = emailEdt.getText().toString().trim();
                String pass = passEdt.getText().toString().trim();
                final String userName = useNameEdt.getText().toString().trim();
                final String type = s.getSelectedItem().toString();


                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignupActivity.this, "Email empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                if (TextUtils.isEmpty(pass))
                {
                    Toast.makeText(SignupActivity.this, "Password empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                if (TextUtils.isEmpty(userName))
                {
                    Toast.makeText(SignupActivity.this, "username  empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                if (pass.length()<6)
                {
                    Toast.makeText(SignupActivity.this, "password should be greater than 5", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }



                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {

                            progressBar.setVisibility(View.INVISIBLE);
                            if (type.equals("User"))
                            {
                                Toast.makeText(SignupActivity.this, "Signup Successful : go to login Page", Toast.LENGTH_SHORT).show();

                                HashMap<String,String> hashMap= new HashMap<>();

                                hashMap.put("Email",email);
                                hashMap.put("Username",userName);
                                hashMap.put("Type",type);

                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(hashMap);

                            }

                            if (type.equals("NGO"))
                            {

                                Toast.makeText(SignupActivity.this, "Signup Successful : go to login Page", Toast.LENGTH_SHORT).show();
                                HashMap<String,String> hashMap= new HashMap<>();
                                hashMap.put("Email",email);
                                hashMap.put("Username",userName);
                                hashMap.put("Type",type);

                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(hashMap);

                            }


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));


    }
}
