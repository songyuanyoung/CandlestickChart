package com.basicmoon.candlestickchart

import kotlin.math.max
import kotlin.math.min

data class CandleStick(val open: Int, val close: Int, val high: Int, val low: Int) {

    val maxPrice: Int
        get() = maxOf(open, close, max(high, low))


    val minPrice: Int
        get() = minOf(open, close, min(high, low))
}