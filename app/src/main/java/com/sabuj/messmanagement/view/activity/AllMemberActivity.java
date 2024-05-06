package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.adapter.allMemberRVadapter;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.storage.Save;

import java.util.ArrayList;
import java.util.List;

public class AllMemberActivity extends AppCompatActivity {
   private  RecyclerView recyclerView;
   private List<Member> memberList=new ArrayList<>();
   private String messId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_member);

        recyclerView=findViewById(R.id.memberRV);
        messId=new Save().admin_loadInfo(getApplicationContext()).getId();
        if(messId==null){
            messId=new Save().member_loadInfo(getApplicationContext()).getMember_mess_id();
        }
        Query memberRef= FirebaseDatabase.getInstance().getReference().child("member").orderByChild("admin_id").equalTo(messId);
        memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memberList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                            Member member=dataSnapshot1.getValue(Member.class);
                            memberList.add(member);
                        }
                    }
                    allMemberRVadapter  memberRVadapter=new allMemberRVadapter(AllMemberActivity.this,memberList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllMemberActivity.this));
                    memberRVadapter.notifyDataSetChanged();
                    recyclerView.setAdapter(memberRVadapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
