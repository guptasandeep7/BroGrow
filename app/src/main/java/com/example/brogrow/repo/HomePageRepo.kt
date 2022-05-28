package com.example.brogrow.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.brogrow.model.HomePageModel.HomePageModel
import com.example.brogrow.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageRepo(val Api:ApiInterface) {

    private val getHOmePAgeLiveData = MutableLiveData<com.example.brogrow.network.Response<HomePageModel>>()
    fun getStudentProfileApi(
        pincode:String,
        state:String,
        district:String,
        typeofbusinesss:String

    ): MutableLiveData<com.example.brogrow.network.Response<HomePageModel>> {
        val result =Api.homePage(pincode,state,district,typeofbusinesss)
       result.enqueue(object : Callback<HomePageModel?> {
           override fun onResponse(call: Call<HomePageModel?>, response: Response<HomePageModel?>) {
               Log.d("adsas","${pincode} ${state} ${district} ${typeofbusinesss} ${response.message()}")
               when
               {
                   response.isSuccessful->{getHOmePAgeLiveData.postValue(com.example.brogrow.network.Response.Success(response.body()))}
                   else->
                   {
                       getHOmePAgeLiveData.postValue(com.example.brogrow.network.Response.Error(response.message()))
                   }
               }
           }

           override fun onFailure(call: Call<HomePageModel?>, t: Throwable) {
               getHOmePAgeLiveData.postValue(com.example.brogrow.network.Response.Error(t.localizedMessage.toString()))
           }
       })
        return getHOmePAgeLiveData
    }
}