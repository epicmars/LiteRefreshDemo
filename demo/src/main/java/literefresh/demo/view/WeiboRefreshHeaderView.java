package literefresh.demo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.ImageViewCompat;

import literefresh.LiteRefresh;
import literefresh.OnRefreshListener;
import literefresh.OnScrollListener;
import literefresh.behavior.Configuration;
import literefresh.behavior.RefreshHeaderBehavior;
import literefresh.demo.R;
import literefresh.widget.LoadingView;
import literefresh.widget.RefreshHeaderLayout;

public class WeiboRefreshHeaderView extends RefreshHeaderLayout implements OnScrollListener, OnRefreshListener {

    private TextView tvState;
    private LoadingView loadingView;
    private ImageView ivArrow;
    private ObjectAnimator rotateUpAnimator;
    private ObjectAnimator rotateDownAnimator;

    public WeiboRefreshHeaderView(Context context) {
        this(context, null);
    }

    public WeiboRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeiboRefreshHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.view_weibo_refresh_header, this);

        tvState = findViewById(R.id.tv_state);
        ivArrow = findViewById(R.id.iv_arrow);
        ImageViewCompat.setImageTintList(ivArrow, ColorStateList.valueOf(getResources().getColor(literefresh.widget.R.color.lr_color_gray)));
        loadingView = findViewById(R.id.loading_view);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        RefreshHeaderBehavior behavior = LiteRefresh.getAttachedBehavior(this);
        behavior.addOnRefreshListener(this);
        behavior.addOnScrollListener(this);
    }

    @Override
    public void onStartScroll(CoordinatorLayout parent, View view, Configuration config, int type) {

    }

    @Override
    public void onPreScroll(@NonNull CoordinatorLayout parent, @NonNull View view, Configuration config, int type) {

    }

    @Override
    public void onScroll(CoordinatorLayout parent, View view, Configuration config, int delta, int type) {

    }

    @Override
    public void onStopScroll(CoordinatorLayout parent, View view, Configuration config, int type) {

    }

    @Override
    public void onRefreshStart() {
        loadingView.setVisibility(GONE);
        ivArrow.setVisibility(VISIBLE);
        rotateDown();
        tvState.setText(literefresh.widget.R.string.refresh_start);
    }

    @Override
    public void onReleaseToRefresh() {
        loadingView.setVisibility(GONE);
        ivArrow.setVisibility(VISIBLE);
        rotateUp();
        tvState.setText(literefresh.widget.R.string.release_to_refresh);
    }

    @Override
    public void onRefresh() {
        loadingView.setVisibility(VISIBLE);
        ivArrow.setVisibility(GONE);
        tvState.setText(literefresh.widget.R.string.refreshing);
    }

    @Override
    public void onRefreshComplete(Throwable throwable) {
        tvState.setText(literefresh.widget.R.string.refresh_complete);
    }

    private void rotateUp() {
        if (rotateUpAnimator == null) {
            rotateUpAnimator = ObjectAnimator.ofFloat(ivArrow, "rotation", 0, 180);
            rotateUpAnimator.setDuration(100L);
        }
        rotateUpAnimator.start();
    }

    private void rotateDown() {
        if (rotateDownAnimator == null) {
            rotateDownAnimator = ObjectAnimator.ofFloat(ivArrow, "rotation", 180, 0);
            rotateDownAnimator.setDuration(100L);
        }
        rotateDownAnimator.start();
    }
}