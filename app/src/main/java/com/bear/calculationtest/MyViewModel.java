package com.bear.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static String KEY_HIGH_SCORE = "key_high_score";
    private static String KEY_LEFT_NUM = "key_left_num";
    private static String KEY_RIGHT_NUM = "key_right_num";
    private static String KEY_OPERATOR = "key_operator";
    private static String KEY_ANS = "key_ans";
    private static String KEY_CURRENT_SCORE = "key_current_score";
    private static String PREFS_DATA_NAME = "prefs_data_name";
    boolean winFlag;
    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        prefs = getApplication().getSharedPreferences(PREFS_DATA_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        if(!handle.contains(KEY_HIGH_SCORE)) {
            handle.set(KEY_HIGH_SCORE,prefs.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_LEFT_NUM,0);
            handle.set(KEY_RIGHT_NUM,0);
            handle.set(KEY_OPERATOR,"+");
            handle.set(KEY_ANS,0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        this.handle = handle;
    }

    //MutableLiveData method 需設為 public，否則 data binding 無法識別
    public MutableLiveData<Integer> getLeftNum() {
        return handle.getLiveData(KEY_LEFT_NUM);
    }
    public MutableLiveData<Integer> getRightNum() {
        return handle.getLiveData(KEY_RIGHT_NUM);
    }
    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }
    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }
    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }
    public MutableLiveData<Integer> getAns() {
        return handle.getLiveData(KEY_ANS);
    }

    void generator() {
        Random random = new Random();
        int LEVEL = 20, x = random.nextInt(LEVEL) + 1, y = random.nextInt(LEVEL) + 1;

        if (x%2 == 0) {
            getOperator().setValue("+");
            if (x>y) {
                getAns().setValue(x);
                getLeftNum().setValue(y);
                getRightNum().setValue(x - y);
            } else {
                getAns().setValue(y);
                getLeftNum().setValue(x);
                getRightNum().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x>y) {
                getAns().setValue(x - y);
                getLeftNum().setValue(x);
                getRightNum().setValue(y);
            } else {
                getAns().setValue(y - x);
                getLeftNum().setValue(y);
                getRightNum().setValue(x);
            }
        }
    }

    void save() {
        editor.putInt(KEY_HIGH_SCORE, getHighScore().getValue());
        editor.apply();
    }

    void ansCorrect() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()){
            getHighScore().setValue(getCurrentScore().getValue());
            winFlag = true;
        }
        generator();
    }
}
