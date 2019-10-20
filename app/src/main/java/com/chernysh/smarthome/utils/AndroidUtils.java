package com.chernysh.smarthome.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author Andrii Chernysh
 *         Developed by <u>Ubrainians</u>
 */
public class AndroidUtils {
    public static int getColorById(Context context, @ColorRes int id) {
        return ResourcesCompat.getColor(context.getResources(), id, null);
    }

    public static void showShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(Context context, @StringRes int message){
        Toast.makeText(context, context.getString(message), Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void showLongToast(Context context, @StringRes int message){
        Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG).show();
    }

    public static void makeStatusBarTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = activity.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
