package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.adapter.addMealAdapter;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.storage.Save;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class AddmealActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateMealButton,submitButon;
    private DatePicker datePicker;
    private int currentDay,currentMonth,currentYear;
    private RecyclerView recyclerView;
    private List<Member> memberList=new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeal);

        dateMealButton=findViewById(R.id.dateButtonMoney);
        recyclerView=findViewById(R.id.addMealRv);
        submitButon=findViewById(R.id.AddMoneySubmitButton);
        progressBar=findViewById(R.id.mealProgressbar);
        datePicker=new DatePicker(AddmealActivity.this);
        currentDay=datePicker.getDayOfMonth();
        currentMonth=datePicker.getMonth();
        currentYear=datePicker.getYear();
       StringBuilder stringBuilder=new StringBuilder();
       stringBuilder.append(currentDay+"-");
       stringBuilder.append(currentMonth+1+"-");
       stringBuilder.append(currentYear);
       dateMealButton.setText(stringBuilder.toString());

        dateMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog =new DatePickerDialog(AddmealActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                StringBuilder stringBuilder=new StringBuilder();
                                stringBuilder.append(dayOfMonth+"-");
                                stringBuilder.append((month+1)+"-");
                                stringBuilder.append(year);
                                dateMealButton.setText(stringBuilder.toString());
                            }
                        },currentYear,currentMonth,currentDay

                );
                datePickerDialog.show();

            }
        });


        final String messId=new Save().member_loadInfo(getApplicationContext()).getMember_mess_id();
        final Query memberRef= FirebaseDatabase.getInstance().getReference().child("member").orderByChild("admin_id").equalTo(messId);
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
                    addMealAdapter adapter=new addMealAdapter(AddmealActivity.this,memberList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AddmealActivity.this));
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    submitButon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          Double totalMeal =0.0;

                           for(int i=0;i<memberList.size();i++) {
                               totalMeal = totalMeal + addMealAdapter.mealCountList.get(i);
                           }

                               AlertDialog.Builder builder=new AlertDialog.Builder(AddmealActivity.this);
                               builder.setMessage("Total Meal : "+Double.toString(totalMeal));

                            final Double finalTotalMeal = totalMeal;
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       progressBar.setVisibility(View.VISIBLE);
                                       final DatabaseReference messRef=FirebaseDatabase.getInstance().getReference().child("mess").child(messId);
                                       messRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               if(dataSnapshot.exists()){
                                                   final String total_meal=dataSnapshot.child("totalMeal").getValue().toString();
                                                   final Double double_meal=Double.parseDouble(total_meal);
                                                   messRef.child("totalMeal").setValue(Double.toString(double_meal+ finalTotalMeal)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if(task.isSuccessful()){
                                                               messRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                       if(dataSnapshot.exists()){
                                                                           String totalMealCost=dataSnapshot.child("totalMealCost").getValue().toString();
                                                                           Double doubletotalMealCost=Double.parseDouble(totalMealCost);
                                                                           String totalMeal=dataSnapshot.child("totalMeal").getValue().toString();
                                                                           Double doubleTotalmeal=Double.parseDouble(totalMeal);
                                                                           Double  mealRate=doubletotalMealCost/doubleTotalmeal;
                                                                           messRef.child("mealRate").setValue(String.format("%.2f", mealRate)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                               @Override
                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                   if(task.isSuccessful()){
                                                                                       for(int i=0;i<memberList.size();i++) {
                                                                                           final DatabaseReference mRef=FirebaseDatabase.getInstance().getReference().child("member").child(memberList.get(i).getMember_id());
                                                                                           final int finalI = i;
                                                                                           final int finalI1 = i;
                                                                                           mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                               @Override
                                                                                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                   if(dataSnapshot.exists()){
                                                                                                       String meal=dataSnapshot.child("member_meal").getValue().toString();
                                                                                                       Double mealDouble=Double.parseDouble(meal);

                                                                                                       mRef.child("member_meal").setValue(Double.toString(mealDouble+addMealAdapter.mealCountList.get(finalI))).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                           @Override
                                                                                                           public void onComplete(@NonNull Task<Void> task) {
                                                                                                               if(task.isSuccessful()){
                                                                                                                   messRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                       @Override
                                                                                                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                           if(dataSnapshot.exists()){
                                                                                                                              final String mealRate=dataSnapshot.child("mealRate").getValue().toString();
                                                                                                                              mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                  @Override
                                                                                                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                      if(dataSnapshot.exists()){
                                                                                                                                          String meal2=dataSnapshot.child("member_meal").getValue().toString();
                                                                                                                                          Double doubleMeal2=Double.parseDouble(meal2);
                                                                                                                                          Double memberCost=Double.parseDouble(mealRate)*doubleMeal2;
                                                                                                                                          mRef.child("member_cost").setValue(String.format("%.2f", memberCost)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                              @Override
                                                                                                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                  mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                      @Override
                                                                                                                                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                                          if(dataSnapshot.exists()){
                                                                                                                                                              String member_deposit=dataSnapshot.child("member_deposit").getValue().toString();
                                                                                                                                                              Double doubleMember_deposit=Double.parseDouble(member_deposit);
                                                                                                                                                              String member_cost=dataSnapshot.child("member_cost").getValue().toString();
                                                                                                                                                              Double doubleMember_Cost=Double.parseDouble(member_cost);
                                                                                                                                                              Double balance=doubleMember_deposit-doubleMember_Cost;
                                                                                                                                                              mRef.child("member_balance").setValue(Double.toString(balance)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                  @Override
                                                                                                                                                                  public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                      if(task.isSuccessful()){
                                                                                                                                                                          progressBar.setVisibility(View.GONE);
                                                                                                                                                                      }
                                                                                                                                                                  }
                                                                                                                                                              });
                                                                                                                                                          }
                                                                                                                                                      }

                                                                                                                                                      @Override
                                                                                                                                                      public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                                      }
                                                                                                                                                  });
                                                                                                                                              }
                                                                                                                                          });
                                                                                                                                      }
                                                                                                                                  }

                                                                                                                                  @Override
                                                                                                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                  }
                                                                                                                              });
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

                                                                                               @Override
                                                                                               public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                               }
                                                                                           });



                                                                                       }
                                                                                   }
                                                                               }
                                                                           });
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

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

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
                               builder.show();



                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
