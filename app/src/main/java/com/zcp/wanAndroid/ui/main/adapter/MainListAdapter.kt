package com.zcp.wanAndroid.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zcp.wanAndroid.module.ArticleInfo

const val LIST_ITEM = 0
internal const val LIST_HEADER = 1

class MainListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet = mutableListOf<ArticleInfo>()
    fun setDatas(articleInfos: MutableList<ArticleInfo>) {
        this.dataSet = articleInfos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    }

    override fun getItemCount(): Int {
        return dataSet.size
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
    }
}