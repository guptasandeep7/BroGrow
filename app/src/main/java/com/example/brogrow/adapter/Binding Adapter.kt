package com.example.brogrow.adapter

import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.brogrow.R

@BindingAdapter("toInt")
fun ProgressBar.toInt(data:Double) {
    this.progress = data.toInt()
}

@BindingAdapter("toString")
fun TextView.toString(data:Double) {
    this.text = data.toInt().toString()
}