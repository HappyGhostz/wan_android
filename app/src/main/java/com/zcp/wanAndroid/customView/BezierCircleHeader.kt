package com.zcp.wanAndroid.customView


import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.simple.SimpleComponent
import com.scwang.smart.refresh.layout.util.SmartUtil
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt


/**
 * CircleRefresh
 * Created by scwang on 2018/7/18.
 * from https://github.com/tuesda/CircleRefreshLayout
 */
class BezierCircleHeader @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    SimpleComponent(context, attrs, 0), RefreshHeader {
    //<editor-fold desc="Field">
    private var mPath: Path
    private var mBackPaint: Paint
    private var mFrontPaint: Paint
    private var mOuterPaint: Paint
    private var mHeight = 0
    private var mWaveHeight = 0f
    private var mHeadHeight = 0f
    private var mSpringRatio = 0f
    private var mFinishRatio = 0f
    private var mBollY //弹出球体的Y坐标
            = 0f
    private var mBollRadius //球体半径
            = 0f
    private var mShowOuter = false
    private var mShowBoll //是否显示中心球体
            = false
    private var mShowBollTail //是否显示球体拖拽的尾巴
            = false
    private var mRefreshStop = 90
    private var mRefreshStart = 90
    private var mOuterIsStart = true
    private var mWavePulling = false
    private var mKernel: RefreshKernel? = null

    //</editor-fold>
    //<editor-fold desc="Draw">
    override fun dispatchDraw(canvas: Canvas) {
        val thisView: View = this
        val viewWidth = thisView.width
        val viewHeight = mHeight //thisView.getHeight();
        //noinspection EqualsBetweenInconvertibleTypes
        val footer = mKernel != null && (this.equals(mKernel!!.refreshLayout.refreshFooter))
        if (footer) {
            canvas.save()
            canvas.translate(0f, thisView.height.toFloat())
            canvas.scale(1f, -1f)
        }
        if (thisView.isInEditMode) {
            mShowBoll = true
            mShowOuter = true
            mHeadHeight = viewHeight.toFloat()
            mRefreshStop = 270
            mBollY = mHeadHeight / 2
            mBollRadius = mHeadHeight / 6
        }
        drawWave(canvas, viewWidth, viewHeight)
        drawSpringUp(canvas, viewWidth)
        drawBoll(canvas, viewWidth)
        drawOuter(canvas, viewWidth)
        drawFinish(canvas, viewWidth)
        if (footer) {
            canvas.restore()
        }
        super.dispatchDraw(canvas)
    }

    private fun drawWave(
        canvas: Canvas,
        viewWidth: Int,
        viewHeight: Int
    ) {
        val baseHeight = min(mHeadHeight, viewHeight.toFloat())
        if (mWaveHeight != 0f) {
            mPath.reset()
            mPath.lineTo(viewWidth.toFloat(), 0f)
            mPath.lineTo(viewWidth.toFloat(), baseHeight)
            mPath.quadTo(viewWidth / 2f, baseHeight + mWaveHeight * 2, 0f, baseHeight)
            mPath.close()
            canvas.drawPath(mPath, mBackPaint)
        } else {
            canvas.drawRect(0f, 0f, viewWidth.toFloat(), baseHeight, mBackPaint)
        }
    }

    private fun drawSpringUp(canvas: Canvas, viewWidth: Int) {
        if (mSpringRatio > 0) {
            val leftX =
                viewWidth / 2f - 4 * mBollRadius + mSpringRatio * 3 * mBollRadius
            if (mSpringRatio < 0.9) {
                mPath.reset()
                mPath.moveTo(leftX, mBollY)
                mPath.quadTo(
                    viewWidth / 2f, mBollY - mBollRadius * mSpringRatio * 2,
                    viewWidth - leftX, mBollY
                )
                canvas.drawPath(mPath, mFrontPaint)
            } else {
                canvas.drawCircle(viewWidth / 2f, mBollY, mBollRadius, mFrontPaint)
            }
        }
    }

    private fun drawBoll(canvas: Canvas, viewWidth: Int) {
        if (mShowBoll) {
            canvas.drawCircle(viewWidth / 2f, mBollY, mBollRadius, mFrontPaint)
            drawBollTail(canvas, viewWidth, (mHeadHeight + mWaveHeight) / mHeadHeight)
        }
    }

