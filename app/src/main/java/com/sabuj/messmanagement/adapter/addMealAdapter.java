package com.sabuj.messmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class addMealAdapter extends RecyclerView.Adapter<addMealAdapter.addMealViewHolder> {

    private List<Member> memberList;
    private Context context;
    private Double mealcount=0.0;
    public static List<Double> mealCountList=new ArrayList<>();
    private Double mealC;

    public addMealAdapter(Context context, List<Member> memberList) {

        this.context = context;
        this.memberList = memberList;
    }

    public  addMealAdapter(){

    }

    @NonNull
    @Override
    public addMealAdapter.addMealViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.addmealsiglelayout,viewGroup,false);
        return new addMealAdapter.addMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final addMealAdapter.addMealViewHolder addMealViewHolder, final int i) {
        addMealViewHolder.memberUserName.setText(memberList.get(addMealViewHolder.getAdapterPosition()).getMember_userName());
        addMealViewHolder.mealCountEt.setText(Double.toString(mealcount));
         Double[] doubles=new Double[memberList.size()];
        Arrays.fill(doubles,0.0);
        mealCountList=Arrays.asList(doubles);
        addMealViewHolder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealC=mealCountList.get(addMealViewHolder.getAdapterPosition());
                mealC=mealC+0.5;
                mealCountList.set(addMealViewHolder.getAdapterPosition(),mealC);
                addMealViewHolder.mealCountEt.setText(Double.toString(mealCountList.get(addMealViewHolder.getAdapterPosition())));


            }
        });
        addMealViewHolder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealC=mealCountList.get(addMealViewHolder.getAdapterPosition());
                if(mealC>0){
                    mealC=mealC-0.5;
                    mealCountList.set(addMealViewHolder.getAdapterPosition(),mealC);
                    addMealViewHolder.mealCountEt.setText(Double.toString(mealCountList.get(addMealViewHolder.getAdapterPosition())));
                }else {
                    SweetToast.warning(context,"Minimum meal number is 0");
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    class addMealViewHolder extends  RecyclerView.ViewHolder {
         TextView memberUserName;
      ImageView plusBtn,minusBtn;
       EditText mealCountEt;

        public addMealViewHolder(@NonNull View itemView) {
            super(itemView);
            memberUserName=itemView.findViewById(R.id.addMealMemberUserName);
            plusBtn=itemView.findViewById(R.id.addmealImageView2);
            minusBtn=itemView.findViewById(R.id.mealImageView2);
            mealCountEt=itemView.findViewById(R.id.mealEditText);

        }
    }

    public void updateCollection(List<Member> memberList){
        this.memberList=memberList;
        notifyDataSetChanged();
    }
}
