package com.example.brogrow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brogrow.model.HomePageModel.HomePageModel
import com.example.brogrow.network.Response
import com.example.brogrow.network.ServiceBuilder
import com.example.brogrow.repo.HomePageRepo

class HomePageViewModel: ViewModel() {
     fun  getHomeLiveData(pincode:String,state:String,district:String,type:String): MutableLiveData<Response<HomePageModel>> {
        var Api=ServiceBuilder.buildService()
        var homePageRepo=HomePageRepo(Api)
        var result=homePageRepo.getStudentProfileApi(pincode,state,district,type)
        return result
    }
}