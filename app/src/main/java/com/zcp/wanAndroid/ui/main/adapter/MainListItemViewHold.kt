package com.zcp.wanAndroid.ui.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.zcp.wanAndroid.R
import com.zcp.wanAndroid.module.ArticleInfo
import com.zcp.wanAndroid.module.Tag
import com.zcp.wanAndroid.utils.ResourcesProvider
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColor

private const val ARTICLE_TYPE_TOP = 1
private const val ARTICLE_TYPE_NORMAL = 0

class MainListItemViewHold constructor(
    itemView: View,
    private val context: Context,
    private val resourcesProvider: ResourcesProvider,
    private val onTitleClicked: ((ArticleInfo) -> Unit)?,
    private val onNameClicked: ((ArticleInfo) -> Unit)?,
    private val onTypeClicked: ((ArticleInfo) -> Unit)?,
    private val onImageLikeClicked: ((ArticleInfo) -> Unit)?,
) : RecyclerView.ViewHolder(itemView) {
    var title = ObservableField<String>()
    var nameTitle = ObservableField<String>()
    var nameValue = ObservableField<String>()
    var typeValue = ObservableField<String>()
    var time = ObservableField<String>()

    private var mArticleInfo: ArticleInfo? = null

    private val tagContainer: LinearLayout by lazy(LazyThreadSafetyMode.NONE) {
        itemView.findViewById<LinearLayout>(
            R.id.tag_container
        )
    }

    fun setData(
        articleInfo: ArticleInfo,
    ) {
        mArticleInfo = articleInfo
        title.set(articleInfo.title)
        nameTitle.set(if (articleInfo.author.isNullOrEmpty()) "分享人" else "作者")
        nameValue.set(if (articleInfo.author.isNullOrEmpty()) articleInfo.shareUser else articleInfo.author)
        typeValue.set("${articleInfo.superChapterName} / ${articleInfo.chapterName}")
        time.set(articleInfo.niceDate)
        ensureTags(articleInfo.tags, articleInfo.type)
    }

    private fun ensureTags(
        tags: List<Tag>?,
        type: Int
    ) {
        var textBackgroundDrawable: Drawable? = null
        var autoTextColor: Int = 0
        val autoTextSize: Float = resourcesProvider.resources.getDimension(R.dimen.font_size_10)
        when (type) {
            ARTICLE_TYPE_NORMAL -> {
                textBackgroundDrawable = resourcesProvider.getDrawable(R.drawable.tag_green)
                autoTextColor = resourcesProvider.resources.getColor(R.color.green_primary)
            }
            ARTICLE_TYPE_TOP -> {
                textBackgroundDrawable = resourcesProvider.getDrawable(R.drawable.tag_red)
                autoTextColor = resourcesProvider.resources.getColor(R.color.warning)
            }
        }
        tagContainer.removeAllViews()
        tags?.run {
            this.forEach { tag ->
                val textView = TextView(context)
                textView.apply {
                    text = tag.name
                    background = textBackgroundDrawable
                    textColor = autoTextColor
                }
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoTextSize)
                textView.setPadding(6,6,6,6)
                tagContainer.addView(textView)
                val lp = textView.layoutParams as LinearLayout.LayoutParams
                lp.setMargins(0,0,4,0)
            }
        }

    }

    fun onTitleClicked() {
        mArticleInfo?.let {
            onTitleClicked?.invoke(it)
        }
    }
    fun onNameClicked() {
        mArticleInfo?.let {
            onNameClicked?.invoke(it)
        }
    }
    fun onTypeClicked() {
        mArticleInfo?.let {
            onTypeClicked?.invoke(it)
        }
    }
    fun onImageLikeClicked() {
        mArticleInfo?.let {
            onImageLikeClicked?.invoke(it)
        }
    }
}