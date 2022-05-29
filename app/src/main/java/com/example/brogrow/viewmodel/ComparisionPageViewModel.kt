package com.example.brogrow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brogrow.model.HomePageModel
import com.example.brogrow.network.Response
import com.example.brogrow.network.ServiceBuilder
import com.example.brogrow.repo.HomePageRepo

class ComparisionPageViewModel : ViewModel() {

    var result1 = MutableLiveData<Response<HomePageModel>>()
    var district1: String? = null
    var category1: String? = null

    var result2 = MutableLiveData<Response<HomePageModel>>()
    var district2: String? = null
    var category2: String? = null

    fun getData1(
        pincode: String,
        state: String,
        district: String,
        type: String
    ): MutableLiveData<Response<HomePageModel>> {
        val Api = ServiceBuilder.buildService()
        val homePageRepo = HomePageRepo(Api)
        result1 = homePageRepo.getStudentProfileApi(pincode, state, district, type)
        return result1
    }

    fun getData2(
        pincode: String,
        state: String,
        district: String,
        type: String
    ): MutableLiveData<Response<HomePageModel>> {
        val Api = ServiceBuilder.buildService()
        val homePageRepo = HomePageRepo(Api)
        result2 = homePageRepo.getStudentProfileApi(pincode, state, district, type)
        return result2
    }
}