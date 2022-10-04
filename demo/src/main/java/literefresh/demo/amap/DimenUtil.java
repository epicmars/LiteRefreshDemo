package literefresh.demo.amap;

import android.content.Context;
import android.util.DisplayMetrics;

public class DimenUtil {
    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int dp2px(DisplayMetrics displayMetrics, float f) {
        return (int) ((f * displayMetrics.density) + 0.5f);
    }

    public static int px2dp(DisplayMetrics displayMetrics, float f) {
        return (int) ((f / displayMetrics.density) + 0.5f);
    }

    public static int px2sp(DisplayMetrics displayMetrics, float f) {
        return (int) ((f / displayMetrics.scaledDensity) + 0.5f);
    }

    public static int sp2px(DisplayMetrics displayMetrics, float f) {
        return (int) ((f * displayMetrics.scaledDensity) + 0.5f);
    }
}