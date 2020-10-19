package com.basicmoon.candlestickchart.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.basicmoon.candlestickchart.CandleStick
import com.basicmoon.candlestickchart.R
import java.util.*

class CandleSticksView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private lateinit var paint: Paint

    private lateinit var candleSticksList: List<CandleStick>

    private lateinit var candleStick: CandleStick

    private var baseLine = MIN_HEIGHT_IN_DP / 2F

    private var candleStickWidth = 20f

    private var cancleStickHeight = 300f

    private var maxPrice = Int.MIN_VALUE

    private var minPrice = Int.MAX_VALUE

    init {
        setUpPaints()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //Log.d(CandleSticksView::class.java.simpleName, "height:" + height + ":" + "width:" + width)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)


        when (widthMode) {
            MeasureSpec.EXACTLY -> Log.d(
                CandleSticksView::class.java.simpleName,
                "width mode: EXACTLY"
            )
            MeasureSpec.AT_MOST -> Log.d(
                CandleSticksView::class.java.simpleName,
                "width mode: AT_MOST"
            )
            MeasureSpec.UNSPECIFIED -> Log.d(
                CandleSticksView::class.java.simpleName,
                "width mode: UNSPECIFIED"
            )
        }

        when (heightMode) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                Log.d(CandleSticksView::class.java.simpleName, "height mode: EXACTLY")
                if (height < Utils.dp2Px(MIN_HEIGHT_IN_DP.toFloat(), context)) {
                    height = Utils.dp2Px(MIN_HEIGHT_IN_DP.toFloat(), context).toInt()

                }
            }
            MeasureSpec.UNSPECIFIED -> Log.d(
                CandleSticksView::class.java.simpleName,
                "height mode: UNSPECIFIED"
            )
        }

        Log.d(
            CandleSticksView::class.java.simpleName,
            "height:" + height.toFloat() + ":" + "width:" + width.toFloat()
        )

        Log.d(
            CandleSticksView::class.java.simpleName,
            "height in DP:" + Utils.px2dp(
                height.toFloat(),
                context
            ) + ":" + "width in DP:" + Utils.px2dp(width.toFloat(), context)
        )
        setMeasuredDimension(width, height)
    }

    var startX = 10

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val candleStick = CandleStick(100, 30, 150, 5)

        for (i in 0..30) {
            startX += 40
            drawCandle(startX.toFloat(), candleStick, canvas, paint)
        }


        canvas.drawLine(
            0f,
            MIN_HEIGHT_IN_DP / 2f,
            width.toFloat(),
            MIN_HEIGHT_IN_DP / 2f,
            paint
        )

    }

    private fun drawCandle(x: Float, candleStick: CandleStick, canvas: Canvas, paint: Paint) {

        val priceDiffOpenAndClose = candleStick.open - candleStick.close
        val priceDiffHighAndLow = candleStick.high - candleStick.low

        canvas.drawRect(
            x - candleStickWidth / 2f,
            MIN_HEIGHT_IN_DP / 2f - priceDiffOpenAndClose / 2f,
            x + candleStickWidth / 2f,
            MIN_HEIGHT_IN_DP / 2f + priceDiffOpenAndClose / 2f,
            paint
        )

        canvas.drawLine(
            x,
            MIN_HEIGHT_IN_DP / 2f - priceDiffHighAndLow / 2f,
            x,
            MIN_HEIGHT_IN_DP / 2f + priceDiffHighAndLow / 2f,
            paint
        )
    }

    private fun setUpPaints() {
        paint = Paint()
        paint.isAntiAlias = true
        paint.color = ContextCompat.getColor(context, R.color.chart_red)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setdata(candleSticksList: List<CandleStick>) {
        this.candleSticksList = candleSticksList

        maxPrice = candleSticksList.maxWith(Comparator.comparingInt { it.maxPrice })?.maxPrice
            ?: Int.MIN_VALUE
        minPrice =
            candleSticksList.minWith(kotlin.Comparator.comparingInt { it.minPrice })?.minPrice
                ?: Int.MAX_VALUE
    }


    companion object {
        const val MIN_HEIGHT_IN_DP = 600

    }

}