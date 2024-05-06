package com.sabuj.messmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.model.Member;

import java.util.List;

public class allMemberRVadapter extends RecyclerView.Adapter<allMemberRVadapter.allMemberViewHolder> {

    private List<Member> memberList;
    private Context context;

    public allMemberRVadapter(Context context,List<Member> memberList) {

        this.context = context;
        this.memberList = memberList;
    }

    public  allMemberRVadapter(){

    }

    @NonNull
    @Override
    public allMemberRVadapter.allMemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.allmembersingle_layout,viewGroup,false);
        return new allMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allMemberRVadapter.allMemberViewHolder memberViewHolder, int i) {
        memberViewHolder.memberUserName.setText(memberList.get(memberViewHolder.getAdapterPosition()).getMember_userName());
        memberViewHolder.memberEmail.setText(memberList.get(memberViewHolder.getAdapterPosition()).getMember_email());
        memberViewHolder.memberMealNumber.setText("Total meal : "+memberList.get(memberViewHolder.getAdapterPosition()).getMember_meal());
        memberViewHolder.memberDeposit.setText("Deposit : "+memberList.get(memberViewHolder.getAdapterPosition()).getMember_deposit());
        memberViewHolder.memberCost.setText("Total cost : "+memberList.get(memberViewHolder.getAdapterPosition()).getMember_cost());
        memberViewHolder.memberBanlce.setText("Balance : "+memberList.get(memberViewHolder.getAdapterPosition()).getMember_balance());


    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    class allMemberViewHolder extends  RecyclerView.ViewHolder {
        TextView memberUserName,memberEmail,memberMealNumber,memberDeposit,memberCost,memberBanlce;
        public allMemberViewHolder(@NonNull View itemView) {
            super(itemView);
           memberUserName=itemView.findViewById(R.id.memberUserNameS);
           memberEmail=itemView.findViewById(R.id.memberEmailS);
           memberMealNumber=itemView.findViewById(R.id.memberMealNumberS);
           memberDeposit=itemView.findViewById(R.id.memberDepositS);
           memberCost=itemView.findViewById(R.id.memberCostS);
           memberBanlce=itemView.findViewById(R.id.memberBalanceS);
        }
    }

    public void updateCollection(List<Member> memberList){
        this.memberList=memberList;
        notifyDataSetChanged();
    }
}
