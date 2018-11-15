package com.ndimension.smartlibrary.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    private SharedPreferences _pref;
    private static final String PREF_FILE = "com.smartlibrary";
    private SharedPreferences.Editor _editorPref;

    @SuppressLint("CommitPrefEdits")
    public Pref(Context context) {
        _pref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        _editorPref = _pref.edit();
    }



    public String getUserName() {
        return _pref.getString("user_name", "");
    }
    public void saveUserName(String name) {
        _editorPref.putString("user_name", name);
        _editorPref.commit();
    }



    public void saveUserId(String user_id) {
        _editorPref.putString("user_id", user_id);
        _editorPref.commit();
    }
    public String getUserId() {
        return _pref.getString("user_id", "");
    }

    public String getEmail() {
        return _pref.getString("email", "");
    }
    public void saveEmail(String email) {
        _editorPref.putString("email", email);
        _editorPref.commit();
    }

    public String getMobile() {
        return _pref.getString("mobile", "");
    }
    public void saveMobile(String mobile) {
        _editorPref.putString("mobile", mobile);
        _editorPref.commit();
    }

    public String getName() {
        return _pref.getString("name", "");
    }
    public void saveName(String name) {
        _editorPref.putString("name", name);
        _editorPref.commit();
    }

    public String getPass() {
        return _pref.getString("pass", "");
    }
    public void savePass(String pass) {
        _editorPref.putString("pass", pass);
        _editorPref.commit();
    }





    public String getImage(){
        return _pref.getString("profile_img", "");
    }

    public void saveImage(String profile_img) {
        _editorPref.putString("profile_img", profile_img);
        _editorPref.commit();
    }

    public void saveBlockNotification(String block) {
        _editorPref.putString("block", block);
        _editorPref.commit();
    }


    public String getBlockNotification() {
        return _pref.getString("block", "");
    }

    public void saveSound(String sound) {
        _editorPref.putString("sound", sound);
        _editorPref.commit();
    }


    public String getSound() {
        return _pref.getString("sound", "");
    }


    public void saveVibrate(String vibrate) {
        _editorPref.putString("vibrate", vibrate);
        _editorPref.commit();
    }


    public String getVibrate() {
        return _pref.getString("vibrate", "");
    }



}
