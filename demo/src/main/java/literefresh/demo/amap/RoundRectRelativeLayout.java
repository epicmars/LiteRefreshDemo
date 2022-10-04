package literefresh.demo.amap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;
import android.widget.RelativeLayout;

import literefresh.demo.R;

public class RoundRectRelativeLayout extends RelativeLayout {
    public static final int DRAG_BAR_VERTICAL_DP = 12;
    public static final int SHADOW_HEIGHT_DP = 10;
    private int LINE_WIDTH;
    private int STROKE_WIDTH;
    private boolean isShowDragBar = true;
    private float mEndX;
    private float mEndY;
    private Paint mPaint;
    private float mStartX;
    private float mStartY;
    private View mTopSearchBarSpaceView;

    public RoundRectRelativeLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RoundRectRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public RoundRectRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    public RoundRectRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, null);
    }

    public void init(Context context, Size size) {

        this.LINE_WIDTH = dp2px(22);
        this.STROKE_WIDTH = DimensUtil.dp2px(context, 3);

        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setARGB(102, 0, 0, 0);
        this.mPaint.setStrokeWidth((float) this.STROKE_WIDTH);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
//        addView(getTopSpaceCorner());

        setShowDragBar(true);
        setGradientEnable(true);
    }

    private View getTopSpaceCorner() {
        View view = new View(getContext());
        this.mTopSearchBarSpaceView = view;
        view.setBackgroundResource(R.drawable.qs_container_searchbar_on_top_bg);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ScreenUtil.getScreenSize(getContext()).width(), DimenUtil.dp2px(getContext(), 12.0f));
        layoutParams.topMargin = DimenUtil.dp2px(getContext(), 20.0f);
        this.mTopSearchBarSpaceView.setLayoutParams(layoutParams);
        this.mTopSearchBarSpaceView.setId(R.id.slide_shadow_space);
        this.mTopSearchBarSpaceView.bringToFront();
        this.mTopSearchBarSpaceView.setVisibility(View.INVISIBLE);
        return this.mTopSearchBarSpaceView;
    }

    public int dp2px(int i) {
        return DimenUtil.dp2px(getContext(), (float) i);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.isShowDragBar) {
            canvas.drawLine(this.mStartX, this.mStartY, this.mEndX, this.mEndY, this.mPaint);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float halfWidth = ((float) w) / 2.0f;
        this.mStartX = halfWidth - (((float) this.LINE_WIDTH) / 2.0f);
        this.mStartY = (float) dp2px(12);
        this.mEndX = (((float) this.LINE_WIDTH) / 2.0f) + halfWidth;
        this.mEndY = (float) dp2px(12);
    }

    public void setGradientEnable(boolean z) {
        setBackgroundResource(z ? R.drawable.map_home_rect_layout_bg_top : R.drawable.map_home_rect_layout_bg);
    }

    public void setShowDragBar(boolean z) {
        this.isShowDragBar = z;
        postInvalidate();
    }

    public void setTopSearchBarSpaceViewVisible(boolean z) {
        View view = this.mTopSearchBarSpaceView;
        if (view != null) {
            view.setVisibility(z ? View.VISIBLE : View.INVISIBLE);
        }
    }
}