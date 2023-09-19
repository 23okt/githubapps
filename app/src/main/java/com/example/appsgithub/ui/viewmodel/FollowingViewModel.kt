package com.example.appsgithub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsgithub.response.FollowingResponseItem
import com.example.appsgithub.retrofit.ApiConfig
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _following = MutableLiveData<List<FollowingResponseItem>>()
    val following: LiveData<List<FollowingResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        const val TAG = "FollowingViewModel"
    }

    fun getFollowing(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().following(username, ApiConfig.TOKEN)
        client.enqueue(object :Callback<List<FollowingResponseItem>>{
            override fun onResponse(
                call: retrofit2.Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isLoading.value =false
                if(response.isSuccessful){
                    _following.postValue(response.body())
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: retrofit2.Call<List<FollowingResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message.toString()}}")
            }
        })
    }
}
