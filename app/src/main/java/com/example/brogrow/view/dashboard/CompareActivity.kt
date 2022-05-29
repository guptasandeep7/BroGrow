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