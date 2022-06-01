package literefresh.demo

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
import literefresh.demo.databinding.FragmentAmapBinding
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.RecyclerAdapter


class AmapFragment : BaseFragment() {
    @BindLayout(R.layout.fragment_amap)
    lateinit var binding: FragmentAmapBinding
    val recyclerAdapter = RecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = recyclerAdapter

        recyclerAdapter.register(ImageViewHolder::class.java)
        recyclerAdapter.addPayload(
            ImageItem(R.drawable.amap_grid_entries),
            ImageItem(R.drawable.amap_list1),
            ImageItem(R.drawable.amap_list2)
        )

        val searchBarHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 87f, resources.displayMetrics)
        val searchBarBehavior = LiteRefresh.getHeaderBehavior(binding?.ivSearch)
        searchBarBehavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(0).build(),
            Checkpoint.Type.STOP_POINT
        )

        val behavior = LiteRefresh.getContentBehavior(binding?.recyclerView)
        behavior.config.bottomEdgeConfig.deactiveCheckpoint(
            OffsetConfig.Builder()
                .setOffset(0).build(),
            Checkpoint.Type.STOP_POINT
        )
        behavior.config.bottomEdgeConfig.addCheckpoint(
            OffsetConfig.Builder()
                .setOffsetRatioOfParent(1.0f).build(),
            Checkpoint.Type.STOP_POINT
        )
        // fixme remove a default stop point, should stop point be treated specially?
        behavior.config.topEdgeConfig.deactiveCheckpoint(
            OffsetConfig.Builder().setOffset(0).build(), Checkpoint.Type.STOP_POINT
        )

        behavior.config.addTopCheckpoint(
            OffsetConfig.Builder().setOffset(searchBarHeight.toInt()).build(),
            Checkpoint.Type.STOP_POINT, Checkpoint.Type.ANCHOR_POINT
        )

        behavior.config.addTopCheckpoint(
            OffsetConfig.Builder()
                .setOffsetRatioOfParent(0.6f)
                .build(), Checkpoint.Type.ANCHOR_POINT
        )

        behavior.config.addTopCheckpoint(
            OffsetConfig.Builder()
                .setOffsetRatioOfParent(1.0f)
                .build(), Checkpoint.Type.ANCHOR_POINT
        )

        behavior.addOnScrollListener(object : OnScrollListener {
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

        behavior.addOnRefreshListener(object : OnRefreshListener {
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
}