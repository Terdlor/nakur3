package com.example.l.nakur3.database;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by L on 16.08.2017.
 */
public class Log {

    public static  void showError(Context context,  Exception e){
        String ff = "";
        for ( StackTraceElement ste : e.getStackTrace()){
            ff = ff + ste.toString()+"  ";
        }
        Toast.makeText(context, ff, Toast.LENGTH_LONG).show();
    }

    public static  void showMassage(Context context,  String e){
        Toast.makeText(context, e, Toast.LENGTH_LONG).show();
    }

    public static String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
    }
}
