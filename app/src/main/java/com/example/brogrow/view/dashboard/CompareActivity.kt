package com.example.brogrow.view.dashboard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.brogrow.R
import com.example.brogrow.databinding.ActivityCompareBinding
import com.example.brogrow.model.HomePageModel
import com.example.brogrow.network.Response
import com.example.brogrow.utill.makeSlug
import com.example.brogrow.view.dashboard.ComparisonPage.Companion.selectedCategory
import com.example.brogrow.view.dashboard.HomePageFragment.Companion.result
import com.example.brogrow.viewmodel.ComparisionPageViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import org.json.JSONException

class CompareActivity : AppCompatActivity() {
    private lateinit var comparisionPageViewModel: ComparisionPageViewModel
    private lateinit var binding: ActivityCompareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_compare)
        actionBar?.hide()

        getdata1(ComparisonPage.selectedPincode, selectedCategory)
        getdata2(ComparisonPage.selectedPincode2,ComparisonPage.selectedCategory2)

        comparisionPageViewModel = ViewModelProvider(this)[ComparisionPageViewModel::class.java]

        binding.category1.text = ComparisonPage.selectedCategory2
        binding.category2.text = ComparisonPage.selectedCategory

    }

    private fun getdata2(pincode: String, selectedCategory2: String){

        val url = "http://www.postalpincode.in/api/pincode/$pincode"

        val queue = Volley.newRequestQueue(this)

        val objectRequest =
            JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {

                        val postOfficeArray = response!!.getJSONArray("PostOffice")
                        if (response!!.getString("Status") == "Error") {

                        } else {

                            val obj = postOfficeArray.getJSONObject(0)

                            val district = obj.getString("District")
                            binding.area2.text = district
                            comparisionPageViewModel.category2 = selectedCategory2
                            val state = obj.getString("State")
                            val country = obj.getString("Country")
                            lifecycleScope.launch {

                                var result2 =
                                    comparisionPageViewModel.getData2(
                                        pincode,
                                        makeSlug(state).toString(),
                                        district.lowercase(),
                                        selectedCategory2
                                    )
                                result2.observe(this@CompareActivity){
                                    when(it){
                                        is Response.Success -> {
                                            binding.data2 = it.data
                                            binding.progressBar.visibility = View.GONE
                                            it.data?.let { it1 -> showPieChart2(it1) }
                                        }
                                    }
                                }
                            }

                        }
                    } catch (e: JSONException) {

                        e.printStackTrace()
                    }
                }) { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        queue.add(objectRequest)
    }

    private fun showPieChart1(result: HomePageModel)
    {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Float> = HashMap()

        for(i in 0 until result.competitorAnalysis.competitors.size)
        {
            var size= result.competitorAnalysis.competitors.size*100
            if(i<5)
            {
//               Toast.makeText(requireContext(),((result!!.competitorAnalysis.competitors[i].competitor_rating.toFloat()/size)*100).toString(),Toast.LENGTH_LONG).show()
                typeAmountMap[result!!.competitorAnalysis.competitors[i].competitor_name.toString()]=(result!!.competitorAnalysis.competitors[i].competitor_rating.toFloat())/size*100
            }
        }

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#E272B6"))
        colors.add(Color.parseColor("#4FC4F5"))
        colors.add(Color.parseColor("#F0D64C"))
        colors.add(Color.parseColor("#DA7878"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#7F84EC"))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not
        pieData.setDrawValues(true)

        binding.CompetitionChart.setData(pieData)
        binding.CompetitionChart.invalidate()
    }


    private fun showPieChart2(result: HomePageModel)
        {
            val pieEntries: ArrayList<PieEntry> = ArrayList()
            val label = "type"

            //initializing data
            val typeAmountMap: MutableMap<String, Float> = HashMap()

            for(i in 0 until result.competitorAnalysis.competitors.size)
            {
                var size= result.competitorAnalysis.competitors.size*100
                if(i<5)
                {
//               Toast.makeText(requireContext(),((result!!.competitorAnalysis.competitors[i].competitor_rating.toFloat()/size)*100).toString(),Toast.LENGTH_LONG).show()
                    typeAmountMap[result!!.competitorAnalysis.competitors[i].competitor_name.toString()]=(result!!.competitorAnalysis.competitors[i].competitor_rating.toFloat())/size*100
                }
            }

            //initializing colors for the entries
            val colors: ArrayList<Int> = ArrayList()
            colors.add(Color.parseColor("#E272B6"))
            colors.add(Color.parseColor("#4FC4F5"))
            colors.add(Color.parseColor("#F0D64C"))
            colors.add(Color.parseColor("#DA7878"))
            colors.add(Color.parseColor("#a35567"))
            colors.add(Color.parseColor("#ff5f67"))
            colors.add(Color.parseColor("#7F84EC"))

            //input data and fit data into pie chart entry
            for (type in typeAmountMap.keys) {
                pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
            }

            //collecting the entries with label name
            val pieDataSet = PieDataSet(pieEntries, label)
            //setting text size of the value
            pieDataSet.valueTextSize = 12f
            //providing color list for coloring different entries
            pieDataSet.colors = colors
            //grouping the data set from entry to chart
            val pieData = PieData(pieDataSet)
            //showing the value of the entries, default true if not
            pieData.setDrawValues(true)

            binding.CompetitionChart2.setData(pieData)
            binding.CompetitionChart.invalidate()
    }

    private fun getdata1(pincode: String, selectedCategory: String) {

        val url = "http://www.postalpincode.in/api/pincode/$pincode"

        val queue = Volley.newRequestQueue(this)

        val objectRequest =
            JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {

                        val postOfficeArray = response!!.getJSONArray("PostOffice")
                        if (response!!.getString("Status") == "Error") {

                        } else {

                            val obj = postOfficeArray.getJSONObject(0)

                            val district = obj.getString("District")
                            binding.area1.text = district
                            comparisionPageViewModel.category1 = selectedCategory
                            val state = obj.getString("State")
                            val country = obj.getString("Country")
                            lifecycleScope.launch {

                                var result1 =
                                    comparisionPageViewModel.getData1(
                                        pincode,
                                        makeSlug(state).toString(),
                                        district.lowercase(),
                                        selectedCategory
                                    )
                                result1.observe(this@CompareActivity){
                                    when(it){
                                        is Response.Success -> {
                                            binding.data1 = it.data
                                            binding.progressBar.visibility = View.GONE
                                            it.data?.let { it1 -> showPieChart1(it1) }
                                        }
                                    }
                                }
                            }

                        }
                    } catch (e: JSONException) {

                        e.printStackTrace()
                    }
                }) { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        queue.add(objectRequest)
    }
}