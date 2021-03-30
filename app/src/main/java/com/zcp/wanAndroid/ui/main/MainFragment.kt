package com.zcp.wanAndroid.ui.main

import com.zcp.wanAndroid.R
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zcp.wanAndroid.base.BaseFragment
import com.zcp.wanAndroid.databinding.FragmentMainBinding
import com.zcp.wanAndroid.ui.layoutStatusViewModel.LayoutStatusViewModel
import com.zcp.wanAndroid.ui.main.adapter.MainListAdapter
import com.zcp.wanAndroid.ui.main.di.DaggerMainFragmentComponent
import com.zcp.wanAndroid.ui.main.di.MainFragmentViewModule
import com.zcp.wanAndroid.ui.main.viewmodel.CollectStatusData
import com.zcp.wanAndroid.ui.main.viewmodel.MainListData
import com.zcp.wanAndroid.ui.main.viewmodel.MainViewModel
import com.zcp.wanAndroid.utils.DialogUtils
import com.zcp.wanAndroid.utils.ResponseLoadStatus
import kotlinx.android.synthetic.main.layout_list.view.*
import javax.inject.Inject


class MainFragment : BaseFragment<FragmentMainBinding>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainListAdapter: MainListAdapter

    @Inject
    lateinit var layoutStatusViewModel: LayoutStatusViewModel

    @Inject
    lateinit var dialogUtils: DialogUtils

    override fun upDataView() {
        mainViewModel.initData()
    }

    override fun viewCreated(mRootView: View) {
        binding.vm = mainViewModel
        binding.loadingViewModel = layoutStatusViewModel
        binding.mainList.rv_list.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = mainListAdapter
        }
        observedList()
        refreshOrLoadMoreData()
        setClickListener()
    }

    private fun refreshOrLoadMoreData() {
        binding.mainList.sr_refresh.setOnRefreshListener {
            mainViewModel.refreshData()
        }
        binding.mainList.sr_refresh.setOnLoadMoreListener {
            mainViewModel.loadMoreData()
        }
    }

    private fun observedList() {
        mainViewModel.mainListData.observe(this, Observer<MainListData> {
            binding.mainList.sr_refresh.finishRefresh()
            mainListAdapter.setDatas(
                it.articleInfos!!.toMutableList(),
                it.banner!!
            )
        })
        mainViewModel.mainListMoreData.observe(this, Observer<MainListData> {
            binding.mainList.sr_refresh.finishLoadMore()
            if (!it.articleInfos.isNullOrEmpty()) {
                mainListAdapter.loadMoreData(it.articleInfos!!.toMutableList())
            }
        })
        mainViewModel.collectStatusData.observe(this, Observer<CollectStatusData> {
            when (it.loadStatus) {
                ResponseLoadStatus.LOADING -> {
                    dialogUtils.dismissDialog()
                    dialogUtils.createdLoadingDialog(
                        childFragmentManager,
                        getWidthScreen() / 4
                    )
                }
                ResponseLoadStatus.ERROR -> {
                    dialogUtils.dismissDialog()
                    dialogUtils.createdInfoDialog(context, childFragmentManager, info = it.msg)
                }
                ResponseLoadStatus.SUCCEEDED -> {
                    dialogUtils.dismissDialog()
                    mainListAdapter.updateCollectData(it.position)
                }
                else -> {
                    dialogUtils.dismissDialog()
                }
            }
        })
    }

    private fun setClickListener() {
        mainListAdapter.apply {
            onTitleClicked {}
            onNameClicked {}
            onTypeClicked {}
            onImageLikeClicked { articleInfo, position ->
                mainViewModel.setCollectArticleInfo(articleInfo, position)
            }
        }
    }

    override fun initInject() {
        DaggerMainFragmentComponent.builder()
            .applicationComponent(getAppComponent())
            .mainFragmentViewModule(MainFragmentViewModule(this))
            .build()
            .inject(this)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_main
}