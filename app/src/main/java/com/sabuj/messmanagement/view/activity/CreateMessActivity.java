package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Admin;
import com.sabuj.messmanagement.model.CreatedMess;
import com.sabuj.messmanagement.model.Mess;
import com.sabuj.messmanagement.storage.Save;

import xyz.hasnat.sweettoast.SweetToast;

public class CreateMessActivity extends AppCompatActivity {
   private EditText messUserName,messAddress,messPassword,messConfirmPassword;
   private Button messCreateButton;
   private String messCreatedt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mess);
        messUserName=findViewById(R.id.messNameEt);
        messAddress=findViewById(R.id.messAddressEt);
        messPassword=findViewById(R.id.messPasswordEt);
        messConfirmPassword=findViewById(R.id.messConfirmPasswordEt);
        messCreateButton=findViewById(R.id.messCreateButton);
        messCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name=messUserName.getText().toString();
                final String mess_address=messAddress.getText().toString();
                final String mess_password=messPassword.getText().toString();
                String mess_confirm_password=messConfirmPassword.getText().toString();
                final String id=new Save().admin_loadInfo(getApplicationContext()).getId();
                if(user_name.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter a user name");
                }else if(mess_address.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter mess address");
                }else if(mess_password.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Enter mess password");
                }else if(mess_password.length()<6){
                    SweetToast.warning(getApplicationContext(),"Enter 6 digit password");
                }else if(mess_confirm_password.isEmpty()){
                    SweetToast.warning(getApplicationContext(),"Confirm your password");
                }else if(!mess_confirm_password.equals(mess_password)){
                    SweetToast.warning(getApplicationContext(),"Password does not match");
                }else {
                    Query adminRef= FirebaseDatabase.getInstance().getReference().child("admin").orderByChild("id").equalTo(id);
                    adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    if(snapshot.hasChildren()){
                                        Admin admin=snapshot.getValue(Admin.class);
                                        messCreatedt=admin.getMessCreated();





                                    }



                                }
                            }

                            if(!messCreatedt.equals("true")){
                                DatabaseReference mess_createRef= FirebaseDatabase.getInstance().getReference().child("mess");
                                String key=mess_createRef.push().getKey();
                                Mess mess=new Mess(
                                        id,
                                        user_name,
                                        mess_password,
                                        mess_address,
                                        id,
                                        "",
                                        "",
                                        "",
                                        "0",
                                        "0",
                                        "0",
                                        "0",
                                        "0",
                                        "",
                                        "",
                                        "0",
                                        ""




                                );
                                mess_createRef.child(id).setValue(mess)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    DatabaseReference adminRef =FirebaseDatabase.getInstance().getReference().child("admin").child(id);
                                                    adminRef.child("messCreated").setValue("true");
                                                    adminRef.child("messUserNme").setValue(user_name);

                                                    Query adminRef2= FirebaseDatabase.getInstance().getReference().child("admin").orderByChild("id").equalTo(id);
                                                    adminRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                                    if(snapshot.hasChildren()){

                                                                        Admin admin2=snapshot.getValue(Admin.class);
                                                                        new Save().admin_saveInfo(getApplicationContext(),admin2);
                                                                        AlertDialog.Builder builder=new AlertDialog.Builder(CreateMessActivity.this);
                                                                        builder.setMessage("Mess create successfully,Do you go to home?");
                                                                        builder.setIcon(R.drawable.tick);
                                                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                Intent intent=new Intent(CreateMessActivity.this,AdminActivity.class);
                                                                                startActivity(intent);
                                                                                finish();
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



                                                                    }


                                                                }
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });


                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        SweetToast.error(getApplicationContext(),e.getMessage());
                                    }
                                });

                            }else {
                                AlertDialog.Builder builder=new AlertDialog.Builder(CreateMessActivity.this);
                                builder.setMessage("You have already create a mess,Do you go to home?");
                                builder.setIcon(R.drawable.warn);
                                builder.setTitle(" ");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(CreateMessActivity.this,AdminActivity.class);
                                        startActivity(intent);
                                        finish();
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


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }





            }
        });
    }
}
