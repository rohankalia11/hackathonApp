package com.example.newhackathonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView neddAccount;

    EditText emailEdt,passEdt;
    ImageView signinbtn;

    ProgressBar progressBar;
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
        setContentView(R.layout.activity_login);

        neddAccount = findViewById(R.id.needanaccount);



        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Accounts");

       firebaseUser= firebaseAuth.getCurrentUser();
        emailEdt= findViewById(R.id.signin_email);
        passEdt=findViewById(R.id.sign_pass);
        signinbtn=findViewById(R.id.sign_btn);

        progressBar=findViewById(R.id.progressBar2);





        neddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,SignupActivity.class));

                finish();

            }
        });



        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);

                final String email = emailEdt.getText().toString().trim();
                String pass = passEdt.getText().toString().trim();


                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginActivity.this, "Email empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                if (TextUtils.isEmpty(pass))
                {
                    Toast.makeText(LoginActivity.this, "Password empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }




                if (pass.length()<6)
                {
                    Toast.makeText(LoginActivity.this, "password should be greater than 5", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful())
                        {
                            databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String type = dataSnapshot.child("Type").getValue().toString();

                                    String name = dataSnapshot.child("Username").getValue().toString();
                                    if (type.equals("User"))
                                    {

                                        Toast.makeText(LoginActivity.this, "usewr", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("username",name);
                                        startActivity(intent);
                                    }
                                    if (type.equals("NGO"))
                                    {
                                        Toast.makeText(LoginActivity.this, "ngo", Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(LoginActivity.this,NgoMainActivity.class));
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });



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



}
