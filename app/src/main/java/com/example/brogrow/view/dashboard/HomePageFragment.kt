package com.example.brogrow.view.dashboard

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentHomePageBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomePageFragment : Fragment() {

    lateinit var  binding:FragmentHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home_page, container, false)

        binding.Location.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bottomSheet = BottomSheetDialog(requireContext())
                val dialodView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bottom_pincode, null)
                bottomSheet.setContentView(dialodView)
                bottomSheet.show()
            }
        })
        showPieChart()
        binding.Fashion.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bottomSheet = BottomSheetDialog(requireContext())
                val dialodView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bottom_fashion, null)
                bottomSheet.setContentView(dialodView)
                bottomSheet.show()
                var listview= dialodView.findViewById<ListView>(R.id.listview)
                var edittext=dialodView.findViewById<EditText>(R.id.edit_text)
                var arrayList=ArrayList<String>()
                arrayList.add("Accomdation")
                arrayList.add("Sandeep")
                var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayList)
                listview.adapter=adapter
                edittext.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        adapter.getFilter().filter(s);
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })
            }
        })
        return binding.root
    }

    private fun showPieChart() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Toys"] = 200
        typeAmountMap["Snacks"] = 230
        typeAmountMap["Clothes"] = 100
        typeAmountMap["Stationary"] = 500
        typeAmountMap["Phone"] = 50

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))
        colors.add(Color.parseColor("#476567"))
        colors.add(Color.parseColor("#890567"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#3ca567"))

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
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        binding.CompetitionChart.setData(pieData)
        binding.CompetitionChart.invalidate()
    }


}