    private fun drawBollTail(
        canvas: Canvas,
        viewWidth: Int,
        fraction: Float
    ) {
        if (mShowBollTail) {
            val bottom = mHeadHeight + mWaveHeight
            val startY = mBollY + mBollRadius * fraction / 2
            val startX =
                viewWidth / 2f + sqrt(mBollRadius * mBollRadius * (1 - fraction * fraction / 4).toDouble())
                    .toFloat()
            val bezier1x = viewWidth / 2f + mBollRadius * 3 / 4 * (1 - fraction)
            val bezier2x = bezier1x + mBollRadius
            mPath.reset()
            mPath.moveTo(startX, startY)
            mPath.quadTo(bezier1x, bottom, bezier2x, bottom)
            mPath.lineTo(viewWidth - bezier2x, bottom)
            mPath.quadTo(viewWidth - bezier1x, bottom, viewWidth - startX, startY)
            canvas.drawPath(mPath, mFrontPaint)
        }
    }

    private fun drawOuter(canvas: Canvas, viewWidth: Int) {
        if (mShowOuter) {
            val outerR = mBollRadius + mOuterPaint.strokeWidth * 2
            mRefreshStart += if (mOuterIsStart) 3 else 10
            mRefreshStop += if (mOuterIsStart) 10 else 3
            mRefreshStart %= 360
            mRefreshStop %= 360
            var swipe = mRefreshStop - mRefreshStart
            swipe = if (swipe < 0) swipe + 360 else swipe
            canvas.drawArc(
                RectF(
                    viewWidth / 2f - outerR,
                    mBollY - outerR,
                    viewWidth / 2f + outerR,
                    mBollY + outerR
                ),
                mRefreshStart.toFloat(), swipe.toFloat(), false, mOuterPaint
            )
            if (swipe >= TARGET_DEGREE) {
                mOuterIsStart = false
            } else if (swipe <= 10) {
                mOuterIsStart = true
            }
            val thisView: View = this
            thisView.invalidate()
        }
    }

    private fun drawFinish(canvas: Canvas, viewWidth: Int) {
        if (mFinishRatio > 0) {
            val beforeColor = mOuterPaint.color
            if (mFinishRatio < 0.3) {
                canvas.drawCircle(viewWidth / 2f, mBollY, mBollRadius, mFrontPaint)
                val outerR =
                    (mBollRadius + mOuterPaint.strokeWidth * 2 * (1 + mFinishRatio / 0.3f)).toInt()
                val afterColor = ColorUtils.setAlphaComponent(
                    beforeColor,
                    (0xff * (1 - mFinishRatio / 0.3f)).toInt()
                )
                mOuterPaint.color = afterColor
                canvas.drawArc(
                    RectF(
                        viewWidth / 2f - outerR,
                        mBollY - outerR,
                        viewWidth / 2f + outerR,
                        mBollY + outerR
                    ), 0f, 360f, false, mOuterPaint
                )
            }
            mOuterPaint.color = beforeColor
            if (mFinishRatio >= 0.3 && mFinishRatio < 0.7) {
                val fraction = (mFinishRatio - 0.3f) / 0.4f
                mBollY = (mHeadHeight / 2 + (mHeadHeight - mHeadHeight / 2) * fraction)
                canvas.drawCircle(viewWidth / 2f, mBollY, mBollRadius, mFrontPaint)
                if (mBollY >= mHeadHeight - mBollRadius * 2) {
                    mShowBollTail = true
                    drawBollTail(canvas, viewWidth, fraction)
                }
                mShowBollTail = false
            }
            if (mFinishRatio in 0.7..1.0) {
                val fraction = (mFinishRatio - 0.7f) / 0.3f
                val leftX =
                    (viewWidth / 2f - mBollRadius - 2 * mBollRadius * fraction).toInt()
                mPath.reset()
                mPath.moveTo(leftX.toFloat(), mHeadHeight)
                mPath.quadTo(
                    viewWidth / 2f, mHeadHeight - mBollRadius * (1 - fraction),
                    viewWidth - leftX.toFloat(), mHeadHeight
                )
                canvas.drawPath(mPath, mFrontPaint)
            }
        }
    }

    //</editor-fold>
    //<editor-fold desc="RefreshHeader">
    override fun onInitialized(
        @NonNull kernel: RefreshKernel,
        height: Int,
        maxDragHeight: Int
    ) {
        mKernel = kernel
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
        mHeight = offset
        if (isDragging || mWavePulling) {
            mWavePulling = true
            mHeadHeight = height.toFloat()
            mWaveHeight = max(offset - height, 0) * .8f
        }
        this.invalidate()
    }

