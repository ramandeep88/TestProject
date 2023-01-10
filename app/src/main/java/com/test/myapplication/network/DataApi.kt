package com.test.myapplication.network

import com.test.myapplication.model.DataList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {
    @GET("/v2/list")
    fun getDataList(@Query("page") page: Int,
                    @Query("limit") limit: Int) : Call<List<DataList>>
}