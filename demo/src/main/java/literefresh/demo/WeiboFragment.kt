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

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
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
import literefresh.demo.utils.StatusBarUtils
import literefresh.sample.base.ui.BaseFragment
import literefresh.sample.base.ui.BaseViewHolder
import literefresh.sample.base.ui.RecyclerAdapter
import literefresh.sample.base.ui.ViewBinder
import timber.log.Timber
import kotlin.random.Random


class WeiboFragment : BaseFragment() {
    val TAG = WeiboFragment::class.java.name

    @BindLayout(R.layout.fragment_weibo)
    lateinit var binding: FragmentWeiboBinding

    val recyclerAdapter = RecyclerAdapter()

    val imgArray = arrayOf(
        R.drawable.weibo_item1, R.drawable.weibo_item2, R.drawable.weibo_item3,
        R.drawable.weibo_item4, R.drawable.weibo_item5, R.drawable.weibo_item6,
        R.drawable.weibo_item7, R.drawable.weibo_item8, R.drawable.weibo_item9,
        R.drawable.weibo_item10, R.drawable.weibo_item11, R.drawable.weibo_item12
    )
    lateinit var mediaPlayer: MediaPlayer
    lateinit var handler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(context, R.raw.newblogtoast)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.clWeibo.setPadding(0, StatusBarUtils.getStatusBarHeight(requireContext()), 0, 0)

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

        val msgHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            32F,
            context?.resources?.displayMetrics
        ).toInt()
        val behavior = LiteRefresh.getContentBehavior(binding?.recyclerView)

        val topTriggerOffset = OffsetConfig.Builder().setOffset(headerHeight).build()
        behavior.config.topEdgeConfig.addCheckpoint(topTriggerOffset, Checkpoint.Type.TRIGGER_POINT)

        val bottomTrigger = OffsetConfig.Builder().setOffsetRatioOfParent(0.9f).build()
        behavior.config.bottomEdgeConfig.addCheckpoint(bottomTrigger, Checkpoint.Type.TRIGGER_POINT)

        val resetHeader = {
            binding.tvMsgUpdated.animate().alpha(0f).translationY(-msgHeight.toFloat())
                .setDuration(500).start()
            behavior.stopScroll(false)
        }
        behavior.addOnRefreshListener(object : OnRefreshListener {
            override fun onRefreshStart() {
                Timber.d("onRefreshStart")
                binding.refreshHeader.visibility = View.VISIBLE
//                view.removeCallbacks(resetHeader)
            }

            override fun onReleaseToRefresh() {
                Timber.d("onReleaseToRefresh")
            }

            override fun onRefresh() {
                Timber.d("onRefresh")
                behavior.showHeader(500, headerHeight)
                view.postDelayed({
                    simRefreshingWeibo()
                    // Todo 区分是上边还是左边
                    behavior.refreshComplete()
                }, 1500)
            }

            override fun onRefreshComplete(throwable: Throwable?) {
                Timber.d("onRefreshEnd")
                playNewBlogSound()
                behavior.animateToPositionIfLarger(msgHeight)
                view.removeCallbacks(resetHeader)
                view.post {
                    revealTopToast()
                }

                binding.refreshHeader.visibility = View.GONE

                val dismissDelay = 1500L
                view.postDelayed(resetHeader, dismissDelay)
            }
        })

        behavior.addOnLoadListener(object : OnLoadListener {
            override fun onLoadStart() {
                Timber.d("onLoadStart")

            }

            override fun onReleaseToLoad() {
                Timber.d("onReleaseToLoad")

            }

            override fun onLoad() {
                Timber.d("onLoad")
                behavior.showFooter(500, headerHeight)
                view.postDelayed({
                    simLoadingMoreWeibo()
                    // Todo 区分对应的边
                    behavior.loadComplete()
                }, 1500)
            }

            override fun onLoadComplete(throwable: Throwable?) {
                Timber.d("onLoadComplete")
                behavior.stopScroll(false)
            }
        })

        behavior.addOnScrollListener(object : OnScrollListener {
            override fun onStartScroll(
                parent: CoordinatorLayout,
                child: View,
                config: Configuration?,
                type: Int
            ) {
                Timber.d("cancelAnimation")
                behavior.cancelAnimation()
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
//                    Timber.d("showHeader")
//                    behavior.showHeader(500, headerHeight)
                }
            }
        })
    }

    private fun revealTopToast() {
        // 圆形揭露动画效果
        // get the center for the clipping circle
        val cx = binding.tvMsgUpdated.width / 2
        val cy = binding.tvMsgUpdated.height / 2

        // get the final radius for the clipping circle
        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        // create the animator for this view (the start radius is zero)
        val circularRevealAnim = ViewAnimationUtils.createCircularReveal(
            binding.tvMsgUpdated,
            cx,
            cy,
            0f,
            finalRadius
        ).setDuration(300)
        // make the view visible and start the animation
        binding.tvMsgUpdated.visibility = View.VISIBLE
        binding.tvMsgUpdated.translationY = 0f

        val alphaAnim = ObjectAnimator.ofFloat(binding.tvMsgUpdated, "alpha", 0.0f, 1.0f)
            .setDuration(800)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(circularRevealAnim, alphaAnim)
        animatorSet.start()
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

    fun playNewBlogSound() {
        val hasVolume = context?.getSystemService(AudioManager::class.java)
            ?.getStreamVolume(AudioManager.STREAM_SYSTEM) != 0
        if (hasVolume) {
            synchronized(this) {
                if (this.mediaPlayer.isLooping() || this.mediaPlayer.isPlaying()) {
                    this.mediaPlayer.pause();
                    this.mediaPlayer.seekTo(0)
                }
                mediaPlayer.start()
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    mediaPlayer.setOnCompletionListener(null)
                }, mediaPlayer.duration + 50L)
            }
        }
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