/*
 * Copyright 2022 yinpinjiu@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package literefresh.demo

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import layoutbinder.annotations.BindLayout
import literefresh.LiteRefresh
import literefresh.OnLoadListener
import literefresh.OnRefreshListener
import literefresh.OnScrollListener
import literefresh.behavior.Checkpoint
import literefresh.behavior.Configuration
import literefresh.behavior.OffsetConfig
import literefresh.demo.databinding.FragmentWeiboBinding
import literefresh.demo.databinding.ViewholderWeiboItemBinding
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.BaseViewHolder
import literefresh.sample.base.ui.RecyclerAdapter
import literefresh.sample.base.ui.ViewBinder
import timber.log.Timber
import kotlin.random.Random


class WeiboFragment : BaseFragment() {
    val TAG = WeiboFragment::javaClass.name
    @BindLayout(R.layout.fragment_weibo)
    var binding: FragmentWeiboBinding? = null

    val recyclerAdapter = RecyclerAdapter()

    val imgArray = arrayOf(R.drawable.weibo_item1, R.drawable.weibo_item2, R.drawable.weibo_item3,
                    R.drawable.weibo_item4, R.drawable.weibo_item5, R.drawable.weibo_item6,
                    R.drawable.weibo_item7, R.drawable.weibo_item8, R.drawable.weibo_item9,
                    R.drawable.weibo_item10, R.drawable.weibo_item11, R.drawable.weibo_item12)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = recyclerAdapter

        recyclerAdapter.register(WeiboViewHolder::class.java)
        recyclerAdapter.addPayload(
            WeiboItem(R.drawable.weibo_item1),
            WeiboItem(R.drawable.weibo_item2)
        )

        val headerHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            64F,
            context?.resources?.displayMetrics
        ).toInt()
        val behavior = LiteRefresh.getContentBehavior(binding?.recyclerView)

        val topTriggerOffset = OffsetConfig.Builder().setOffset(headerHeight).build()
        behavior.config.topEdgeConfig.addCheckpoint(topTriggerOffset, Checkpoint.Type.TRIGGER_POINT)

        val bottomTrigger = OffsetConfig.Builder().setOffsetRatioOfParent(0.9f).build()
        behavior.config.bottomEdgeConfig.addCheckpoint(bottomTrigger, Checkpoint.Type.TRIGGER_POINT)

        behavior.addOnRefreshListener(object : OnRefreshListener {
            override fun onRefreshStart() {
                Timber.d(TAG, "onRefreshStart")
            }

            override fun onReleaseToRefresh() {
                Timber.d(TAG, "onReleaseToRefresh")
            }

            override fun onRefresh() {
                Timber.d(TAG, "onRefresh")
                behavior.showHeader(500, headerHeight)
                view.postDelayed({
                    simRefreshingWeibo()
                    // Todo 区分是上边还是左边
                    behavior.refreshComplete()
                }, 800)
            }

            override fun onRefreshComplete(throwable: Throwable?) {
                Timber.d(TAG, "onRefreshEnd")
                behavior.stopScroll(true)
            }
        })

        behavior.addOnLoadListener(object : OnLoadListener {
            override fun onLoadStart() {
                Timber.d(TAG, "onLoadStart")

            }

            override fun onReleaseToLoad() {
                Timber.d(TAG, "onReleaseToLoad")

            }

            override fun onLoad() {
                Timber.d(TAG, "onLoad")
                behavior.showFooter(500, headerHeight)
                view.postDelayed({
                    simLoadingMoreWeibo()
                    // Todo 区分对应的边
                    behavior.loadComplete()
                }, 800)
            }

            override fun onLoadComplete(throwable: Throwable?) {
                Timber.d(TAG, "onLoadComplete")
                behavior.stopScroll(true)
            }
        })

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
                if (behavior.controller.isRefreshing) {
                    behavior.showHeader(500, headerHeight)
                }
            }
        })
    }

    private fun simRefreshingWeibo() {
        val item = WeiboItem(imgArray[Random.nextInt(imgArray.size)])
        val item2 = WeiboItem(imgArray[Random.nextInt(imgArray.size)])
        recyclerAdapter.setPayloads(item, item2)
    }

    private fun simLoadingMoreWeibo() {
        val item = WeiboItem(imgArray[Random.nextInt(imgArray.size)])
        recyclerAdapter.addPayload(item)
    }

}

class WeiboItem(@DrawableRes var imgRes: Int) {
    var name: String? = null
}

@ViewBinder(R.layout.viewholder_weibo_item, dataTypes = [WeiboItem::class])
class WeiboViewHolder(itemView: View?) : BaseViewHolder<ViewholderWeiboItemBinding>(itemView) {
    override fun <T> onBind(data: T, position: Int) {
        val item = data as WeiboItem
        binding?.ivWeibo?.setImageResource(item.imgRes)
    }
}