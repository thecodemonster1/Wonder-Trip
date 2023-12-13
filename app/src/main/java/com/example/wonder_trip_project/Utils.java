package com.example.wonder_trip_project;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.Optional;

public class Utils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String message, String type, String tag) {

//        if (type.equals("e") || type.equals("error")) {
//            Log.e(tag, message);
//        } else if (type.equals("d") || type.equals("debug")) {
//            Log.d(tag, message);
//        } else if (type.equals("i") || type.equals("info")) {
//            Log.i(tag, message);
//        } else if (type.equals("w") || type.equals("warning")) {
//            Log.w(tag, message);
//        } else if (type.equals("v") || type.equals("verborse")) {
//            Log.v(tag, message);
//        } else if (type.equals("wtf") || type.equals("what a terrible failiure")) {
//            Log.wtf(tag, message);
//        } else {
//            Log.e(tag, "Unknown log type: " + type + ". Message: " + message);
//        }
        switch (type) {
            case "e":
            case "error":
                Log.e(tag, message);
                break;
            case "d":
            case "debug":
                Log.d(tag, message);
                break;
            case "i":
            case "info":
                Log.i(tag, message);
                break;
            case "w":
            case "warning":
                Log.w(tag, message);
                break;
            case "v":
            case "verborse":
                Log.v(tag, message);
                break;
            case "wtf":
            case "what a terrible failiure":
                Log.wtf(tag, message);
                break;
            default:
                Log.e(tag, "Unknown log type: " + type + ". Message: " + message);
                break;
        }
    }

    public static void showLog(String message, String type) {

        String tag = "MyApp";

        switch (type) {
            case "e":
            case "error":
                Log.e(tag, message);
                break;
            case "d":
            case "debug":
                Log.d(tag, message);
                break;
            case "i":
            case "info":
                Log.i(tag, message);
                break;
            case "w":
            case "warning":
                Log.w(tag, message);
                break;
            case "v":
            case "verborse":
                Log.v(tag, message);
                break;
            case "wtf":
            case "what a terrible failiure":
                Log.wtf(tag, message);
                break;
            default:
                Log.e(tag, "Unknown log type: " + type + ". Message: " + message);
                break;
        }
    }

    public static void showLog(String message) {
        String tag = "MyApp";
        Log.i(tag, message);
    }

}
