package literefresh.demo.amap;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

public class ScreenUtil {
    public static int getScreenBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static float getScreenDensity(Context context) {
        if (context == null || context.getResources() == null) {
            return 0.0f;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi(Context context) {
        if (context == null || context.getResources() == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static Rect getScreenSize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static Rect getServiceScreenSize(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class).invoke(defaultDisplay, displayMetrics);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean isLand(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels >= displayMetrics.heightPixels;
    }

    public static boolean isScreenBrightnessAutoMode(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_brightness_mode") == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isScreenLocked(@NotNull Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if (keyguardManager != null) {
            return keyguardManager.inKeyguardRestrictedInputMode();
        }
        return false;
    }

    public static boolean setScreenBrightness(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.System.canWrite(context)) {
            return false;
        }
        try {
            Settings.System.putInt(context.getContentResolver(), "screen_brightness", i);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setShowWhenLocked(Activity activity, boolean z) {
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= 27) {
                activity.setShowWhenLocked(z);
                activity.setTurnScreenOn(z);
            } else if (z) {
                activity.getWindow().addFlags(524288);
            } else {
                activity.getWindow().clearFlags(524288);
            }
        }
    }

    public static Rect getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }
}