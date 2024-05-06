package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.extra.AlertDialogh;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.storage.Save;

import java.util.regex.Pattern;

import xyz.hasnat.sweettoast.SweetToast;

public class addMemberActivity extends AppCompatActivity {
    private EditText member_userName,member_Email,member_address,member_nid;
    private Button addMember_button;
    private String messId;
    private String messUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        member_userName=findViewById(R.id.memberUserName);
        member_Email=findViewById(R.id.memberEmail);
        member_address=findViewById(R.id.memberAddress);
        member_nid=findViewById(R.id.memberNid);
        messId=new Save().admin_loadInfo(getApplicationContext()).getId();
        messUserName=new Save().admin_loadInfo(getApplicationContext()).getMessUserNme();
        addMember_button=findViewById(R.id.addMemberButton);
        addMember_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=member_userName.getText().toString();
                String email=member_Email.getText().toString();
                String address=member_address.getText().toString();
                String nid=member_nid.getText().toString();
                if(userName.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter member username");

                }else if(email.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter member email");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    SweetToast.warning(getApplicationContext(),"Enter valid email");
                }else if(address.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter address");
                }else if(nid.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter nid");
                }else {
                    DatabaseReference memberRef= FirebaseDatabase.getInstance().getReference().child("member");
                    String key=memberRef.push().getKey();
                    Member member=new Member(key,
                            userName,
                            address,
                            email,
                            messUserName,
                            messId,
                            "",
                            "",
                            nid,
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            messId,
                            "false",
                            ""


                    );
                    memberRef.child(key).setValue(member)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        member_userName.setText("");
                                        member_Email.setText("");
                                        member_address.setText("");
                                        member_nid.setText("");
                                        new AlertDialogh().show(addMemberActivity.this,"Member added Successfully","");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new AlertDialogh().show(getApplicationContext(),e.getMessage(),"");
                        }
                    });

                }

            }
        });
    }
}
