package literefresh.demo.alipay

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import layoutbinder.annotations.BindLayout
import literefresh.LiteRefresh
import literefresh.behavior.Checkpoint
import literefresh.behavior.OffsetConfig
import literefresh.demo.ImageItem
import literefresh.demo.ImageViewHolder
import literefresh.demo.R
import literefresh.demo.amap.DimenUtil
import literefresh.demo.databinding.FragmentAlipayBinding
import literefresh.demo.utils.StatusBarUtils
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.RecyclerAdapter

/**
 * TODO 实现支付宝财富页面需要定义新的Checkpoint类型
 */
class AlipayFragment : BaseFragment() {

    companion object {
        fun newInstance() = AlipayFragment()
    }

    @BindLayout(R.layout.fragment_alipay)
    lateinit var binding: FragmentAlipayBinding

    private lateinit var viewModel: AlipayViewModel

    private val recyclerAdapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlipayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clMain.setPadding(0, StatusBarUtils.getStatusBarHeight(requireContext()), 0, 0)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerAdapter

        recyclerAdapter.register(ImageViewHolder::class.java)
        recyclerAdapter.addPayload(ImageItem(R.drawable.ali_w_3))
        recyclerAdapter.addPayload(ImageItem(R.drawable.ali_w_4))
        recyclerAdapter.addPayload(ImageItem(R.drawable.ali_w_5))
        recyclerAdapter.addPayload(ImageItem(R.drawable.ali_w_6))

        var portfolioHeaderHeight: Int = DimenUtil.dp2px(context, 64f)

        setupContentBehavior()

        setupPortfolioBehavior(portfolioHeaderHeight)

    }

    fun setupContentBehavior() {
        val contentBehavior = LiteRefresh.getScrollableBehavior(binding.recyclerView)
    }

    fun setupPortfolioBehavior(portfolioHeaderHeight: Int) {

    }

}