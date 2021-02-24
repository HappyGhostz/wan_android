package com.zcp.wanAndroid.module

import android.R.bool
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopArticle(
    var data: List<ArticleInfo>? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null
) : Parcelable {
}

@Parcelize
data class Article(
    var data: ArticleData? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null
) : Parcelable {
}

@Parcelize
data class ArticleData(
    var curPage: Int = 0,
    var datas: List<ArticleInfo>? = null,
    var offset: Int = 0,
    var over: Boolean? = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0, ) : Parcelable {

}

@Parcelize
data class ArticleInfo(
    var apkLink: String? = null,
    var audit: Int = 0,
    var author: String? = null,
    var canEdit: Boolean = false,
    var chapterId: Int = 0,
    var chapterName: String? = null,
    var collect: Boolean = false,
    var courseId: Int = 0,
    var desc: String? = null,
    var descMd: String? = null,
    var envelopePic: String? = null,
    var fresh: Boolean = false,
    var id: Int = 0,
    var link: String? = null,
    var niceDate: String? = null,
    var niceShareDate: String? = null,
    var origin: String? = null,
    var prefix: String? = null,
    var projectLink: String? = null,
    var publishTime: Double = 0.0,
    var selfVisible: Int = 0,
    var shareDate: Double = 0.0,
    var shareUser: String? = null,
    var superChapterId: Int = 0,
    var superChapterName: String? = null,
    var tags: List<Tag>? = null,
    var title: String? = null,
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0
) : Parcelable {
}

@Parcelize
data class Tag(
    var name: String? = null,
    var url: String? = null
) : Parcelable {

}