package com.zcp.wanAndroid.customView


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.zcp.wanAndroid.R


//源码 https://github.com/zzz40500/android-shapeLoadingView
class LoadingView : LinearLayout {
    private var mShapeLoadingView: ShapeLoadingView? = null
    private var mIndicationIm: ImageView? = null
    private var mLoadTextView: TextView? = null
    private var mUpAnimatorSet: AnimatorSet? = null
    private var mDownAnimatorSet: AnimatorSet? = null
    private var mStopped = false
    var delay = 0

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        orientation = VERTICAL
        mDistance = dip2px(context, 54f).toFloat()
        LayoutInflater.from(context).inflate(R.layout.layout_loading_view, this, true)
        mShapeLoadingView =
            findViewById<View>(R.id.shapeLoadingView) as ShapeLoadingView
        mIndicationIm =
            findViewById<View>(R.id.indication) as ImageView
        mLoadTextView = findViewById<View>(R.id.promptTV) as TextView
        mIndicationIm!!.animate().scaleX(0.2f).start()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        val loadText = typedArray.getString(R.styleable.LoadingView_loadingText)
        val textAppearance = typedArray.getResourceId(R.styleable.LoadingView_loadingText, -1)
        delay = typedArray.getInteger(R.styleable.LoadingView_delay, 80)
        typedArray.recycle()
        if (textAppearance != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mLoadTextView!!.setTextAppearance(textAppearance)
            } else {
                mLoadTextView!!.setTextAppearance(getContext(), textAppearance)
            }
        }
        loadingText = loadText
    }

    private fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (visibility == View.VISIBLE) {
            startLoading(delay.toLong())
        }
    }

    private val mFreeFallRunnable = Runnable {
        mShapeLoadingView?.animate()?.rotation(180f)?.start()
        mShapeLoadingView?.animate()?.translationY(0f)?.start()
        mIndicationIm?.animate()?.scaleX(0.2f)?.start()
        mStopped = false
        freeFall()
    }

    private fun startLoading(delay: Long) {
        if (mDownAnimatorSet != null && mDownAnimatorSet!!.isRunning) {
            return
        }
        removeCallbacks(mFreeFallRunnable)
        if (delay > 0) {
            postDelayed(mFreeFallRunnable, delay)
        } else {
            post(mFreeFallRunnable)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLoading()
    }

    private fun stopLoading() {
        mStopped = true
        if (mUpAnimatorSet != null) {
            if (mUpAnimatorSet!!.isRunning) {
                mUpAnimatorSet!!.cancel()
            }
            mUpAnimatorSet!!.removeAllListeners()
            for (animator in mUpAnimatorSet!!.childAnimations) {
                animator.removeAllListeners()
            }
            mUpAnimatorSet = null
        }
        if (mDownAnimatorSet != null) {
            if (mDownAnimatorSet!!.isRunning) {
                mDownAnimatorSet!!.cancel()
            }
            mDownAnimatorSet!!.removeAllListeners()
            for (animator in mDownAnimatorSet!!.childAnimations) {
                animator.removeAllListeners()
            }
            mDownAnimatorSet = null
        }
        removeCallbacks(mFreeFallRunnable)
    }

    override fun setVisibility(visibility: Int) {
        this.setVisibility(visibility, delay)
    }

    fun setVisibility(visibility: Int, delay: Int) {
        super.setVisibility(visibility)
        if (visibility == View.VISIBLE) {
            startLoading(delay.toLong())
        } else {
            stopLoading()
        }
    }

    var loadingText: CharSequence?
        get() = mLoadTextView!!.text
        set(loadingText) {
            if (TextUtils.isEmpty(loadingText)) {
                mLoadTextView!!.visibility = View.GONE
            } else {
                mLoadTextView!!.visibility = View.VISIBLE
            }
            mLoadTextView!!.text = loadingText
        }

    /**
     * 上抛
     */
    fun upThrow() {
        if (mUpAnimatorSet == null) {
            val objectAnimator = ObjectAnimator.ofFloat(
                mShapeLoadingView,
                "translationY",
                mDistance,
                0f
            )
            val scaleIndication =
                ObjectAnimator.ofFloat(mIndicationIm, "scaleX", 1f, 0.2f)
            var objectAnimator1: ObjectAnimator? = null
            objectAnimator1 = when (mShapeLoadingView!!.shape) {
                ShapeLoadingView.Shape.SHAPE_RECT -> ObjectAnimator.ofFloat(
                    mShapeLoadingView,
                    "rotation",
                    0f,
                    180f
                )
                ShapeLoadingView.Shape.SHAPE_CIRCLE -> ObjectAnimator.ofFloat(
                    mShapeLoadingView,
                    "rotation",
                    0f,
                    180f
                )
                ShapeLoadingView.Shape.SHAPE_TRIANGLE -> ObjectAnimator.ofFloat(
                    mShapeLoadingView,
                    "rotation",
                    0f,
                    180f
                )
            }
            mUpAnimatorSet = AnimatorSet()
            mUpAnimatorSet!!.playTogether(objectAnimator, objectAnimator1, scaleIndication)
            mUpAnimatorSet!!.duration = ANIMATION_DURATION.toLong()
            mUpAnimatorSet!!.interpolator = DecelerateInterpolator(FACTOR)
            mUpAnimatorSet!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    if (!mStopped) {
                        freeFall()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        mUpAnimatorSet!!.start()
    }

    /**
     * 下落
     */
    fun freeFall() {
        if (mDownAnimatorSet == null) {
            val objectAnimator = ObjectAnimator.ofFloat(
                mShapeLoadingView,
                "translationY",
                0f,
                mDistance
            )
            val scaleIndication =
                ObjectAnimator.ofFloat(mIndicationIm, "scaleX", 0.2f, 1f)
            mDownAnimatorSet = AnimatorSet()
            mDownAnimatorSet!!.playTogether(objectAnimator, scaleIndication)
            mDownAnimatorSet!!.duration = ANIMATION_DURATION.toLong()
            mDownAnimatorSet!!.interpolator = AccelerateInterpolator(FACTOR)
            mDownAnimatorSet!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    if (!mStopped) {
                        mShapeLoadingView!!.changeShape()
                        upThrow()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        mDownAnimatorSet!!.start()
    }

    companion object {
        private const val ANIMATION_DURATION = 500
        private const val FACTOR = 1.2f
        private var mDistance = 200f
    }
}