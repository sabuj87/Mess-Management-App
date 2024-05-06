package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Admin;

import xyz.hasnat.sweettoast.SweetToast;

public class AdminSignUpActivity extends AppCompatActivity {
    private EditText adminSignUpName,adminSignupemail,adminSignUppassword,adminConfirmPassword;
    private Button adminsignUpButton;
    private FirebaseAuth auth;
    private DatabaseReference adminRef;
    private Button gologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);
        adminSignUpName=findViewById(R.id.admin_sign_up_name);
        adminSignupemail=findViewById(R.id.admin_sign_up_email);
        adminSignUppassword=findViewById(R.id.admin_sign_up_password);
        adminConfirmPassword=findViewById(R.id.admin_sign_up_confirm_password);
        adminsignUpButton=findViewById(R.id.admin_signUp_Btn);
        gologin=findViewById(R.id.gologin);
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSignUpActivity.this,adminLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        auth=FirebaseAuth.getInstance();

        adminsignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });



    }

    private void signUp() {
        final String name=adminSignUpName.getText().toString();
        final String email=adminSignupemail.getText().toString().trim();
        final String password=adminSignUppassword.getText().toString();
        String confirm_password=adminConfirmPassword.getText().toString();

        if (name.isEmpty()){
            SweetToast.warning(getApplicationContext(),"Enter your name");
        }else if(email.isEmpty()){
            SweetToast.warning(getApplicationContext(),"Enter your Email");

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            SweetToast.warning(getApplicationContext(),"Enter a valid Email");
        }else  if(password.isEmpty()){
            SweetToast.error(getApplicationContext(),"Enter a password");
        }else if(password.length()<6){
            SweetToast.error(getApplicationContext(),"Enter 6 digit password");

        }else if(confirm_password.isEmpty()){
            SweetToast.error(getApplicationContext(),"Confirm  your password");
        }else if(!confirm_password.equals(password)){
            SweetToast.error(getApplicationContext(),"Password does no match");
        }else {
            auth.createUserWithEmailAndPassword(email, confirm_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = auth.getCurrentUser().getUid();
                                adminRef = FirebaseDatabase.getInstance().getReference().child("admin").child(uid);
                                Admin admin = new Admin(uid,name, email, "", password,"false","");
                                adminRef.setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        SweetToast.success(getApplicationContext(), "SignUp Successfully");
                                        Intent intent = new Intent(AdminSignUpActivity.this, adminLoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        SweetToast.error(getApplicationContext(), e.getMessage());
                                    }
                                });
                            } else {
                                auth.signOut();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    SweetToast.error(getApplicationContext(), e.getMessage());

                }
            });
        }
    }
}
