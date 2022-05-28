package com.example.brogrow.network

import com.example.brogrow.model.HomePageModel.HomePageModel
import com.example.brogrow.model.UserResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("/user-create/")
    fun createUser(
        @Field("name") name: String,
        @Field("mobile") mobile: String
    ): retrofit2.Call<UserResponse>

    @FormUrlEncoded
    @POST("/business/")
    fun homePage(
        @Field("pincode") pincode: String,
        @Field("state") state: String,
        @Field("district") district: String,
        @Field("typeofbusiness") typeofbusiness: String,
    ): Call<HomePageModel>

}