package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.extra.AlertDialogh;
import com.sabuj.messmanagement.model.Manager;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.storage.Save;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class ManagerActivity extends AppCompatActivity {
    private Button changeManagerButton1,changeManagerButton2;
    private Spinner mangerSpinner;
    private TextView managerName,managerEmail,managerMonth,noManger;
    private List<String> memberList=new ArrayList<>();
    private List<String>  memberIdList=new ArrayList<>();
    private CardView mangerCardView;
    private String messId;
    private Boolean isManager=false;
    private String selected_userName;
    private String selected_memberID;
    private String previous_memberID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        changeManagerButton1=findViewById(R.id.changeManagerButton1);
        changeManagerButton2=findViewById(R.id.changeMangerButton2);
        mangerSpinner=findViewById(R.id.mangerSpinner);
        managerName=findViewById(R.id.managerName);
        managerEmail=findViewById(R.id.managerEmail);
        managerMonth=findViewById(R.id.managerMonth);
        mangerCardView=findViewById(R.id.managerCardView);
        noManger=findViewById(R.id.noManager);
        messId=new Save().admin_loadInfo(getApplicationContext()).getId();




        final Query managerRef= FirebaseDatabase.getInstance().getReference().child("member").orderByChild("admin_id").equalTo(messId);
        managerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memberList.clear();
                memberIdList.clear();
                memberList.add(0,"Select a member");
                memberIdList.add(0,"0");

                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                            Member member=dataSnapshot1.getValue(Member.class);
                            memberList.add(member.getMember_userName());
                            memberIdList.add(member.getMember_id());
                            if(member.getIs_manager().equals("true")){
                                managerName.setText(member.getMember_userName());
                                managerEmail.setText(member.getMember_email());
                                managerMonth.setText("Month : "+member.getMonth());
                                previous_memberID=member.getMember_id();
                                isManager=true;
                            }
                        }
                    }

                    if(!isManager){
                        noManger.setText("No manager is slected");
                        changeManagerButton1.setVisibility(View.VISIBLE);
                    }else {
                        noManger.setVisibility(View.GONE);
                        mangerCardView.setVisibility(View.VISIBLE);
                        changeManagerButton1.setVisibility(View.VISIBLE);
                        changeManagerButton1.setText("Change manager");
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(ManagerActivity.this,android.R.layout.simple_list_item_1,memberList);
                    mangerSpinner.setAdapter(arrayAdapter);
                    mangerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selected_userName=parent.getItemAtPosition(position).toString();
                            selected_memberID=memberIdList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changeManagerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeManagerButton1.setVisibility(View.GONE);
                mangerSpinner.setVisibility(View.VISIBLE);
                changeManagerButton2.setVisibility(View.VISIBLE);
            }


        });
        changeManagerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected_userName.equals("Select a member")){
                    final AlertDialog.Builder builder=new AlertDialog.Builder(ManagerActivity.this);
                    builder.setMessage("Confirm change manager?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference memberRef2=FirebaseDatabase.getInstance().getReference().child("member").child(selected_memberID);

                            if(!previous_memberID.equals("")){
                                DatabaseReference memberRef3=FirebaseDatabase.getInstance().getReference().child("member").child(previous_memberID);
                                memberRef3.child("is_manager").setValue("false");
                            }

                            memberRef2.child("is_manager").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        AlertDialog.Builder builder1=new AlertDialog.Builder(ManagerActivity.this);
                                        builder1.setMessage("Manager Change succefully");
                                        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                recreate();
                                            }
                                        });
                                        AlertDialog alertDialog=builder1.create();
                                        alertDialog.show();


                                    }
                                }
                            });



                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }else {
                    SweetToast.warning(getApplicationContext(),"Select a member");
                }

            }
        });


    }
}
