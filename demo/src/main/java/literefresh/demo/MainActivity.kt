package literefresh.demo

import android.os.Build
import android.os.Bundle
import android.view.View
import layoutbinder.annotations.BindLayout
import literefresh.sample.base.ui.BaseActivity

@BindLayout(R.layout.activity_main)
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_content, WeiboFragment())
            .commit()
    }
}