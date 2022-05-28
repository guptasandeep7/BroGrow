package com.example.brogrow.view.dashboard

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class XAxisValueFormatter(values: Array<String>) : ValueFormatter() {
  lateinit var values: Array<String>

    fun XAxisValueFormatter(values: Array<String>) {
        this.values = values
    }
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        // "value" represents the position of the label on the axis (x or y)
        return this.values[ value.toInt()]
    }

}
