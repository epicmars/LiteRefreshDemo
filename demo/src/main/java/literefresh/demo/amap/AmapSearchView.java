package literefresh.demo.amap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import literefresh.LiteRefresh;
import literefresh.OnRefreshListener;
import literefresh.OnScrollListener;
import literefresh.behavior.Configuration;
import literefresh.behavior.HeaderRefreshBehavior;
import literefresh.demo.R;
import literefresh.widget.RefreshHeaderLayout;

// TODO make RefreshHeaderLayout to implement onScrollListener, onRefreshListener
public class AmapSearchView extends RefreshHeaderLayout implements OnScrollListener, OnRefreshListener {

    public AmapSearchView(Context context) {
        this(context, null);
    }

    public AmapSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmapSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.view_amap_search, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HeaderRefreshBehavior behavior = LiteRefresh.getAttachedBehavior(this);
        behavior.addOnRefreshListener(this);
        behavior.addOnScrollListener(this);
    }

    /**
     * The start of a refreshing lifecycle.
     */
    @Override
    public void onRefreshStart() {

    }

    /**
     * The refreshing came to a critical state when user release their finger from screen,
     * which means a touch event or scrolling is over, it will trigger the refreshing to happen.
     */
    @Override
    public void onReleaseToRefresh() {

    }

    /**
     * The refreshing is happening now.
     */
    @Override
    public void onRefresh() {

    }

    /**
     * The end of refreshing.
     *
     * @param throwable the exception that was reported by a refresher.
     */
    @Override
    public void onRefreshComplete(@Nullable Throwable throwable) {

    }

    /**
     * The child that a behavior is attached is starting to scroll, when implementing this method,
     * you should be careful with which type of touch event causes the scroll to happen. So does
     * the {@link #onStopScroll(CoordinatorLayout, View, Configuration, int)} method of
     * this interface.
     * <p>
     * The reason is that after a normal touch scroll is end, it may be followed by a fling motion
     * immediately which can cause this method be invoked again and start another start-scroll-stop
     * round.
     *
     * @param parent the child's parent view, it must be CoordinatorLayout
     * @param child  the view to which the behavior is attached
     * @param config
     * @param type   the type of touch event that cause the scrolling to happen
     */
    @Override
    public void onStartScroll(@NonNull CoordinatorLayout parent, @NonNull View child, Configuration config, int type) {

    }

    /**
     * Right before the view scroll, you can care less about which type of
     * touch event type now, because no matter what the touch event is, it just scrolls.
     * <p>
     * <strong>
     * Note: When compute a progress percentage, because all the number values are integers, you may
     * need to do some number type conversion to make things right.
     * </strong>
     *
     * @param parent the view's parent view, it must be CoordinatorLayout
     * @param view   the view to which the behavior is attached
     * @param config
     * @param type   the type of touch event that cause the scrolling to happen
     */
    @Override
    public void onPreScroll(@NonNull CoordinatorLayout parent, @NonNull View view, Configuration config, int type) {

    }

    /**
     * The view that a behavior is attached is scrolling now, you can care less about which type of
     * touch event type now, because no matter what the touch event is, it just scrolls.
     * <p>
     * <strong>
     * Note: When compute a progress percentage, because all the number values are integers, you may
     * need to do some number type conversion to make things right.
     * </strong>
     *
     * @param parent the view's parent view, it must be CoordinatorLayout
     * @param view   the view to which the behavior is attached
     * @param config
     * @param delta  the offset delta
     * @param type   the type of touch event that cause the scrolling to happen
     */
    @Override
    public void onScroll(@NonNull CoordinatorLayout parent, @NonNull View view, Configuration config, int delta, int type) {

    }

    /**
     * The view that a behavior is attached has stopped to scroll, when implementing this method,
     * you should be careful with which type of touch event causes the scrolling.
     * <p>
     * The reason is the same as the {@link #onStartScroll(CoordinatorLayout, View, Configuration, int)} method.
     *
     * @param parent the view's parent view, it must be CoordinatorLayout
     * @param view   the view to which the behavior is attached
     * @param config
     * @param type   the type of touch event that cause the scrolling to happen
     * @see #onStartScroll(CoordinatorLayout, View, Configuration, int)
     */
    @Override
    public void onStopScroll(@NonNull CoordinatorLayout parent, @NonNull View view, Configuration config, int type) {

    }
}
