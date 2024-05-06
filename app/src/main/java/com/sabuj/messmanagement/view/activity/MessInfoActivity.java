package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Mess;
import com.sabuj.messmanagement.storage.Save;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class MessInfoActivity extends AppCompatActivity {
    private TextView messName,manger,created_at,updated_at,balance_count,total_costCount,total_mealCount,mealRate_count,total_depositCount,total_memberCount;
    private String mess_name;
    private String id=null;
    private List<String> memberIdList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_info);
        messName = findViewById(R.id.messUname);
        manger=findViewById(R.id.managerName);
        created_at=findViewById(R.id.createdTv);
        updated_at=findViewById(R.id.updatedAt);
        balance_count=findViewById(R.id.balanceCount);
        total_costCount=findViewById(R.id.mealCostCount);
        total_mealCount=findViewById(R.id.totalMealCount);
        mealRate_count=findViewById(R.id.MealRateCount);
        total_depositCount=findViewById(R.id.TotalDipositCount);
        total_memberCount=findViewById(R.id.TotalMemberCount);


        mess_name=new Save().admin_loadInfo(getApplicationContext()).getMessUserNme();
        if(!mess_name.equals("")){
            messName.setText(mess_name);
        }else if(new Save().member_loadInfo(getApplicationContext()).getMember_mess_userName()!=null){
            mess_name=new Save().member_loadInfo(getApplicationContext()).getMember_mess_userName();
            messName.setText(mess_name);
        }

        id=new Save().admin_loadInfo(getApplicationContext()).getId();
        if(id==null){
            id=new Save().member_loadInfo(getApplicationContext()).getAdmin_id();
        }else {
            id=new Save().admin_loadInfo(getApplicationContext()).getId();

        }
        Query allmemberRef=FirebaseDatabase.getInstance().getReference().child("member").orderByChild("admin_id").equalTo(id);
        allmemberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memberIdList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                            String key=dataSnapshot.getKey();
                            memberIdList.add(key);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query messref= FirebaseDatabase.getInstance().getReference().child("mess").orderByChild("id").equalTo(id);
        messref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(snapshot.hasChildren()){
                            Mess mess=snapshot.getValue(Mess.class);
                            balance_count.setText(mess.getBalance()+"tk");
                            total_costCount.setText(mess.getTotalMealCost()+"tk");
                            total_mealCount.setText(mess.getTotalMeal());
                            mealRate_count.setText(mess.getMealRate());
                            total_depositCount.setText(mess.getTotalDeposit()+"tk");
                            total_memberCount.setText(Integer.toString(memberIdList.size()));
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
