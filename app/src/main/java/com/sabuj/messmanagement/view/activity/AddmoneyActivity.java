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
import com.sabuj.messmanagement.extra.AlertDialogh;
import com.sabuj.messmanagement.model.Member;
import com.sabuj.messmanagement.storage.Save;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class AddmoneyActivity extends AppCompatActivity {

    private Button moneyDateButton,submitBotton;
    private EditText moneyEt;
    private Spinner moneySpinner;
    private DatePicker datePicker;
    private int currentDay,currentMonth,currentYear;
    private StringBuilder stringBuilder;
    private List<Member> memberList=new ArrayList<>();
    private List<String> memberNameList=new ArrayList<>();
    private List<String> memberIdList=new ArrayList<>();
    private String SelectedName;
    private String SectedId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmoney);

        moneyDateButton=findViewById(R.id.dateButtonMoney);
        submitBotton=findViewById(R.id.AddMoneySubmitButton);
        moneyEt=findViewById(R.id.addMoneyET);
        moneySpinner=findViewById(R.id.moneySpinner);
        progressBar=findViewById(R.id.moneyProgressBar);
        datePicker=new DatePicker(AddmoneyActivity.this);
        currentDay=datePicker.getDayOfMonth();
        currentMonth=datePicker.getMonth();
        currentYear=datePicker.getYear();
        stringBuilder=new StringBuilder();
        stringBuilder.append(currentDay+"/");
        stringBuilder.append(currentMonth+1+"/");
        stringBuilder.append(currentYear);
        moneyDateButton.setText(stringBuilder.toString());
        moneyDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(AddmoneyActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                StringBuilder stringBuilder=new StringBuilder();
                                stringBuilder.append(dayOfMonth+"/");
                                stringBuilder.append((month+1)+"/");
                                stringBuilder.append(year);
                                moneyDateButton.setText(stringBuilder.toString());
                            }
                        },currentYear,currentMonth,currentDay

                );
                datePickerDialog.show();
            }
        });

        final String messId=new Save().member_loadInfo(getApplicationContext()).getAdmin_id();
        Query memberRef= FirebaseDatabase.getInstance().getReference().child("member").orderByChild("admin_id").equalTo(messId);
        memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memberList.clear();
                memberNameList.clear();
                memberIdList.clear();
                memberNameList.add(0,"Select a member");
                memberIdList.add(0,"id");

                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Member member=snapshot.getValue(Member.class);
                        memberList.add(member);
                        memberNameList.add(member.getMember_userName());
                        memberIdList.add(member.getMember_id());

                    }

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AddmoneyActivity.this,android.R.layout.simple_list_item_1,memberNameList);
                    moneySpinner.setAdapter(arrayAdapter);
                    moneySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SelectedName=parent.getItemAtPosition(position).toString();
                            SectedId=memberIdList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                 submitBotton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                        final String amount=moneyEt.getText().toString();
                         if(!amount.isEmpty()){
                             if(!SelectedName.equals("Select a member")){
                                 InputMethodManager inputManager = (InputMethodManager)
                                         getSystemService(Context.INPUT_METHOD_SERVICE);

                                 inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                         InputMethodManager.HIDE_NOT_ALWAYS);

                                 AlertDialog.Builder builder=new AlertDialog.Builder(AddmoneyActivity.this);
                                 builder.setMessage("Add "+amount+" taka to "+SelectedName+" ?");
                                 builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         progressBar.setVisibility(View.VISIBLE);
                                         final DatabaseReference memberRef=FirebaseDatabase.getInstance().getReference().child("member").child(SectedId);
                                         memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 if(dataSnapshot.exists()){
                                                     String pre_amount=dataSnapshot.child("member_deposit").getValue().toString();
                                                     Integer intPre_amount=Integer.parseInt(pre_amount);
                                                    final String NewAmount=Integer.toString(intPre_amount+Integer.parseInt(amount));
                                                     memberRef.child("member_deposit").setValue(NewAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {
                                                             if(task.isSuccessful()){
                                                                 final DatabaseReference messRef=FirebaseDatabase.getInstance().getReference().child("mess").child(messId);
                                                                 messRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                     @Override
                                                                     public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                                                         if(dataSnapshot.exists()){
                                                                             String totalDeposit=dataSnapshot.child("totalDeposit").getValue().toString();
                                                                             Integer intToatalDeposit=Integer.parseInt(totalDeposit);
                                                                             String newTotalDeposit=Integer.toString(intToatalDeposit+Integer.parseInt(amount));
                                                                             messRef.child("totalDeposit").setValue(newTotalDeposit).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                 @Override
                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                     if(task.isSuccessful()){
                                                                                         final DatabaseReference memberRef2=FirebaseDatabase.getInstance().getReference().child("member").child(SectedId);
                                                                                         memberRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                             @Override
                                                                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                 if(dataSnapshot.exists()){
                                                                                                     String memberCost=dataSnapshot.child("member_cost").getValue().toString();
                                                                                                     Double doubleMemberCost=Double.parseDouble(memberCost);
                                                                                                     final String memberDeposit2=dataSnapshot.child("member_deposit").getValue().toString();
                                                                                                     final Double doubleMemeberDeposit2=Double.parseDouble(memberDeposit2);
                                                                                                     Double newBalance=doubleMemeberDeposit2-doubleMemberCost;
                                                                                                     memberRef2.child("member_balance").setValue(Double.toString(newBalance)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                         @Override
                                                                                                         public void onComplete(@NonNull Task<Void> task) {
                                                                                                             if(task.isSuccessful()){
                                                                                                                 final DatabaseReference messRef2=FirebaseDatabase.getInstance().getReference().child("mess").child(messId);
                                                                                                                 messRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                     @Override
                                                                                                                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                         if(dataSnapshot.exists()){
                                                                                                                             String messCost2=dataSnapshot.child("totalMealCost").getValue().toString();
                                                                                                                             Double doubleMealCost2=Double.parseDouble(messCost2);
                                                                                                                             String messDeposit2=dataSnapshot.child("totalDeposit").getValue().toString();
                                                                                                                             Double doubleMessDeposit2=Double.parseDouble(messDeposit2);
                                                                                                                             Double newBalance=doubleMessDeposit2-doubleMealCost2;
                                                                                                                             messRef2.child("balance").setValue(Double.toString(newBalance)).addOnCompleteListener(new OnCompleteListener<Void>(){
                                                                                                                                 @Override
                                                                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                     progressBar.setVisibility(View.GONE);
                                                                                                                                     moneyEt.setText("");
                                                                                                                                     new AlertDialogh().show(AddmoneyActivity.this,"Money added succesfully","");
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

                                 AlertDialog alertDialog=builder.create();
                                 alertDialog.show();


                             }else {
                                 SweetToast.warning(getApplicationContext(),"Select a member");
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
