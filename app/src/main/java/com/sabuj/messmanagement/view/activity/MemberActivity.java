package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.model.Mess;
import com.sabuj.messmanagement.storage.Save;

public class MemberActivity extends AppCompatActivity {
   private TextView managerUserName,managerEmail,messName;
   private String messId;
   private String messUserName;
   private String manager_email;
   private String manager_User_Name;
   private CardView addMealCard,messInfoCard,addMoneyCard,addCostCard,allMemberCard,logOutCard;
   private TextView mName,mEmail,mMessName,mDeposit,mCost,mBalance,mMeal,mMealRate,
    pDeposit,pMeal,pCost,pBalance;
   private Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new Save().member_loadInfo(getApplicationContext()).getIs_manager().equals("true")){
            setContentView(R.layout.activity_manager2);
            managerUserName=findViewById(R.id.managerUserNamem);
            managerEmail=findViewById(R.id.managerEmailm);
            messName=findViewById(R.id.managerMessName);
            messId=new Save().member_loadInfo(getApplicationContext()).getMember_mess_id();
            messUserName=new Save().member_loadInfo(getApplicationContext()).getMember_mess_userName();
            manager_email=new Save().member_loadInfo(getApplicationContext()).getMember_email();
            manager_User_Name=new Save().member_loadInfo(getApplicationContext()).getMember_userName();
            managerUserName.setText(manager_User_Name);
            managerEmail.setText(manager_email);
            messName.setText(messUserName);
            addMealCard=findViewById(R.id.AddMealCardM);
            messInfoCard=findViewById(R.id.messInfoCardM);
            addMoneyCard=findViewById(R.id.addMoneyCardM);
            addCostCard=findViewById(R.id.AddCostCardM);
            allMemberCard=findViewById(R.id.allMemberCardM);
            logOutCard=findViewById(R.id.logoutCardM);
            addMoneyCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MemberActivity.this,AddmoneyActivity.class));
                }
            });
            allMemberCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MemberActivity.this,AllMemberActivity.class));
                }
            });
            messInfoCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MemberActivity.this,MessInfoActivity.class));
                }
            });
            addCostCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MemberActivity.this,AddCostActivity.class));
                }
            });
            logOutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MemberActivity.this);
                    builder.setMessage("Want to exit?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(MemberActivity.this,adminLoginActivity.class);
                            new Save().member_saveData(getApplicationContext(),false);
                            startActivity(intent);
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
            addMealCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MemberActivity.this,AddmealActivity.class);
                    startActivity(intent);
                }
            });





        }else {
            setContentView(R.layout.member_layout);
          mName=findViewById(R.id.memberNameTv);
          mEmail=findViewById(R.id.memberEmail2);
          mMessName=findViewById(R.id.mMessName);
          mDeposit=findViewById(R.id.meDeposit);
          mCost=findViewById(R.id.meCost);
          mMeal=findViewById(R.id.mTotalMeal);
          mBalance=findViewById(R.id.meBalance);
          mMealRate=findViewById(R.id.mMealRate);
          pDeposit=findViewById(R.id.pDeposit);
          pMeal=findViewById(R.id.pMeal);
          pCost=findViewById(R.id.pCost);
          pBalance=findViewById(R.id.pBalance);
          logOutBtn=findViewById(R.id.pLogoutBtn);


          String messId=new Save().member_loadInfo(getApplicationContext()).getMember_mess_id();
          mName.setText(new Save().member_loadInfo(getApplicationContext()).getMember_userName());
          mEmail.setText(new Save().member_loadInfo(getApplicationContext()).getMember_email());
          mMessName.setText(new Save().member_loadInfo(getApplicationContext()).getMember_mess_userName());

          Query mMessRef= FirebaseDatabase.getInstance().getReference().child("mess").orderByChild("id").equalTo(messId);
          mMessRef.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  if(dataSnapshot.exists()){
                      for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                          if(snapshot.hasChildren()){
                              Mess mess=snapshot.getValue(Mess.class);
                              mDeposit.setText("Total Deposit : "+mess.getTotalDeposit()+" tk");
                              mCost.setText("Total Cost : "+mess.getTotalMealCost()+" tk");
                              mBalance.setText("Balance : "+mess.getBalance()+" tk");
                              mMeal.setText("Total Meal : "+mess.getTotalMeal());
                              mMealRate.setText("Meal Rate : "+mess.getMealRate()+" tk");
                          }
                      }
                  }

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

         Query pRef=FirebaseDatabase.getInstance().getReference().child("member").orderByChild("member_id").equalTo(new Save().member_loadInfo(getApplicationContext()).getMember_id());
         pRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists()){
                     for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                         if(dataSnapshot1.hasChildren()){
                             Member member=dataSnapshot1.getValue(Member.class);
                             pDeposit.setText(member.getMember_deposit()+" tk");
                             pMeal.setText(member.getMember_meal());
                             pCost.setText(member.getMember_cost()+" tk");
                             pBalance.setText(member.getMember_balance()+" tk");
                         }
                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
         logOutBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder=new AlertDialog.Builder(MemberActivity.this);
                 builder.setMessage("Do you want exit?");
                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         new Save().member_saveData(getApplicationContext(),false);
                         startActivity(new Intent(MemberActivity.this,adminLoginActivity.class));

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
}
