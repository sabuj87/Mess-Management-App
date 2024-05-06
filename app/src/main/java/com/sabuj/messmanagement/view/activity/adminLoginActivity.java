package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Admin;
import com.sabuj.messmanagement.storage.Save;

import xyz.hasnat.sweettoast.SweetToast;

public class adminLoginActivity extends AppCompatActivity {
    private EditText adminLoginEmail,adminLoginPassword;
    private Button adminLoginButton;
    private FirebaseAuth auth;
    private DatabaseReference adminRef;
    private Button adminGoSignUp,loginAsMessMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
       adminGoSignUp=findViewById(R.id.goSignUp);
        adminGoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminLoginActivity.this,AdminSignUpActivity.class);
                startActivity(intent);
            }
        });
        auth=FirebaseAuth.getInstance();
       adminLoginEmail=findViewById(R.id.loginAdminEmail);
       adminLoginPassword=findViewById(R.id.adminLoginPass);
      adminLoginButton=findViewById(R.id.adminLoginButton);
      loginAsMessMember=findViewById(R.id.logInasMessMemember);
      loginAsMessMember.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(adminLoginActivity.this,MemberLogInActivity.class);
              startActivity(intent);
          }
      });

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        final String email=adminLoginEmail.getText().toString().trim();
        String password=adminLoginPassword.getText().toString().trim();
        if(email.isEmpty()){
            SweetToast.warning(getApplicationContext(),"Enter email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            SweetToast.warning(getApplicationContext(),"Enter Valid email");
        }else  if(password.isEmpty()){
            SweetToast.error(getApplicationContext(),"Enter Password");
        }else  if(password.length()<6){
            SweetToast.error(getApplicationContext(),"Password length must be 6");
        }else {

            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Query adminRef= FirebaseDatabase.getInstance().getReference().child("admin").orderByChild("email").equalTo(email);
                                adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                if(snapshot.hasChildren()){
                                                    Admin admin=snapshot.getValue(Admin.class);
                                                    new Save().admin_saveInfo(getApplicationContext(),admin);
                                                }
                                                SweetToast.success(getApplicationContext(),"Log in Successfully");
                                                Intent intent=new Intent(adminLoginActivity.this,AdminActivity.class);
                                                startActivity(intent);
                                                new Save().admin_saveData(getApplicationContext(),true);
                                                finish();


                                            }
                                        }




                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }else {

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                     SweetToast.error(getApplicationContext(),e.getMessage());
                }
            });
        }

    }
}
