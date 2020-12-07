package com.zcp.wanAndroid.customView

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.zcp.wanAndroid.R
import kotlin.math.min


const val DEFAULT_MEASURE: Int = 60
const val WAVE_OFFSET_DURATION: Long = 1000
const val WAVE_HEIGHT_DURATION: Int = 5

class WaveFlashesView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    lateinit var mPaint: Paint
    lateinit var mFlashesPaint: Paint
    private var mFillColor: Int
    private var mFlashesColor: Int

    private lateinit var mWavePath: Path
    private lateinit var mCirclePath: Path
    private var mWidth = 0
    private var mHeight = 0
    private var mWaveHeight :Float = 0.toFloat()
    private var mRadius = 0

    // 浪花当前的偏移量
    private var mCurWaveOffset: Float = 0.toFloat()
    private var mAnimator: ValueAnimator
    private var mAnimatorWaveHeight: ValueAnimator
    private var mAnimatorSet : AnimatorSet

    private var mWaveAnimatorDuration: Int

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveFlashesView)
        mFillColor = typedArray.getColor(R.styleable.WaveFlashesView_wf_fillColor, -0xBBDEFB)
        mFlashesColor = typedArray.getColor(R.styleable.WaveFlashesView_wf_flashesColor, -0xFFFFFF)
        mWaveAnimatorDuration = typedArray.getInt(R.styleable.WaveFlashesView_wf_down_time, WAVE_HEIGHT_DURATION)
        typedArray.recycle()
        initRes()

        mAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            interpolator = LinearInterpolator()
            duration = WAVE_OFFSET_DURATION
            repeatCount = ValueAnimator.INFINITE
        }
        mAnimator.addUpdateListener(AnimatorUpdateListener { animation ->
            val value = animation.animatedValue as Float
            mCurWaveOffset = mWidth * value
            postInvalidate()
        })

        mAnimatorWaveHeight = ValueAnimator.ofFloat(0f, 1f).apply {
            interpolator = LinearInterpolator()
            duration = mWaveAnimatorDuration*1000.toLong()
        }
        mAnimatorWaveHeight.addUpdateListener(AnimatorUpdateListener { animation ->
            val value = animation.animatedValue as Float
            mWaveHeight = mRadius * 2.toFloat() * value
            postInvalidate()
        })

        mAnimatorSet = AnimatorSet()
        mAnimatorSet.apply {
            playTogether(mAnimator,mAnimatorWaveHeight)
        }
    }

    private fun initRes() {
        //初始化图形画笔
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.apply {
            style = Paint.Style.FILL
            color = mFillColor
        }
        //初始化闪光画笔
        mFlashesPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mFlashesPaint.apply {
            style = Paint.Style.FILL
            color = mFlashesColor
        }
        mWavePath = Path()
        mCirclePath = Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w - paddingLeft - paddingRight
        mHeight = h - paddingBottom - paddingTop
        mRadius = min(mWidth / 2, mHeight / 2)

        // 初始化 浪的路径
        initPath(mWavePath, mRadius*2, mRadius / 4, true, 2.toFloat())
        mCirclePath.addCircle(
            mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(),
            Path.Direction.CW
        )
    }

    private fun initPath(path: Path, length: Int, height: Int, isClose: Boolean, lengthTime: Float) {
        path.moveTo((-length).toFloat(), (mWaveHeight).toFloat())
        var i = -length
        while (i < mRadius*2 * lengthTime + length) {

            // rQuadTo 和 quadTo 区别在于
            // rQuadTo 是相对上一个点 而 quadTo是相对于画布
            path.rQuadTo(
                (length / 4).toFloat(),
                (-height).toFloat(),
                (length / 2).toFloat(),
                0.toFloat()
            )
            path.rQuadTo(
                (length / 4).toFloat(),
                height.toFloat(),
                (length / 2).toFloat(),
                0.toFloat()
            )
            i += length
        }
        if (isClose) {
            path.rLineTo(0.toFloat(), mRadius*2.toFloat())
            path.rLineTo((-(mRadius*2 * 2 + 2 * length)).toFloat(), 0.toFloat())
            path.close()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            measureSize(widthMeasureSpec, DEFAULT_MEASURE),
            measureSize(heightMeasureSpec, DEFAULT_MEASURE)
        )
    }

    private fun measureSize(defaultMeasureSpec: Int, defaultMeasure: Int): Int {
        val mode = MeasureSpec.getMode(defaultMeasureSpec)
        val size = MeasureSpec.getSize(defaultMeasureSpec)
        var resultSize = defaultMeasure
        when (mode) {
            MeasureSpec.EXACTLY -> resultSize = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> resultSize = min(size, resultSize)
        }
        return resultSize
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(mCirclePath)
        canvas?.drawCircle(mRadius.toFloat(),mRadius.toFloat(),mRadius.toFloat(),mPaint)
        canvas?.save()
        canvas?.translate(mCurWaveOffset, mWaveHeight)
        canvas?.drawPath(mWavePath, mFlashesPaint)
        canvas?.restore()
    }

    fun startAnim() {
        mAnimatorSet.start()
    }

    fun stopAnim() {
        mAnimatorSet.cancel()
    }
}