package com.example.brogrow.view.dashboard

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentCategorialAnalysisBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class CategorialAnalysis : Fragment() {

    lateinit var binding:FragmentCategorialAnalysisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_categorial_analysis, container, false)
        setSkillGraph()
        return binding.root
    }
    fun setSkillGraph(){
        var skillRatingChart = binding.HorizontalBArChart            //skill_rating_chart is the id of the XML layout

        skillRatingChart.setDrawBarShadow(false)
        val description = Description()
        description.text = ""
        skillRatingChart.description = description
        skillRatingChart.legend.setEnabled(false)
        skillRatingChart.setPinchZoom(false)
        skillRatingChart.setDrawValueAboveBar(false)

        //Display the axis on the left (contains the labels 1*, 2* and so on)
        val xAxis = skillRatingChart.getXAxis()
        xAxis.setDrawGridLines(false)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setEnabled(true)
        xAxis.setDrawAxisLine(false)


        val yLeft = skillRatingChart.axisLeft

//Set the minimum and maximum bar lengths as per the values that they represent
        yLeft.axisMaximum = 100f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        //Set label count to 5 as we are displaying 5 star rating
        xAxis.setLabelCount(5)

//Now add the labels to be added on the vertical axis
        val values = arrayOf("1 *", "2 *", "3 *", "4 *", "5 *")
        xAxis.valueFormatter = XAxisValueFormatter(values)

        val yRight = skillRatingChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        //Set bar entries and add necessary formatting
        setGraphData()

        //Add animation to the graph
        skillRatingChart.animateY(2000)
    }
    private fun setGraphData() {
        var skillRatingChart = binding.HorizontalBArChart
        //Add a list of bar entries
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 27f))
        entries.add(BarEntry(1f, 45f))
        entries.add(BarEntry(2f, 65f))
        entries.add(BarEntry(3f, 77f))
        entries.add(BarEntry(4f, 93f))

        //Note : These entries can be replaced by real-time data, say, from an API
        val barDataSet = BarDataSet(entries, "Bar Data Set")
        barDataSet.setColors(
            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
            ContextCompat.getColor(skillRatingChart.context, R.color.blue))

                    skillRatingChart.setDrawBarShadow(true)
                    barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
        val data = BarData(barDataSet)

        //Set the bar width
        //Note : To increase the spacing between the bars set the value of barWidth to < 1f
        data.barWidth = 0.2f
        //Finally set the data and refresh the graph
        skillRatingChart.data = data
        skillRatingChart.invalidate()
    }

}