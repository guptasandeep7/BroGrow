package com.example.brogrow.view.dashboard

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentHomePageBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONException
import org.json.JSONObject


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
                var okButton=dialodView.findViewById<Button>(R.id.PinCodeButton)
                okButton.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {

                    }
                })
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
        colors.add(Color.parseColor("#E272B6"))
        colors.add(Color.parseColor("#4FC4F5"))
        colors.add(Color.parseColor("#F0D64C"))
        colors.add(Color.parseColor("#DA7878"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#7F84EC"))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat()))
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
        pieData.setDrawValues(true);
        binding.CompetitionChart.setData(pieData)
        binding.CompetitionChart.invalidate()
    }
    private fun getDataFromPinCode(pinCode: String) {

        // clearing our cache of request queue.
//        mRequestQueue.cache.clear()

        // below is the url from where we will be getting
        // our response in the json format.
        val url = "http://www.postalpincode.in/api/pincode/$pinCode"

        // below line is use to initialize our request queue.
        val queue = Volley.newRequestQueue(requireContext())

        // in below line we are creating a
        // object request using volley.
        val objectRequest =
            JsonObjectRequest(Request.Method.GET, url, null, object : Response.Listener<JSONObject?> {

                override fun onResponse(response: JSONObject?) {
                    // inside this method we will get two methods
                    // such as on response method
                    // inside on response method we are extracting
                    // data from the json format.
                    try {
                        // we are getting data of post office
                        // in the form of JSON file.
                        val postOfficeArray = response!!.getJSONArray("PostOffice")
                        if (response!!.getString("Status") == "Error") {
                            // validating if the response status is success or failure.
                            // in this method the response status is having error and
                            // we are setting text to TextView as invalid pincode.
//                            pinCodeDetailsTV.text = "Pin code is not valid."
                        } else {
                            // if the status is success we are calling this method
                            // in which we are getting data from post office object
                            // here we are calling first object of our json array.
                            val obj = postOfficeArray.getJSONObject(0)

                            // inside our json array we are getting district name,
                            // state and country from our data.
                            val district = obj.getString("District")
                            val state = obj.getString("State")
                            val country = obj.getString("Country")

                            // after getting all data we are setting this data in
                            // our text view on below line.
//                            pinCodeDetailsTV.text = """
//                        Details of pin code is :
//                        District is : $district
//                        State : $state
//                        Country : $country
//                        """.trimIndent()
                        }
                    } catch (e: JSONException) {
                        // if we gets any error then it
                        // will be printed in log cat.
                        e.printStackTrace()
//                        pinCodeDetailsTV.text = "Pin code is not valid"
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    // below method is called if we get
                    // any error while fetching data from API.
                    // below line is use to display an error message.
                    Toast.makeText(requireContext(), "Pin code is not valid.", Toast.LENGTH_SHORT)
                        .show()

                }
            })
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest)
    }


}