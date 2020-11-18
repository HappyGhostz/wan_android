package com.zcp.wanAndroid.utils

import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics

class ResourcesProvider(val resources: Resources) {
    public fun getAssets(): AssetManager = resources.assets

    public fun getDisplayMetrics(): DisplayMetrics = resources.displayMetrics

    public fun getDrawable(id: kotlin.Int): Drawable = resources.getDrawable(id)

    public fun getTheme(): Resources.Theme? = resources.newTheme()

}