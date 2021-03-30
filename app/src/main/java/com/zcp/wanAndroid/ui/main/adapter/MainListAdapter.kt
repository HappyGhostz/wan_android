package com.zcp.wanAndroid.ui.main.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.databinding.ItemBannerBinding
import com.zcp.wanAndroid.databinding.ItemMainListBinding
import com.zcp.wanAndroid.module.ArticleInfo
import com.zcp.wanAndroid.module.Banner
import com.zcp.wanAndroid.module.BannerData
import com.zcp.wanAndroid.utils.ResourcesProvider

const val LIST_ITEM = 0
internal const val LIST_HEADER = 1

class MainListAdapter(
    private val context: Context,
    private val resourcesProvider: ResourcesProvider,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet = mutableListOf<ArticleInfo>()

    private var bannerDataSet: Banner? = null

    private val bannerAdapter: MainBannerAdapter by lazy {
        MainBannerAdapter(listOf<BannerData>())
    }

    private var onTitleClicked: ((ArticleInfo) -> Unit)? = null
    private var onNameClicked: ((ArticleInfo) -> Unit)? = null
    private var onTypeClicked: ((ArticleInfo) -> Unit)? = null
    private var onImageLikeClicked: ((ArticleInfo, Int) -> Unit)? = null

    fun onTitleClicked(callback: (ArticleInfo) -> Unit) {
        onTitleClicked = callback
    }

    fun onNameClicked(callback: (ArticleInfo) -> Unit) {
        onNameClicked = callback
    }

    fun onTypeClicked(callback: (ArticleInfo) -> Unit) {
        onTypeClicked = callback
    }

    fun onImageLikeClicked(callback: (ArticleInfo, Int) -> Unit) {
        onImageLikeClicked = callback
    }

    fun setDatas(articleInfos: MutableList<ArticleInfo>, banner: Banner) {
        this.dataSet = articleInfos
        this.bannerDataSet = banner
        notifyDataSetChanged()
    }

    fun loadMoreData(articleInfos: MutableList<ArticleInfo>) {
        val oldSet = dataSet
        dataSet.addAll(articleInfos)
        notifyItemRangeChanged(oldSet.size, dataSet.size)
    }

    fun updateCollectData(position: Int) {
        dataSet[position-1].apply {
            collect = !collect
        }
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LIST_HEADER -> {
                val binding =
                    ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val holder = MainListBannerViewHold(binding.root)
                binding.holder = holder
                binding.banner.apply {
                    adapter = bannerAdapter
                }
                binding.banner.indicator = CircleIndicator(context)
                binding.banner.setIndicatorSelectedColor(resourcesProvider.resources.getColor(R.color.red))
                binding.banner.setIndicatorNormalColor(resourcesProvider.resources.getColor(R.color.White))
                binding.banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                val objectAnimationY =
                    ObjectAnimator.ofFloat(binding.tvBannerInfo, "translationY", 72F, 0F)
                val objectAnimationA =
                    ObjectAnimator.ofFloat(binding.tvBannerInfo, "alpha", 0F, 1F)
                val animatorSet = AnimatorSet()
                animatorSet.apply {
                    play(objectAnimationY).with(objectAnimationA)
                    duration = 2000
                }

                binding.banner.addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        if (positionOffset >= 0.1 && positionOffset < 0.95) {
                            binding.tvBannerInfo.translationY = 72F * positionOffset
                            binding.tvBannerInfo.alpha = 1F - positionOffset
                        }
                    }

                    override fun onPageSelected(position: Int) {
                        bannerDataSet?.run {
                            binding.tvBannerInfo.translationY = 72F
                            animatorSet.start()
                            binding.tvBannerInfo.text = this.data?.get(position)?.title
                        }
                    }
                })
                holder
            }
            else -> {
                val binding =
                    ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val holder = MainListItemViewHold(
                    binding.root,
                    context,
                    resourcesProvider,
                    onTitleClicked,
                    onNameClicked,
                    onTypeClicked,
                    onImageLikeClicked
                )
                binding.holder = holder
                holder
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> LIST_HEADER
            1 -> LIST_ITEM
            else -> {
                LIST_ITEM
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainListBannerViewHold -> holder.setData(bannerDataSet)
            is MainListItemViewHold -> holder.setData(dataSet[position - 1], position)
        }
    }
}