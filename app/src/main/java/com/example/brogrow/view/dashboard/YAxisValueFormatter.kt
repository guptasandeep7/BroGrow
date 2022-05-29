package com.example.brogrow.view.dashboard

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class YAxisValueFormatter(values: Array<String>) : ValueFormatter() {
    lateinit var values: Array<String>

    fun YAxisValueFormatter(values: Array<String>) {
        this.values = values
    }
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        // "value" represents the position of the label on the axis (x or y)
        return this.values[ value.toInt()]
    }
}