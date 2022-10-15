package literefresh.demo.amap

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import layoutbinder.annotations.BindLayout
import literefresh.LiteRefresh
import literefresh.OnRefreshListener
import literefresh.OnScrollListener
import literefresh.behavior.Checkpoint
import literefresh.behavior.Configuration
import literefresh.behavior.OffsetConfig
import literefresh.demo.ImageItem
import literefresh.demo.ImageViewHolder
import literefresh.demo.R
import literefresh.demo.databinding.FragmentAmapBinding
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.RecyclerAdapter


class AmapFragment : BaseFragment() {
    @BindLayout(R.layout.fragment_amap)
    lateinit var binding: FragmentAmapBinding
    val recyclerAdapter = RecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.coordinator.post {
//            val params = binding.coordinator.layoutParams as ConstraintLayout.LayoutParams
//            params.topMargin = StatusBarUtils.getStatusBarHeight(requireContext())
//            binding.coordinator.layoutParams = params
//        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerAdapter

        recyclerAdapter.register(ImageViewHolder::class.java)
        recyclerAdapter.addPayload(
            ImageItem(R.drawable.amap_grid_entries),
            ImageItem(R.drawable.amap_list1),
            ImageItem(R.drawable.amap_list2)
        )

        val searchBarHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86f, resources.displayMetrics).toInt()
        val searchBarTopMargin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()

        setupSearchBarBehavior(searchBarTopMargin)

//        searchBarBehavior.config.bottomEdgeConfig.deactiveCheckpoint(
//            OffsetConfig.Builder().setOffset(Integer.MAX_VALUE).build(), Checkpoint.Type.STOP_POINT
//        )

//        searchBarBehavior.config.addBottomCheckpoint(
//            OffsetConfig.Builder().setOffsetRatioOfParent(0.98f).build(),
//            Checkpoint.Type.STOP_POINT
//        )

        setupSearbarBgBehavior(searchBarTopMargin)
        setupContentBehavior(searchBarHeight)
    }

    private fun setupSearchBarBehavior(searchBarTopMargin: Int) {
        val searchBarBehavior = LiteRefresh.getHeaderBehavior(binding.searchBar.root)

        searchBarBehavior.config.deactivateTopDefaultMinOffset()
        searchBarBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarTopMargin).build(),
            Checkpoint.Type.STOP_POINT

        )
    }

    private fun setupContentBehavior(searchBarHeight: Int) {
        val scrollableBehavior = LiteRefresh.getScrollableBehavior(binding.recyclerView)
        scrollableBehavior.config.bottomEdgeConfig.deactivateCheckpoint(
            OffsetConfig.Builder()
                .setOffset(0).build(),
            Checkpoint.Type.STOP_POINT
        )
        scrollableBehavior.config.bottomEdgeConfig.addCheckpoint(
            OffsetConfig.Builder()
                .setOffsetRatioOfParent(1.0f).build(),
            Checkpoint.Type.STOP_POINT
        )
        // fixme remove a default stop point, should stop point be treated specially?
        scrollableBehavior.config.topEdgeConfig.deactivateCheckpoint(
            OffsetConfig.Builder().setOffset(0).build(), Checkpoint.Type.STOP_POINT
        )

        // TODO 是否保留deactive方法
        scrollableBehavior.config.topEdgeConfig.deactivateCheckpoint(
            OffsetConfig.Builder().setOffsetRatioOfParent(1.0f).build(), Checkpoint.Type.STOP_POINT
        )

        scrollableBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarHeight).build(),
            Checkpoint.Type.STOP_POINT, Checkpoint.Type.ANCHOR_POINT
        )

        scrollableBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder()
                .setOffsetRatioOfParent(0.6f)
                .build(), Checkpoint.Type.ANCHOR_POINT
        )

        scrollableBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder()
                .setOffsetRatioOfParent(0.99f)
                .build(), Checkpoint.Type.ANCHOR_POINT, Checkpoint.Type.STOP_POINT
        )

        scrollableBehavior.addOnScrollListener(object : OnScrollListener {
            override fun onStartScroll(
                parent: CoordinatorLayout,
                child: View,
                config: Configuration?,
                type: Int
            ) {

            }

            override fun onPreScroll(
                parent: CoordinatorLayout,
                view: View,
                config: Configuration?,
                type: Int
            ) {

            }

            override fun onScroll(
                parent: CoordinatorLayout,
                view: View,
                config: Configuration?,
                delta: Int,
                type: Int
            ) {

            }

            override fun onStopScroll(
                parent: CoordinatorLayout,
                view: View,
                config: Configuration?,
                type: Int
            ) {

            }
        })

        scrollableBehavior.addOnRefreshListener(object : OnRefreshListener {
            override fun onRefreshStart() {

            }

            override fun onReleaseToRefresh() {

            }

            override fun onRefresh() {

            }

            override fun onRefreshComplete(throwable: Throwable?) {

            }
        })
    }

    private fun setupSearbarBgBehavior(searchBarTopMargin: Int) {
        val searchBarBgBehavior = LiteRefresh.getHeaderBehavior(binding.searchBarBg)
        searchBarBgBehavior.config.deactivateTopDefaultMinOffset()
        searchBarBgBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarTopMargin).build(),
            Checkpoint.Type.STOP_POINT
        )
    }
}