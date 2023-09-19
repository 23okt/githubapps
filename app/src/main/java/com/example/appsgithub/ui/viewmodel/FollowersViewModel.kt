package com.example.appsgithub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsgithub.response.FollowersResponseItem
import com.example.appsgithub.response.FollowingResponseItem
import com.example.appsgithub.response.UserResponse
import com.example.appsgithub.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    private val _listFollowers = MutableLiveData<List<FollowersResponseItem>>()
    val listFollowers:LiveData<List<FollowersResponseItem>> = _listFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    companion object{
        const val TAG = "FollowersViewModel"
    }
    fun getFollowUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().followers(username,ApiConfig.TOKEN)
        client.enqueue(object : Callback<List<FollowersResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listFollowers.postValue(response.body())
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}
