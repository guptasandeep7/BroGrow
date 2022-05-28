package com.example.brogrow.network

import com.example.brogrow.model.UserResponse
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
}