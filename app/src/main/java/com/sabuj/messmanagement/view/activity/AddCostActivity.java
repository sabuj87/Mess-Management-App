package com.sabuj.messmanagement.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.storage.Save;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class AddCostActivity extends AppCompatActivity {
     private Button costDateButton,submitBotton;
     private EditText costEt;
     private Spinner costSpinner;
     private DatePicker datePicker;
     private int currentDay,currentMonth,currentYear;
     private StringBuilder stringBuilder;
     private List<String> memberNameList=new ArrayList<>();
     private List<Member> memberList=new ArrayList<>();
     private String SelectedName;
     private Boolean dataUpload;
     private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
        costDateButton=findViewById(R.id.dateButtonCost);
        submitBotton=findViewById(R.id.AddCostSubmitButton);
        costEt=findViewById(R.id.addCostET);
        dataUpload=false;
        costSpinner=findViewById(R.id.CostSpinner);
        progressBar=findViewById(R.id.CostProgressber);
        datePicker=new DatePicker(AddCostActivity.this);
        currentDay=datePicker.getDayOfMonth();
        currentMonth=datePicker.getMonth();
        currentYear=datePicker.getYear();
        stringBuilder=new StringBuilder();
        stringBuilder.append(currentDay+"/");
        stringBuilder.append(currentMonth+1+"/");
        stringBuilder.append(currentYear);
        costDateButton.setText(stringBuilder.toString());
        costDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatePickerDialog  datePickerDialog =new DatePickerDialog(AddCostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                StringBuilder stringBuilder=new StringBuilder();
                                stringBuilder.append(dayOfMonth+"/");
                                stringBuilder.append((month+1)+"/");
                                stringBuilder.append(year);
                                costDateButton.setText(stringBuilder.toString());
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
                memberNameList.clear();
                memberNameList.add(0,"Select a member");
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(snapshot.hasChildren()){
                            Member member=snapshot.getValue(Member.class);
                            memberList.add(member);
                            memberNameList.add(member.getMember_userName());
                        }
                    }
                    final ArrayAdapter<String> adapter=new ArrayAdapter<>(AddCostActivity.this,android.R.layout.simple_list_item_1,memberNameList);
                    costSpinner.setAdapter(adapter);
                    costSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SelectedName=parent.getItemAtPosition(position).toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    submitBotton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String amount=costEt.getText().toString().trim();

                            if(!amount.isEmpty()){
                                if(!SelectedName.equals("Select a member")){
                                    InputMethodManager inputManager = (InputMethodManager)
                                            getSystemService(Context.INPUT_METHOD_SERVICE);

                                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
                                    AlertDialog.Builder builder=new AlertDialog.Builder(AddCostActivity.this);
                                    builder.setMessage("Added taka "+amount+" to "+SelectedName+"?");
                                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            final DatabaseReference messRef=FirebaseDatabase.getInstance().getReference().child("mess").child(messId);
                                            messRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()){
                                                        String p_cost_count=dataSnapshot.child("cost_Count").getValue().toString();
                                                        String mealCost=dataSnapshot.child("totalMealCost").getValue().toString();

                                                        int inTMealCost=Integer.parseInt(mealCost);

                                                        messRef.child("cost_Count").setValue(p_cost_count+SelectedName+"-"+amount+",");

                                                        messRef.child("totalMealCost").setValue(Integer.toString(Integer.parseInt(amount)+inTMealCost))

                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            final DatabaseReference messRef2=FirebaseDatabase.getInstance().getReference().child("mess").child(messId);
                                                                            messRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                    if(dataSnapshot.exists()){
                                                                                        String mealCost2=dataSnapshot.child("totalMealCost").getValue().toString();
                                                                                        String totalMeal=dataSnapshot.child("totalMeal").getValue().toString();
                                                                                        final String Diposit=dataSnapshot.child("totalDeposit").getValue().toString();
                                                                                        int intDeposit=Integer.parseInt(Diposit);
                                                                                        Double double_mealCos2=Double.parseDouble(mealCost2);
                                                                                        Double double_totalMeal=Double.parseDouble(totalMeal);
                                                                                        Double mealRate=double_mealCos2/double_totalMeal;
                                                                                        int balance=intDeposit-Integer.parseInt(mealCost2);
                                                                                        messRef2.child("mealRate").setValue(String.format("%.2f", mealRate));
                                                                                        messRef2.child("balance").setValue(Integer.toString(balance)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if(task.isSuccessful()){
                                                                                                    messRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                            if(dataSnapshot.exists()){
                                                                                                                final String mealRate1=dataSnapshot.child("mealRate").getValue().toString();
                                                                                                                for(int i=0;i<memberList.size();i++){
                                                                                                                    final DatabaseReference mRef2=FirebaseDatabase.getInstance().getReference().child("member").child(memberList.get(i).getMember_id());
                                                                                                                    mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                        @Override
                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                            if(dataSnapshot.exists()){
                                                                                                                                String memberMeal=dataSnapshot.child("member_meal").getValue().toString();
                                                                                                                                Double doubleMemberMeal=Double.parseDouble(memberMeal);
                                                                                                                                Double memberCost=doubleMemberMeal*Double.parseDouble(mealRate1);
                                                                                                                                mRef2.child("member_cost").setValue(String.format("%.2f", memberCost)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        if(task.isSuccessful()){
                                                                                                                                            mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                                    String memberDeposit=dataSnapshot.child("member_deposit").getValue().toString();
                                                                                                                                                    Double doubleMemberDeposit=Double.parseDouble(memberDeposit);
                                                                                                                                                    String  memberCost2=dataSnapshot.child("member_cost").getValue().toString();
                                                                                                                                                    Double doublememberCost2=Double.parseDouble(memberCost2);
                                                                                                                                                    Double memberBalance=doubleMemberDeposit-doublememberCost2;
                                                                                                                                                    mRef2.child("member_balance").setValue(String.format("%.2f", memberBalance)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                            if(task.isSuccessful()){
                                                                                                                                                                progressBar.setVisibility(View.GONE);
                                                                                                                                                                dataUpload=true;
                                                                                                                                                                costEt.setText("");
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    });
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
                                                                                                                    if(dataUpload){
                                                                                                                        SweetToast.success(getApplicationContext(),"Cost Added Successfully");
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
                                    alertDialog.show();

                                }else {
                                    SweetToast.error(getApplicationContext(),"Select a member");

                                }

                            }else {
                                SweetToast.warning(getApplicationContext(),"Enter amount");
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
