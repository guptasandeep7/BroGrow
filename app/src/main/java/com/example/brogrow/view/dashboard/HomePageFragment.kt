package com.example.brogrow.view.dashboard

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.brogrow.R
import com.example.brogrow.auth.FirstFragment
import com.example.brogrow.databinding.FragmentHomePageBinding
import com.example.brogrow.model.HomePageModel
import com.example.brogrow.viewmodel.HomePageViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern


class HomePageFragment : Fragment() {

    lateinit var  binding:FragmentHomePageBinding
    lateinit var homePageViewModel:HomePageViewModel
    companion object
    {
        var result: HomePageModel?=null
    }

     var district:String=""
    var state:String=""
    var pincode:String=""
    var filter:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageViewModel=ViewModelProvider(this)[HomePageViewModel::class.java]
        pincode=FirstFragment.selectedPincode
        filter=FirstFragment.selectedCategory

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home_page, container, false)
        getDataFromPinCode(pincode)
        binding.Location.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bottomSheet = BottomSheetDialog(requireContext())
                val dialodView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bottom_pincode, null)
                var okButton=dialodView.findViewById<Button>(R.id.PinCodeButton)
                okButton.setOnClickListener(object : View.OnClickListener {
                    override  fun onClick(v: View?) {
                        bottomSheet.dismiss()
                        var pincodeBox=dialodView.findViewById<EditText>(R.id.textView2)
                        pincode=pincodeBox.text.toString()
                        getDataFromPinCode(pincode)
                    }
                })
                bottomSheet.setContentView(dialodView)
                bottomSheet.show()
            }
        })

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
                arrayList.add("Hospitality")
                arrayList.add("Logistics")
                arrayList.add("Banks")
                arrayList.add("Telecom")
                arrayList.add("Paper")
                arrayList.add("Media &amp; Entertainment")
                arrayList.add("Ship Building")
                arrayList.add("Automobile &amp; Ancillaries")
                arrayList.add("Electricals")
                arrayList.add("Metals &amp; Mining")
                arrayList.add("Footwear")
                arrayList.add("Software &amp; IT Services")
                arrayList.add("Insurance")
                arrayList.add("Aviation")
                arrayList.add("Infrastructure")
                arrayList.add("Containers &amp; Packaging")
                arrayList.add("Real Estate")
                arrayList.add("Construction Materials")
                arrayList.add("Photographic Products")
                arrayList.add("Diversified")
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
                listview.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.Fashion.text=adapter.getItem(position)
                        filter=adapter.getItem(position).toString()
                        getDataFromPinCode(pincode)
                        bottomSheet.dismiss()

                    }
                })
            }
        })


        binding.CatgoralAnalysis.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.categorialAnalysis)
            }
        })
        return binding.root
    }

    private fun showPieChart() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Float> = HashMap()

       for(i in 0 until result?.competitorAnalysis?.competitors!!.size)
       {
           var size= result?.competitorAnalysis?.competitors!!.size*100
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
    private fun getDataFromPinCode(pinCode: String) {
        val dialod1View =
            LayoutInflater.from(requireContext()).inflate(R.layout.loader, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(dialod1View)
        val alertDialog: AlertDialog = mBuilder.create()
        alertDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        alertDialog.show()
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
                             district = obj.getString("District")
                             state = obj.getString("State")
                            binding.Location.text=district.toString()
                            val country = obj.getString("Country")
                            lifecycleScope.launch {

                                 var result1 = homePageViewModel.getHomeLiveData(
                                    pinCode,
                                    makeSlug(state).toString(),
                                    district.toLowerCase(),
                                    filter
                                )
                                result1.observe(viewLifecycleOwner) {
                                    when (it) {
                                        is com.example.brogrow.network.Response.Success -> {
                                            result=it.data
                                            var opportunityrating= it.data!!.oppurtunityRating.rating.toInt()
                                            if(opportunityrating<25)
                                            {
                                                binding.OpportunitiesProgressBar.progressTintList=
                                                    ColorStateList.valueOf(Color.parseColor("#CF3939"))
                                                binding.textView4.setBackgroundColor(Color.parseColor("#CF3939"))
                                                binding.textView5.text="Very less Opprtunities"
                                            }
                                            else if(opportunityrating<50)
                                            {
                                                binding.OpportunitiesProgressBar.progressTintList=
                                                    ColorStateList.valueOf(Color.parseColor("#D0691F"))
                                                binding.textView4.setBackgroundColor(Color.parseColor("#D0691F"))
                                                binding.textView5.text="Average Opportunities"
                                            }
                                            else if(opportunityrating<75)
                                            {
                                                binding.OpportunitiesProgressBar.progressTintList=
                                                    ColorStateList.valueOf(Color.parseColor("#CFB739"))
                                                binding.textView4.setBackgroundColor(Color.parseColor("#CFB739"))
                                                binding.textView5.text="Moderate Opportunities"
                                            }
                                            else
                                            {
                                                binding.OpportunitiesProgressBar.progressTintList=
                                                    ColorStateList.valueOf(Color.parseColor("#43932F"))
                                                binding.textView4.setBackgroundColor(Color.parseColor("#43932F"))
                                            }
                                            showPieChart()

                                            binding.OpportunitiesPercentage.text= it.data!!.oppurtunityRating.rating.toString() + "%"
                                            binding.OpportunitiesProgressBar.progress =
                                                it.data?.oppurtunityRating?.rating?.toInt()!!
                                            alertDialog.dismiss()
                                        }

                                    }
                                }
                            }

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
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT)
                        .show()

                }
            })
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest)
    }
    fun makeSlug(input: String?): String? {
        var NONLATIN = Pattern.compile("[^\\w_-]");
        var SEPARATORS = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
        val noseparators: String = SEPARATORS.matcher(input).replaceAll("-")
        val normalized: String = Normalizer.normalize(noseparators, Normalizer.Form.NFD)
        val slug: String = NONLATIN.matcher(normalized).replaceAll("")
        return slug.lowercase(Locale.ENGLISH).replace("-{2,}".toRegex(), "-")
            .replace("^-|-$".toRegex(), "")
    }

}