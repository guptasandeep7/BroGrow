package com.example.brogrow.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brogrow.R

class CompareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)
        actionBar?.hide()
    }
}