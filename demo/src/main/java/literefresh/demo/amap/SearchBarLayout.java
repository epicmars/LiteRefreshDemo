package literefresh.demo.amap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import literefresh.demo.R;

public class SearchBarLayout extends FrameLayout {
    private int mSavedVisibility;

    public SearchBarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void enterImmersive() {
        this.mSavedVisibility = getVisibility();
        super.setVisibility(View.GONE);
    }

    public void exitImmersive() {
        setVisibility(this.mSavedVisibility);
    }

    public void setContentView(View view) {
        addView(view);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        this.mSavedVisibility = i;
    }

    public SearchBarLayout(@NonNull Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SearchBarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSavedVisibility = 0;
        setPadding(0, ScreenUtil.getStatusBarHeight(context), 0, 0);
        setBackgroundResource(R.color.transParent);
    }
}