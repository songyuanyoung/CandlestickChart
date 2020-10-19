package com.basicmoon.candlestickchart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val candleStick = CandleStick(10, 15, 50, 5)
        Log.d(MainActivity::class.java.simpleName, candleStick.minPrice.toString() + ":" + candleStick.maxPrice)

    }
}