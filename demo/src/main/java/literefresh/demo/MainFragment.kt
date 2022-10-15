package literefresh.demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import layoutbinder.annotations.BindLayout
import literefresh.demo.alipay.AlipayFragment
import literefresh.demo.amap.AmapFragment
import literefresh.demo.bilibili.BilibiliFragment
import literefresh.demo.databinding.FragmentMainBinding
import literefresh.demo.weibo.WeiboFragment
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.RecyclerAdapter

class MainFragment : BaseFragment(), OnEntryClickListener {

    companion object {
        val NAME_WEIBO = "微博"
        val NAME_AMAP = "高德地图"
        val NAME_ALIPAY = "支付宝"
        val NAME_Bilibili = "哔哩哔哩"
    }

    @BindLayout(R.layout.fragment_main)
    lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = RecyclerAdapter()
        adapter.register(AppListViewHolder::class.java)
        binding.recyclerView.adapter = adapter

        var appListItem = AppListItem("资讯")
        appListItem.add(AppEntryItem(R.drawable.weibo_logo, R.string.name_weibo, this))
        appListItem.add(AppEntryItem(R.drawable.amap_v3_icon, R.string.name_amap, this))
        appListItem.add(AppEntryItem(R.mipmap.bilibili_ic_launcher, R.string.name_bilibili, this))
        // appListItem.add(AppEntryItem(R.drawable.alipay_appicon, R.string.name_alipay, this))
        adapter.addPayload(appListItem)
    }

    fun showFragment(fragmentClass: Class<out Fragment>) {
        childFragmentManager.beginTransaction().add(R.id.fl_content, fragmentClass, null)
            .addToBackStack(null).commit()
    }

    override fun onEntryClick(entryItem: AppEntryItem) {
        when (entryItem.nameRes) {
            R.string.name_weibo -> {
                showFragment(WeiboFragment::class.java)
            }

            R.string.name_amap -> {
                showFragment(AmapFragment::class.java)
            }

            R.string.name_bilibili -> {
                showFragment(BilibiliFragment::class.java)
            }

            R.string.name_alipay -> {
                showFragment(AlipayFragment::class.java)
            }
        }
    }

    fun onBackPressed() : Boolean {
        if (childFragmentManager.fragments.isNotEmpty()) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }
}