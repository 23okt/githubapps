package com.example.appsgithub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsgithub.response.DetailResponse
import com.example.appsgithub.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:ViewModel() {
    companion object{
        const val TAG = "ProfileViewModel"
    }

    private val _profileUser = MutableLiveData<DetailResponse>()
    val profileUser: LiveData<DetailResponse> = _profileUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfileUser(user: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().profileUser(user,ApiConfig.TOKEN)
        client.enqueue(object: Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _profileUser.value = response.body()
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}" )
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}