package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.model.Mess;
import com.sabuj.messmanagement.storage.Save;

import xyz.hasnat.sweettoast.SweetToast;

public class MemberLogInActivity extends AppCompatActivity {
    private EditText memberLogInEmail,memberLoginPassword;
    private Button memberLoginButton;
    String messId;
    String memberEmail2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_log_in);
        memberLogInEmail=findViewById(R.id.loginMemberEmail);
        memberLoginPassword=findViewById(R.id.memberLoginPass);
        memberLoginButton=findViewById(R.id.memberLoginButton);
        memberLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberlogin();
            }
        });
    }

    private void memberlogin() {
        final String memberEmail=memberLogInEmail.getText().toString();
        final String memberPassword=memberLoginPassword.getText().toString();
        if(memberEmail.isEmpty()){
            SweetToast.warning(getApplicationContext(),"Enter email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(memberEmail).matches()){
            SweetToast.warning(getApplicationContext(),"Enter valid email");
        }else if(memberPassword.isEmpty()){
            SweetToast.warning(getApplicationContext(),"Enter password");
        }else {
            Query memberRef= FirebaseDatabase.getInstance().getReference().child("member").orderByChild("member_email").equalTo(memberEmail);
            memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            if(dataSnapshot1.hasChildren()){
                                Member member=dataSnapshot1.getValue(Member.class);
                                memberEmail2=member.getMember_email();
                                messId=member.getMember_mess_id();
                                new Save().member_saveInfo(getApplicationContext(),member);



                            }
                        }
                        if(messId!=null){
                            Query messRef=FirebaseDatabase.getInstance().getReference().child("mess").orderByChild("id").equalTo(messId);
                            messRef.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                                            if(dataSnapshot2.hasChildren()){
                                                Mess mess=dataSnapshot2.getValue(Mess.class);
                                                final  String meesPassword=mess.getPassword();
                                                SweetToast.success(getApplicationContext(),meesPassword);
                                                if(meesPassword.equals(memberPassword)){
                                                    SweetToast.success(getApplicationContext(),"Login successfully");
                                                    Intent intent=new Intent(MemberLogInActivity.this,MemberActivity.class);
                                                    new Save().member_saveData(getApplicationContext(),true);
                                                    startActivity(intent);


                                                }else {
                                                    SweetToast.error(getApplicationContext(),"You entered wrong password");
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }else {
                        SweetToast.error(getApplicationContext(),"Your email number is not registered!Contact admin of the mess");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
