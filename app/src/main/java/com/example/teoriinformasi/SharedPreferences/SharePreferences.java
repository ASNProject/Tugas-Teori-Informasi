package com.example.teoriinformasi.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int private_mode = 0;
    private static final String PREF_NAME="TeoriInformasi";

    public SharePreferences (Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, private_mode);
        editor = pref.edit();
    }
     public void setData (String data){
         editor.putString("data", data);
         editor.commit();
     }
    public String getData (){
        return pref.getString("data", null);
    }
}
