package com.example.brogrow.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.brogrow.network.Response
import com.example.brogrow.utill.makeSlug
import com.example.brogrow.view.dashboard.ComparisonPage.Companion.selectedCategory
import com.example.brogrow.viewmodel.ComparisionPageViewModel
import kotlinx.coroutines.launch
import org.json.JSONException

class CompareActivity : AppCompatActivity() {
    private lateinit var comparisionPageViewModel: ComparisionPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCompareBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_compare)
        actionBar?.hide()

        getdata1(ComparisonPage.selectedPincode, selectedCategory)
        getdata2(ComparisonPage.selectedPincode2,ComparisonPage.selectedCategory2)

        comparisionPageViewModel = ViewModelProvider(this)[ComparisionPageViewModel::class.java]

        binding.area1.text = comparisionPageViewModel.district1.toString()
        binding.area2.text = comparisionPageViewModel.district2.toString()

        binding.category1.text = comparisionPageViewModel.category1.toString()
        binding.category2.text = comparisionPageViewModel.category2.toString()

        comparisionPageViewModel.result1.observe(this){
            when(it){
                is Response.Success -> {

                }
            }

        }

        comparisionPageViewModel.result2.observe(this){
            when(it){
                is Response.Success -> {
                    binding.data2 = it.data
                }
            }
        }




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
                            comparisionPageViewModel.district2 = district
                            comparisionPageViewModel.category2 = selectedCategory2
                            val state = obj.getString("State")
                            val country = obj.getString("Country")
                            lifecycleScope.launch {

                                comparisionPageViewModel.result2 =
                                    comparisionPageViewModel.getData2(
                                        pincode,
                                        makeSlug(state).toString(),
                                        district.lowercase(),
                                        selectedCategory2
                                    )
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
                            comparisionPageViewModel.district1 = district
                            comparisionPageViewModel.category1 = selectedCategory
                            val state = obj.getString("State")
                            val country = obj.getString("Country")
                            lifecycleScope.launch {

                                comparisionPageViewModel.result1 =
                                    comparisionPageViewModel.getData1(
                                        pincode,
                                        makeSlug(state).toString(),
                                        district.lowercase(),
                                        selectedCategory
                                    )
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