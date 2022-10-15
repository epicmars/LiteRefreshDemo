package literefresh.demo.bilibili

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import layoutbinder.annotations.BindLayout
import literefresh.LiteRefresh
import literefresh.behavior.Checkpoint
import literefresh.behavior.OffsetConfig
import literefresh.demo.ImageItem
import literefresh.demo.ImageViewHolder
import literefresh.demo.R
import literefresh.demo.databinding.FragmentBilibiliBinding
import literefresh.demo.utils.StatusBarUtils
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.RecyclerAdapter

class BilibiliFragment : BaseFragment() {

    companion object {
        fun newInstance() = BilibiliFragment()
    }

    @BindLayout(R.layout.fragment_bilibili)
    lateinit var binding: FragmentBilibiliBinding

    private lateinit var viewModel: BilibiliViewModel

    private val recyclerAdapter = RecyclerAdapter()

    val refreshCompleteRunnable = Runnable {
        binding.swipeRefershLayout.isRefreshing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BilibiliViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clMain.setPadding(0, StatusBarUtils.getStatusBarHeight(requireContext()), 0, 0)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerAdapter

        recyclerAdapter.register(ImageViewHolder::class.java)
        recyclerAdapter.addPayload(ImageItem(R.drawable.bilibili_page1))
        recyclerAdapter.addPayload(ImageItem(R.drawable.bilibili_page2))
        recyclerAdapter.addPayload(ImageItem(R.drawable.bilibili_page3))

        binding.swipeRefershLayout.setOnRefreshListener {
            simRefresh()
        }

        val searchBarHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            64F,
            context?.resources?.displayMetrics
        ).toInt()

        val headerHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            123F,
            context?.resources?.displayMetrics
        ).toInt()

        val footerHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            65F,
            context?.resources?.displayMetrics
        ).toInt()

        setupHeader(searchBarHeight, headerHeight)

        setupScrollable(searchBarHeight, headerHeight)

        setupFooter(footerHeight)
    }

    private fun simRefresh() {
        binding.swipeRefershLayout.removeCallbacks(refreshCompleteRunnable)
        binding.swipeRefershLayout.postDelayed(refreshCompleteRunnable, 2000L);
    }

    private fun setupScrollable(searchBarHeight: Int, headerHeight: Int) {
        // setup scrollable behavior
        val scrollableBehavior = LiteRefresh.getScrollableBehavior(binding.swipeRefershLayout)
        scrollableBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarHeight).build(),
            Checkpoint.Type.STOP_POINT,
            Checkpoint.Type.ANCHOR_POINT
        )

        scrollableBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(headerHeight).build(),
            Checkpoint.Type.STOP_POINT,
            Checkpoint.Type.ANCHOR_POINT
        )

        // fixme remove a default stop point, should stop point be treated specially?
        scrollableBehavior.config.topEdgeConfig.deactivateCheckpoint(
            OffsetConfig.Builder().setOffset(0).build(), Checkpoint.Type.STOP_POINT
        )

        // TODO 是否保留deactive方法
        scrollableBehavior.config.topEdgeConfig.deactivateCheckpoint(
            OffsetConfig.Builder().setOffsetRatioOfParent(1.0f).build(), Checkpoint.Type.STOP_POINT
        )

        scrollableBehavior.config.topEdgeConfig.setInitiateOffset(
            OffsetConfig.Builder().setOffset(headerHeight).build()
        )
    }

    private fun setupHeader(searchBarHeight: Int, headerHeight: Int) {
        // setup header behavior of search bar
        val searchBarBehavior = LiteRefresh.getHeaderBehavior(binding.ivHeader)
        searchBarBehavior.config.deactivateTopDefaultMinOffset()
        searchBarBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarHeight - headerHeight).build(),
            Checkpoint.Type.STOP_POINT
        )

        searchBarBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(0).build(),
            Checkpoint.Type.STOP_POINT
        )

        searchBarBehavior.config.deactivateBottomDefaultMinOffset()
        searchBarBehavior.config.addBottomCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarHeight).build(),
            Checkpoint.Type.STOP_POINT
        )

        searchBarBehavior.config.addBottomCheckpoint(
            OffsetConfig.Builder().setOffset(headerHeight).build(),
            Checkpoint.Type.STOP_POINT
        )
    }

    private fun setupFooter(footerHeight: Int) {
        // setup header behavior of search bar
    }


}