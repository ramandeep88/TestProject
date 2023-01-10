package com.test.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.myapplication.model.DataList
import com.test.myapplication.network.DataApi
import com.test.myapplication.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val dataList = MutableLiveData<List<DataList>>()
    val errorMessage = MutableLiveData<String>()
    private val listApi = RetrofitHelper.getInstance().create(DataApi::class.java)

    fun getDataList(page:Int) {
        listApi.getDataList(page, 20).enqueue(object : Callback<List<DataList>> {
            override fun onResponse(call: Call<List<DataList>>, response: Response<List<DataList>>) {
                dataList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<DataList>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}
