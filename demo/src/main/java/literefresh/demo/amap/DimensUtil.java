package literefresh.demo.amap;

import android.content.Context;

public class DimensUtil {
    public static int ajxpx2dp(float f) {
        return ((int) f) / 2;
    }

    public static int ajxpx2px(Context context, float f) {
        return dp2px(context, ajxpx2dp(f));
    }

    public static int dp2ajxpx(int i) {
        return i * 2;
    }

    public static int dp2px(Context context, int i) {
        if (context == null) {
            return 0;
        }
        return (int) ((((float) i) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dp(Context context, int i) {
        if (context == null) {
            return 0;
        }
        return (int) ((((float) i) / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}