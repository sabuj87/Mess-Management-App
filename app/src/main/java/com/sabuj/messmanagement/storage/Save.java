package com.sabuj.messmanagement.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.sabuj.messmanagement.model.Admin;
import com.sabuj.messmanagement.model.Member;

import static android.content.Context.MODE_PRIVATE;

public class Save {
  //loginCheck
  private Boolean value;
    public void admin_saveData(Context context, Boolean b) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isUserLogIn", b);
        editor.apply();

    }
    public boolean admin_loadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        value = pref.getBoolean("isUserLogIn", false);
        return value;


    }

    public void member_saveData(Context context, Boolean b) {
        SharedPreferences pref = context.getSharedPreferences("member", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isUserLogIn", b);
        editor.apply();

    }
    public boolean member_loadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("member", MODE_PRIVATE);
      Boolean  value1 = pref.getBoolean("isUserLogIn", false);
        return value1;


    }
    public void admin_saveInfo(Context context, Admin admin) {
        SharedPreferences pref = context.getSharedPreferences("adminInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", admin.getId());
        editor.putString("name",admin.getName());
        editor.putString("email",admin.getEmail());
        editor.putString("password",admin.getPassword());
        editor.putString("created_at",admin.getCreated_at());
        editor.putString("messCreated",admin.getMessCreated());
        editor.putString("messUserName",admin.getMessUserNme());
        editor.apply();

    }
    public Admin admin_loadInfo(Context context) {
        SharedPreferences pref =context.getSharedPreferences("adminInfo", MODE_PRIVATE);
        Admin admin=new Admin(pref.getString("id",null),
                pref.getString("name",null),
                pref.getString("email",null),
                pref.getString("created_at",null),
                pref.getString("password",null),
                pref.getString("messCreated","false"),
                pref.getString("messUserName","")
                );
        return admin;


    }

    public void member_saveInfo(Context context, Member member) {
        SharedPreferences pref = context.getSharedPreferences("memberInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("member_id",member.getMember_id());
        editor.putString("member_userName",member.getMember_userName());
        editor.putString("member_address",member.getMember_address());
        editor.putString("member_email",member.getMember_email());
        editor.putString("member_mess_userName",member.getMember_mess_userName());
        editor.putString("member_mess_id",member.getMember_mess_id());
        editor.putString("joined_date",member.getJoined_date());
        editor.putString("updated_at",member.getUpdated_at());
        editor.putString("member_nid",member.getMember_nid());
        editor.putString("member_meal",member.getMember_meal());
        editor.putString("member_mealRate",member.getMember_mealRate());
        editor.putString("member_deposit",member.getMember_deposit());
        editor.putString("member_cost",member.getMember_cost());
        editor.putString("member_due",member.getMember_due());
        editor.putString("member_balance",member.getMember_balance());
        editor.putString("admin_id",member.getAdmin_id());
        editor.putString("is_manager",member.getIs_manager());
        editor.putString("month",member.getMonth());




        editor.apply();

    }
    public Member member_loadInfo(Context context) {
        SharedPreferences pref =context.getSharedPreferences("memberInfo", MODE_PRIVATE);
        Member member=new Member(
                pref.getString("member_id", null),
                pref.getString("member_userName",null),
                pref.getString("member_address",null),
                pref.getString("member_email",null),
                pref.getString("member_mess_userName",null),
                pref.getString("member_mess_id",null),
                pref.getString("joined_date",null),
                pref.getString("updated_at",null),
                pref.getString("member_nid",null),
                pref.getString("member_meal",null),
                pref.getString("member_mealRate",null),
                pref.getString("member_deposit",null),
                pref.getString("member_cost",null),
                pref.getString("member_due",null),
                pref.getString("member_balance",null),
                pref.getString("admin_id",null),
                pref.getString("is_manager",null),
                pref.getString("month",null)




        );

        return member;
    }


}