    override fun onReleased(
        @NonNull refreshLayout: RefreshLayout,
        height: Int,
        maxDragHeight: Int
    ) {
        mWavePulling = false
        mHeadHeight = height.toFloat()
        mBollRadius = height / 6f
        val interpolator = DecelerateInterpolator()
        val reboundHeight = min(mWaveHeight * 0.8f, mHeadHeight / 2)
        val waveAnimator = ValueAnimator.ofFloat(
            mWaveHeight, 0f,
            -(reboundHeight * 1.0f), 0f,
            -(reboundHeight * 0.4f), 0f
        )
        waveAnimator.addUpdateListener(object : AnimatorUpdateListener {
            var speed = 0f
            var springBollY = 0f
            var springRatio = 0f
            var status = 0 //0 还没开始弹起 1 向上弹起 2 在弹起的最高点停住
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val curValue = animation.animatedValue as Float
                if (status == 0 && curValue <= 0) {
                    status = 1
                    speed = abs(curValue - mWaveHeight)
                }
                if (status == 1) {
                    springRatio = -curValue / reboundHeight
                    if (springRatio >= mSpringRatio) {
                        mSpringRatio = springRatio
                        mBollY = mHeadHeight + curValue
                        speed = abs(curValue - mWaveHeight)
                    } else {
                        status = 2
                        mSpringRatio = 0f
                        mShowBoll = true
                        mShowBollTail = true
                        springBollY = mBollY
                    }
                }
                if (status == 2) {
                    if (mBollY > mHeadHeight / 2) {
                        mBollY = max(mHeadHeight / 2, mBollY - speed)
                        val bally =
                            animation.animatedFraction * (mHeadHeight / 2 - springBollY) + springBollY
                        if (mBollY > bally) {
                            mBollY = bally
                        }
                    }
                }
                if (mShowBollTail && curValue < mWaveHeight) {
                    mShowOuter = true
                    mShowBollTail = false
                    mOuterIsStart = true
                    mRefreshStart = 90
                    mRefreshStop = 90
                }
                if (!mWavePulling) {
                    mWaveHeight = curValue
                    val thisView: View = this@BezierCircleHeader
                    thisView.invalidate()
                }
            }
        })
        waveAnimator.interpolator = interpolator
        waveAnimator.duration = 1000
        waveAnimator.start()
    }

    override fun onFinish(@NonNull layout: RefreshLayout, success: Boolean): Int {
        mShowBoll = false
        mShowOuter = false
        val DURATION_FINISH = 800 //动画时长
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            val thisView: View = this@BezierCircleHeader
            mFinishRatio = animation.animatedValue as Float
            thisView.invalidate()
        }
        animator.interpolator = AccelerateInterpolator()
        animator.duration = DURATION_FINISH.toLong()
        animator.start()
        return DURATION_FINISH
    }

    /**
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Deprecated("请使用 {@link RefreshLayout#setPrimaryColorsId(int...)}")
    override fun setPrimaryColors(@ColorInt vararg colors: Int) {
        if (colors.isNotEmpty()) {
            mBackPaint.color = colors[0]
            if (colors.size > 1) {
                mFrontPaint.color = colors[1]
                mOuterPaint.color = colors[1]
            }
        }
    } //    @NonNull

    //    @Override
    //    public SpinnerStyle getSpinnerStyle() {
    //        return SpinnerStyle.Scale;
    //    }
    //</editor-fold>
    companion object {
        private const val TARGET_DEGREE = 270
    }

    //</editor-fold>
    //<editor-fold desc="View">
    init {
        mSpinnerStyle = SpinnerStyle.FixedBehind
        val thisView: View = this
        thisView.minimumHeight = SmartUtil.dp2px(100f)
        mBackPaint = Paint()
        mBackPaint.color = -0xee4401
        mBackPaint.isAntiAlias = true
        mFrontPaint = Paint()
        mFrontPaint.color = -0x1
        mFrontPaint.isAntiAlias = true
        mOuterPaint = Paint()
        mOuterPaint.isAntiAlias = true
        mOuterPaint.color = -0x1
        mOuterPaint.style = Paint.Style.STROKE
        mOuterPaint.strokeWidth = SmartUtil.dp2px(2f).toFloat()
        mPath = Path()
    }
}