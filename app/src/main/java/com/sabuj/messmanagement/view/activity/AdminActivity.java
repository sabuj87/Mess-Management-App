package com.sabuj.messmanagement.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.storage.Save;

import xyz.hasnat.sweettoast.SweetToast;

public class AdminActivity extends AppCompatActivity {
    private CardView createMessCard,messInfoCard,addMemberCard,managerCard,allMemberCard,
    logoutCard;
    private FirebaseAuth auth;
    private TextView admin_user_name,admin_email,admin_mess_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        auth=FirebaseAuth.getInstance();
        createMessCard=findViewById(R.id.AddMealCardM);
        messInfoCard=findViewById(R.id.messInfoCardM);
        managerCard=findViewById(R.id.AddCostCardM);
        addMemberCard=findViewById(R.id.addMoneyCardM);
        allMemberCard=findViewById(R.id.allMemberCardM);
        logoutCard=findViewById(R.id.logoutCardM);
        managerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,ManagerActivity.class);
                startActivity(intent);
            }
        });

        allMemberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AllMemberActivity.class);
                startActivity(intent);
            }
        });
        addMemberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,addMemberActivity.class);
                startActivity(intent);
            }
        });
        createMessCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new Save().admin_loadInfo(getApplicationContext()).getMessCreated().equals("true")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(AdminActivity.this);
                    builder.setTitle(" ");
                    builder.setIcon(R.drawable.warn);
                    builder.setMessage("You have already create a mess! Your mess is "+new Save().admin_loadInfo(getApplicationContext()).getMessUserNme()+".You can create a mess only onetime.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }else {
                    Intent intent=new Intent(AdminActivity.this, CreateMessActivity.class);
                    startActivity(intent);
                }

            }
        });
        messInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new Save().admin_loadInfo(getApplicationContext()).getMessCreated().equals("true")){
                    Intent intent=new Intent(AdminActivity.this,MessInfoActivity.class);
                    startActivity(intent);
                }else {
                    SweetToast.warning(getApplicationContext(),"Please create a mess first");
                }

            }
        });

        admin_user_name=findViewById(R.id.adminUserNameTV);
        admin_email=findViewById(R.id.adminEmail);
        admin_mess_name=findViewById(R.id.managerMessName);
        if(new Save().admin_loadInfo(getApplicationContext()).getMessUserNme().equals("")){
            admin_mess_name.setText("No mess created");
        }else {
            admin_mess_name.setText(new Save().admin_loadInfo(getApplicationContext()).getMessUserNme());
        }
        admin_user_name.setText( new Save().admin_loadInfo(getApplicationContext()).getName());


        admin_email.setText(new Save().admin_loadInfo(getApplicationContext()).getEmail());
        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AdminActivity.this);
                builder.setMessage("Want to be exit?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                        Intent intent=new Intent(AdminActivity.this,adminLoginActivity.class);
                        new Save().admin_saveData(getApplicationContext(),false);
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
        });
    }
}
