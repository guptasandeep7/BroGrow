package com.example.brogrow.view.dashboard

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentCategorialAnalysisBinding
import com.example.brogrow.viewmodel.HomePageViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.util.HashMap


class CategorialAnalysis : Fragment() {

    lateinit var binding:FragmentCategorialAnalysisBinding
    lateinit var homePageViewModel: HomePageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageViewModel= ViewModelProvider(this)[HomePageViewModel::class.java]
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
        var result=HomePageFragment.result
//        Toast.makeText(requireContext(),
//            result.toString(), Toast.LENGTH_LONG).show()
        var skillRatingChart = binding.HorizontalBArChart              //skill_rating_chart is the id of the XML layout
        skillRatingChart.setDrawBarShadow(false)
        val description = Description()
        description.text = "KUNAL"
        skillRatingChart.description = description
        skillRatingChart.legend.setEnabled(true)
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
        yLeft.axisMaximum = 500f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        //Set label count to 5 as we are displaying 5 star rating
        xAxis.setLabelCount(5)

//Now add the labels to be added on the vertical axis


        val values = arrayOf("SECSADSadsdASDADaSDaDSAS*", "2 *", "3 *", "4 *", "5 *")
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
        val entries = ArrayList<BarEntry>()
        val pieEntries: java.util.ArrayList<BarEntry> = java.util.ArrayList()
        val label = "type"
    var result=HomePageFragment.result
        //initializing data
        var array=ArrayList<String>()
        val colors: java.util.ArrayList<Int> = java.util.ArrayList()
        colors.add(Color.parseColor("#E272B6"))
        colors.add(Color.parseColor("#4FC4F5"))
        colors.add(Color.parseColor("#F0D64C"))
        colors.add(Color.parseColor("#DA7878"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#7F84EC"))
        val typeAmountMap: MutableMap<String, Float> = HashMap()
var barDataSet=ArrayList<BarDataSet>()
        for(i in 0 until result!!.sectoralAnalysis.top_performing_sectors.size) {
            entries.add(BarEntry((i+1).toFloat(), result.sectoralAnalysis.top_performing_sectors[i].percentChange.toFloat(),result.sectoralAnalysis.top_performing_sectors[i].sectorName ))
             barDataSet.add(BarDataSet(entries, result.sectoralAnalysis.top_performing_sectors[i].sectorName))
            barDataSet[i].setColor(Color.parseColor("#08ADFF"))
            array.add(result.sectoralAnalysis.top_performing_sectors[i].sectorName.toString())
        }
       binding.textView10.text="Various Factor are : \n 1.${array[0]} \n" +
               " 2.${array[1]} \n" +
               " 3.${array[2]} \n" +
               " 4.${array[3]}\n" +
               " 5.${array[4]}"
//        entries.add(BarEntry(2f, 65f))
//        entries.add(BarEntry(3f, 77f))
//        entries.add(BarEntry(4f, 93f))
//        val barDataSet = BarDataSet(entries, "Description")
//        barDataSet.setColors(
//            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
//            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
//            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
//            ContextCompat.getColor(skillRatingChart.context, R.color.blue),
//            ContextCompat.getColor(skillRatingChart.context, R.color.blue))
//        skillRatingChart.setDrawBarShadow(true)
//        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
//        val data = BarData(barDataSet)

        skillRatingChart.setDrawBarShadow(true)
//        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
        val data = BarData(barDataSet as List<IBarDataSet>?)
        //Set the bar width
        //Note : To increase the spacing between the bars set the value of barWidth to < 1f
        data.barWidth = 0.2f
        //Finally set the data and refresh the graph
        skillRatingChart.data = data
        skillRatingChart.invalidate()
    }

}