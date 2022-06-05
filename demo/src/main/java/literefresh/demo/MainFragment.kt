package literefresh.demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import layoutbinder.annotations.BindLayout
import literefresh.demo.databinding.FragmentMainBinding
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.RecyclerAdapter

class MainFragment : BaseFragment(), OnEntryClickListener {

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
        appListItem.add(AppEntryItem(R.drawable.weibo_logo, "微博", this))
        appListItem.add(AppEntryItem(R.drawable.amap_v3_icon, "高德地图", this))
        adapter.addPayload(appListItem)
    }

    fun showFragment(fragmentClass: Class<out Fragment>) {
        childFragmentManager.beginTransaction().add(R.id.fl_content, fragmentClass, null)
            .addToBackStack(null).commit()
    }

    override fun onEntryClick(entryItem: AppEntryItem) {
        when (entryItem.name) {
            "微博" -> {
                showFragment(WeiboFragment::class.java)
            }

            "高德地图" -> {
                showFragment(AmapFragment::class.java)
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